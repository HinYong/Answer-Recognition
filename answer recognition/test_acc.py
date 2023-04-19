# 模型精度检测

import tensorflow as tf

# 加载训练好的模型
model = tf.keras.models.load_model('./model/model_ft.h5')  # 加载ft模型
# model = tf.keras.models.load_model('./mm/model_abcd.h5')  # 加载abcd模型

test_dir = "./dataset_ft/letters_test/"  # 测试ft模型
# test_dir = "./dataset_abcd/letters_test/"  # 测试abcd模型

BATCH_SIZE = 32  # 数据批次大小
image_height = 32  # 图片高度
image_width = 32  # 图片宽度

# ----------------------------------------------------------------------------------------------------------------------
# def get_datasets(): --- 准备数据集
# ImageDataGenerator()是keras.preprocessing.image模块中的图片生成器，同时也可以在batch中对数据进行增强，扩充数据集大小，增强模型的泛化能力。比如进行旋转，变形，归一化等等
# rescale=1.0 / 255.0 --- 归一化处理
# flow_from_directory --- 第一个参数是文件的位置，第二个参数是目标文件的尺寸，第三个参数是图片文件的颜色空间，
# 第四个参数是一次喂入神经网络数据的个数，第五个参数是ImageDataGenerator的方法.flow_from_directory()加载图片数据流时，
# 参数class_mode要设为‘categorical’，如果是二分类问题该值可设为‘binary’,
# train_generator.samples --- 得到训练数据集的样本数量
# ----------------------------------------------------------------------------------------------------------------------


def get_datasets():
    # 测试集数据归一化
    test_data = tf.keras.preprocessing.image.ImageDataGenerator(rescale=1.0 / 255.0)

    # 测试集图像生成器
    test_generator = test_data.flow_from_directory(test_dir,
                                                      target_size=(image_height, image_width),
                                                      color_mode="grayscale",
                                                      batch_size=BATCH_SIZE,
                                                      class_mode="categorical"
                                                      )
    # 测试集样本数
    test_num = test_generator.samples

    return test_generator, test_num


test_generator, test_num = get_datasets()
test_loss, test_acc = model.evaluate(test_generator)  # 检测loss以及accuracy
print("accuracy: %.4f, loss: %.4f, num of pic %d " % (test_acc, test_loss, test_num))
