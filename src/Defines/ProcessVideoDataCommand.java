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
public class ProcessVideoDataCommand
    extends ATPMsg
{
    private String rawData;
    
    public ProcessVideoDataCommand() {
        super(MyMessageType.processVideoDataCommand);
    }

    /**
     * @return the rawData
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * @param rawData the rawData to set
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
    
    
}
