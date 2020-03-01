#########
#File: irBBtest.py
#Date: Sunday Nov 03, 2019   22:32 PM
#Author: George Alexandris
#Purpose: Going to implement uploading data to the firebase database
########

import time
import RPi.GPIO as GPIO

Gpio_Pin=0

#setup GPIO pin as input to read value for HIGH or LOW
GPIO.setmode(GPIO.BCM)
GPIO.setup(Gpio_Pin,GPIO.IN)

try:
    while True:
        x = GPIO.input(Gpio_Pin)
        if(x==1):
            print("Solid,LED OFF")
        if(x==0):
                print("Beam Broken,LED ON")
        
        time.sleep(2.0)     
    
except KeyboardInterrupt:
    print("CTRL+C clicked")

except:
    print("some error occurred")

finally:
    GPIO.cleanup()

