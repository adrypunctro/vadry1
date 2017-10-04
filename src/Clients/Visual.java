package Clients;

import Messages.ATPMsg;
import Messages.MyMessageType;
import Messages.PersonDetectedRequest;
import System.MyApplicationId;
import System.ChannelManager;
import System.Client;
import System.Monitor;
import System.OnOffState;
import System.VA_DEBUG;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;


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
