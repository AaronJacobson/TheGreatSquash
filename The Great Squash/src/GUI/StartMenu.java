package GUI;

import Main.GameRunner;
import gameworld.Player;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ros_dmlamarca
 */
public class StartMenu {

    private JFrame FRAME = new JFrame("The Great Squash");
    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private Border TEXT_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private JTextField CLIENT_IP;
    private Button CLIENT_CONNECT;
    private JTextField SERVER_MAP;
    private Button SERVER_CREATE;
    private JTextField PLAYER_NAME;
    private Button PLAYER_BROWSE;
    private JPanel BASE;
    private JPanel SERVER_PANEL;
    private JPanel CLIENT_PANEL;
    private JPanel PLAYER_PANEL;
    private AL ACTION_LISTENER;

    public StartMenu() {
        ACTION_LISTENER = new AL();
        formatClient();
        formatServer();
        formatPlayer();
        formatFrame();

//        JFileChooser choosy = new JFileChooser();
//        choosy.showOpenDialog(choosy);
    }

    private void formatClient() {
        CLIENT_IP = new JTextField(10);
        CLIENT_IP.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Server IP"));

        CLIENT_CONNECT = new Button("Connect to Server");
        CLIENT_CONNECT.addActionListener(ACTION_LISTENER);

        CLIENT_PANEL = new JPanel();
        CLIENT_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Client"));
        CLIENT_PANEL.setBounds(1, 1, 146, 102);
        CLIENT_PANEL.add(CLIENT_IP);
        CLIENT_PANEL.add(CLIENT_CONNECT);
    }

    private void formatServer() {
        SERVER_MAP = new JTextField(10);
        SERVER_MAP.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Map Name"));

        SERVER_CREATE = new Button("Create Server");
        SERVER_CREATE.addActionListener(ACTION_LISTENER);

        SERVER_PANEL = new JPanel();
        SERVER_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Server"));
        SERVER_PANEL.setBounds(147, 1, 146, 102);
        SERVER_PANEL.add(SERVER_MAP);
        SERVER_PANEL.add(SERVER_CREATE);
    }

    private void formatPlayer() {
        PLAYER_NAME = new JTextField(19);
        PLAYER_NAME.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Player File"));

        PLAYER_BROWSE = new Button("Browse");
        PLAYER_BROWSE.addActionListener(ACTION_LISTENER);

        PLAYER_PANEL = new JPanel();
        PLAYER_PANEL.setBounds(1, 101, 292, 50);
        //PLAYER_PANEL.setBackground(Color.RED);
        PLAYER_PANEL.add(PLAYER_NAME);
        PLAYER_PANEL.add(PLAYER_BROWSE);
    }

    private void formatFrame() {
        FRAME.add(SERVER_PANEL);
        FRAME.add(CLIENT_PANEL);
        FRAME.add(PLAYER_PANEL);

        BASE = new JPanel();
        FRAME.add(BASE);

        FRAME.setSize(300, 180);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PLAYER_NAME.requestFocusInWindow();
    }

    public void closeMenu() {
        FRAME.dispose();
    }
    
    public Player createPlayer() {
        String fileName = PLAYER_NAME.getText();
        if(!fileName.contains(".player") && !fileName.contains("\\")) {
            fileName = "src\\gameworld\\players\\" + fileName + ".player";
        } 
        Player player = new Player(new File(fileName));
        System.out.println(player.getName());
        System.out.println(player.getSprite());
        System.out.println(player.getSpecies());
        System.out.println(player.getLevel());
        System.out.println(player.getXP());
        System.out.println(player.getMaxHealth());
        System.out.println(player.getEndurance());
        System.out.println(player.getStrength());
         System.out.println(player.getIntelligence());
        System.out.println(player.getDexterity());       
        return player;
    }

    public class AL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String action = ae.getActionCommand();
            if (action.equals(CLIENT_CONNECT.getActionCommand())) {
                GameRunner.connectToServer(CLIENT_IP.getText());
            } else if (action.equals(SERVER_CREATE.getActionCommand())) {
                GameRunner.createServer(SERVER_MAP.getText());
            } else if (action.equals(PLAYER_BROWSE.getActionCommand())) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Player Files","player");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(chooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    PLAYER_NAME.setText(chooser.getSelectedFile().getAbsoluteFile() + "");
                } 
                createPlayer();
            }

        }
    }
}
