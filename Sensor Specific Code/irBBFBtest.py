#########
#File: irBBFBtest.py
#Date: Tuesday Feb 25, 2020   15:52 PM
#Author: George Alexandris
#Purpose: This file uses the IR Break Beam to send data 
#to the Firebase database
########

import time
import RPi.GPIO as GPIO
import pyrebase
import math
import os

Gpio_Pin=17

trueCheck = 0
falseCheck = 1

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
        
# Sensor Data
noEntry = {"Status": falseCheck, "timestamp": str(int(math.ceil(seconds)))}
entry = {"Status": trueCheck, "timestamp": str(int(math.ceil(seconds)))}
                

#setup GPIO pin as input to read value for HIGH or LOW
GPIO.setmode(GPIO.BCM)
GPIO.setup(Gpio_Pin,GPIO.IN)

try:
	while True:
		x = GPIO.input(Gpio_Pin)
		if(x==1):
			print("Solid,LED OFF")
			print("Vehicle currently not detected at entry")
			print(bool(falseCheck))
			db.child("GateStatus").set(noEntry)
                	print("Gate status successfully updated to Firebase!\n")
		if(x==0):
        		print("Beam Broken,LED ON")
			print("Vehicle detected at entry")
			print(bool(trueCheck))
			db.child("GateStatus").set(entry)
                	print("Gate status successfully updated to Firebase!\n")
			#add system call to run Camera python program 
			#when car approaches gate
			#os.system("./carsnaptest.sh")
 
		time.sleep(2.0)		
	
except KeyboardInterrupt:
	print("CTRL+C clicked")

except:
	print("some error occurred")

finally:
	GPIO.cleanup()

