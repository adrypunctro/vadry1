#!/usr/bin/env python

import sys, getopt
from time import sleep
from Classifier import *
from LEDsInterface import *

class Manager:
    def __init__(self):
        self.running = False
        self.classifier = Classifier()
        self.leds = LEDsInterface()

    def train(self, path):
        print("Training...")
        self.classifier.train(path)
    
    def start(self):
        if self.running == True:
            print('Skip start. Reason: Already running!')
            return

        print("Starting...")
        self.running = True
        while self.running:
            #capture
            image=''#todo:
            #classify
            label = self.classifier.recognize(image)
            #display
            sigs = self.classifier.encode(label)
            self.leds.set(sigs);
            
            sleep(1)#todo:

    def stop(self):
        if self.running == False:
            print('Skip stop. Reason: Not running!')
            return

        print("Stoping...")
        self.running = False
