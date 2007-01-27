/*
 * DemoMain.java
 *
 * Created on January 23, 2007, 10:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.olin.blink.network.demo;

import edu.olin.blink.network.*;

/**
 *
 * @author jstanton
 */
public class DemoMain {
    
    public static void main(String[] args){
        DemoServer ds = new DemoServer(34344); //start a listening server
        DemoClient dc = new DemoClient(); //create a client
        System.out.println(dc.getSimpleResponse("127.0.0.1",34344)); //connect to server, get response, disconnect
        ds.stop(); //close the server
        System.exit(0);
        }
    
}
