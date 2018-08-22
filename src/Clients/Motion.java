/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Messages.ATPMsg;
import Messages.MessageResponse;
import Messages.MyMessageType;
import System.ChannelManager;
import System.Client;
import System.MyApplicationId;
import System.OnOffState;
import System.VA_DEBUG;
import java.util.Observable;

/**
 *
 * @author ASimionescu
 */
public class Motion
    extends Client
{
    public enum Gesture
    {
        Head_Yes, Head_No
    }

    public Motion()
    {
        super(MyApplicationId.MOTION);
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
        VA_DEBUG.INFO("[MOTION] handleRequest("+msg.getMsgType().name()+")", true);
        switch((MyMessageType)msg.getMsgType())
        {
            case headGestureYes:
                _handleHeadGestureCommand(msg, Gesture.Head_Yes);
                break;
                
            case headGestureNo:
                _handleHeadGestureCommand(msg, Gesture.Head_No);
                break;
            
            default:
                VA_DEBUG.WARNING("[MOTION] unknown message type received("+msg.getMsgType().name()+")", true);
                return false;
        }
        
        return true;
    }
    
    private boolean _handleHeadGestureCommand(ATPMsg msg, Gesture gest)
    {
        ChannelManager manager = ChannelManager.getInstance();
        
        if (manager == null)
        {
            VA_DEBUG.WARNING("[MOTION] ChannelManager is null.", true);
            return false;
        }
        
        if (!manager.isClientRegistered(MyApplicationId.MEMORY))
        {
            VA_DEBUG.WARNING("[TOUCH] MEMORY is not registered.", true);
            return false;
        }
        
        // TODO: Proceed head gesture
        if (gest == Gesture.Head_Yes)
        {
            System.out.println("Head YES!");
            try { Thread.sleep(5000); } catch (InterruptedException ex) { }
        }
        else
        if(gest == Gesture.Head_No)
        {
            System.out.println("Head No!");
            try { Thread.sleep(5000); } catch (InterruptedException ex) { }
        }
        // TODO: Wait to be done

        // Send status response
        MessageResponse msg2 = new MessageResponse();
        msg2.setSource(MyApplicationId.MOTION);
        msg2.setTarget(msg.getSourceId());
        
        manager.send(msg2);

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
