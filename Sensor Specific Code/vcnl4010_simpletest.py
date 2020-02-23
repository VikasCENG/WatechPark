
# Distributed with a free-will license.
# Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
# VCNL4010
# This code is designed to work with the VCNL4010_I2CS I2C Mini Module available from ControlEverything.com.
# https://www.controleverything.com/content/Light?sku=VCNL4010_I2CS#tabs-0-product_tabset-2

import random
import pyrebase
import time
import math

import smbus
from time import sleep
from gpiozero import LED
import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)
# Get I2C bus
bus = smbus.SMBus(1)

red = LED(17)
green = LED(18)
blue = LED(27)

open1 = 0
occupied = 1


def turnOff():
    red.off()
    green.off()
    blue.off()
    

    global db
    config = {
    "apiKey": "AIzaSyBHz-ZrX8ANSYz3qcVdbjQ_KvpX8Kz3PnU",
    "authDomain": "watechpark.firebaseapp.com",
    "databaseURL": "https://watechpark.firebaseio.com",
    "storageBucket": "watechpark.appspot.com"
    }
    firebase = pyrebase.initialize_app(config)
    db = firebase.database()
    
try:     
    while True:

# VCNL4010 address, 0x13(19)
# Select command register, 0x80(128)
#		0xFF(255)	Enable ALS and proximity measurement, LP oscillator
        bus.write_byte_data(0x13, 0x80, 0xFF)
# VCNL4010 address, 0x13(19)
# Select proximity rate register, 0x82(130)
#		0x00(00)	1.95 proximity measeurements/sec
        bus.write_byte_data(0x13, 0x82, 0x00)
# VCNL4010 address, 0x13(19)
# Select ambient light register, 0x84(132)
#		0x9D(157)	Continuos conversion mode, ALS rate 2 samples/sec
#bus.write_byte_data(0x13, 0x84, 0x9D)

        time.sleep(0.8)

# VCNL4010 address, 0x13(19)
# Read data back from 0x85(133), 4 bytes
# luminance MSB, luminance LSB, Proximity MSB, Proximity LSB
        data = bus.read_i2c_block_data(0x13, 0x85, 4)

# Convert the data
#luminance = data[0] * 256 + data[1]
        proximity = data[2] * 256 + data[3]
   
        global db
        config = {
        "apiKey": "AIzaSyBHz-ZrX8ANSYz3qcVdbjQ_KvpX8Kz3PnU",
        "authDomain": "watechpark.firebaseapp.com",
        "databaseURL": "https://watechpark.firebaseio.com",
        "storageBucket": "watechpark.appspot.com"
        }
        firebase = pyrebase.initialize_app(config)
        db = firebase.database()
        
                    
        seconds = time.time()
        a = {"proximity": proximity,"Spot 1": occupied, 
        "timestamp": str(int(math.ceil(seconds)))}
        b = {"proximity": proximity,"Spot 1": open1, 
        "timestamp": str(int(math.ceil(seconds)))}

# Output data to screen
    
#print "Ambient Light Luminance : %d lux" %luminance
        if(proximity<=2500):
            green.on()
            
            print("\nParking space is available")
            print("Proximity of the Device : %d" %proximity)
            print(bool(open1))
            db.child("ProximityData").push(b)
            print("Proximity data successfully updated to Firebase!\n")
            sleep(2)
            turnOff()
        elif(proximity>5000):
            red.blink()
           
            print("\nParking space is full")
            print("Gate is closing!")
            print("Proximity of the Device : %d" %proximity)
            print(bool(occupied))
            db.child("ProximityData").push(a)
            print("Proximity data successfully updated to Firebase!\n")
            sleep(2)
            turnOff()
            #red.off()
        elif(proximity>=3000) or (proximity<=4500):
            blue.on()
        
            print("\nCar is approaching the parking space")
            print("Gate is opening...")
            print("Proximity of the Device : %d" %proximity)
            sleep(2)
            turnOff()
            #blue.off()

            sleep(2)
            turnOff()

    
            db.child("ProximityData").push(a)
            print("Proximity data successfully updated to Firebase!\n")
        else:
            print("Failed to push proximity data to Firebase!\n")
            
finally:
    
    GPIO.cleanup()
    
    
    #GPIO.output(18, 1)
    #green.on()



    
    
    
    


    

  
    