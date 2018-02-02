/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import System.TCPClient.Callback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        TCPClient conn = new TCPClient();
        conn.setCallback((BufferedReader in) -> {
            VA_DEBUG.INFO("[SimulatorConnector] Start reading input buffer.", true, 1);
            String line = null;
            while ((line = in.readLine()) != null)
            {
                VA_DEBUG.INFO("[SimulatorConnector] Received message="+line, true, 1);
                Packet packet = decode(line);
                switch(packet.appId)
                {
                    case PacketSensorId.TOUCH1:
                        if (handlerTouch != null) {
                            handlerTouch.change(packet.data);
                        }
                        else {
                            VA_DEBUG.WARNING("[SimulatorConnector] No handlerTouch.", true, 1);
                        }
                        break;
                    case PacketSensorId.CAMERA1:
                        if (handlerVideo != null) {
                            handlerVideo.change(packet.data);
                        }
                        else {
                            VA_DEBUG.WARNING("[SimulatorConnector] No handlerVideo.", true, 1);
                        }
                        break;
                    default:
                        VA_DEBUG.WARNING("[SimulatorConnector] Unknown appId="+packet.appId, true, 1);
                        break;
                }
            }
        });
        conn.setConfig("", port);
        conn.setName("SimulatorConnector");
        conn.connect();
    }
    
    private Packet decode(String buffer)
    {
        Packet packet = new Packet();
        JSONParser parser = new JSONParser();
        try
        {
            Object obj = parser.parse(buffer);
            JSONObject jsonObject = (JSONObject) obj;
            packet.appId = (int)(long)jsonObject.get("appId");
            JSONObject dataObj = (JSONObject) jsonObject.get("data");
            for (Object key : dataObj.keySet()) {
                String sKey = (String)key;
                packet.data.put(sKey, dataObj.get(sKey));
            }
        }
        catch (ParseException e) {
            VA_DEBUG.INFO("[SimulatorConnector] Failed decode this msg="+buffer, true, 1);
        }
        
        return packet;
    }
    
    private class Packet
    {
        public int appId = 0;
        public Map<String, Object> data = new HashMap<>();
    }
    
    private class PacketSensorId
    {
        public final static int TOUCH1 = 1029;
        public final static int CAMERA1 = 829;
    
    }
    
}
