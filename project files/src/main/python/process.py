# 图像处理以及识别

import tensorflow as tf
import numpy as np
import cv2

choose_model = tf.keras.models.load_model('model_abcd.h5')  # 加载模型
judge_model = tf.keras.models.load_model('model_ft.h5')  # 加载模型
choose_classes = ['A', 'B', 'C', 'D']
judge_classes = ['F', 'T']


def measure_choose(image):
    # cv2.GaussianBlur 高斯模糊进行平滑处理，降低噪点
    blur = cv2.GaussianBlur(image, (5, 5), 0)
    # cv2.cvtColor 灰度化
    gray = cv2.cvtColor(blur, cv2.COLOR_RGB2GRAY)
    # cv2.threshold 将图片二值化，加快图像处理速度，对灰度图像操作
    # 第一个参数原图像，第二个参数进行分类的阈值，第三个是参数高于（低于）阈值时赋予的新值，第四个参数是二值化的方法
    _, out = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    ret, thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    # 对二值化图像操作，cv2.getStructuringElement 设置一个5*5的矩形
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
    # cv2.dilate 使用5*5的核对于图像整体做膨胀操作
    erode = cv2.dilate(thresh, kernel)
    # cv2.findContours 检测轮廓 只接受二值化图像
    # 第一个参数：输入图像；第二个参数：轮廓的模式，这里只检测外轮廓；第三个参数：轮廓的近似方法。
    # contours是返回的轮廓，hierarchy为每条轮廓对应的属性
    contours, hierarchy = cv2.findContours(erode, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    predict = 'K'

    for i, contour in enumerate(contours):
        # 得到轮廓的外包矩形的四个值，左上角的横、纵坐标，矩形的宽度与高度
        x, y, w, h = cv2.boundingRect(contour)
        # print(w, h)
        if w > 17 and h > 20:  # 轮廓过滤
            cv2.rectangle(image, (x, y), (x + w, y + h), (0, 255, 0), 2)
            hcenter = x + w / 2
            vcenter = y + h / 2
            # print(x, y, w, h, hcenter, vcenter)
            if h > w:
                w = h
                x = hcenter - (w / 2)
            else:
                h = w
                y = vcenter - (h / 2)
            if (x <= 0):
                x -= 2 * x
            if (y <= 0):
                y -= 2 * y
            # print(x, y, w, h, hcenter, vcenter)
            result = out[int(y):int(y + h), int(x):int(x + w)]

            image = cv2.resize(result, (32, 32))
            # cv2.namedWindow('p_image', 0)
            # cv2.imshow('p_image', image)
            # cv2.waitKey(0)
            x = np.array([np.reshape(image, (32, 32, 1))]) / 255
            # print(choose_model.predict(x)[0])
            predict = np.argmax(choose_model.predict(x)[0])  # 返回最大值的索引
            predict = choose_classes[predict]  # 取出对应的字母
            # print(predict)

    # print("------------------------")
    return predict

def measure_judge(image):
    # cv2.GaussianBlur 高斯模糊进行平滑处理，降低噪点
    blur = cv2.GaussianBlur(image, (5, 5), 0)
    # cv2.cvtColor 灰度化
    gray = cv2.cvtColor(blur, cv2.COLOR_RGB2GRAY)
    # cv2.threshold 将图片二值化，加快图像处理速度，对灰度图像操作
    # 第一个参数原图像，第二个参数进行分类的阈值，第三个是参数高于（低于）阈值时赋予的新值，第四个参数是二值化的方法
    _, out = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    ret, thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    # 对二值化图像操作，cv2.getStructuringElement 设置一个5*5的矩形
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
    # cv2.dilate 使用5*5的核对于图像整体做膨胀操作
    erode = cv2.dilate(thresh, kernel)
    # cv2.findContours 检测轮廓 只接受二值化图像
    # 第一个参数：输入图像；第二个参数：轮廓的模式，这里只检测外轮廓；第三个参数：轮廓的近似方法。
    # contours是返回的轮廓，hierarchy为每条轮廓对应的属性
    contours, hierarchy = cv2.findContours(erode, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    predict = 'K'

    for i, contour in enumerate(contours):
        # 得到轮廓的外包矩形的四个值，左上角的横、纵坐标，矩形的宽度与高度
        x, y, w, h = cv2.boundingRect(contour)
        # print(w, h)
        # 对轮廓进行过滤
        if w > 15 and h > 20:  # 轮廓过滤
            # 描绘出轮廓边框
            cv2.rectangle(image, (x, y), (x + w, y + h), (0, 255, 0), 1)
            hcenter = x + w / 2
            vcenter = y + h / 2
            # print(x, y, w, h, hcenter, vcenter)
            if h > w:
                w = h
                x = hcenter - (w / 2)
            else:
                h = w
                y = vcenter - (h / 2)
            if (x <= 0):
                x -= 1.5 * x
            if (y <= 0):
                y -= 1.5 * y
            # print(x, y, w, h, hcenter, vcenter)
            result = out[int(y):int(y + h), int(x):int(x + w)]

            image = cv2.resize(result, (32, 32))
            # cv2.namedWindow('p_image', 0)
            # cv2.imshow('p_image', image)
            # cv2.waitKey(0)
            x = np.array([np.reshape(image, (32, 32, 1))]) / 255
            # print(judge_model.predict(x)[0])
            predict = np.argmax(judge_model.predict(x)[0])  # 返回最大值的索引
            predict = judge_classes[predict]  # 取出对应的字母
            # print(predict)

    # print(predict)
    # print("------------------------")

    return predict