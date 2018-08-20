/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author adryp
 */
public class RaspberryPiCapture
        implements CameraCapture
{

    public RaspberryPiCapture()
    {
    }

    @Override
    public boolean init(String buff, boolean grayscape)
    {
        this.grayscape = grayscape;
        
        String patternString = "capture:([0-9]+)x([0-9]+),buff=([0-9,]+);checksum=([0-9]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(buff);
        boolean matches = matcher.matches();
        if (!matches)
        {
            return false;
        }
        
        width = Integer.parseInt(matcher.group(1));
        height = Integer.parseInt(matcher.group(2));
        String capture_vals = matcher.group(3);
        int checksum = Integer.parseInt(matcher.group(4));

        List<String> vals_str = Arrays.asList(capture_vals.split(","));
        vals.clear();
        // Check-sum
        int sum=0;
        for (String val_str : vals_str)
        {
            Integer val = Integer.parseInt(val_str);
            sum += val;
            vals.add(val);
        }

        sum = sum%126;

        if (sum != checksum)
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public BufferedImage image()
    {
        BufferedImage image = null;
        if (grayscape)
        {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            byte [] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            for (int i = 0; i < data.length; i++)
            {
                data[i] = vals.get(i).byteValue();
            }
            return image;
        }
        else
        {
            int[] data = new int[width * height];
            for (int i=0; i<width*height; i++)
            {
                int red     = vals.get( 3*i+0 );
                int green   = vals.get( 3*i+1 );
                int blue    = vals.get( 3*i+2 );
                data[i] = (red << 16) | (green << 8) | blue;
            }
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, width, height, data, 0, width);
        }
        
        return image;
    }
    
    @Override
    public String toString()
    {
        StringBuilder ss = new StringBuilder();
        boolean first=true;
        for (Integer val : vals)
        {
            if (!first)
            {
                ss.append(",");
            }
            first=false;
            ss.append(val);
        }
        return ss.toString();
    }
    
    public boolean grayscape;
    public int width;
    public int height;
    public List<Integer> vals = new ArrayList<>();
}
