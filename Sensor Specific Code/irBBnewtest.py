#########
#File: irBBtest.py
#Date: Sunday Nov 03, 2019   22:32 PM
#Author: George Alexandris
#Purpose: Going to implement uploading data to the firebase database
########

import time
import RPi.GPIO as GPIO
import pyrebase

Gpio_Pin=10

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
		time.sleep(2.0)		
	
except KeyboardInterrupt:
	print("CTRL+C clicked")

except:
	print("some error occurred")

finally:
	GPIO.cleanup()