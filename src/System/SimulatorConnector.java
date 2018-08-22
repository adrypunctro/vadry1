/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adryp
 */
public class SimulatorConnector
    extends SensorsConnector
{
    private final int port;
    private SensorHandler handlerTouch = null;
    private SensorHandler handlerVideo = null;
    
    public SimulatorConnector(int port)
    {
        this.port = port;
    }
    
    @Override
    public void setTouchSensorHandler(SensorHandler handler)
    {
        this.handlerTouch = handler;
    }
    
    @Override
    public void setVideoSensorHandler(SensorHandler handler)
    {
        this.handlerVideo = handler;
    }
    
    @Override
    public void run()
    {
        TCPManager conn = new TCPManager(TCPManager.CLIENT);
        conn.setConfig("localhost", port);
        conn.setName("SimulatorConnector");
        conn.asyncConnect();
        new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    String msg = conn.readMessage();
                    if (msg != null)
                    {
                        VA_DEBUG.INFO("[SimulatorConnector] Received message="+msg, true, 1);
                        TCPManager.Packet packet = TCPManager.unpack(msg);
                        switch(packet.appId)
                        {
                            case PacketSensorId.TOUCH1:
                                if (handlerTouch != null) {
                                    //handlerTouch.change(packet.data);
                                }
                                else {
                                    VA_DEBUG.WARNING("[SimulatorConnector] No handlerTouch.", true, 1);
                                }
                                break;
                            case PacketSensorId.CAMERA1:
                                if (handlerVideo != null) {
                                    //handlerVideo.change(packet.data);
                                }
                                else {
                                    VA_DEBUG.WARNING("[SimulatorConnector] No handlerVideo.", true, 1);
                                }
                                break;
                            case 8:// PING
                                
                                break;
                            default:
                                VA_DEBUG.WARNING("[SimulatorConnector] Unknown appId="+packet.appId, true, 1);
                                break;
                        }

                    }
                }
            }
        }.start();
    }
    
    private class PacketSensorId
    {
        public final static int TOUCH1 = 1029;
        public final static int CAMERA1 = 829;
    
    }
    
}
