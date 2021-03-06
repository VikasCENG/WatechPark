
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


def turnOff():
    red.off()
    green.off()
    blue.off()
    
    
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
        "apiKey": "",
        "authDomain": "",
        "databaseURL":"",
        "storageBucket":""
        }
        firebase = pyrebase.initialize_app(config)
        db = firebase.database()
        
                    
        seconds = time.time()
        
        # Sensor Data
        a = {"proximity": proximity,"Slot 1A": occupied1,"Slot 2B": occupied2, "Slot 3C": occupied3, "Slot 4D": occupied4,
        "timestamp": str(int(math.ceil(seconds)))}
        b = {"proximity": proximity,"Slot 1A": open1,"Slot 2B": occupied2, "Slot 3C": occupied3, "Slot 4D": occupied4,
        "timestamp": str(int(math.ceil(seconds)))}
        
        
        

# Output data to screen
    
#print "Ambient Light Luminance : %d lux" %luminance
        if(proximity<=2500):
            green.on()
            print("\nSlot 1A is available")
            print("Proximity of Sensor 1 : %d" %proximity)
            print(bool(open1))
            print("\nSlot 2B is occupied")
            print(bool(occupied2))
            print("\nSlot 3C is occupied")
            print(bool(occupied3))
            print("\nSlot 4D is occupied")
            print(bool(occupied4))
            db.child("ProximityData").set(b)
            print("Proximity data successfully updated to Firebase!\n")
            sleep(2)
            turnOff()
        elif(proximity>5000):
            red.blink()
           
            print("\nSlot 1A is currently occupied")
            print("Proximity of Sensor 1 : %d" %proximity)
            print(bool(occupied1))
            print("\nSlot 2B is currently occupied")
            print(bool(occupied2))
            print("\nSlot 3C is currently occupied")
            print(bool(occupied3))
            print("\nSlot 4D is currently occupied")
            print(bool(occupied4))
            db.child("ProximityData").set(a)
            print("Proximity data successfully updated to Firebase!\n")
            sleep(2)
            turnOff()
            #red.off()
        elif(proximity>=3000) or (proximity<=4500):
            blue.on()
        
            print("\nCar is approaching the parking space")
            print("Proximity of Sensor 1 : %d" %proximity)
            sleep(2)
            turnOff()
            #blue.off()

            sleep(2)
            turnOff()

    
            db.child("ProximityData").set(a)
            print("Proximity data successfully updated to Firebase!\n")
        else:
            print("Failed to push proximity data to Firebase!\n")
            
            
finally:
    
    GPIO.cleanup()
    
    
    #GPIO.output(18, 1)
    #green.on()



    
    
    
    


    

  
    
