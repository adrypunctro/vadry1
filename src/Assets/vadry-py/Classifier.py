#!/usr/bin/env python

# TensorFlow and tf.keras
import tensorflow as tf
from tensorflow import keras

# Helper libraries
import numpy as np
import matplotlib.pyplot as plt

class Classifier:
    def recognize(self, image):
        label = 2
        return label

    def encode(self, label):
        sigs = []
        for i in range(1,6):
            if label >= i:
                sigs.append(1)
            else:
                sigs.append(0)
        return sigs;

    def train(self, path):
        print(path)
