import cv2
import numpy as np
import torch
from PIL import Image
from torchvision import transforms
from sys import argv


def getRedParts(image):
    # src = cv2.imdecode(np.fromfile(image, dtype=np.uint8), cv2.IMREAD_COLOR)
    src = cv2.imread(image)
    hsv = cv2.cvtColor(src, cv2.COLOR_BGR2HSV)

    # only read the red parts
    lowHsv = np.array([156, 43, 46])
    highHsv = np.array([180, 255, 255])
    redParts = cv2.inRange(hsv, lowerb=lowHsv, upperb=highHsv)
    # plt.imshow(redParts)
    # plt.show()

    return redParts


def wordSegment(image):
    image = cv2.bitwise_not(image)
    wordImageList = []
    # gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    th, threshed = cv2.threshold(image, 100, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    # dilate twice
    finalThr = cv2.dilate(threshed, None, iterations=2)

    contours, hierarchy = cv2.findContours(finalThr, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    boundingBoxes = [cv2.boundingRect(c) for c in contours]
    (contours, boundingBoxes) = zip(*sorted(zip(contours, boundingBoxes), key=lambda b: b[1][0], reverse=False))

    for cnt in contours:
        area = cv2.contourArea(cnt)

        # the minimum of area of a word
        if area > 180:
            x, y, w, h = cv2.boundingRect(cnt)
            letterBgr = image[0:image.shape[1], x:x + w]
            wordImageList.append(letterBgr)
            # plt.imshow(letterBgr)
            # plt.show()

    if len(wordImageList) == 0:
        print("未识别出任何字符")
        wordImageList.append(image)
        # plt.imshow(image)
        # plt.show()

    if len(wordImageList) == 1:
        wordImageList.clear()
        wordImageList.append(image)

    return wordImageList


def noiseReduce(image):
    th, threshed = cv2.threshold(image, 100, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    image = cv2.dilate(threshed, None, iterations=3)
    _, binary = cv2.threshold(image, 0.1, 1, cv2.THRESH_BINARY)
    contours, hierarch = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
    for i in range(len(contours)):
        area = cv2.contourArea(contours[i])
        if area < 100:
            cv2.drawContours(image, [contours[i]], 0, 0, -1)
    image = cv2.erode(image, None, iterations=1)
    # plt.imshow(image)
    # plt.show()
    return image


def recognize(wordImageList):
    list = []
    for image in wordImageList:
        image = noiseReduce(image)
        imageData = Image.fromarray(image)
        # imageData = PIL.ImageOps.invert(imageData)

        w, h = imageData.size
        background = Image.new('L', size=(max(w, h), max(w, h)))
        length = int(abs(w - h) // 2)
        box = (length, 0) if w < h else (0, length)
        background.paste(imageData, box)
        # plt.imshow(background)
        # plt.show()
        imageData = background.resize((28, 28))

        # imageData = PIL.ImageOps.invert(imageData)
        #
        # imageData = imageData.resize((28, 28))
        # plt.imshow(imageData)
        # plt.show()
        trans = transforms.Compose(
            [
                transforms.ToTensor(),
                transforms.Normalize((0.1307,), (0.3081,)),
            ]
        )
        data = trans(imageData).unsqueeze(0)
        data = data.to("cpu", torch.float)

        model = torch.load("MNISTModel.pt")
        model.eval()
        outputs = model(data)
        # print(outputs)
        _, preds = torch.max(outputs, dim=1)
        # print(preds.tolist()[0])
        list.append(preds.tolist()[0])
    return list


def main():
    array = recognize(wordSegment(getRedParts(argv[1])))
    number = ""
    for element in array:
        number = str(number) + str(element)
    print(number)


if __name__ == '__main__':
    main()
