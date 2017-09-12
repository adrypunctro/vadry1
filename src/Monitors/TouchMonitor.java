/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitors;

import Messages.TouchDetectedCommand;
import System.ChannelManager;
import System.Monitor;
import System.SystemApplicationId;
import System.VA_DEBUG;
import Clients.Visual;

/**
 *
 * @author ASimionescu
 */
public class TouchMonitor
    extends Monitor
{

    @Override
    protected void _processWorker(int millisecondPeriod)
    {
        VA_DEBUG.INFO("[TOUCH MONITOR] Monitoring started", true, 3);
        while(isAlive())
        {
            // PROCESS IMAGE
            try { Thread.sleep(500); } catch (InterruptedException e) {
                System.out.println(e);
            }
            
            int min = 0;
            int max = 6;
            int detect = (int) ((Math.random() * (max+1-min)) + min);
            
            if (detect == 0)
            {
                VA_DEBUG.INFO("[TOUCH MONITOR] Touch was detected.", true, 2);
                
                _touchDetected();
            }
        }
        VA_DEBUG.INFO("[TOUCH MONITOR] Monitoring stopped", true, 3);
    }
    
    private void _touchDetected()
    {
        ChannelManager manager = ChannelManager.getInstance();
        if (manager != null)
        {
            if (manager.isClientRegistered(SystemApplicationId.TOUCH))
            {
                TouchDetectedCommand msg = new TouchDetectedCommand();
                msg.setSource(SystemApplicationId.TOUCH);
                msg.setTarget(SystemApplicationId.TOUCH);
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
