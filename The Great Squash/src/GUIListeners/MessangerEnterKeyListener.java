package GUIListeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import GUI.LANMessanger;

public class MessangerEnterKeyListener implements KeyListener {

    private LANMessanger messanger;

    public MessangerEnterKeyListener(LANMessanger messanger) {
        this.messanger = messanger;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getExtendedKeyCode() + "|" + e.getKeyChar());
        if (e.getExtendedKeyCode() == 10) {
            //formatOutput(messanger.getInputContents());
            System.out.println("poop");
            messanger.getClient().sendMessage(formatOutput(messanger.getInputContents()));
            messanger.clearInput();

        }

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    private String formatOutput(String input) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        String header = System.getProperty("user.name") + " [" + dateFormat.format(cal.getTime()) + "]: ";
        return header + input;
    }
}
