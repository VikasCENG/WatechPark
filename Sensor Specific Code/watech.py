import pyrebase
import time
import RPi.GPIO as GPIO
import math
import random
import busio
import smbus
import board


#Sensor Specific Imports
import adafrut_vcnl4010
from adafruit_pca9685 import PCA9685
from adafruit_motor import servo

#Firebase initialization
global db
config = {
    "apiKey": "AIzaSyBHz-ZrX8ANSYz3qcVdbjQ_KvpX8Kz3PnU",
    "authDomain": "watechpark.firebaseapp.com",
    "databaseURL": "https://watechpark.firebaseio.com",
    "storageBucket": "watechpark.appspot.com"
}
firebase = pyrebase.initialize_app(config)
db = firebase.database()



#Variable Initialziations
ent_ir = 0
ext_it = 24

trueCheck = 1
falseCheck = 0

seconds = time.time()

i2c = busio.I2C(SCL,SDA)
#might need to use this instead i2c = busio.I2C(board.SCL,board.SDA)

#Sensor variabl declaration
global pca = PCA9685(i2c)
pca.frequency = 50
vcnl = adafruit_vcnl4010.VCNL4010(i2c)
prox = vcnl.proximity


#GPIO Setup
GPIO.setmode(GPIO.BCM)
GPIO.setup(ent_ir,GPIO.IN)
GPIO.setup(ext_ir,GPIO.IN)

# IR Sensor Data
global noEntry = {"Status": falseCheck, "timestamp": str(int(math.ceil(seconds)))}
global entry = {"Status": trueCheck, "timestamp": str(int(math.ceil(seconds)))}

#main code:
try:
    while True:
        #entry sensor part
        entG = GPIO.input(ent_ir)
        if(entG==1):
            print("Solid,LED OFF")
            print("Vehicle currently not detected at entry")
            print(bool(falseCheck))
            print("Gate status successfully updated to Firebase!\n")
        if(entG==0):
            print("Beam Broken,LED ON")
            print("Vehicle detected at entry")
            print(bool(trueCheck))
            db.child("GateStatus").set(entry)
            print("Gate status successfully updated to Firebase!\n")
        #exit sensor part
        extG = GPIO.input(ext_ir)
        if(entG==1):
            print("Solid,LED OFF")
            print("Vehicle currently not detected at entry")
            print("Gate status successfully updated to Firebase!\n")
        if(entG==0):
            print("Beam Broken,LED ON")
            print("Vehicle detected at entry")
            print("Gate status successfully updated to Firebase!\n")
        #parking spot part
        if(prox<=2500):
            #car is on spot condition
            print("spot is now taken")
        elif(prox>=5000):
            #no car is around
            print("spot is open")
            


#function to handle the entry gate
def entGate()
    ent_srv = servo.Servo(pca.channels[4])
    for i in range(180):
        ent_srv.angle = i
        #gate is open at this point, updating database:
        db.child("GateStatus").set(noEntry)
    while(GPIO.input(ent_ir)!=1):
          time.sleep(0.5)
    time.sleep(2.0)
    for i in range(180):
        ent_srv.angle = 180 - i
          
#function to handle the entry gate
def extGate()
    ext_srv = servo.Servo(pca.channels[3])
    for i in range(180):
        ext_srv.angle = i
    while(GPIO.input(ext_ir)!=1):
          time.sleep(0.5)
    time.sleep(2.0)
    for i in range(180):
        ext_srv.angle = 180 - i

def camSen():
    return "1A5-E8K"

except KeyboardInterrupt:
    print("CTRL+C clicked")

except:
    print("some error occurred")

finally:
    pca.deinit()
    GPIO.cleanup()
    


