/*
 * DemoClient.java
 *
 * Created on January 23, 2007, 10:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.olin.blink.network.demo;

import edu.olin.blink.network.*;
import java.io.IOException;

/**
 *
 * @author jstanton
 */
public class DemoClient {
    
    public String getSimpleResponse(String remoteHost, int remotePort){
        BufferedSocket bs = null;
        String rtn = null;
        try{
            bs = new BufferedSocket(remoteHost,remotePort); //connect to the server
            rtn = bs.readUTF(); //get the server response
        }catch(IOException ioe){
            System.err.println("Could not connect: "+ioe);
        }finally{
            try{
                if(bs!=null){ bs.close(); } //clean up
            }catch(IOException ioe){
                System.err.println("Could not close socket: "+ioe);
            }
        }
        return rtn;
    }
    
}
