#!/usr/bin/env python

import sys, getopt
from time import sleep
from Manager import *

if len(sys.argv) == 1:
    print('test.py <start|stop|train> <path>')
    sys.exit(2)

manager = Manager()

if sys.argv[1] == "start":
    manager.start()
elif sys.argv[1] == "stop":
    manager.stop()
elif sys.argv[1] == "train":
    manager.train(sys.argv[2])
