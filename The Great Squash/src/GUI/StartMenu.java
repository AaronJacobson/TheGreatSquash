package GUI;

import Main.GameRunner;
import gameworld.Player;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StartMenu {

    private JFrame FRAME = new JFrame("The Great Squash");
    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private Border TEXT_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private JTextField CLIENT_IP;
    private Button CLIENT_CONNECT;
    private JTextField SERVER_MAP;
    private Button SERVER_CREATE;
    private JTextField PLAYER_FIND;
    private Button PLAYER_BROWSE;
    private Button PLAYER_CREATE;
    private JPanel BASE;
    private JPanel SERVER_PANEL;
    private JPanel CLIENT_PANEL;
    private JPanel FIND_PLAYER_PANEL;
    private Player PLAYER;
    private JPanel DISPLAY_PLAYER_PANEL;
    private JTextField PLAYER_NAME;
    private JTextField PLAYER_CLASS;
    private JTextField PLAYER_SPECIES;
    private JTextField PLAYER_SPRITE;
    private JTextField PLAYER_LEVEL;
    private JTextField PLAYER_SPEED;
    private JTextField PLAYER_ENDURANCE;
    private JTextField PLAYER_HEALTH;
    private JTextField PLAYER_STRENGTH;
    private JTextField PLAYER_INTELLIGENCE;
    private JTextField PLAYER_DEXTERITY;
    private StartMenuButtonListener ACTION_LISTENER;
    private FindPlayerKeyListener KEY_LISTENER;
    
    private JTextField NAME_DISPLAY;
    private JTextField CLASS_DISPLAY;
    private JTextField RACE_DISPLAY;
    private JTextField SPRITE_DISPLAY;
    private JTextField SPEED_DISPLAY;
    private JTextField ENDURANCE_DISPLAY;
    private JTextField HEALTH_DISPLAY;
    private JTextField STRENGTH_DISPLAY;
    private JTextField INTELLIGENCE_DISPLAY;
    private JTextField DEXTERITY_DISPLAY;

    public StartMenu() {
        ACTION_LISTENER = new StartMenuButtonListener();
        KEY_LISTENER = new FindPlayerKeyListener();
        formatClient();
        formatServer();
        formatFindPlayer();
        formatDisplayPlayer();
        formatFrame();
    }

    private void formatClient() {
        CLIENT_IP = new JTextField(11);
        CLIENT_IP.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Server IP"));

        CLIENT_CONNECT = new Button("Connect to Server");
        CLIENT_CONNECT.addActionListener(ACTION_LISTENER);

        CLIENT_PANEL = new JPanel();
        CLIENT_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Client"));
        CLIENT_PANEL.setBounds(1, 1, 176, 102);
        CLIENT_PANEL.add(CLIENT_IP);
        CLIENT_PANEL.add(CLIENT_CONNECT);
    }

    private void formatServer() {
        SERVER_MAP = new JTextField(11);
        SERVER_MAP.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Map Name"));

        SERVER_CREATE = new Button("Create Server");
        SERVER_CREATE.addActionListener(ACTION_LISTENER);

        SERVER_PANEL = new JPanel();
        SERVER_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Server"));
        SERVER_PANEL.setBounds(177, 1, 176, 102);
        SERVER_PANEL.add(SERVER_MAP);
        SERVER_PANEL.add(SERVER_CREATE);
    }


    private void formatFindPlayer() {
        PLAYER_FIND = new JTextField(19);
        PLAYER_FIND.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Player File"));

        PLAYER_NAME = new JTextField(19);
        PLAYER_NAME.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Player File"));

        PLAYER_BROWSE = new Button("Browse");
        PLAYER_BROWSE.setPreferredSize(new Dimension(50, 30));
        PLAYER_BROWSE.addActionListener(ACTION_LISTENER);

        PLAYER_CREATE = new Button("Make New");
        PLAYER_CREATE.setPreferredSize(new Dimension(65, 30));
        PLAYER_CREATE.addActionListener(ACTION_LISTENER);

        FIND_PLAYER_PANEL = new JPanel();
        FIND_PLAYER_PANEL.setBounds(1, 101, 352, 50);
        FIND_PLAYER_PANEL.add(PLAYER_FIND);
        FIND_PLAYER_PANEL.add(PLAYER_BROWSE);
        FIND_PLAYER_PANEL.add(PLAYER_CREATE);
    }

    private void formatDisplayPlayer() {
        DISPLAY_PLAYER_PANEL = new JPanel();
        DISPLAY_PLAYER_PANEL.setBounds(1, 151, 352, 120);
        DISPLAY_PLAYER_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Player"));

        PLAYER_NAME = new JTextField(6);
        formatStat(PLAYER_NAME, "Name", "");
        PLAYER_CLASS = new JTextField(5);
        formatStat(PLAYER_CLASS, "Class", "");
        PLAYER_SPECIES = new JTextField(6);
        formatStat(PLAYER_SPECIES, "Species", "");
        PLAYER_SPRITE = new JTextField(6);
        PLAYER_SPRITE.setFont(new Font("Monospaced", Font.PLAIN, 12));
        formatStat(PLAYER_SPRITE, "Sprite", "");
        PLAYER_LEVEL = new JTextField(2);
        formatStat(PLAYER_LEVEL, "Lvl", "");
        PLAYER_SPEED = new JTextField(3);
        formatStat(PLAYER_SPEED, "Spd", "0");
        PLAYER_ENDURANCE = new JTextField(3);
        formatStat(PLAYER_ENDURANCE, "End", "0");
        PLAYER_HEALTH = new JTextField(4);
        formatStat(PLAYER_HEALTH, "Health", "0");
        PLAYER_STRENGTH = new JTextField(3);
        formatStat(PLAYER_STRENGTH, "Str", "0");
        PLAYER_INTELLIGENCE = new JTextField(3);
        formatStat(PLAYER_INTELLIGENCE, "Int", "0");
        PLAYER_DEXTERITY = new JTextField(3);
        formatStat(PLAYER_DEXTERITY, "Dex", "0");
    }

    private void formatStat(JTextField text, String statName, String startText) {
        text.setEditable(false);
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setText(startText);
        text.setBackground(Color.WHITE);
        text.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, statName));
        DISPLAY_PLAYER_PANEL.add(text);
    }

    private void formatStat(JTextField display, String statName) {
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.CENTER);
        display.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, statName));
        display.setText("1");
        DISPLAY_PLAYER_PANEL.add(display);
    }

    private void formatFrame() {
        FRAME.add(SERVER_PANEL);
        FRAME.add(CLIENT_PANEL);
        FRAME.add(FIND_PLAYER_PANEL);
        //FRAME.add(GET_PLAYER_PANEL);
        FRAME.add(DISPLAY_PLAYER_PANEL);

        BASE = new JPanel();
        FRAME.add(BASE);

        FRAME.setSize(360, 300);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PLAYER_FIND.requestFocusInWindow();
        PLAYER_FIND.addKeyListener(KEY_LISTENER);
        GameGUI.centerWindow(FRAME);
    }

    public void closeMenu() {
        FRAME.dispose();
    }

    private void createPlayer() {
        String fileName = PLAYER_FIND.getText();
        if (!fileName.contains(".player") && !fileName.contains("\\")) {
            fileName = "src\\gameworld\\players\\" + fileName + ".player";
        }

        File playerFile = new File(fileName);

        if (playerFile.getTotalSpace() != 0) {
            Player player = new Player(fileName);
            PLAYER = player;
        } else {
            PLAYER = null;
        }
        
        updatePlayerStats();
    }

    private void updatePlayerStats() {
        try {
            PLAYER_NAME.setText(PLAYER.getName());
            PLAYER_CLASS.setText(PLAYER.getPlayerClass());
            PLAYER_SPECIES.setText(PLAYER.getSpecies());
            PLAYER_LEVEL.setText(PLAYER.getLevel() + "");
            PLAYER_SPRITE.setText(PLAYER.getSprite() + "");
            PLAYER_SPEED.setText(PLAYER.getSpeed() + "");
            PLAYER_ENDURANCE.setText((int) PLAYER.getEndurance() + "");
            PLAYER_HEALTH.setText(PLAYER.getMaxHealth() + "");
            PLAYER_STRENGTH.setText((int) PLAYER.getStrength() + "");
            PLAYER_INTELLIGENCE.setText((int) (PLAYER.getIntelligence()) + "");
            PLAYER_DEXTERITY.setText((int) PLAYER.getDexterity() + "");
        } catch (NullPointerException ex) {
            PLAYER_NAME.setText("");
            PLAYER_CLASS.setText("");
            PLAYER_SPECIES.setText("");
            PLAYER_LEVEL.setText("");
            PLAYER_SPRITE.setText("");
            PLAYER_SPEED.setText("0");
            PLAYER_ENDURANCE.setText("0");
            PLAYER_HEALTH.setText("0");
            PLAYER_STRENGTH.setText("0");
            PLAYER_INTELLIGENCE.setText("0");
            PLAYER_DEXTERITY.setText("0");
        }
    }

    public Player getPlayer() {
        return PLAYER;
    }

    public void setPlayer(Player player) {
        PLAYER = player;
        PLAYER_FIND.setText(player.getName());
    }

    public StartMenu getSelf() {
        return this;
    }


    private class StartMenuButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            String action = ae.getActionCommand();
             if (action.equals(CLIENT_CONNECT.getActionCommand())) {
                 GameRunner.connectToServer(CLIENT_IP.getText());
             } else if (action.equals(SERVER_CREATE.getActionCommand())) {
                 GameRunner.createServer(SERVER_MAP.getText());
             } else if (action.equals(PLAYER_BROWSE.getActionCommand())) {
                JFileChooser chooser = new JFileChooser("src\\gameworld\\players");
                 FileNameExtensionFilter filter = new FileNameExtensionFilter("Player Files", "player");
                 chooser.setFileFilter(filter);
                 int returnVal = chooser.showOpenDialog(chooser);
                 if (returnVal == JFileChooser.APPROVE_OPTION) {
                    PLAYER_NAME.setText(chooser.getSelectedFile().getAbsoluteFile() + "");
                    PLAYER_FIND.setText(chooser.getSelectedFile().getAbsoluteFile() + "");
                    createPlayer();
                 }
            } else if(action.equals(PLAYER_CREATE.getActionCommand())) {
                 CreateCharacter createCharacter = new CreateCharacter(getSelf());
             }
        }
    }

    private class FindPlayerKeyListener implements KeyListener {

        public void keyTyped(KeyEvent ke) {
        }

        public void keyPressed(KeyEvent ke) {
        }

        public void keyReleased(KeyEvent ke) {
            createPlayer();
        }
    }
}
