/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitors;

import Clients.Visual;
import Defines.HeadGestureYes;
import Defines.ProcessVideoDataCommand;
import Defines.MyApplicationId;
import System.ChannelManager;
import System.SensorHandler;
import System.SensorsConnector;
import System.VA_DEBUG;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import Defines.CameraCapture;
import Platform.RaspberryPiCapture;

/**
 *
 * @author ASimionescu
 */
public class VisualSensorHandler
    extends SensorHandler
{
    private CameraMonitor sensor;
    
    public VisualSensorHandler(SensorsConnector conn)
    {
        super(conn);
        conn.setVideoSensorHandler(this);
    }
    
    @Override
    public void init()
    {
        manager = ChannelManager.getInstance();
        if (manager == null)
        {
            VA_DEBUG.WARNING("[VISUAL MONITOR] ChannelManager is null.", true);
            return;
        }
        if (!manager.isClientRegistered(MyApplicationId.VISUAL))
        {
            VA_DEBUG.WARNING("[VISUAL MONITOR] VISUAL client is not registered.", true);
            return;
        }
    }
    
    /**
     * Received data when SensorMonitor is triggered.
     * @param capture
     * @param data
     */
    @Override
    public void handle(Object data)
    {
        CameraCapture capture = (CameraCapture)data;
        System.out.println(capture.toString());
        
        Visual cli = (Visual) manager.getClient(MyApplicationId.VISUAL);
        cli.handle(capture);
    }
    
    private ChannelManager manager = null;
}
