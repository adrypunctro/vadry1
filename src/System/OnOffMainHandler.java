/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Clients.*;
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
        Configurator conf = new Configurator();
        boolean configLoaded = conf.load();
        if (!configLoaded)
        {
            VA_DEBUG.INFO("[ONOFF] Program can't start. Failed load config!", true, 1);
            return;
        }
        
        if (conf.GetBool("console-socket-allow"))
        {
            VA_DEBUG.sendLogsToAddress(conf.GetString("console-socket-address"), conf.GetInt("console-socket-port"));
        }
        
        VA_DEBUG.INFO("[ONOFF] Program config ::::::::::::::::::::::::::::::::::", true, 1);
        
        // CONFIG --------------------------------------------------------------
        VA_DEBUG.INFO("[ONOFF] APP_NAME: "+conf.GetString("app-name"), true, 1);
        VA_DEBUG.INFO("[ONOFF] APP_VERSION: "+conf.getDouble("app-version"), true, 1);
        VA_DEBUG.INFO("[ONOFF] PERSISTENCE_DIR: "+conf.GetString("path-persistence-dir"), true, 1);
        VA_DEBUG.INFO("[ONOFF] CONSOLE_SOCKET_ALLOW: "+conf.GetBool("console-socket-allow"), true, 1);
        VA_DEBUG.INFO("[ONOFF] CONSOLE_SOCKET_ADDRESS: "+conf.GetString("console-socket-address"), true, 1);
        VA_DEBUG.INFO("[ONOFF] CONSOLE_SOCKET_PORT: "+conf.GetInt("console-socket-port"), true, 1);
        VA_DEBUG.INFO("[ONOFF] CAMERA_SOCKET_ALLOW: "+conf.GetBool("camera-socket-allow"), true, 1);
        VA_DEBUG.INFO("[ONOFF] CAMERA_SOCKET_ADDRESS: "+conf.GetString("camera-socket-address"), true, 1);
        VA_DEBUG.INFO("[ONOFF] CAMERA_SOCKET_PORT: "+conf.GetInt("camera-socket-port"), true, 1);
        
        // INIT ----------------------------------------------------------------
        ONOFF.init();
        
        // SETUP ---------------------------------------------------------------
        ONOFF.setPersistentFiles(MyPersistentFile.class);

        ONOFF.registerClient(new Visual());
        ONOFF.registerClient(new Memory());
        ONOFF.registerClient(new Touch());
        
        //SensorsConnector sensorsConn = new SimulatorConnector(60010);
        SensorsConnector sensorsConn = new RobotConnector("192.168.0.104", 5001);
        
        ONOFF.registerMonitor(new VisualSensorHandler(sensorsConn));
        
        // STARTUP -------------------------------------------------------------
        ONOFF.startup();
        
        // START CONNECTOR -----------------------------------------------------
        sensorsConn.run();
        
        VA_DEBUG.INFO("[ONOFF] Program is runing :::::::::::::::::::::::::::::::", true, 1);
        

        
    }
}
