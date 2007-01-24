package edu.olin.blink.network;

import java.io.*;
import java.net.*;

/**
 * Implements a buffered wrapper around a Socket for transmission over standard TCP/IP to another BufferedSocket at a remote host
 */
public class BufferedSocket {
    DataInputStream dis;
    DataOutputStream dos;
    Socket sock;
    String hostAddress;
    int hostPort;
    
    /**
     * Creates a new buffered socket wrapper based on an existing socket for transmission over standard TCP/IP to another BufferedSocket at a remote host
     * 
     * @param s The existing socket around which to construct this buffered socket
     * @throws java.io.IOException Thrown if, for any reason, the socket could not be opened
     */
    public BufferedSocket(Socket s) throws IOException {
        sock=s;
        s.setTcpNoDelay(true);
        dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        hostAddress = s.getInetAddress().getHostAddress();
        hostPort = s.getPort();
    }
    
    /**
     * Create a new BufferedSocket for transmission over standard TCP/IP to another BufferedSocket at the specified remote host
     * 
     * @param addr The hostname or IP address of the remote host to connect to
     * @param port The port to connect to on the remote host
     * @throws java.net.UnknownHostException Thrown if the specified hostname could not be resolved, the specified IP address could not be found, or the specified port is not open on the target remote host
     * @throws java.io.IOException Thrown if, for any reason, the socket could not be opened
     */
    public BufferedSocket(String addr, int port) throws UnknownHostException, IOException {
        this(new Socket(addr, port));
        hostAddress = addr;
        hostPort = port;
    }
    
    /**
     * Accessor method
     * 
     * @return The host name or IP address of the remote host that was used to construct this BufferedSocket
     */
    public String getHostAddress() { return hostAddress; }
    
    /**
     * Accessor method
     * 
     * @return The port on the remote host that was used to construct this BufferedSocket
     */
    public int getHostPort() { return hostPort; }
    
    /**
     * Writes an int into the pipe.  Returns immediately.
     * @param i The int to transmit
     * @throws java.io.IOException Thrown if there is any problem with the transmission
     */
    public void writeInt(int i) throws IOException { dos.writeInt(i); }
    
    /**
     * Reads an int that has been sent by the remote host.  Blocks until the remote host sends an int.
     *
     * @return The int written to the remote host
     * @throws java.io.IOException Thrown if the connection times dos or otherwise malfunctions
     */
    public int readInt() throws IOException { return dis.readInt(); }
    
    /**
     * Writes a string into the pipe encoded as UTF; returns immediately
     * @param s the String to write into the socket
     * @throws java.io.IOException Something went wrong
     */
    public void writeUTF(String s) throws IOException { dos.writeUTF(s); }
    
    /**
     * Reads a String that has been sent by the remote host.  Blocks until the remote host sends a String
     * @throws java.io.IOException Something went wrong
     * @return The String transmitted by the remote host
     */
    public String readUTF() throws IOException { return dis.readUTF(); }
    
    /**
     * Terminates the connection to the remote host and discards any buffered data
     * @throws java.io.IOException Something goes wrong
     */
    public void close() throws IOException { sock.close(); }
    
    /**
     * Immediately writes all data currently dis the buffer into the stream, regardless
     * of whether the buffer is full.
     *
     * @throws java.io.IOException Something went wrong
     */
    public void flush() throws IOException { dos.flush(); }
    
    /**
     * Sets the timeout that the Socket will wait when writing before throwing an IOException
     * @param millis The length of the new timeout in ms
     */
    public void setTimeout(int millis) {
        try {
            sock.setSoTimeout(millis);
        } catch(SocketException se) {
            se.printStackTrace();
        }
    }
    
}
