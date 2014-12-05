package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.JTextComponent;

public class CreateCharacter implements ActionListener{

    private JFrame FRAME = new JFrame("Create Character");
    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private Border TEXT_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private JPanel BASE;
    private JPanel PRESET_PANEL;
    private ButtonGroup PRESET_BUTTONS;
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
        formatPresets();
        formatFrame();
    }
    
    private void formatPresets() {
        PRESET_PANEL = new JPanel();
        PRESET_PANEL.setBounds(100, 2, 100, 200);
        PRESET_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Presets"));
        
        PRESET_BUTTONS = new ButtonGroup();
        File presetFile = new File("src/GUI/ClassPresets.tgs");
        JRadioButton currentButton = new JRadioButton("Knight");
        
        PRESET_BUTTONS.add(currentButton);
        currentButton.addActionListener(this);
        PRESET_PANEL.add(currentButton);
    }
    
    private String getLineElement(String line) {
        String output = "";
        Scanner lineScanner = new Scanner(line);
        boolean first = true;
        while (lineScanner.hasNext()) {
            String current = lineScanner.next();
            //System.out.println(current);
            if (!current.contains("*") && first) {
                output += current;
                first = false;
            } else if (!current.contains("*")) {
                output += " " + current;
            }
        }
        //System.out.println(output);
        return output;
    }

    private void formatAllStats() {
        int displaySize = 1;

        SET_SPEED = new JPanel();
        SPEED_DISPLAY = new JTextField(displaySize);
        formatStatPanel(2, 2, SET_SPEED, SPEED_DISPLAY, SPEED, "Speed");

        SET_ENDURANCE = new JPanel();
        ENDURANCE_DISPLAY = new JTextField(displaySize);
        formatStatPanel(2, 53, SET_ENDURANCE, ENDURANCE_DISPLAY, ENDURANCE, "Endurance");

        SET_STRENGTH = new JPanel();
        STRENGTH_DISPLAY = new JTextField(displaySize);
        formatStatPanel(2, 104, SET_STRENGTH, STRENGTH_DISPLAY, STRENGTH, "Strength");

        SET_INTELLIGENCE = new JPanel();
        INTELLIGENCE_DISPLAY = new JTextField(displaySize);
        formatStatPanel(2, 155, SET_INTELLIGENCE, INTELLIGENCE_DISPLAY, INTELLIGENCE, "Intelligence");

        SET_DEXTERITY = new JPanel();
        DEXTERITY_DISPLAY = new JTextField(displaySize);
        formatStatPanel(2, 206, SET_DEXTERITY, DEXTERITY_DISPLAY, DEXTERITY, "Dexterity");
    }

    private void formatStatPanel(int x, int y, JPanel panel, JTextField display, int stat, String statName) {
        panel.setBounds(x, y, 85, 50);
        panel.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, statName));

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
        FRAME.add(PRESET_PANEL);

        BASE = new JPanel();
        BASE.setBorder(PANEL_BORDER);
        FRAME.add(BASE);

        FRAME.setSize(300, 300);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //PLAYER_NAME.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object performer = ae.getSource();
        if(performer instanceof JRadioButton) {
            JRadioButton button = (JRadioButton)(performer);
            System.out.println(button.getLabel());
        }
    }
}
