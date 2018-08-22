/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.*;

/**
 *
 * @author adryp
 */
public class Configurator
{
    
    boolean load()
    {
        
        try
        {
            File file = new File("src/Assets/config.json");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            
            String buff = new String(data, "UTF-8");
            obj = new JSONObject(buff);
        }
        catch(IOException e)
        {
            VA_DEBUG.WARNING("[Configurator] "+e.getMessage(), true);
            return false;
        }
        
        return true;
    }
    
    String GetString(String key)
    {
        return obj.getString(key);
    }
    
    int GetInt(String key)
    {
        return obj.getInt(key);
    }
    
    boolean GetBool(String key)
    {
        return obj.getBoolean(key);
    }
    
    double getDouble(String key)
    {
        return obj.getDouble(key);
    }
    
    private JSONObject obj = null;
}
