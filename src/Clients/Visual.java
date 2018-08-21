package Clients;

import Messages.ATPMsg;
import Messages.MyMessageType;
import Messages.PersonDetectedRequest;
import Messages.ProcessVideoDataCommand;
import System.MyApplicationId;
import System.ChannelManager;
import System.Client;
import System.OnOffState;
import System.TCPManager;
import System.VA_DEBUG;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASimionescu
 */
public class Visual
    extends Client
{
    
    public Visual()
    {
        super(MyApplicationId.VISUAL);
    }
    
    
    @Override
    public boolean registerClient()
    {
        ChannelManager manager = ChannelManager.getInstance();
        
        if (manager != null)
        {
            return manager.registerClient(this);
        }
        
        return false;
    }
    
    @Override
    public boolean unregisterClient()
    {
        ChannelManager manager = ChannelManager.getInstance();
        
        if (manager != null)
        {
            return manager.unregisterClient(this);
        }
        
        return false;
    }
    
    @Override
    public boolean handleRequest(ATPMsg msg)
    {
        VA_DEBUG.INFO("[VISUAL] handleRequest("+msg.getMsgType().name()+")", true, 3);
        
        switch((MyMessageType)msg.getMsgType())
        {
            case processVideoDataCommand:
                _handleProcessVideoData((ProcessVideoDataCommand)msg);
                break;
            
            case personDetectedCommand:
                _handlePersonDetectedCommand(msg);
                break;
                
            case personDetectedResponse:
                _handlePersonDetectedResponse(msg);
                break;
            
            default:
                VA_DEBUG.WARNING("[VISUAL] unknown message type received("+msg.getMsgType().name()+")", true);
                return false;
        }
        
        return true;
    }
    
    private boolean _handleProcessVideoData(ProcessVideoDataCommand msg)
    {
        ChannelManager manager = ChannelManager.getInstance();
        
        if (manager == null)
        {
            VA_DEBUG.WARNING("[VISUAL] ChannelManager is null.", true);
            return false;
        }
        
        if (!manager.isClientRegistered(MyApplicationId.MEMORY))
        {
            VA_DEBUG.WARNING("[VISUAL] MEMORY is not registered.", true);
            return false;
        }
        VA_DEBUG.WARNING("[VISUAL] HERE 1.", true);
        String rawData = msg.getRawData();
        
        //VA_DEBUG.WARNING("[VISUAL] Process image buffer("+rawData+").", true);
        // TODO: process image
        // TODO: detect person
        //
        BufferedImage imagebuff = TCPManager.decodeToImage(rawData);
        Graphics2D g2d = imagebuff.createGraphics();
        g2d.setColor(Color.red);
        g2d.fill(new Ellipse2D.Float(0, 0, 200, 100));
        g2d.dispose();
        
        String rawData2 = TCPManager.encodeToString(imagebuff, "jpg");
        {
            Map<String, String> data = new HashMap<>();
            data.put("value", rawData2);
            VA_DEBUG.SOCKET_MSG(20, "image", data);
        }
        {
            Map<String, String> data = new HashMap<>();
            data.put("image", rawData2);
            data.put("firstname", "Adrian");
            data.put("lastname", "Simionescu");
            VA_DEBUG.SOCKET_MSG(30, "profile", data);
        }
        
//        PersonDetectedRequest reply = new PersonDetectedRequest();
//        reply.setSource(MyApplicationId.VISUAL);
//        reply.setTarget(MyApplicationId.MEMORY);
//        reply.setTransactionId(msg.getTransactionId());
//        
//        manager.send(reply);

        return true;
    }
    
    private boolean _handlePersonDetectedCommand(ATPMsg msg)
    {
        ChannelManager manager = ChannelManager.getInstance();
        
        if (manager == null)
        {
            VA_DEBUG.WARNING("[VISUAL] ChannelManager is null.", true);
            return false;
        }
        
        if (!manager.isClientRegistered(MyApplicationId.MEMORY))
        {
            VA_DEBUG.WARNING("[VISUAL] MEMORY is not registered.", true);
            return false;
        }
        
        PersonDetectedRequest reply = new PersonDetectedRequest();
        reply.setSource(MyApplicationId.VISUAL);
        reply.setTarget(MyApplicationId.MEMORY);
        reply.setTransactionId(msg.getTransactionId());
        
        manager.send(reply);

        return true;
    }
    
    private boolean _handlePersonDetectedResponse(ATPMsg msg)
    {
        ChannelManager manager = ChannelManager.getInstance();
        
        if (manager == null)
        {
            VA_DEBUG.WARNING("[VISUAL] ChannelManager is null.", true);
            return false;
        }
        
        if (!manager.isClientRegistered(MyApplicationId.MEMORY))
        {
            VA_DEBUG.WARNING("[VISUAL] MEMORY is not registered.", true);
            return false;
        }
        
        //PersonDetectedRequest msg2 = new PersonDetectedRequest();
        //msg2.setSource(ApplicationId.VISUAL);
        //msg2.setTarget(ApplicationId.MEMORY);
        
        //manager.send(msg2);

        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onoffState(OnOffState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
