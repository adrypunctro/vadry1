/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author adryp
 */
public class RobotConnector
    extends SensorsConnector
{
    private final int port;
    private final String address;

    public RobotConnector(String address, int port)
    {
        this.address = address;
        this.port = port;
    }
    
    @Override
    public void setTouchSensorHandler(SensorHandler handler)
    {
        
    }

    @Override
    public void setVideoSensorHandler(SensorHandler handler)
    {
        
    }

    @Override
    public void run()
    {
        
        new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try (Socket socket = new Socket(address, port))
                    {
                        InputStream input = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        String line;
                        while ((line = reader.readLine()) != null)
                        {
                            CameraCapture capture = new RaspberryPiCapture();
                            boolean ok = capture.init(line, true);
                            if (ok)
                            {
                                System.out.println(capture.toString());
                                final BufferedImage image = capture.image();

                                JFrame frame = new JFrame("ColorPan");
                                frame.getContentPane().add(new JComponent() {
                                    @Override
                                    public void paint(Graphics g) {
                                        g.drawImage(image, 0, 0, this);
                                    }
                                });
                                frame.setSize(300, 300);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.setVisible(true);
                            }
                            else
                            {
                                VA_DEBUG.INFO("[CONNECTOR] Capture was discarded! "+line, true, 2);
                            }
                        }

                    } catch (UnknownHostException ex) {
                        System.out.println("Server not found: " + ex.getMessage());
                    } catch (IOException ex) {
                        System.out.println("I/O error: " + ex.getMessage());
                    }
                    
                    try { Thread.sleep(5000); } catch (InterruptedException ex) { }
                }
            }
        }.start();
        
    }
    
}
