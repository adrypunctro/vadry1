/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitors;

import Messages.PersonDetectedCommand;
import Messages.ProcessVideoDataCommand;
import System.MyApplicationId;
import System.ChannelManager;
import System.SensorHandler;
import System.SensorsConnector;
import System.VA_DEBUG;
import java.util.Map;

/**
 *
 * @author ASimionescu
 */
public class VisualSensorHandler
    extends SensorHandler
{
    private VideoSensorMonitor sensor;
    
    public VisualSensorHandler(SensorsConnector conn)
    {
        super(conn);
        conn.setVideoSensorHandler(this);
    }
    
    @Override
    public void init()
    {
        sensor = new VideoSensorMonitor();
        sensor.setSensorHandler(this);
        sensor.run();
    }
    
    /**
     * Received data when SensorMonitor is triggered.
     * @param data
     */
    @Override
    public void change(Map<String, Object> data)
    {
        ChannelManager manager = ChannelManager.getInstance();
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
        
        try
        {
            ProcessVideoDataCommand msg = new ProcessVideoDataCommand();
            msg.setSource(MyApplicationId.VISUAL);
            msg.setTarget(MyApplicationId.VISUAL);
            msg.setRawData((String)data.get("rawData"));
            msg.createTransactionId();
            int transId = manager.send(msg);
        }
        catch(Exception e)
        {
            VA_DEBUG.WARNING("[VISUAL MONITOR] Failed create ProcessVideoDataCommand because wrong data received="+data.toString(), true);
        }
    }
}
