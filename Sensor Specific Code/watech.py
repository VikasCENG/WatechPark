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

#function to handle the entry gate
def entGate():
    ent_srv = servo.Servo(pca.channels[7])
    for i in range(90):
        ent_srv.angle = i
        #gate is open at this point, updating database:
    db.child("GateStatus").set(noEntry)
    while(GPIO.input(ent_ir)!=1):
          time.sleep(0.5)
    time.sleep(2.0)
    for i in range(90):
        ent_srv.angle = 90 - i

#function to handle the entry gate
def extGate():
    ext_srv = servo.Servo(pca.channels[3])
    for i in range(90):
        ext_srv.angle = i
    while(GPIO.input(ext_ir)!=1):
          time.sleep(0.5)
    time.sleep(2.0)
    for i in range(90):
        ext_srv.angle = 90 - i


def camSen():
    return "1A5-E8K"

def licCheck():
    incommingCar = camSen()
    getstat = db.child("Cars").get("lplate")
    print(getstat)


#Variable Initialziations
ent_ir = 0
ext_ir = 24

trueCheck = 1
falseCheck = 0

seconds = time.time()

i2c = busio.I2C(board.SCL,board.SDA)
#might need to use this instead i2c = busio.I2C(board.SCL,board.SDA)

#Sensor variabl declaration
global pca
pca = PCA9685(i2c)
pca.frequency = 60
vcnl = adafruit_vcnl4010.VCNL4010(i2c)


#GPIO Setup
GPIO.setmode(GPIO.BCM)
GPIO.setup(ent_ir,GPIO.IN)
GPIO.setup(ext_ir,GPIO.IN)

# IR Sensor Data
global noEntry
noEntry = {"Status": falseCheck, "timestamp": str(int(math.ceil(seconds)))}
global entry
entry = {"Status": trueCheck, "timestamp": str(int(math.ceil(seconds)))}


# Sensor 1A Status
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


#main code:
try:
    while True:
        #entry sensor part
        entG = GPIO.input(ent_ir)
        eStat = db.child("EntryStatus").get()
        if(entG==1):
            print("Vehicle currently not detected at entry")
        if(eStat.val()["lotStatus"]==0):
            print("Lot is full, all spots are occupied!")
        if(entG==0):
            print("Vehicle detected at entry")
            entGate()
            #db.child("GateStatus").set(entry)
            
        #exit sensor part
#         extG = GPIO.input(ext_ir)
#         if(extG==1):
#             print("Solid,LED OFF")
#             print("Vehicle currently not detected at entry")
#             print("Gate status successfully updated to Firebase!\n")
#         if(extG==0):
#             print("Beam Broken,LED ON")
#             print("Vehicle detected at entry")
#             print("Gate status successfully updated to Firebase!\n")
        #parking spot part
        prox = vcnl.proximity
        #VCNL variables for future usage
        a = {"proximity": prox,"Slot 1A": occupied1, "Slot 2B": occupied2, "Slot 3C": occupied3, "Slot 4D": occupied4,
        "timestamp": str(int(math.ceil(seconds)))}
        b = {"proximity": prox,"Slot 1A": open1,"Slot 2B": occupied2, "Slot 3C": occupied3, "Slot 4D": occupied4,
        "timestamp": str(int(math.ceil(seconds)))}
        if(prox<=2500):
            #car is on spot condition
            print("\nSlot 1A is available")
            print("Proximity of Sensor 1 : %d" %prox)
            print(bool(open1))
            #TODO: Pair with an if statement to avoid updating the database when there is no change
            db.child("ProximityData").set(b)
        elif(prox>=5000):
            #no car is around
            print("\nSlot 1A is currently occupied")
            print("Proximity of Sensor 1 : %d" %prox)
            print(bool(occupied1))
            #TODO: Pair with an if statement to avoid updating the database when there is no change
            db.child("ProximityData").set(a)
        adminC = db.child("AdminControl").get()
        if(adminC.val()["adminStatus"]==1):
            entGate()
            db.child("AdminControl").set({"adminStatus": 0})
        time.sleep(1)
            
except KeyboardInterrupt:
    print("CTRL+C clicked")

except:
    print("some error occurred")

finally:
    pca.deinit()
    GPIO.cleanup()
    


