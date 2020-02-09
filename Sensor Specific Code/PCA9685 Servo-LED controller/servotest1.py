from board import SCL, SDA
import busio
import time

from adafruit_pca9685 import PCA9685

from adafruit_motor import servo

i2c = busio.I2C(SCL,SDA)

pca = PCA9685(i2c)
pca.frequency = 50

servo4 = servo.Servo(pca.channels[4])
servo1 = servo.Servo(pca.channels[0])

for i in range(180):
    servo4.angle = i
    servo1.angle = i
    #time.sleep(0.1)
time.sleep(5)
for i in range(180):
    servo4.angle = 180 - i
    servo1.angle = 180 - i
pca.deinit()