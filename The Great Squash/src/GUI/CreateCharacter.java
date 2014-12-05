package GUI;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.JTextComponent;

public class CreateCharacter {
    private JFrame FRAME = new JFrame("Create Character");
    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private JPanel BASE;
    private JPanel SET_SPEED;
    private int SPEED = 1;
    private JTextField SPEED_DISPLAY;
    private JPanel SET_ENDURANCE;
    private int ENDURANCE = 1;
    private JTextField ENDURANCE_DISPLAY;
    private JPanel SET_STRENGTH;
    private int STRENGTH = 1;
    private JTextField STRENGTH_DISPLAY;
    private JPanel SET_INTELLIGENCE;
    private int INTELLIGENCE = 1;
    private JTextField INTELLIGENCE_DISPLAY;
    private JPanel SET_DEXTERITY;
    private int DEXTERITY = 1;
    private JTextField DEXTERITY_DISPLAY;
    
    public CreateCharacter() {
        formatAllStats();
        formatFrame();
    }
    
    private void formatAllStats() {
        SET_SPEED = new JPanel();
        SPEED_DISPLAY = new JTextField(1);
        formatStatPanel(1,1,SET_SPEED,SPEED_DISPLAY,SPEED);
        
        SET_ENDURANCE = new JPanel();
        ENDURANCE_DISPLAY = new JTextField(1);
        formatStatPanel(1,52,SET_ENDURANCE,ENDURANCE_DISPLAY,ENDURANCE);
        
        SET_STRENGTH = new JPanel();
        STRENGTH_DISPLAY = new JTextField(1);
        formatStatPanel(1,103,SET_STRENGTH,STRENGTH_DISPLAY,STRENGTH);
        
        SET_INTELLIGENCE = new JPanel();
        INTELLIGENCE_DISPLAY = new JTextField(1);
        formatStatPanel(1,154,SET_INTELLIGENCE,INTELLIGENCE_DISPLAY,INTELLIGENCE);
        
        SET_DEXTERITY = new JPanel();
        DEXTERITY_DISPLAY = new JTextField(1);
        formatStatPanel(1,205,SET_DEXTERITY,DEXTERITY_DISPLAY,DEXTERITY);
    }
    
    private void formatStatPanel(int x, int y, JPanel panel, JTextField display, int stat) {
        panel.setBounds(x, y, 150, 50);
        panel.setBorder(PANEL_BORDER);
        
        display.setEditable(false);
        display.setText(stat + "");
        display.setBackground(Color.WHITE);
        
        panel.add(display);
    }
    
    private void formatFrame() { 
        FRAME.add(SET_SPEED);
        FRAME.add(SET_ENDURANCE);
        FRAME.add(SET_STRENGTH);
        FRAME.add(SET_INTELLIGENCE);
        FRAME.add(SET_DEXTERITY);
        
        BASE = new JPanel();
        FRAME.add(BASE);

        FRAME.setSize(300, 300);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //PLAYER_NAME.requestFocusInWindow();
    }
}
