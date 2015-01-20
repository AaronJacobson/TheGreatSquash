package GUI;

import GUI.listeners.MessengerEnterKeyListener;
import GUI.listeners.MovementListener;
import Main.GameRunner;
import gameworld.Board;
import gameworld.Creature;
import gameworld.Inventory;
import gameworld.Player;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameGUI {

    private Border PANEL_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    private Border DISPLAY_BORDER = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private Font DISPLAY_FONT = new Font("Monospaced", Font.PLAIN, 12);
    private JFrame FRAME = new JFrame("The Great Squash");
    private MessengerEnterKeyListener CHAT_LISTENER;
    private MovementListener MOVEMENT_LISTENER;
    private JTextArea INVENTORY_DISPLAY;
    private JPanel INVENTORY_PANEL;
    private JPanel CREATURE_PANEL;
    private JTextField CREATURE_CLASS;
    private JTextField CREATURE_TYPE;
    private JTextField CREATURE_LEVEL;
    private JTextField CREATURE_XP;
    private JTextField CREATURE_HEALTH;
    private JTextField CREATURE_ENDURANCE;
    private JTextField CREATURE_SPEED;
    private JTextField CREATURE_STRENGTH;
    private JTextField CREATURE_INTELLIGENCE;
    private JTextField CREATURE_DEXTERITY;
    private JTextArea BOARD_DISPLAY;
    private JPanel BOARD_PANEL;
    private JTextArea CHAT_DISPLAY;
    private JTextField CHAT_INPUT;
    private JPanel CHAT_PANEL;
    private JPanel BASE = new JPanel();
    private static ArrayList<Creature> CONTROLLED_CREATURES;
    private static int CURRENT_CREATURE;
    private Board BOARD;

    public GameGUI() {
        CONTROLLED_CREATURES = new ArrayList<Creature>();
        CURRENT_CREATURE = 0;
        MOVEMENT_LISTENER = new MovementListener();
        formatInventory();
        formatCreature();
        formatBoard();
        formatChat();

        formatFrame();
    }

    public static ArrayList<Creature> getControlledCreatures() {
        return CONTROLLED_CREATURES;
    }

    public void addCreature(Creature creature) {
        CONTROLLED_CREATURES.add(creature);
        updateCreateStats();
    }

    public void setCreature(int creatureNumber) {
        CURRENT_CREATURE = creatureNumber;
        CHAT_LISTENER.setCreature(CONTROLLED_CREATURES.get(CURRENT_CREATURE));
        INVENTORY_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Inventory - " + CONTROLLED_CREATURES.get(CURRENT_CREATURE).getName()));
        updateInventoryDisplay();
        MOVEMENT_LISTENER.setCreature(CONTROLLED_CREATURES.get(CURRENT_CREATURE));
        updateCreateStats();
    }

    public void setCreature(Creature creature) {
        CONTROLLED_CREATURES.add(creature);
        CURRENT_CREATURE = CONTROLLED_CREATURES.size() - 1;
        CHAT_LISTENER.setCreature(CONTROLLED_CREATURES.get(CURRENT_CREATURE));
        INVENTORY_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Inventory - " + CONTROLLED_CREATURES.get(CURRENT_CREATURE).getName()));
        updateInventoryDisplay();
        MOVEMENT_LISTENER.setCreature(CONTROLLED_CREATURES.get(CURRENT_CREATURE));
        updateCreateStats();
    }

    private void formatInventory() {
        INVENTORY_DISPLAY = new JTextArea(27, 26);
        INVENTORY_DISPLAY.setFont(DISPLAY_FONT);
        INVENTORY_DISPLAY.setPreferredSize(new Dimension(27, 26));
        INVENTORY_DISPLAY.setLineWrap(true);
        INVENTORY_DISPLAY.setBorder(DISPLAY_BORDER);
        INVENTORY_DISPLAY.setEditable(false);
        INVENTORY_DISPLAY.addKeyListener(MOVEMENT_LISTENER);

        INVENTORY_PANEL = new JPanel();
        INVENTORY_PANEL.setBounds(2, 2, 200, 500);
        INVENTORY_PANEL.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, "Inventory"));
        INVENTORY_PANEL.add(INVENTORY_DISPLAY);
        INVENTORY_PANEL.addKeyListener(MOVEMENT_LISTENER);
    }

    public void updateInventoryDisplay() {
        Inventory creatureInventory = CONTROLLED_CREATURES.get(CURRENT_CREATURE).getInventory();
        INVENTORY_DISPLAY.setText(creatureInventory.toString());
    }

    private void formatCreature() {
        CREATURE_PANEL = new JPanel();

        CREATURE_PANEL.setBounds(204, 2, 738, 50);
        CREATURE_PANEL.setBorder(PANEL_BORDER);

        CREATURE_PANEL.addKeyListener(MOVEMENT_LISTENER);
    }
    
    private void formatCreatureStats() {
        
        CREATURE_TYPE = new JTextField();
        formatCharcaterAspect(CREATURE_TYPE, "Type");
        Creature creature = CONTROLLED_CREATURES.get(CURRENT_CREATURE);
        String type = creature.getType();
        if(creature instanceof Player) {
            CREATURE_CLASS = new JTextField();
            formatCharcaterAspect(CREATURE_CLASS, "Class");
        }
        CREATURE_LEVEL = new JTextField();
        formatCharcaterAspect(CREATURE_LEVEL, "Lvl");
        CREATURE_XP = new JTextField();
        formatCharcaterAspect(CREATURE_XP, "XP");
        CREATURE_HEALTH = new JTextField();
        formatCharcaterAspect(CREATURE_HEALTH, "Health");
        CREATURE_ENDURANCE = new JTextField();
        formatCharcaterAspect(CREATURE_ENDURANCE, "End");
        CREATURE_SPEED = new JTextField();
        formatCharcaterAspect(CREATURE_SPEED, "Spd");
        CREATURE_STRENGTH = new JTextField();
        formatCharcaterAspect(CREATURE_STRENGTH, "Str");
        CREATURE_INTELLIGENCE = new JTextField();
        formatCharcaterAspect(CREATURE_INTELLIGENCE, "Int");
        CREATURE_DEXTERITY = new JTextField();
        formatCharcaterAspect(CREATURE_DEXTERITY, "Dex");
    }

    private void formatCharcaterAspect(JTextField field, String title) {
        field.setBorder(BorderFactory.createTitledBorder(PANEL_BORDER, title));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setEditable(false);
        CREATURE_PANEL.add(field);
    }
    
    
    public void updateCreateStats() {
        formatCreatureStats();
        
        Creature creature = CONTROLLED_CREATURES.get(CURRENT_CREATURE);
        String type = creature.getType();
        if(creature instanceof Player) {
            Player player = (Player)(creature);
            type = player.getSpecies();
            CREATURE_CLASS.setText("     " + player.getPlayerClass() + "     ");
        }
        
        CREATURE_TYPE.setText("     " + type + "     ");
        CREATURE_LEVEL.setText("     " + creature.getLevel() + "     ");
        CREATURE_XP.setText("     " + creature.getXP() + "     ");
        CREATURE_HEALTH.setText("  " + creature.getCurrentHealth() + "/" + creature.getMaxHealth() + "  ");
        CREATURE_ENDURANCE.setText("     " + creature.getEndurance() + "     ");
        CREATURE_SPEED.setText("     " + creature.getSpeed() + "     ");
        CREATURE_STRENGTH.setText("     " + creature.getStrength() + "     ");
        CREATURE_INTELLIGENCE.setText("     " + creature.getIntelligence() + "     ");
        CREATURE_DEXTERITY.setText("     " + creature.getDexterity() + "     ");
    }

    private void formatBoard() {
        BOARD_DISPLAY = new JTextArea(25, 103);
        BOARD_DISPLAY.setFont(DISPLAY_FONT);
        BOARD_DISPLAY.setPreferredSize(new Dimension(20, 20));
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
        CHAT_DISPLAY = new JTextArea(7, 130);
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
        chatScroll.setPreferredSize(new Dimension(920, 130));

        CHAT_PANEL.add(chatScroll);
        CHAT_PANEL.add(CHAT_INPUT);
    }

    private void formatFrame() {
        FRAME.add(INVENTORY_PANEL);
        FRAME.add(CREATURE_PANEL);
        FRAME.add(BOARD_PANEL);
        FRAME.add(CHAT_PANEL);
        FRAME.addKeyListener(MOVEMENT_LISTENER);

        //BASE.setBorder(BASE_BORDER);
        FRAME.add(BASE);

        FRAME.setSize(950, 700);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameGUI.centerWindow(FRAME);
    }

    public static void centerWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public ArrayList<Creature> getCreatures() {
        return CONTROLLED_CREATURES;
    }

    public void updateCreatures() {
        for (int currentCreature = 0; currentCreature < CONTROLLED_CREATURES.size(); currentCreature++) {
            if (GameRunner.GAME_BOARD.hasCreature(CONTROLLED_CREATURES.get(currentCreature).getName())) {
                CONTROLLED_CREATURES.set(currentCreature, GameRunner.GAME_BOARD.getCreature(CONTROLLED_CREATURES.get(currentCreature).getName()));
                MOVEMENT_LISTENER.setCreature(CONTROLLED_CREATURES.get(currentCreature));
                System.out.println("GameGUI: Replaced creature: " + CONTROLLED_CREATURES.get(currentCreature).getName());
            }
        }
    }

    public Creature getCreature() {
        return CONTROLLED_CREATURES.get(CURRENT_CREATURE);
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
