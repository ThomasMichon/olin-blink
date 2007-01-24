/*
 * DemoServer.java
 *
 * Created on January 23, 2007, 9:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.olin.blink.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author jstanton
 */
public class DemoServer implements Runnable {
    
    private ArrayList clients = new ArrayList(); //the client connections we currently have
    private ServerSocket ss; //the thing looking for connections
    private int port = 42400;
    private boolean running = false;
    
    public DemoServer(int port){
        this.port = port;
        try{ ss = new ServerSocket(port); //start listening on the specified port
        }catch(Exception e){ throw new RuntimeException("Unable to create server socket");
        }
        (new Thread(this)).start(); //run a separate thread to listen for connections
    }
    
    public void stop(){ //cease running the server
        running = false;
        if(ss!=null && !ss.isClosed()){
            try{ ss.close(); }catch(IOException ioe){ System.err.println("Could not close ServerSocket: "+ioe); }
        }
    }
    
    public void run() {
        running = true;
        System.out.println("Server listening on port "+port);
        while(running){ //listen for connections until stopped
            Thread.yield(); //don't be a processor hog - let others do something.
            BufferedSocket s;
            try{
                s = new BufferedSocket(ss.accept()); //construct a BufferedSocket to communicate with the client
            }catch(IOException e){ continue; /*no incoming connections; continue loop*/
            }
            try{
                String name = "client"+System.currentTimeMillis();//s.readUTF();
                /* next I just write out the client's host back to the client and then close
                 * in an actual server, you would start a new thread to read/write this socket
                 * so that the server can take more connections
                 */
                s.writeUTF("you are "+s.getHostAddress()+":"+s.getHostPort());
                s.flush(); //force the data to write
                s.close(); //close the connection; prepare to wait for another
            }catch(Exception e) {
                System.err.println("Server: New client failed: "+e);
                continue;
            }
        }
    }
}
