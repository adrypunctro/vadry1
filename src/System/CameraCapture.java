/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author adryp
 */
public interface CameraCapture {

    public boolean init(String buff, boolean grayscape);
    public BufferedImage image();
}
