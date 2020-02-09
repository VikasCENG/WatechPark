#!/bin/bash

DATE=$(date +"%Y-%m-%d_%H%M")

fswebcam -r 1280x720 -S 7 --no-banner /home/pi/Desktop/image-testing/$DATE.jpg

echo $(python3 mod_recog.py $DATE.jpg)
