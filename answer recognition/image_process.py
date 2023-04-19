import cv2
from imutils.perspective import four_point_transform


def image_process(image_path, file):
    image = cv2.imread(image_path)
    # cv2.namedWindow('origin', 0)
    # cv2.imshow('origin', image)
    # cv2.waitKey(0)
    orig = image.copy()  # copy()不对原图改变
    # w,h缩小为原来5倍
    # resize_image = cv2.resize(orig, None, fx=0.2, fy=0.2)

    # 预处理操作
    # 灰度化
    # gray = cv2.cvtColor(resize_image, cv2.COLOR_BGR2GRAY)
    gray = cv2.cvtColor(orig, cv2.COLOR_BGR2GRAY)
    # 高斯模糊
    gray = cv2.GaussianBlur(gray, (5, 5), 0)

    edged = cv2.Canny(gray, 75, 200)

    orig = image.copy()  # copy()不对原图改变

    # 轮廓检测
    cnts, hierancy = cv2.findContours(edged.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    # 找出图像中的轮廓
    cnts = sorted(cnts, key=cv2.contourArea, reverse=True)[:3]  # 将轮廓按照面积的大小进行排序，并且选出前3个最大的轮廓
    # print('cnts', cnts[0])

    for c in cnts:
        peri = cv2.arcLength(c, True)  # 计算封闭轮廓的周长
        area = cv2.contourArea(c, True)
        # print(area)
        # print(peri)
        # cv2.drawContours(resize_image, c, -1, (0, 0, 255), 3)
        # cv2.namedWindow('image', 0)
        # cv2.imshow('image', resize_image)
        # cv2.waitKey(0)
        # print("---------------------")
        # 参数1是轮廓；参数2是轮廓两个点之间的距离值，用于精度的控制；True表示闭合。返回值为角点的个数
        approx = cv2.approxPolyDP(c, 0.02 * peri, True)  # 检测出来的轮廓可能是离散的点，故因在此做近似计算，使其形成一个矩形
        # cv2.polylines(orig, [approx], True, (0, 255, 0), 2)
        # cv2.namedWindow('image', 0)
        # cv2.imshow('image', orig)
        # cv2.waitKey(0)
        if len(approx) == 4:  # 如果检测出来的是矩形，则赋值
            screenCnt = approx

    # 透视变换：摆正图像内容
    # 透视变换摆正图像时候直接乘以1/fx即可（此处5倍）
    # wraped = four_point_transform(orig, screenCnt.reshape(4, 2) * 5)
    wraped = four_point_transform(orig, screenCnt.reshape(4, 2))
    # cv2.namedWindow('wrap', 0)
    # cv2.imshow('wrap', wraped)
    # cv2.waitKey(0)
    # print(file)

    cv2.imwrite('./processed_data/'+file, wraped)
    return './processed_data/'+file


if __name__ == "__main__":
    print(image_process("rect_data/201992007.png", "201992007.png"))
