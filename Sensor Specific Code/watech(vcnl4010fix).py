import pyrebase
import time
import RPi.GPIO as GPIO
import math
import random
import busio
import smbus
import board


#Sensor Specific Imports
import adafruit_vcnl4010
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

#VCNL4010 Initializations(Added)
# Sensor 1 Status
open1 = 0
occupied1 = 1
# Sensor 2 Status
open2 = 0
occupied2 = 1
# Sensor 3 Status
open3 = 0
occupied3 = 1
# Sensor 4 Status
open4 = 0
occupied4 = 1

#function to handle the entry gate
def entGate():
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
def extGate():
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

seconds = time.time()

i2c = busio.I2C(SCL,SDA)
#might need to use this instead i2c = busio.I2C(board.SCL,board.SDA)

#Sensor variabl declaration
pca = PCA9685(i2c)
pca.frequency = 50
vcnl = adafruit_vcnl4010.VCNL4010(i2c)
prox = vcnl.proximity


#GPIO Setup
GPIO.setmode(GPIO.BCM)
GPIO.setup(ent_ir,GPIO.IN)
GPIO.setup(ext_ir,GPIO.IN)

# IR Sensor Data
noEntry = {"Status": falseCheck, "timestamp": str(int(math.ceil(seconds)))}
entry = {"Status": trueCheck, "timestamp": str(int(math.ceil(seconds)))}

# VCNL4010 Sensor Data(Added)
a = {"proximity": proximity,"Slot 1A": occupied1,
"timestamp": str(int(math.ceil(seconds)))}
b = {"proximity": proximity,"Slot 1A": open1,
"timestamp": str(int(math.ceil(seconds)))}
        

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
            print("\nSlot 1A is available")
            print("\nSlot 2B is occupied")
            print("\nSlot 3C is occupied")
            print("\nSlot 4D is occupied")
            print("Proximity of Sensor 1 : %d" %prox)
            print(bool(open1))
            print(bool(occupied2))
            print(bool(occupied3))
            print(bool(occupied4))
            db.child("ProximityData").set(b)
            print("Proximity data successfully updated to Firebase!\n")
        elif(prox>5000):
            #no car is around
            print("\nSlot 1A is currently occupied")
            print("Proximity of Sensor 1 : %d" %prox)
            print(bool(occupied1))
            print("\nSlot 2B is currently occupied")
            print(bool(occupied2))
            print("\nSlot 3C is currently occupied")
            print(bool(occupied3))
            print("\nSlot 4D is currently occupied")
            print(bool(occupied4))
            db.child("ProximityData").set(a)
            print("Proximity data successfully updated to Firebase!\n")
           

except KeyboardInterrupt:
    print("CTRL+C clicked")

except:
    print("some error occurred")

finally:
    pca.deinit()
    GPIO.cleanup()