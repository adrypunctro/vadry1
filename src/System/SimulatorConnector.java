/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 *
 * @author adryp
 */
public class SimulatorConnector
    extends SensorsConnector
{
    private final int port; 
    private SensorHandler handler;
    
    public SimulatorConnector(int port)
    {
        this.port = port;
    }
    
    @Override
    public void setTouchSensorHandler(SensorHandler handler)
    {
        this.handler = handler;
    }
    
    @Override
    public void run()
    {
        ServerSocket ss;
        try {
            ss = new ServerSocket(port);
            
            Socket s = ss.accept();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                // TODO: mesjul trebuie dat handlerului caruia ii corespunde semnalul
                Map<String, Object> data = null;
                handler.change(data);
                //System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
