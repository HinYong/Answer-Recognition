# 训练模型

from tensorflow.keras.layers import Conv2D, MaxPool2D, Flatten, Dense, Dropout, LeakyReLU
from tensorflow.keras.models import Sequential
import tensorflow as tf

EPOCHS = 10  # 训练的轮数
BATCH_SIZE = 32  # 数据批次大小
image_height = 32  # 训练图片的高度
image_width = 32  # 训练图片的宽度
channels = 1  # 训练图片的通道数
model_dir = "./model/model_ft.h5"
train_dir = "./dataset_ft/letters_train/"
test_dir = "./dataset_ft/letters_test/"

# 准备数据集
def get_datasets():

    # 归一化处理，加快收敛。
    # ImageDataGenerator()是keras.preprocessing.image模块中的图片生成器，同时也可以在batch中对数据进行增强，扩充数据集大小，增强模型的泛化能力。比如进行旋转，变形，归一化。
    train_data = tf.keras.preprocessing.image.ImageDataGenerator(rescale=1.0 / 255.0)
    test_data = tf.keras.preprocessing.image.ImageDataGenerator(rescale=1.0 / 255.0)

    # 图像增强
    # flow_from_directory()能够获取目录路径并生成一批增强数据。
    # train_dir是文件的位置；target_size将调整找到的所有图像的尺寸为32*32；grayscale灰度化，转为单通道；
    # batch_size为数据批次大小；class_mode为类模式，确定返回的标签数组的类型，为分类问题
    train_generator = train_data.flow_from_directory(train_dir,
                                                        target_size=(image_height, image_width),
                                                        color_mode="grayscale",
                                                        batch_size=BATCH_SIZE,
                                                        class_mode="categorical")
    test_generator = test_data.flow_from_directory(test_dir,
                                                      target_size=(image_height, image_width),
                                                      color_mode="grayscale",
                                                      batch_size=BATCH_SIZE,
                                                      class_mode="categorical"
                                                      )

    # 得到训练集与测试集的样本数量
    train_num = train_generator.samples
    test_num = test_generator.samples

    return train_generator, test_generator, train_num, test_num


# ----------------------------------------------------------------------------------------------------------------------
# 搭建网络的类
# tf.keras.layers.Conv2D --- 搭建卷积层 --- 第一个参数：卷积核的数量，
#                                         第二个参数：卷积核的大小，
#                                         第三个参数：激活函数
# tf.keras.layers.MaxPool2D --- 搭建池化层 --- 第一个参数：池化窗口的大小
#                                            第二个参数：池化窗口移动的步长
# tf.keras.layers.Flatten() --- 将所有的数据压缩成一个行向量
# tf.keras.layers.Dense --- 搭建全连接层 --- 第一个参数：全连接层神经元的个数
#                                          第二个参数：激活函数
# tf.keras.layers.Dropout(0.5) --- 随机的断开连接，为了防止过拟合 --- 参数是断开的比例
# ----------------------------------------------------------------------------------------------------------------------

def net():
    model = Sequential([
        # 64个卷积核，每个大小为3*3，步长为1，padding是填充值
        Conv2D(filters=64, kernel_size=(3, 3), strides=(1, 1), padding='same', input_shape=(32, 32, 1)),
        LeakyReLU(alpha=0.1),
        # 最大池化层 可以保留下了卷积后有用的信息，同时还达到简化计算的目的
        # 2×2的最大池化层 步长为1
        MaxPool2D(pool_size=(2, 2), strides=(1, 1)),
        Dropout(0.5),

        # 128个卷积核，每个大小为3*3，步长为1
        Conv2D(filters=128, kernel_size=(3, 3), strides=(1, 1), padding='same'),
        LeakyReLU(alpha=0.1),
        # 2×2的最大池化层 步长为1
        MaxPool2D(pool_size=(2, 2), strides=(1, 1)),
        Dropout(0.5),

         # 将所有的数据压缩成一个行向量
        Flatten(),

        # 搭建全连接层，256个神经元
        Dense(units=256),
        LeakyReLU(alpha=0.1),
        Dropout(0.5),
        # 2个类别的softmax分类器
        Dense(units=2, activation='softmax')
    ])

    return model


# 训练函数
def train():
    # 初始化神经网络类
    model = net()
    # 定义优化器adam
    # 定义损失函数为categorical_crossentropy多类的对数损失
    # 评估指标accuracy
    # 编译创建好的模型
    model.compile(loss=tf.keras.losses.categorical_crossentropy,
                  optimizer=tf.keras.optimizers.Adam(learning_rate=0.0001),
                  metrics=['accuracy'])
    # 获取数据集
    train_generator, test_generator, train_num, test_num = get_datasets()
    tensorboard = tf.keras.callbacks.TensorBoard(log_dir='log')
    callback_list = [tensorboard]

    # 打印出模型概况
    model.summary()
    # model.fit_generator --- 训练模型 --- train_generator：训练集生成器
    #                                     epochs：训练的迭代次数
    #                                     steps_per_epoch：每一轮训练的次数，为训练集样本数量整除数据批次大小(即32)
    #                                     validation_data：测试集生成器
    #                                     validation_steps：每一轮测试的次数，为测试集样本数量整除数据批次大小(即32)
    #                                     callbacks：tensorboard设置
    model.fit_generator(train_generator,
                        epochs=EPOCHS,
                        steps_per_epoch=train_num // BATCH_SIZE,
                        validation_data=test_generator,
                        validation_steps=test_num // BATCH_SIZE,
                        callbacks=callback_list)

    # 保存模型
    model.save(model_dir)


if __name__ == "__main__":
    train()
