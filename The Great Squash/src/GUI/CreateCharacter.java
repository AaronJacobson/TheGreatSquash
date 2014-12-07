package GUI;

import gameworld.Player;
import gameworld.tools.CreateFromDocument;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class CreateCharacter implements ActionListener, KeyListener {

    private JFrame FRAME = new JFrame("Create Character");
    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private Border TEXT_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private JPanel BASE;
    private JPanel PRESET_PANEL;
    private ButtonGroup PRESET_BUTTONS;
    private Hashtable<String, ClassPreset> PRESET_TABLE;
    private ArrayList<String> PRESET_NAMES;
    private JPanel SPECIES_PANEL;
    private ButtonGroup SPECIES_BUTTONS;
    private String SPECIES;
    private JPanel SPEED_PANEL;
    private JTextField SPEED_DISPLAY;
    private JPanel ENDURANCE_PANEL;
    private JTextField ENDURANCE_DISPLAY;
    private JPanel HEALTH_PANEL;
    private JTextField HEALTH_DISPLAY;
    private JPanel STRENGTH_PANEL;
    private JTextField STRENGTH_DISPLAY;
    private JPanel INTELLIGENCE_PANEL;
    private JTextField INTELLIGENCE_DISPLAY;
    private JPanel DEXTERITY_PANEL;
    private JTextField DEXTERITY_DISPLAY;
    private JPanel NAME_PANEL;
    private JTextField NAME_DISPLAY;
    private JPanel CLASS_PANEL;
    private JTextField CLASS_DISPLAY;
    private JPanel SPRITE_PANEL;
    private JTextField SPRITE_DISPLAY;
    private JPanel CREATE_PANEL;
    private JButton CREATE_BUTTON;

    public CreateCharacter() {
        formatClassPresets();
        formatAllStats();
        formatSpecies();
        formatFrame();
    }

    private void formatSpecies() {
        SPECIES_PANEL = new JPanel();
        SPECIES_PANEL.setBounds(485, 2, 107, 148);
        SPECIES_PANEL.setLayout(new GridLayout(0, 1));
        SPECIES_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Species"));

        SPECIES_BUTTONS = new ButtonGroup();
        try {
            Scanner fileScanner = new Scanner(new File("src/GUI/PlayableSpecies.tgs"));
            while(fileScanner.hasNextLine()) {
            JRadioButton current = new JRadioButton(fileScanner.nextLine());
            current.addActionListener(this);
            SPECIES_BUTTONS.add(current);
            SPECIES_PANEL.add(current);
            }
        } catch (FileNotFoundException ex) {

        }

    }

    private void formatClassPresets() {
        makePresetList();
//        System.out.println(PRESET_TABLE.get("Knight"));
        PRESET_PANEL = new JPanel();
        PRESET_PANEL.setBounds(85, 52, 320, 98);
        PRESET_PANEL.setLayout(new GridLayout(0, 3));
        PRESET_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Presets"));

        PRESET_BUTTONS = new ButtonGroup();
        for (int i = 0; i < PRESET_TABLE.size(); i++) {
            ClassPreset preset = PRESET_TABLE.get(PRESET_NAMES.get(i));
            JRadioButton currentButton = new JRadioButton(preset.getName());
            currentButton.addActionListener(this);
            PRESET_BUTTONS.add(currentButton);
            PRESET_PANEL.add(currentButton);
        }
    }

    private void makePresetList() {
        PRESET_TABLE = new Hashtable<String, ClassPreset>();
        PRESET_NAMES = new ArrayList<String>();
        try {
            File file = new File("src/GUI/ClassPresets.tgs");
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String classStats = "";
                for (int i = 0; i <= 5; i++) {
                    classStats += fileScanner.nextLine() + "\n";
                }
                ClassPreset preset = new ClassPreset(classStats);
                PRESET_TABLE.put(preset.getName(), preset);
                PRESET_NAMES.add(preset.getName());
            }
        } catch (FileNotFoundException ex) {

        }
    }

    private void formatAllStats() {
        SPEED_PANEL = new JPanel();
        SPEED_DISPLAY = new JTextField(1);
        SPEED_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(2, 2, 85, SPEED_PANEL, SPEED_DISPLAY, "1", "Speed");

        ENDURANCE_PANEL = new JPanel();
        ENDURANCE_DISPLAY = new JTextField(1);
        ENDURANCE_DISPLAY.addKeyListener(this);
        ENDURANCE_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(2, 50, 85, ENDURANCE_PANEL, ENDURANCE_DISPLAY, "1", "Endurance");

        HEALTH_PANEL = new JPanel();
        HEALTH_DISPLAY = new JTextField(2);
        HEALTH_DISPLAY.setEditable(false);
        HEALTH_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(2, 98, 85, HEALTH_PANEL, HEALTH_DISPLAY, "15", "Health");

        STRENGTH_PANEL = new JPanel();
        STRENGTH_DISPLAY = new JTextField(1);
        STRENGTH_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(403, 2, 85, STRENGTH_PANEL, STRENGTH_DISPLAY, "1", "Strength");

        INTELLIGENCE_PANEL = new JPanel();
        INTELLIGENCE_DISPLAY = new JTextField(1);
        INTELLIGENCE_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(403, 50, 85, INTELLIGENCE_PANEL, INTELLIGENCE_DISPLAY, "1", "Intelligence");

        DEXTERITY_PANEL = new JPanel();
        DEXTERITY_DISPLAY = new JTextField(1);
        DEXTERITY_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(403, 98, 85, DEXTERITY_PANEL, DEXTERITY_DISPLAY, "1", "Dexterity");

        NAME_PANEL = new JPanel();
        NAME_DISPLAY = new JTextField(7);
        NAME_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(85, 2, 100, NAME_PANEL, NAME_DISPLAY, "", "Name");

        CLASS_PANEL = new JPanel();
        CLASS_DISPLAY = new JTextField(7);
        CLASS_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        formatStatPanel(182, 2, 100, CLASS_PANEL, CLASS_DISPLAY, "", "Class");

        SPRITE_PANEL = new JPanel();
        SPRITE_DISPLAY = new JTextField(2);
        SPRITE_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        SPRITE_DISPLAY.setFont(new Font("Monospaced", Font.PLAIN, 12));
        formatStatPanel(280, 2, 60, SPRITE_PANEL, SPRITE_DISPLAY, "@", "Sprite");

        CREATE_PANEL = new JPanel();
        CREATE_PANEL.setBounds(336, 10, 70, 52);
        CREATE_BUTTON = new JButton("Create");
        CREATE_BUTTON.setPreferredSize(new Dimension(50, 30));
        CREATE_BUTTON.setMargin(new Insets(0, 0, 0, 0));

        CREATE_PANEL.add(CREATE_BUTTON);
    }

    private void formatStatPanel(int x, int y, int sizeX, JPanel panel, JTextField display, String stat, String statName) {
        panel.setBounds(x, y, sizeX, 52);
        panel.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, statName));

        display.setText(stat);
        display.setBackground(Color.WHITE);

        panel.add(display);
    }

    private void formatFrame() {
        FRAME.add(SPEED_PANEL);
        FRAME.add(ENDURANCE_PANEL);
        FRAME.add(HEALTH_PANEL);
        FRAME.add(STRENGTH_PANEL);
        FRAME.add(INTELLIGENCE_PANEL);
        FRAME.add(DEXTERITY_PANEL);
        FRAME.add(PRESET_PANEL);
        FRAME.add(NAME_PANEL);
        FRAME.add(CLASS_PANEL);
        FRAME.add(SPRITE_PANEL);
        FRAME.add(CREATE_PANEL);
        FRAME.add(SPECIES_PANEL);

        BASE = new JPanel();
        BASE.setBorder(PANEL_BORDER);
        FRAME.add(BASE);

        FRAME.setSize(600, 179);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NAME_DISPLAY.requestFocusInWindow();
        GameGUI.centerWindow(FRAME);
    }

    public void actionPerformed(ActionEvent e) {
        Object performer = e.getSource();
        if (performer instanceof JRadioButton) {
            JRadioButton button = (JRadioButton) (performer);
            if (button.getParent() == PRESET_PANEL) {
                ClassPreset preset = PRESET_TABLE.get(button.getLabel());
                if (!preset.getName().equals("[N/A]")) {
                    CLASS_DISPLAY.setText(preset.getName());
                } else {
                    CLASS_DISPLAY.setText("");
                }
                SPEED_DISPLAY.setText(preset.getSpeed() + "");
                ENDURANCE_DISPLAY.setText(preset.getEndurance() + "");
                HEALTH_DISPLAY.setText((10 + (preset.getEndurance() * 5)) + "");
                STRENGTH_DISPLAY.setText(preset.getStrength() + "");
                INTELLIGENCE_DISPLAY.setText(preset.getIntelligence() + "");
                DEXTERITY_DISPLAY.setText(preset.getDexterity() + "");
            } else if (button.getParent() == SPECIES_PANEL) {
                SPECIES = button.getLabel();
            }
        } else if(performer instanceof JButton) {
            Player player = new Player(NAME_DISPLAY.getText(), CLASS_DISPLAY.getText(), SPECIES, SPRITE_DISPLAY.getText().charAt(0));
            int speed = Integer.parseInt(SPEED_DISPLAY.getText());
            int endurance = Integer.parseInt(ENDURANCE_DISPLAY.getText());
            int strength = Integer.parseInt(STRENGTH_DISPLAY.getText());
            int intelligence = Integer.parseInt(INTELLIGENCE_DISPLAY.getText());
            int dexterity = Integer.parseInt(DEXTERITY_DISPLAY.getText());
            player.initateStats(speed, endurance, strength, intelligence, dexterity);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        Object performer = e.getSource();
        if (performer instanceof JTextField) {
            try {
                JTextField text = (JTextField) (performer);
//                System.out.println(text.getText());
                HEALTH_DISPLAY.setText("" + (10 + (Integer.parseInt(text.getText()) * 5)));

            } catch (NumberFormatException ex) {
//                System.out.println("Exception");
                HEALTH_DISPLAY.setText("0");
            }
        }
    }

    private class ClassPreset {

        private String CLASS_NAME;
        private int SPEED;
        private int ENDURANCE;
        private int STRENGTH;
        private int INTELLIGENCE;
        private int DEXTERITY;

        public ClassPreset(String stats) {
            Scanner scanPreset = new Scanner(stats);
            CLASS_NAME = CreateFromDocument.getLineElement(scanPreset.nextLine());
            SPEED = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            ENDURANCE = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            STRENGTH = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            INTELLIGENCE = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            DEXTERITY = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
//            System.out.println(CreateFromDocument.getLineElement(scanPreset.nextLine()));
        }

        public String getName() {
            return CLASS_NAME;
        }

        public int getSpeed() {
            return SPEED;
        }

        public int getEndurance() {
            return ENDURANCE;
        }

        public int getStrength() {
            return STRENGTH;
        }

        public int getIntelligence() {
            return INTELLIGENCE;
        }

        public int getDexterity() {
            return DEXTERITY;
        }
    }
}
