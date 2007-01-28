package edu.olin.blink.network.writer;

import edu.olin.blink.LightLevelCue;
import edu.olin.blink.network.BufferedSocket;
import java.io.IOException;

/**
 * Class for serializing a LightLevelCue and transporting it over a BufferedSocket to a remote host.
 * @author jstanton
 */
public class LightLevelCueWriter {
    
    /**
     * The BufferedSocket into which data will be written
     */
    private BufferedSocket bs = null;
    
    /**
     * Creates a new instance of LightLevelCueWriter.  The BufferedSocket must already have a connection to the remote host - LightLevelCueWriter does not manage the socket connection.
     * @param bs The BufferedSocket in which to write data
     */
    public LightLevelCueWriter(BufferedSocket bs) {
        if(bs==null){ throw new IllegalArgumentException("Cannot write to a null socket"); }
        this.bs = bs;
    }
    
    /**
     * Writes the given LightLevelCue into the socket
     * @param cue The LightLevelCue to send over the socket
     * @return True if the data was successfully sent
     */
    public boolean write(LightLevelCue cue){
        if(cue==null){ return false; }
        return write(cue.getTime(),0,cue.getLevel());
    }
    
    /**
     * Writes the given parameters of a LightLevelCue across the network
     * @param time The time associated with the LightLevelCue
     * @param channel The channel associated with the LightLevelCue
     * @param level The level associated with the LightLevelCue
     * @return True if the data was successfully sent
     */
    private boolean write(int time, int channel, int level){
        if(!bs.isConnected()){ throw new IllegalStateException("Cannot write to a socket that is not open!"); }
        String serialized = "CUE "+time+" "+channel+" "+level;
        try{
            bs.writeUTF(serialized);
        }catch(IOException ioe){
            System.err.println("Could not write the Cue: "+ioe);
            return false;
        }
        return true;
    }
    
}
