package GUI.tests;

import GUI.listeners.MovementListener;
import LAN.Client;
import gameworld.Board;
import gameworld.Creature;
import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author ros_dmlamarca
 */
public class TestMovementGUI {
    private JTextArea DISPLAY_AREA = new JTextArea();
    private JPanel DISPLAY_PANEL = new JPanel();
    private MovementListener MOVEMENT_LISTENER;
    private JFrame FRAME = new JFrame("Board Test");
    private JPanel BASE = new JPanel();   
    private Creature CREATURE;
    private Board BOARD;
    
    public TestMovementGUI() {
        BOARD = new Board(21,61);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestMovementGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        MOVEMENT_LISTENER = new MovementListener(this);
        formatDisplay();
        formatFrame();
    }
    
    private void formatDisplay() {
        DISPLAY_PANEL.setBounds(0,0,500,400);
        
        DISPLAY_AREA.setColumns(61);
        DISPLAY_AREA.setRows(21);
        DISPLAY_AREA.setPreferredSize(new Dimension(61,21));
        DISPLAY_AREA.setEditable(false);
        DISPLAY_AREA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        DISPLAY_AREA.addKeyListener(MOVEMENT_LISTENER);
        DISPLAY_AREA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        DISPLAY_PANEL.addKeyListener(MOVEMENT_LISTENER);
        DISPLAY_PANEL.add(DISPLAY_AREA);
    }
    
    private void formatFrame() {
        BASE.setBorder(BorderFactory.createBevelBorder(EtchedBorder.LOWERED));
        BASE.addKeyListener(MOVEMENT_LISTENER);
        
        FRAME.addKeyListener(MOVEMENT_LISTENER);
        FRAME.add(DISPLAY_PANEL);
        FRAME.add(BASE);
        
        FRAME.setSize(new Dimension(500,400));
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setVisible(true);
    }
    
    public JTextArea getDisplay() {
        return DISPLAY_AREA;
    }
    
    public Board getBoard(){
        return BOARD;
    }
    
    public void setCreature(Creature creature){
        CREATURE = creature;
        MOVEMENT_LISTENER.setCreature(creature);
    }
}
