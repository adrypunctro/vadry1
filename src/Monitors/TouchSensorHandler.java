/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitors;

import Messages.TouchDetectedCommand;
import System.ChannelManager;
import System.SensorHandler;
import System.MyApplicationId;
import System.VA_DEBUG;
import java.util.Map;

/**
 *
 * @author ASimionescu
 */
public class TouchSensorHandler
    extends SensorHandler
{
    private ForceSensorMonitor sensor;

    public TouchSensorHandler()
    {
        
    }
    
    @Override
    public void init()
    {
        sensor = new ForceSensorMonitor();
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
        if (manager != null)
        {
            if (manager.isClientRegistered(MyApplicationId.TOUCH))
            {
                TouchDetectedCommand msg = new TouchDetectedCommand();
                msg.setSource(MyApplicationId.TOUCH);
                msg.setTarget(MyApplicationId.TOUCH);
                msg.createTransactionId();

                int transId = manager.send(msg);
            }
            else
            {
                VA_DEBUG.WARNING("[TOUCH MONITOR] TOUCH client is not registered.", true);
            }
        }
        else
        {
            VA_DEBUG.WARNING("[TOUCH MONITOR] ChannelManager is null.", true);
        }
    }
}
