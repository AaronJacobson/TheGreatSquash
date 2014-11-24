package GUI.tests;
//
//import javax.swing.BorderFactory;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.border.EtchedBorder;
//
//import GUI.listeners.MessangerEnterKeyListener;
//import LAN.TestClient;
//import LAN.TestServer;
//import Main.GameRunner;
//
public class LANMessanger {
//
//    private MessangerEnterKeyListener enterListener;
//    private TestClient client;
//    private JFrame frame;
//    private JPanel display;
//    private JTextArea displayScreen;
//    private JPanel input;
//    private JTextField inputBox;
//    private JPanel base;
//    private TestServer SERVER;
//
//    public LANMessanger(boolean shouldServer) {
//        if (shouldServer) {
//            ServerThread servThread = new ServerThread(this);
//            Thread serverThread = new Thread(servThread);
//            serverThread.start();
//        }
//        formatInput();
//        formatDisplay();
//        formatFrame();
//        //10.135.66.52
//        this.client = new TestClient("10.135.66.52", this);
//        
//    }
//
//    private void formatInput() {
//        enterListener = new MessangerEnterKeyListener(this);
//        System.out.println("XD");
//        input = new JPanel();
//        inputBox = new JTextField(36);
//
//        input.setBounds(2, 402, 440, 70);
//        //input.setBackground(Color.PINK);
//
//        inputBox.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
//        inputBox.addKeyListener(enterListener);
//
//        input.add(inputBox);
//    }
//
//    private void formatDisplay() {
//        display = new JPanel();
//        displayScreen = new JTextArea(24, 38);
//
//        display.setBounds(2, 2, 440, 400);
//        //display.setBackground(Color.BLUE);
//
//        displayScreen.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
//
//        displayScreen.setEditable(false);
//        displayScreen.setLineWrap(true);
//        display.add(displayScreen);
//    }
//
//    private void formatFrame() {
//        frame = new JFrame("LAN Messanger");
//        base = new JPanel();
//
//        base.setBorder(BorderFactory.createBevelBorder(EtchedBorder.LOWERED));
//
//        frame.setSize(450, 470);
//
//        frame.add(input);
//        frame.add(display);
//        frame.add(base);
//
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        inputBox.requestFocusInWindow();
//    }
//
//    public void clearInput() {
//        inputBox.setText("");
//    }
//
//    public String getInputContents() {
//        return inputBox.getText();
//    }
//
//    public void printToDisplay(String string) {
//        if (!displayScreen.getText().equals("")) {
//            displayScreen.setText(displayScreen.getText() + "\n" + string);
//        } else {
//            displayScreen.setText(string);
//        }
//    }
//
//    public MessangerEnterKeyListener getEnterListener() {
//        return enterListener;
//    }
//
//    public TestClient getClient() {
//        return client;
//    }
//
//    public TestServer getServer() {
//        return SERVER;
//    }
//
//    public void setServer(TestServer toSet) {
//        SERVER = toSet;
//    }
//}
//
//class ServerThread implements Runnable {
//
//    LANMessanger MESSANGER;
//    
//    public ServerThread(LANMessanger messanger){
//        MESSANGER = messanger;
//    }
//    
//    @Override
//    public void run() {
//        MESSANGER.setServer(new TestServer());
//    }
}