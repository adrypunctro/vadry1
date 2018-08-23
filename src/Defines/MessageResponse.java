/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Defines;

import Messages.ATPMsg;

/**
 *
 * @author ASimionescu
 */
public class MessageResponse
    extends ATPMsg
{
    
    public MessageResponse() {
        super(MyMessageType.messageResponse);
    }
    
}
