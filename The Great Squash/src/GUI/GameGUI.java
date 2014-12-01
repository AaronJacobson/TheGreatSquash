/*
 * I'm watching you o_0
 */
package GUI;

import GUI.listeners.MessengerEnterKeyListener;
import GUI.listeners.MovementListener;
import gameworld.Board;
import gameworld.Creature;
import gameworld.Inventory;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author ros_Dmlamarca
 */
public class GameGUI {

    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private Border DISPLAY_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private Font DISPLAY_FONT = new Font("Monospaced", Font.PLAIN, 12);
    private JFrame FRAME = new JFrame("The Great Squash");
    private MessengerEnterKeyListener CHAT_LISTENER;
    private MovementListener MOVEMENT_LISTENER;
    private JTextArea INVENTORY_DISPLAY;
    private JPanel INVENTORY_PANEL;
    private JPanel BUTTONS_PANEL;
    private JTextArea BOARD_DISPLAY;
    private JPanel BOARD_PANEL;
    private JTextArea CHAT_DISPLAY;
    private JTextField CHAT_INPUT;
    private JPanel CHAT_PANEL;
    private JPanel BASE = new JPanel();
    
    private Creature CONTAINED_CREATURE;
    private Board BOARD;

    public GameGUI() {
        MOVEMENT_LISTENER = new MovementListener();
        formatInventory();
        formatButtons();
        formatBoard();
        formatChat();

        formatFrame();
    }
    
    public GameGUI(Creature creature) {
        this();
        setCreature(creature);
    }

    private void formatInventory() {
        INVENTORY_DISPLAY = new JTextArea(27,26);
        INVENTORY_DISPLAY.setFont(DISPLAY_FONT);
        INVENTORY_DISPLAY.setPreferredSize(new Dimension(27,26));
        INVENTORY_DISPLAY.setLineWrap(true);
        INVENTORY_DISPLAY.setBorder(DISPLAY_BORDER);
        INVENTORY_DISPLAY.setEditable(false);
        INVENTORY_DISPLAY.addKeyListener(MOVEMENT_LISTENER);
        
        INVENTORY_PANEL = new JPanel();
        INVENTORY_PANEL.setBounds(2, 2, 200, 500);
        INVENTORY_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER,"Inventory"));
        INVENTORY_PANEL.add(INVENTORY_DISPLAY);
        INVENTORY_PANEL.addKeyListener(MOVEMENT_LISTENER);
    }
    
    public void updateInventoryDisplay() {
        Inventory creatureInventory = CONTAINED_CREATURE.getInventory();
        INVENTORY_DISPLAY.setText(creatureInventory.toString());
    }

    private void formatButtons() {
        BUTTONS_PANEL = new JPanel();
        //BUTTONS_PANEL.setBackground(Color.BLUE);
        BUTTONS_PANEL.setBounds(204, 2, 738, 50);
        BUTTONS_PANEL.setBorder(PANEL_BORDER);
        BUTTONS_PANEL.addKeyListener(MOVEMENT_LISTENER);
    }

    private void formatBoard() {
        BOARD_DISPLAY = new JTextArea(25,103);
        BOARD_DISPLAY.setFont(DISPLAY_FONT);
        BOARD_DISPLAY.setPreferredSize(new Dimension(20,20));
        BOARD_DISPLAY.setLineWrap(true);
        BOARD_DISPLAY.setBorder(DISPLAY_BORDER);
        BOARD_DISPLAY.setEditable(false);
        BOARD_DISPLAY.addKeyListener(MOVEMENT_LISTENER);
        
        BOARD_PANEL = new JPanel();
        //BOARD_PANEL.setBackground(Color.RED);
        BOARD_PANEL.setBounds(204, 54, 738, 448);
        BOARD_PANEL.setBorder(PANEL_BORDER);
        BOARD_PANEL.add(BOARD_DISPLAY);
        BOARD_PANEL.addKeyListener(MOVEMENT_LISTENER);
    }

    private void formatChat() {
        CHAT_DISPLAY = new JTextArea(7,130);
        CHAT_DISPLAY.setFont(DISPLAY_FONT);
        CHAT_DISPLAY.setEditable(false);
        CHAT_DISPLAY.setLineWrap(true);
        CHAT_DISPLAY.setBorder(DISPLAY_BORDER);
        
        CHAT_INPUT = new JTextField(83);
        CHAT_INPUT.setEditable(true);
        CHAT_LISTENER = new MessengerEnterKeyListener(this);
        CHAT_INPUT.addKeyListener(CHAT_LISTENER);
        
        CHAT_PANEL = new JPanel();
        CHAT_PANEL.setBounds(2, 504, 940, 170);   
        CHAT_PANEL.setBorder(PANEL_BORDER);
        
        JScrollPane chatScroll = new JScrollPane(CHAT_DISPLAY);
        chatScroll.setBorder(PANEL_BORDER);
        chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatScroll.setPreferredSize(new Dimension(920,130));  
                
        CHAT_PANEL.add(chatScroll);
        CHAT_PANEL.add(CHAT_INPUT);
    }

    private void formatFrame() {
        FRAME.add(INVENTORY_PANEL);
        FRAME.add(BUTTONS_PANEL);
        FRAME.add(BOARD_PANEL);
        FRAME.add(CHAT_PANEL);
        FRAME.addKeyListener(MOVEMENT_LISTENER);
        
        //BASE.setBorder(BASE_BORDER);
        FRAME.add(BASE);

        FRAME.setSize(950, 700);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void setCreature(Creature creature) {
        CONTAINED_CREATURE = creature;
        CHAT_LISTENER.setCreature(creature);
        INVENTORY_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER,"Inventory - " + CONTAINED_CREATURE.getName()));
        updateInventoryDisplay();
        MOVEMENT_LISTENER.setCreature(creature);
    }
    
    public Creature getCreature() {
        return CONTAINED_CREATURE;
    }
    
    public void setBoard(Board board) {
        BOARD = board;
    }
    
    public Board getBoard() {
        return BOARD;
    }
    
    public void updateBoard(Board board) {
        BOARD_DISPLAY.setText(board.toString());
    }
    
    public JTextArea getChatDisplay() {
        return CHAT_DISPLAY;
    }
    
    public JTextField getChatInput() {
        return CHAT_INPUT;
    }
}
