from boxdetect import config
from boxdetect.pipelines import get_boxes
import process
import cv2
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

image_path = 'rect_data/201992027.png'
image_path = image_process(image_path, "201992027.png")
image = cv2.imread(image_path)
# rotate_img(image_path)
rects, grouping_rects, _, output_image = get_boxes(image_path, cfg=cfg, plot=False)
rects = sorted(rects, key=(lambda x: x[1]))

# rects中的元素存放的是每个矩形框左上角的x,y,宽度，高度
print("rects:", rects)
print("rects_len:", len(rects))

# 存储第一行的矩形框信息
first_line = []
# 选择题第一题题号的矩形框，因为这个框在边角上，一定可以定位得到
standard_rect = []

for i in range(0, len(rects) - 1):
    if rects[i][2] > 50 and rects[i][3] > 40:
        if rects[i+1][1]-rects[i][1] < 13:  # 未发生换行
            first_line.append(rects[i])
        else:
            break
print("first_line:", first_line)
print("len(first_line):", len(first_line))
first_line = sorted(first_line, key=(lambda x: x[0]))

standard_rect = [first_line[0][0], first_line[0][1], first_line[0][2], first_line[0][3]]
print("standard_rect:", standard_rect)
# ----------------------------------------------------------------------------------------------------------------------
# 将框的左上角坐标和右下角坐标以及宽度，高度放在list_1中
# ----------------------------------------------------------------------------------------------------------------------

# list_1存放每个矩形框的左上角x,y坐标以及右下角x,y坐标
list_1 = []
for rect in rects:
    if rect[2] > 50 and rect[3] > 40:
        x1 = rect[0]
        y1 = rect[1]
        x2 = rect[0] + rect[2]
        y2 = rect[1] + rect[3]
        width = rect[2]
        height = rect[3]
        list_1.append([x1, y1, x2, y2, width, height])
        # cv2.rectangle(image, (x1, y1), (x2, y2), (0, 255, 0), 3)
        # cv2.namedWindow('image', 0)
        # cv2.imshow('image', image)
        # cv2.waitKey(0)

# ----------------------------------------------------------------------------------------------------------------------
# 对y1处理，从小到大的顺序排列
# ----------------------------------------------------------------------------------------------------------------------

list_1 = sorted(list_1, key=(lambda x: x[1]))
print(list_1)

# ----------------------------------------------------------------------------------------------------------------------
# list_2存放发生换行矩形框的序号
# ----------------------------------------------------------------------------------------------------------------------

list_2 = []
for i in range(0, len(list_1) - 1):
    if (abs(list_1[i + 1][1] - list_1[i][1])) > list_1[i][5]/3:  # 当发现高度差超过了1/3个答题框高度时，第i+1个矩形框发生换行
        list_2.append(i + 1)

print(list_2)

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

print("检测前")
print(question_1)
print(len(question_1))
print(question_2)
print(len(question_2))

# ----------------------------------------------------------------------------------------------------------------------
# 检测是否所有的矩形框都识别成功，如果有识别失败的则需要添加进去
# ----------------------------------------------------------------------------------------------------------------------

# 检测选择题是否齐全
if len(question_1) < 10:
    count = 10 - len(question_1)
    while count > 0:
        print(count)
        # 第一个识别不出来，要根据第一题题号的答题框standard_rect来补全
        if question_1[0][0] - standard_rect[0] > question_1[0][4]*0.7:
            x1 = standard_rect[0]
            y1 = standard_rect[1] + standard_rect[3]
            x2 = x1 + question_1[0][4]
            y2 = y1 + question_1[0][5]
            width = question_1[0][4]
            height = question_1[0][5]
            question_1.append([x1, y1, x2, y2, width, height])
            count -= 1
            question_1 = sorted(question_1, key=(lambda x: x[0]))
            print("添加第一个选择题框")
        # 遍历识别
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
                print("添加中间选择题框")
                count -= 1
                question_1 = sorted(question_1, key=(lambda x: x[0]))
                if count <= 0:
                    break
        # 最后的选择题框缺失，进行补全
        while count > 0:
            index = len(question_1)-1
            x1 = question_1[index][0] + question_1[index][4]
            y1 = question_1[index][1]
            x2 = x1 + question_1[index][4]
            y2 = y1 + question_1[index][5]
            width = question_1[index][4]
            height = question_1[index][5]
            question_1.append([x1, y1, x2, y2, width, height])
            count -= 1
            question_1 = sorted(question_1, key=(lambda x: x[0]))
            print("添加最后一个选择题框")

# 检测判断题是否齐全
if len(question_2) < 5:
    count = 5 - len(question_2)
    while count > 0:
        print(count)
        # 判断题第一个框识别不出来，看左上角点的横坐标
        if question_2[0][0] - question_1[0][0] > question_2[0][4] * 0.4:
            x1 = question_1[0][0]
            y1 = question_2[0][1]
            x2 = question_1[0][0] + question_2[0][4]
            y2 = question_2[0][1] + question_2[0][5]
            width = question_2[0][4]
            height = question_2[0][5]
            question_2.append([x1, y1, x2, y2, width, height])
            count -= 1
            print("添加第一个判断题框")
            question_2 = sorted(question_2, key=(lambda x: x[0]))

        # 判断题最后一个框识别不出来，看右下角点的横坐标
        if question_1[len(question_1) - 1][2] - question_2[len(question_2) - 1][2] > question_2[len(question_2) - 1][
            4] * 0.4:
            x2 = question_1[len(question_1) - 1][2]
            y2 = question_2[len(question_2) - 1][3]
            x1 = x2 - question_2[len(question_2) - 1][4]
            y1 = y2 - question_2[len(question_2) - 1][5]
            width = question_2[len(question_2) - 1][4]
            height = question_2[len(question_2) - 1][5]
            question_2.append([x1, y1, x2, y2, width, height])
            count -= 1
            print("添加最后一个判断题框")
            question_2 = sorted(question_2, key=(lambda x: x[0]))
            print(question_2)

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
                print("添加中间判断题框")
                count -= 1
                question_2 = sorted(question_2, key=(lambda x: x[0]))
                if count <= 0:
                    break

question_1 = sorted(question_1, key=(lambda x: x[0]))
question_2 = sorted(question_2, key=(lambda x: x[0]))

# 检测后
print("检测后")
print(question_1)
print(len(question_1))
print(question_2)
print(len(question_2))
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

    roi = image[y1 + cut_height:y2 - cut_height, x1 + cut_width:x2 - cut_width]  # 裁去边角料，防止边线干扰识别结果

    # cv2.namedWindow('roi', 0)
    # cv2.imshow('roi', roi)
    # cv2.waitKey(0)

    predict = process.measure_choose(roi)
    cv2.putText(image, str(predict), (x1, y1 - 10), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 0, 255), 2)
    result_1.append(predict)

    cv2.rectangle(image, (x1, y1), (x2, y2), (0, 0, 255), 1)
    cv2.namedWindow('image', 0)
    cv2.imshow('image', image)
    cv2.waitKey(0)

print(result_1)

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

    # cv2.namedWindow('roi', 0)
    # cv2.imshow('roi', roi)
    # cv2.waitKey(0)

    predict = process.measure_judge(roi)
    cv2.putText(image, str(predict), (x1, y1 - 10), cv2.FONT_HERSHEY_COMPLEX, 1, (0, 0, 255), 2)
    result_2.append(predict)

    cv2.rectangle(image, (x1, y1), (x2, y2), (0, 0, 255), 1)
    cv2.namedWindow('image', 0)
    cv2.imshow('image', image)
    cv2.waitKey(0)

print(result_2)

# ----------------------------------------------------------------------------------------------------------------------

cv2.imwrite('single_test/test.jpg', image)

# ----------------------------------------------------------------------------------------------------------------------
