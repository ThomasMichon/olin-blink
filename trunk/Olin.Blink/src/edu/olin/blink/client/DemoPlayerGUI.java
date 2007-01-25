package edu.olin.blink.client;

import edu.olin.blink.Cue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A simple demonstration of the DemoPlayer, with a nice GUI
 * @author jstanton
 */
public class DemoPlayerGUI extends JFrame {
    
    private JPanel blinker;
    
    /**
     * Creates a new instance of DemoPlayerGUI
     */
    public DemoPlayerGUI() {
        setTitle("Blinker Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0,0));
        blinker = new JPanel();
        blinker.setDoubleBuffered(true);
        blinker.setOpaque(true);
        blinker.setBackground(Color.BLACK);
        blinker.setVisible(false);
        getContentPane().add(blinker,BorderLayout.CENTER);
        pack();
        setSize(100,100);
        setLocationRelativeTo(null);
    }
    
    public void flip(){
        blinker.setVisible(!blinker.isVisible());
    }
    
    public static void main(String[] args){
        DemoPlayerGUI flipper = new DemoPlayerGUI();
        flipper.setVisible(true);
        List<Cue> cues = new LinkedList<Cue>();
        for (int i = 0; i < 20; i ++)
            cues.add(new FlipGUICue(flipper,i * 500 + 2000, i));
        CuePlayer player = new CuePlayer();
        player.setCues(cues);
        player.start();
    }
    
    private static class FlipGUICue extends Cue {
        private int id;
        private DemoPlayerGUI flipper;
        public FlipGUICue(DemoPlayerGUI flipper, int time, int id) {
            super(time);
            this.id = id;
            this.flipper = flipper;
        }
        
        public void run() {
            flipper.flip();
        }
    }
    
}
