#!/usr/bin/env python

class LEDsInterface:
    def set(self, sigs):
        if sigs[0]:
            print("1", end='')
        else:
            print("0", end='')
            
        if sigs[1]:
            print("1", end='')
        else:
            print("0", end='')

        if sigs[2]:
            print("1", end='')
        else:
            print("0", end='')

        if sigs[3]:
            print("1", end='')
        else:
            print("0", end='')

        if sigs[4]:
            print("1")
        else:
            print("0")
