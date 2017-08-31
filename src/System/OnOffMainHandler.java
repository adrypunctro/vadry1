/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Clients.Touch;
import Monitors.TouchMonitor;
import Monitors.VisualMonitor;
import Clients.Visual;
import Clients.Memory;

/**
 *
 * @author ASimionescu
 */
public class OnOffMainHandler
{    
    private static final OnOff ONOFF = OnOff.getInstance();
    
    public static void run()
    {
        VA_DEBUG.INFO("[ONOFF] Program starting ::::::::::::::::::::::::::::::::", true, 1);
        
        ONOFF.init();
        
        // SETUP ---------------------------------------------------------------
        ONOFF.setPersistentFiles(MyPersistentFile.class);
        
        ONOFF.registerClient(new Visual());
        ONOFF.registerClient(new Memory());
        ONOFF.registerClient(new Touch());
        
        ONOFF.registerMonitor(new VisualMonitor());
        ONOFF.registerMonitor(new TouchMonitor());
        
        // STARTUP -------------------------------------------------------------
        ONOFF.startup();
        
        VA_DEBUG.INFO("[ONOFF] Program is runing :::::::::::::::::::::::::::::::", true, 1);
        

        
    }
}