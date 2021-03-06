package Clients;

import Messages.ATPMsg;
import Defines.MyMessageType;
import Defines.PersonDetectedResponse;
import Defines.MyApplicationId;
import System.ChannelManager;
import System.Client;
import System.SensorHandler;
import System.OnOffState;
import System.VA_DEBUG;
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
public class Memory
    extends Client
{
    public Memory() {
        super(MyApplicationId.MEMORY);
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
        VA_DEBUG.INFO("[MEMORY] handleRequest("+msg.getMsgType().name()+")", true);
        switch((MyMessageType)msg.getMsgType())
        {
//            case personDetectedRequest:
//                _handlePersonDetectedRequest(msg);
//                break;
            
            default:
                VA_DEBUG.WARNING("[MEMORY] unknown message type received("+msg.getMsgType().name()+")", true);
                return false;
        }
        
        //return true;
    }
    
    private boolean _handlePersonDetectedRequest(ATPMsg msg)
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
        
        PersonDetectedResponse reply = new PersonDetectedResponse();
        reply.setSource(MyApplicationId.MEMORY);
        reply.setTarget(msg.getSourceId());
        reply.setTransactionId(msg.getTransactionId());
        
        manager.send(reply);

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
