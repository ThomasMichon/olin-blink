package edu.olin.blink.client;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author jstanton
 */
public class LookAndFeelEnumerator {
    
    public static void main(String[] args){
        LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
        for(int i=0; i<lafs.length; i++){
            System.out.println(i+"\t"+lafs[i]);
            }
        }
    
}
