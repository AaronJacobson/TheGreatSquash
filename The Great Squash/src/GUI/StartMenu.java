package GUI;

import Main.GameRunner;
import gameworld.Player;
import java.awt.Button;
import java.awt.Dimension;
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
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ros_dmlamarca
 */
public class StartMenu implements KeyListener {

    private JFrame FRAME = new JFrame("The Great Squash");
    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private Border TEXT_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private JTextField CLIENT_IP;
    private Button CLIENT_CONNECT;
    private JTextField SERVER_MAP;
    private Button SERVER_CREATE;
    private JTextField PLAYER_NAME;
    private JButton PLAYER_BROWSE;
    private JButton PLAYER_CREATE;
    private JPanel BASE;
    private JPanel SERVER_PANEL;
    private JPanel CLIENT_PANEL;
    private JPanel PLAYER_PANEL;
    private Player PLAYER;
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

    private void formatPlayer() {
        PLAYER_NAME = new JTextField(19);
        PLAYER_NAME.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER, "Player File"));

        PLAYER_BROWSE = new JButton("Browse");
        PLAYER_BROWSE.setPreferredSize(new Dimension(50, 30));
        PLAYER_BROWSE.setMargin(new Insets(0, 0, 0, 0));
        PLAYER_BROWSE.addActionListener(ACTION_LISTENER);
        
        PLAYER_CREATE = new JButton("Make New");
        PLAYER_CREATE.setPreferredSize(new Dimension(65, 30));
        PLAYER_CREATE.setMargin(new Insets(0, 0, 0, 0));
        PLAYER_CREATE.addActionListener(ACTION_LISTENER);

        PLAYER_PANEL = new JPanel();
        PLAYER_PANEL.setBounds(1, 101, 352, 50);
        PLAYER_PANEL.add(PLAYER_NAME);
        PLAYER_PANEL.add(PLAYER_BROWSE);
        PLAYER_PANEL.add(PLAYER_CREATE);
    }

    private void formatFrame() {
        FRAME.add(SERVER_PANEL);
        FRAME.add(CLIENT_PANEL);
        FRAME.add(PLAYER_PANEL);

        BASE = new JPanel();
        FRAME.add(BASE);

        FRAME.setSize(360, 180);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PLAYER_NAME.requestFocusInWindow();
        PLAYER_NAME.addKeyListener(this);
        GameGUI.centerWindow(FRAME);
    }

    public void closeMenu() {
        FRAME.dispose();
    }
    
    private void createPlayer() {
        String fileName = PLAYER_NAME.getText();
        if (!fileName.contains(".player") && !fileName.contains("\\")) {
            fileName = "src\\gameworld\\players\\" + fileName + ".player";
        }
        
        File playerFile = new File(fileName);
        
        if(playerFile.getTotalSpace() != 0) {
            PLAYER = new Player(playerFile);
        } else {
            PLAYER = null;
        }
    }

    public Player getPlayer() {
        return PLAYER;
    }
    
    public void setPlayer(Player player) {
        PLAYER = player;
        PLAYER_NAME.setText(player.getName());
    }
    
    public StartMenu getSelf() {
        return this;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        createPlayer();
    }

    private class AL implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            String action = ae.getActionCommand();
            if (action.equals(CLIENT_CONNECT.getActionCommand())) {
                GameRunner.connectToServer(CLIENT_IP.getText());
            } else if (action.equals(SERVER_CREATE.getActionCommand())) {
                GameRunner.createServer(SERVER_MAP.getText());
            } else if (action.equals(PLAYER_BROWSE.getActionCommand())) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Player Files", "player");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(chooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    PLAYER_NAME.setText(chooser.getSelectedFile().getAbsoluteFile() + "");
                }
            } else if(action.equals(PLAYER_CREATE.getActionCommand())) {
//                System.out.println("poop");
                CreateCharacter createCharacter = new CreateCharacter(getSelf());
            }
        }
    }
}
