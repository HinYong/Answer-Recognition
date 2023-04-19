new_answer.zip为测试上传所用的压缩包；

python部分：
answer_recognition文件夹是python工程文件夹，打开后根据提示需要安装相应的库；
loop_detection.py用于遍历识别文件夹中的答题卡图像；
EMNIST_letters_extract.py用于提取数据集；
image_process.py用于透视变换进行图像的矫正；
process.py用于对提取的答题框内的图像进行识别并且返回相应的结果；
train_abcd.py用于训练检测abcd的模型；
train_ft.py用于训练检测ft的模型；
test_acc.py用于检测模型的准确率；
rect_data文件夹下存放测试用的答题卡；
processed_data文件夹下存放经过矫正处理后的答题卡；

数据库创建与初始化：需要先运行createdatabase.sql在本地完成数据库的创建；

java部分：
project files文件夹是java工程文件夹，用IDEA打开后运行LmsSpringServerApplication，用教师账号进行登录，进入“考试信息”页面，输入选择题标准答案以及判断题标准答案，并且选择压缩文件new_answer.zip，点击上传，识别完成后能够下载csv文件。

用例说明：
标准答案： 选择题：BACBDACDBC     判断题：TFTTF
评分标准：选择题一个5分，判断题一个3分
应得成绩：
     学号          细节           选择题             判断题       总分数
201992007   40+12   BACBCACDBD       TFTFF  	  52
201992027   40+9     BDCBDAADBC       FTTTF   	  49
201992036   40+15   DACBDAADBC       TFTTF   	  55
201992055   30+9     DACDDACCBA       TTFTF  	  39
201992085   45+15   BACBDACDBA        TFTTF  	  60
201992117   45+12   BACBCACDBC         TFTFF 	  57
201992157   20+9     ACDBDBCABD        TFFTT  	  29
201992173   35+12   AACADACDBD        TFFTF 	  47
201992179   50+15   BACBDACDBC         TFTTF  	  65
201992182   35+12   BDCBCACBBC          FFTTF  	  47