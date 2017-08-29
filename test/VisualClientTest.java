/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import System.ChannelManager;
import Clients.Visual;
import System.Client;
import System.MyApplicationId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ASimionescu
 */
public class VisualClientTest {
    
    public VisualClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void registerTest()
    {
        Visual visual = new Visual();
        
        assertTrue("Failed registerClient.", visual.registerClient());
        assertTrue("Failed isClientRegistered. Expected true.", ChannelManager.getInstance().isClientRegistered(MyApplicationId.VISUAL));
        
        Client client = ChannelManager.getInstance().getClient(MyApplicationId.VISUAL);
        assertEquals("Wrong client AppId.", client.getAppId(), MyApplicationId.VISUAL);
        assertTrue("Wrong client instance type.", client instanceof Visual);
        
        assertTrue("Failed unregisterClient.", visual.unregisterClient());
        assertFalse("Failed isClientRegistered. Expected false.", ChannelManager.getInstance().isClientRegistered(MyApplicationId.VISUAL));
        
    }
}
