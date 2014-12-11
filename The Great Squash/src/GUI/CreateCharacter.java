package GUI;

//import Main.tests.PlayerSaveTest;
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
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class CreateCharacter implements ActionListener, KeyListener {

    private JFrame FRAME = new JFrame("Create Character");
    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private JPanel BASE;
    private JPanel PRESET_PANEL;
    private ButtonGroup PRESET_BUTTONS;
    private Hashtable<String, ClassPreset> PRESET_TABLE;
    private ArrayList<String> PRESET_NAMES;
    private JPanel RIGHT_SPECIES_PANEL;
    private JPanel LEFT_SPECIES_PANEL;
    private ButtonGroup SPECIES_BUTTONS;
    private Hashtable<String, Species> SPECIES_TABLE;
    private ArrayList<String> SPECIES_NAMES;
    private String SPECIES;
    private JPanel SPEED_PANEL;
    private JTextField SPEED_DISPLAY;
    private int SPEED_SPECIES_BONUS = 0;
    private JTextField SPEED_SPECIES_DISPLAY;
    private JPanel ENDURANCE_PANEL;
    private JTextField ENDURANCE_DISPLAY;
    private int ENDURANCE_SPECIES_BONUS = 0;
    private JTextField ENDURANCE_SPECIES_DISPLAY;
    private JPanel HEALTH_PANEL;
    private JTextField HEALTH_DISPLAY;
    private JPanel STRENGTH_PANEL;
    private JTextField STRENGTH_DISPLAY;
    private int STRENGTH_SPECIES_BONUS = 0;
    private JTextField STRENGTH_SPECIES_DISPLAY;
    private JPanel INTELLIGENCE_PANEL;
    private JTextField INTELLIGENCE_DISPLAY;
    private int INTELLIGENCE_SPECIES_BONUS = 0;
    private JTextField INTELLIGENCE_SPECIES_DISPLAY;
    private JPanel DEXTERITY_PANEL;
    private JTextField DEXTERITY_DISPLAY;
    private int DEXTERITY_SPECIES_BONUS = 0;
    private JTextField DEXTERITY_SPECIES_DISPLAY;
    private JPanel NAME_PANEL;
    private JTextField NAME_DISPLAY;
    private JPanel CLASS_PANEL;
    private JTextField CLASS_DISPLAY;
    private JPanel SPRITE_PANEL;
    private JTextField SPRITE_DISPLAY;
    private JPanel CREATE_PANEL;
    private JButton CREATE_BUTTON;
    private StartMenu START_MENU;

    public CreateCharacter() {
        formatClassPresets();
        formatAllStats();
        formatSpecies();
        formatFrame();
    }

    public CreateCharacter(StartMenu startMenu) {
        START_MENU = startMenu;
        formatClassPresets();
        formatAllStats();
        formatSpecies();
        formatFrame();
    }

    private void formatSpecies() {
        RIGHT_SPECIES_PANEL = new JPanel();
        RIGHT_SPECIES_PANEL.setBounds(592, 2, 107, 148);
        RIGHT_SPECIES_PANEL.setLayout(new GridLayout(0, 1));
        RIGHT_SPECIES_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Species"));

        LEFT_SPECIES_PANEL = new JPanel();
        LEFT_SPECIES_PANEL.setBounds(2, 2, 107, 148);
        LEFT_SPECIES_PANEL.setLayout(new GridLayout(0, 1));
        LEFT_SPECIES_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Species"));

        SPECIES_BUTTONS = new ButtonGroup();

        makeSpeciesList();
        for (int i = 0; i < 8; i++) {
            JRadioButton current = new JRadioButton(SPECIES_TABLE.get(SPECIES_NAMES.get(i)).getName());
            current.addActionListener(this);
            SPECIES_BUTTONS.add(current);
            LEFT_SPECIES_PANEL.add(current);
        }
        for (int i = 8; i < 16; i++) {
            JRadioButton current = new JRadioButton(SPECIES_TABLE.get(SPECIES_NAMES.get(i)).getName());
            current.addActionListener(this);
            SPECIES_BUTTONS.add(current);
            RIGHT_SPECIES_PANEL.add(current);
        }
    }

    private void makeSpeciesList() {
        SPECIES_TABLE = new Hashtable<String, Species>();
        SPECIES_NAMES = new ArrayList<String>();
        try {
            File file = new File("src/GUI/PlayableSpecies.tgs");
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String classStats = "";
                for (int i = 0; i <= 5; i++) {
                    classStats += fileScanner.nextLine() + "\n";
                }
                Species species = new Species(classStats);
                SPECIES_TABLE.put(species.getName(), species);
                SPECIES_NAMES.add(species.getName());
            }
        } catch (FileNotFoundException ex) {
        }
    }

    private void updateBonuses() {
        SPEED_SPECIES_DISPLAY.setText("+" + SPEED_SPECIES_BONUS);
        ENDURANCE_SPECIES_DISPLAY.setText("+" + ENDURANCE_SPECIES_BONUS);
        STRENGTH_SPECIES_DISPLAY.setText("+" + STRENGTH_SPECIES_BONUS);
        INTELLIGENCE_SPECIES_DISPLAY.setText("+" + INTELLIGENCE_SPECIES_BONUS);
        DEXTERITY_SPECIES_DISPLAY.setText("+" + DEXTERITY_SPECIES_BONUS);
        try {
            HEALTH_DISPLAY.setText((10 + ((Integer.parseInt(ENDURANCE_DISPLAY.getText()) + ENDURANCE_SPECIES_BONUS) * 5)) + "");
        } catch (NumberFormatException ex) {
            HEALTH_DISPLAY.setText((ENDURANCE_SPECIES_BONUS * 5) + "");
        }
    }

    private void formatClassPresets() {
        makePresetList();
//        System.out.println(PRESET_TABLE.get("Knight"));
        PRESET_PANEL = new JPanel();
        PRESET_PANEL.setBounds(192, 52, 320, 98);
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
        formatStatPanel(109, 2, 85, SPEED_PANEL, SPEED_DISPLAY, "1", "Speed");
        SPEED_SPECIES_DISPLAY = new JTextField(2);
        SPEED_SPECIES_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        SPEED_PANEL.add(SPEED_SPECIES_DISPLAY);

        ENDURANCE_PANEL = new JPanel();
        ENDURANCE_DISPLAY = new JTextField(1);
        ENDURANCE_DISPLAY.addKeyListener(this);
        formatStatPanel(109, 50, 85, ENDURANCE_PANEL, ENDURANCE_DISPLAY, "1", "Endurance");
        ENDURANCE_SPECIES_DISPLAY = new JTextField(2);
        ENDURANCE_SPECIES_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        ENDURANCE_PANEL.add(ENDURANCE_SPECIES_DISPLAY);

        HEALTH_PANEL = new JPanel();
        HEALTH_DISPLAY = new JTextField(2);
        HEALTH_DISPLAY.setEditable(false);
        formatStatPanel(109, 98, 85, HEALTH_PANEL, HEALTH_DISPLAY, "15", "Health");

        STRENGTH_PANEL = new JPanel();
        STRENGTH_DISPLAY = new JTextField(1);
        formatStatPanel(510, 2, 85, STRENGTH_PANEL, STRENGTH_DISPLAY, "1", "Strength");
        STRENGTH_SPECIES_DISPLAY = new JTextField(2);
        STRENGTH_SPECIES_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        STRENGTH_PANEL.add(STRENGTH_SPECIES_DISPLAY);

        INTELLIGENCE_PANEL = new JPanel();
        INTELLIGENCE_DISPLAY = new JTextField(1);
        formatStatPanel(510, 50, 85, INTELLIGENCE_PANEL, INTELLIGENCE_DISPLAY, "1", "Intelligence");
        INTELLIGENCE_SPECIES_DISPLAY = new JTextField(2);
        INTELLIGENCE_SPECIES_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        INTELLIGENCE_PANEL.add(INTELLIGENCE_SPECIES_DISPLAY);

        DEXTERITY_PANEL = new JPanel();
        DEXTERITY_DISPLAY = new JTextField(1);
        formatStatPanel(510, 98, 85, DEXTERITY_PANEL, DEXTERITY_DISPLAY, "1", "Dexterity");
        DEXTERITY_SPECIES_DISPLAY = new JTextField(2);
        DEXTERITY_SPECIES_DISPLAY.setHorizontalAlignment(JTextField.CENTER);
        DEXTERITY_PANEL.add(DEXTERITY_SPECIES_DISPLAY);

        NAME_PANEL = new JPanel();
        NAME_DISPLAY = new JTextField(7);
        formatStatPanel(192, 2, 100, NAME_PANEL, NAME_DISPLAY, "", "Name");

        CLASS_PANEL = new JPanel();
        CLASS_DISPLAY = new JTextField(7);
        formatStatPanel(289, 2, 100, CLASS_PANEL, CLASS_DISPLAY, "", "Class");

        SPRITE_PANEL = new JPanel();
        SPRITE_DISPLAY = new JTextField(2);
        SPRITE_DISPLAY.setFont(new Font("Monospaced", Font.PLAIN, 12));
        formatStatPanel(387, 2, 60, SPRITE_PANEL, SPRITE_DISPLAY, "@", "Sprite");

        CREATE_PANEL = new JPanel();
        CREATE_PANEL.setBounds(443, 10, 70, 52);
        CREATE_BUTTON = new JButton("Create");
        CREATE_BUTTON.setPreferredSize(new Dimension(50, 30));
        CREATE_BUTTON.setMargin(new Insets(0, 0, 0, 0));
        CREATE_BUTTON.addActionListener(this);

        CREATE_PANEL.add(CREATE_BUTTON);
        updateBonuses();
    }

    private void formatStatPanel(int x, int y, int sizeX, JPanel panel, JTextField display, String stat, String statName) {
        panel.setBounds(x, y, sizeX, 52);
        panel.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, statName));

        display.setHorizontalAlignment(JTextField.CENTER);
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
        FRAME.add(RIGHT_SPECIES_PANEL);
        FRAME.add(LEFT_SPECIES_PANEL);

        BASE = new JPanel();
        BASE.setBorder(PANEL_BORDER);
        FRAME.add(BASE);

        FRAME.setSize(707, 179);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NAME_DISPLAY.requestFocusInWindow();
        GameGUI.centerWindow(FRAME);
    }

    public Player makePlayer() {
        Player player = new Player(NAME_DISPLAY.getText(), CLASS_DISPLAY.getText(), SPECIES, SPRITE_DISPLAY.getText().charAt(0));
        int speed = Integer.parseInt(SPEED_DISPLAY.getText()) + SPEED_SPECIES_BONUS;
        int endurance = Integer.parseInt(ENDURANCE_DISPLAY.getText()) + ENDURANCE_SPECIES_BONUS;
        int strength = Integer.parseInt(STRENGTH_DISPLAY.getText()) + STRENGTH_SPECIES_BONUS;
        int intelligence = Integer.parseInt(INTELLIGENCE_DISPLAY.getText()) + INTELLIGENCE_SPECIES_BONUS;
        int dexterity = Integer.parseInt(DEXTERITY_DISPLAY.getText()) + DEXTERITY_SPECIES_BONUS;
        player.initateStats(speed, endurance, strength, intelligence, dexterity);
        player.setLevel(1);
        player.setXP(0);
        player.saveToFile();
        return player;
    }

    public void closeMenu() {
        FRAME.dispose();
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
                HEALTH_DISPLAY.setText((10 + ((preset.getEndurance() + ENDURANCE_SPECIES_BONUS) * 5)) + "");
                STRENGTH_DISPLAY.setText(preset.getStrength() + "");
                INTELLIGENCE_DISPLAY.setText(preset.getIntelligence() + "");
                DEXTERITY_DISPLAY.setText(preset.getDexterity() + "");
            } else if (button.getParent() == RIGHT_SPECIES_PANEL || button.getParent() == LEFT_SPECIES_PANEL) {
                Species species = SPECIES_TABLE.get(button.getLabel());
                SPECIES = species.getName();
                SPEED_SPECIES_BONUS = species.getSpeed();
                ENDURANCE_SPECIES_BONUS = species.getEndurance();
                STRENGTH_SPECIES_BONUS = species.getStrength();
                INTELLIGENCE_SPECIES_BONUS = species.getIntelligence();
                DEXTERITY_SPECIES_BONUS = species.getDexterity();
                updateBonuses();
            }
        } else if (performer instanceof JButton) {
//            System.out.println(SPECIES);
            try {
                int speed = Integer.parseInt(SPEED_DISPLAY.getText());
                int endurance = Integer.parseInt(ENDURANCE_DISPLAY.getText());
                int strength = Integer.parseInt(STRENGTH_DISPLAY.getText());
                int intelligence = Integer.parseInt(INTELLIGENCE_DISPLAY.getText());
                int dexterity = Integer.parseInt(DEXTERITY_DISPLAY.getText());
                int total = speed + endurance + strength + intelligence + dexterity;
                if (!NAME_DISPLAY.getText().equals("")) {
                    if (!CLASS_DISPLAY.getText().equals("")) {
                        if (SPECIES != null) {
                            if (total == 12) {
                                START_MENU.setPlayer(makePlayer());
                                closeMenu();
                            } else if (total > 12) {
                                JOptionPane.showMessageDialog(FRAME, "You are only allowed to give your character 12 points.\nYou have " + (total - 12) + " points over the maximum", "Above Maximum Stats", JOptionPane.WARNING_MESSAGE);
                            } else if (total < 12) {
                                Object[] options = {"Create Character", "Redistribute Stats"};
                                int answer = JOptionPane.showOptionDialog(FRAME, "You have extra stat points to distribute", "Below Maximum Stats", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                                if (answer == 0) {
                                    START_MENU.setPlayer(makePlayer());
                                    closeMenu();
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(FRAME, "Please choose a Species", "Null Species", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(FRAME, "Please choose a Class \n(Either choose one from the presets or name your own)", "Null Class", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(FRAME, "Please enter a name for your character", "No Name", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(FRAME, "Please only input whole numbers into the stat selectors", "Input Mismach", JOptionPane.WARNING_MESSAGE);
            }
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
                HEALTH_DISPLAY.setText("" + (10 + ((Integer.parseInt(text.getText()) + ENDURANCE_SPECIES_BONUS) * 5)));

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

    private class Species {

        private String CLASS_NAME;
        private int SPEED_BONUS;
        private int ENDURANCE_BONUS;
        private int STRENGTH_BONUS;
        private int INTELLIGENCE_BONUS;
        private int DEXTERITY_BONUS;

        public Species(String stats) {
            Scanner scanPreset = new Scanner(stats);
            CLASS_NAME = CreateFromDocument.getLineElement(scanPreset.nextLine());
            SPEED_BONUS = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            ENDURANCE_BONUS = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            STRENGTH_BONUS = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            INTELLIGENCE_BONUS = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
            DEXTERITY_BONUS = Integer.parseInt(CreateFromDocument.getLineElement(scanPreset.nextLine()));
//            System.out.println(CreateFromDocument.getLineElement(scanPreset.nextLine()));
        }

        public String getName() {
            return CLASS_NAME;
        }

        public int getSpeed() {
            return SPEED_BONUS;
        }

        public int getEndurance() {
            return ENDURANCE_BONUS;
        }

        public int getStrength() {
            return STRENGTH_BONUS;
        }

        public int getIntelligence() {
            return INTELLIGENCE_BONUS;
        }

        public int getDexterity() {
            return DEXTERITY_BONUS;
        }
    }
}