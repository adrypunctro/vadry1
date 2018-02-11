/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Clients.*;
import Monitors.TouchSensorHandler;
import Monitors.VisualSensorHandler;

/**
 *
 * @author ASimionescu
 */
public class OnOffMainHandler
{    
    private static final OnOff ONOFF = OnOff.getInstance();
    
    public static void run()
    {
        VA_DEBUG.sendLogsToAddress("localhost", 60020);
        
        VA_DEBUG.INFO("[ONOFF] Program starting ::::::::::::::::::::::::::::::::", true, 1);
        
        // CONFIG --------------------------------------------------------------
        Config.APP_NAME         = "VAdry";
        VA_DEBUG.INFO("[ONOFF] APP_NAME: "+Config.APP_NAME, true, 1);
        Config.APP_VERSION      = "1.0";
        VA_DEBUG.INFO("[ONOFF] APP_VERSION: "+Config.APP_VERSION, true, 1);
        Config.PERSISTENCE_DIR  = "/data/persistency/";
        VA_DEBUG.INFO("[ONOFF] PERSISTENCE_DIR: "+Config.PERSISTENCE_DIR, true, 1);
        
        // INIT ----------------------------------------------------------------
        ONOFF.init();
        
        // SETUP ---------------------------------------------------------------
        ONOFF.setPersistentFiles(MyPersistentFile.class);

        ONOFF.registerClient(new Visual());
        //ONOFF.registerClient(new Memory());
        ONOFF.registerClient(new Touch());
        
        SensorsConnector sensorsConn = new SimulatorConnector(60010);
        
        ONOFF.registerMonitor(new VisualSensorHandler(sensorsConn));
        ONOFF.registerMonitor(new TouchSensorHandler(sensorsConn));
        
        // STARTUP -------------------------------------------------------------
        ONOFF.startup();
        
        // START CONNECTOR -----------------------------------------------------
        sensorsConn.run();
        
        VA_DEBUG.INFO("[ONOFF] Program is runing :::::::::::::::::::::::::::::::", true, 1);
        

        
    }
}
