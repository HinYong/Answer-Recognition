# 检测文件夹下的所有答题卡
from boxdetect import config
from boxdetect.pipelines import get_boxes
import process
import cv2
import os
import csv
from image_process import image_process

# ----------------------------------------------------------------------------------------------------------------------

cfg = config.PipelinesConfig()
cfg.width_range = (20, 500)
cfg.height_range = (20, 500)
cfg.scaling_factors = [1.0]
cfg.wh_ratio_range = (0.2, 20)
cfg.group_size_range = (2, 500)
cfg.dilation_iterations = 0

# ----------------------------------------------------------------------------------------------------------------------

# result_1存放选择题答案，result_2存放判断题答案
result_1 = []
result_2 = []

# 标准答案与成绩
answer1 = 'BACBDACDBC'
answer2 = 'TFTTF'
grade = 0

answer1 = answer1.upper()
answer2 = answer2.upper()


question1_num = len(answer1)
question2_num = len(answer2)

# print(question1_num, question2_num)

# CSV文件相关
headers = ['学号', '选择题答案', '判断题答案', '分数']
rows = []

destdir = './rect_data'
dir = os.listdir(destdir)
for file in dir:
    if file.endswith('JPG') or file.endswith('PNG') or file.endswith('png') or file.endswith('jpg'):
        image_path = './rect_data/' + file
        image_path = image_process(image_path, file)
        image = cv2.imread(image_path)

        grade = 0

        # rects中的元素存放的是每个矩形框左上角的x,y,宽度，高度
        rects, grouping_rects, _, output_image = get_boxes(image_path, cfg=cfg, plot=False)
        rects = sorted(rects, key=(lambda x: x[1]))
        # print(rects)
        # print(len(rects))

        # 存储第一行的矩形框信息
        first_line = []
        # 选择题第一题题号的矩形框，因为这个框在边角上，一定可以定位得到，防止出现选择题第一题定位不到的情况
        standard_rect = []

        for i in range(0, len(rects) - 1):
            if rects[i][2] > 50 and rects[i][3] > 40:  # 防止识别到其他的矩形
                if rects[i + 1][1] - rects[i][1] < 13:  # 未发生换行
                    first_line.append(rects[i])
                else:
                    break

        # print("first_line:", first_line)
        # print("len(first_line):", len(first_line))
        first_line = sorted(first_line, key=(lambda x: x[0]))

        standard_rect = [first_line[0][0], first_line[0][1], first_line[0][2], first_line[0][3]]
        # print("standard_rect:", standard_rect)

        # ----------------------------------------------------------------------------------------------------------------------
        # 将框的左上角坐标和右下角坐标以及宽度，高度放在list_1中
        # ----------------------------------------------------------------------------------------------------------------------

        # list_1存放每个矩形框的左上角x,y坐标以及右下角x,y坐标
        list_1 = []
        for rect in rects:
            if rect[0] > 20 and rect[1] > 30:
                x1 = rect[0]
                y1 = rect[1]
                x2 = rect[0] + rect[2]
                y2 = rect[1] + rect[3]
                width = rect[2]
                height = rect[3]
                list_1.append([x1, y1, x2, y2, width, height])

        # ----------------------------------------------------------------------------------------------------------------------
        # 对y1处理，从小到大的顺序排列
        # ----------------------------------------------------------------------------------------------------------------------

        list_1 = sorted(list_1, key=(lambda x: x[1]))
        # print(list_1)

        # ----------------------------------------------------------------------------------------------------------------------
        # list_2存放发生换行矩形框的序号
        # ----------------------------------------------------------------------------------------------------------------------

        list_2 = []
        for i in range(0, len(list_1) - 1):
            if (abs(list_1[i + 1][1] - list_1[i][1])) > list_1[i][5] / 3:  # 当发现高度差超过了1/3个答题框高度时，第i+1个矩形框发生换行
                list_2.append(i + 1)

        # print(list_2)

        # ----------------------------------------------------------------------------------------------------------------------
        # 分题目
        # ----------------------------------------------------------------------------------------------------------------------

        question_1 = []
        question_2 = []

        for i in range(0, len(list_1)):
            # 选择题的矩形框信息加入question_1
            if list_2[0] <= i < list_2[1]:
                question_1.append(list_1[i])
            # 判断题的矩形框信息加入question_2
            elif i >= list_2[2]:
                question_2.append(list_1[i])

        # ----------------------------------------------------------------------------------------------------------------------
        # 对x1处理，从小到大的顺序排列
        # ----------------------------------------------------------------------------------------------------------------------

        question_1 = sorted(question_1, key=(lambda x: x[0]))
        question_2 = sorted(question_2, key=(lambda x: x[0]))

        # print("检测前")
        # print(question_1)
        # print(len(question_1))
        # print(question_2)
        # print(len(question_2))

        # ----------------------------------------------------------------------------------------------------------------------
        # 检测是否所有的矩形框都识别成功，如果有识别失败的则需要添加进去
        # ----------------------------------------------------------------------------------------------------------------------

        # 检测选择题是否齐全
        if len(question_1) < question1_num:
            count = question1_num - len(question_1)
            while count > 0:
                # print(count)
                # 第一个识别不出来，要根据第一题题号的答题框standard_rect来补全
                if question_1[0][0] - standard_rect[0] > question_1[0][4] * 0.7:
                    x1 = standard_rect[0]
                    y1 = standard_rect[1] + standard_rect[3]
                    x2 = x1 + question_1[0][4]
                    y2 = y1 + question_1[0][5]
                    width = question_1[0][4]
                    height = question_1[0][5]
                    question_1.append([x1, y1, x2, y2, width, height])
                    count -= 1
                    question_1 = sorted(question_1, key=(lambda x: x[0]))
                    # print("添加第一个选择题框")

                # 遍历识别，添加中间缺失的答题框
                for i in range(0, 10 - count - 1):  # 当前列表中有的元素个数-2,因为最后一个的下标为元素个数-1，而只需要遍历到倒数第二个，所以-2
                    # print(abs(question_2[i][0]-question_2[i+1][0]))
                    if abs(question_1[i][0] - question_1[i + 1][0]) > question_1[i][4] * 1.4:
                        x1 = question_1[i][0] + question_1[i][4]
                        y1 = question_1[i][1]
                        x2 = question_1[i][2] + question_1[i][4]
                        y2 = question_1[i][3]
                        width = question_1[i][4]
                        height = question_1[i][5]
                        question_1.append([x1, y1, x2, y2, width, height])
                        # print("添加中间选择题框")
                        count -= 1
                        question_1 = sorted(question_1, key=(lambda x: x[0]))
                        if count <= 0:
                            break

                # 最后的选择题框缺失，进行补全，向后循环添加，要基于当前的最后一个答题框的位置信息进行计算
                while count > 0:
                    index = len(question_1) - 1
                    x1 = question_1[index][0] + question_1[index][4]
                    y1 = question_1[index][1]
                    x2 = x1 + question_1[index][4]
                    y2 = y1 + question_1[index][5]
                    width = question_1[index][4]
                    height = question_1[index][5]
                    question_1.append([x1, y1, x2, y2, width, height])
                    count -= 1
                    question_1 = sorted(question_1, key=(lambda x: x[0]))
                    # print("添加最后一个选择题框")

        # 检测判断题是否齐全
        if len(question_2) < question2_num:
            count = question2_num - len(question_2)
            while count > 0:
                # print(count)
                # 判断题第一个框识别不出来，根据第一个选择题答题框的横坐标进行添加
                if question_2[0][0] - question_1[0][0] > question_2[0][4] * 0.4:
                    x1 = question_1[0][0]
                    y1 = question_2[0][1]
                    x2 = question_1[0][0] + question_2[0][4]
                    y2 = question_2[0][1] + question_2[0][5]
                    width = question_2[0][4]
                    height = question_2[0][5]
                    question_2.append([x1, y1, x2, y2, width, height])
                    count -= 1
                    # print("添加第一个判断题框")
                    question_2 = sorted(question_2, key=(lambda x: x[0]))

                # 判断题最后一个框识别不出来，看右下角点的横坐标
                if question_1[len(question_1) - 1][2] - question_2[len(question_2) - 1][2] > \
                        question_2[len(question_2) - 1][4] * 0.4:
                    x2 = question_1[len(question_1) - 1][2]
                    y2 = question_2[len(question_2) - 1][3]
                    x1 = x2 - question_2[len(question_2) - 1][4]
                    y1 = y2 - question_2[len(question_2) - 1][5]
                    width = question_2[len(question_2) - 1][4]
                    height = question_2[len(question_2) - 1][5]
                    question_2.append([x1, y1, x2, y2, width, height])
                    count -= 1
                    # print("添加最后一个判断题框")
                    question_2 = sorted(question_2, key=(lambda x: x[0]))
                    # print(question_2)

                # 前后都检验完成如果还存在识别不出来的，则需要遍历识别
                for i in range(0, 5 - count - 1):  # 当前列表中有的元素个数-2,因为最后一个的下标为元素个数-1，而只需要遍历到倒数第二个，所以-2
                    # print(abs(question_2[i][0]-question_2[i+1][0]))
                    if abs(question_2[i][0] - question_2[i + 1][0]) > question_2[i][4] * 1.4:
                        x1 = question_2[i][0] + question_2[i][4]
                        y1 = question_2[i][1]
                        x2 = question_2[i][2] + question_2[i][4]
                        y2 = question_2[i][3]
                        width = question_2[i][4]
                        height = question_2[i][5]
                        question_2.append([x1, y1, x2, y2, width, height])
                        # print("添加中间判断题框")
                        count -= 1
                        question_2 = sorted(question_2, key=(lambda x: x[0]))
                        if count <= 0:
                            break

        question_1 = sorted(question_1, key=(lambda x: x[0]))
        question_2 = sorted(question_2, key=(lambda x: x[0]))

        # 检测后
        # print("检测后")
        # print(question_1)
        # print(len(question_1))
        # print(question_2)
        # print(len(question_2))

        # ----------------------------------------------------------------------------------------------------------------------
        # 识别选择题部分
        # ----------------------------------------------------------------------------------------------------------------------

        result_1 = []
        for i in range(0, len(question_1)):
            x1 = question_1[i][0]
            y1 = question_1[i][1]
            x2 = question_1[i][2]
            y2 = question_1[i][3]
            cut_width = int(question_1[i][4] / 50)
            cut_height = int(question_1[i][5] / 30)
            # roi区域为截取的矩形区域
            roi = image[y1 + cut_height:y2 - cut_height, x1 + cut_width:x2 - cut_width]  # 裁去边角料，防止边线干扰识别结果

            # 图像处理以及识别得到结果
            predict = process.measure_choose(roi)
            # 答案对比，选择题一个5分
            if (predict == answer1[i]):
                grade += 5
            result_1.append(predict)

        print(''.join(result_1))

        # ----------------------------------------------------------------------------------------------------------------------
        # 识别判断题部分
        # ----------------------------------------------------------------------------------------------------------------------

        result_2 = []
        for i in range(0, len(question_2)):
            x1 = question_2[i][0]
            y1 = question_2[i][1]
            x2 = question_2[i][2]
            y2 = question_2[i][3]
            cut_width = int(question_2[i][4] / 50)
            cut_height = int(question_2[i][5] / 30)

            roi = image[y1 + cut_height:y2 - cut_height, x1 + cut_width:x2 - cut_width]  # 裁去边角料裁去边角料，防止边线干扰识别结果

            predict = process.measure_judge(roi)
            # 答案对比，判断题一个3分
            if (predict == answer2[i]):
                grade += 3
            result_2.append(predict)

        print(''.join(result_2))

        print(file[0:9] + '得分为:', grade)
        tup = (file[0:9], ''.join(result_1), ''.join(result_2), grade)
        rows.append(tup)

        # ----------------------------------------------------------------------------------------------------------------------

with open('score.csv', 'w', encoding='GBK', newline='') as f:
    writer = csv.writer(f)
    writer.writerow(headers)
    writer.writerows(rows)
