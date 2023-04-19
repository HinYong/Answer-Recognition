import cv2
from imutils.perspective import four_point_transform


def image_process(image_path, file):
    image = cv2.imread(image_path)
    # cv2.namedWindow('origin', 0)
    # cv2.imshow('origin', image)
    # cv2.waitKey(0)
    orig = image.copy()  # copy()不对原图改变
    # w,h缩小为原来5倍
    resize_image = cv2.resize(orig, None, fx=0.2, fy=0.2)

    # 预处理操作
    # 灰度化
    gray = cv2.cvtColor(resize_image, cv2.COLOR_BGR2GRAY)
    # 高斯模糊
    gray = cv2.GaussianBlur(gray, (5, 5), 0)

    edged = cv2.Canny(gray, 75, 200)

    # 轮廓检测
    cnts, hierancy = cv2.findContours(edged.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    # 找出图像中的轮廓
    cnts = sorted(cnts, key=cv2.contourArea, reverse=True)[:3]  # 将轮廓按照面积的大小进行排序，并且选出前3个中最大的轮廓
    # print('cnts', len(cnts))

    for c in cnts:
        peri = cv2.arcLength(c, True)  # 周长，闭合
        # print(peri)
        # 参数1是轮廓；参数2是轮廓两个点之间的距离值，用于精度的控制；True表示闭合。返回值为角点的个数
        approx = cv2.approxPolyDP(c, 0.02 * peri, True)  # 检测出来的轮廓可能是离散的点，故因在此做近似计算，使其形成一个矩形
        if len(approx) == 4:  # 如果检测出来的是矩形，则赋值
            screenCnt = approx

    # 透视变换：摆正图像内容
    # 透视变换摆正图像时候直接乘以1/fx即可（此处5倍）
    wraped = four_point_transform(orig, screenCnt.reshape(4, 2) * 5)
    # cv2.namedWindow('wrap', 0)
    # cv2.imshow('wrap', wraped)
    # cv2.waitKey(0)
    # print(file)

    cv2.imwrite('C:/Answers/processed_answers/'+file, wraped)
    return 'C:/Answers/processed_answers/'+file
