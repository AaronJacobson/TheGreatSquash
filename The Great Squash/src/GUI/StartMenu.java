package GUI;

import java.awt.Button;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

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
    private JPanel BASE;
    private JPanel SERVER_PANEL;
    private JPanel CLIENT_PANEL;

    public StartMenu() {
        formatClient();
        formatServer();
        formatFrame();
    }

    private void formatClient() {
        CLIENT_IP = new JTextField(10);
        CLIENT_IP.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER,"Server IP"));
        
        CLIENT_CONNECT = new Button("Connect to Server");
        
        CLIENT_PANEL = new JPanel();
        CLIENT_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER,"Client"));
        CLIENT_PANEL.setBounds(1, 1, 146, 102);
        CLIENT_PANEL.add(CLIENT_IP);
        CLIENT_PANEL.add(CLIENT_CONNECT);
    }

    private void formatServer() {
        SERVER_MAP = new JTextField(10);
        SERVER_MAP.setBorder(BorderFactory.createTitledBorder(TEXT_BORDER,"Map Name"));
        
        SERVER_CREATE = new Button("Create Server");

        SERVER_PANEL = new JPanel();
        SERVER_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER,"Server"));
        SERVER_PANEL.setBounds(147, 1, 146, 102);
        SERVER_PANEL.add(SERVER_MAP);
        SERVER_PANEL.add(SERVER_CREATE);
    }

    private void formatFrame() {
        FRAME.add(SERVER_PANEL);
        FRAME.add(CLIENT_PANEL);
        BASE = new JPanel();
        FRAME.add(BASE);
        
        FRAME.setSize(300,130);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
