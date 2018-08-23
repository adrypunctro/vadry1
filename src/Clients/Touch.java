/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Messages.ATPMsg;
import Defines.MyMessageType;
import System.ChannelManager;
import System.Client;
import Defines.MyApplicationId;
import System.OnOffState;
import System.VA_DEBUG;
import java.util.Observable;

/**
 *
 * @author ASimionescu
 */
public class Touch
    extends Client
{

    public Touch()
    {
        super(MyApplicationId.TOUCH);
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
        VA_DEBUG.INFO("[TOUCH] handleRequest("+msg.getMsgType().name()+")", true);
        switch((MyMessageType)msg.getMsgType())
        {
            case touchDetectedCommand:
                _handleTouchDetectedCommand(msg);
                break;
            
            default:
                VA_DEBUG.WARNING("[TOUCH] unknown message type received("+msg.getMsgType().name()+")", true);
                return false;
        }
        
        return true;
    }
    
    private boolean _handleTouchDetectedCommand(ATPMsg msg)
    {
        ChannelManager manager = ChannelManager.getInstance();
        
        if (manager == null)
        {
            VA_DEBUG.WARNING("[TOUCH] ChannelManager is null.", true);
            return false;
        }
        
        if (!manager.isClientRegistered(MyApplicationId.MEMORY))
        {
            VA_DEBUG.WARNING("[TOUCH] MEMORY is not registered.", true);
            return false;
        }
        
        //PersonDetectedRequest msg2 = new PersonDetectedRequest();
        //msg2.setSource(ApplicationId.VISUAL);
        //msg2.setTarget(ApplicationId.MEMORY);
        
        //int transId = manager.send(msg2);

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
