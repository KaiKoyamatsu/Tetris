package Tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Main extends JFrame {
    public static void main(String[] args) {
        Field field = new Field();

        field.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                field.keyAction(e.getKeyCode());
            }

            public void keyTyped(KeyEvent e) {}

            public void keyReleased(KeyEvent e) {}
        });
        field.setVisible(true);

        TimerTask task = new TimerTask() {
            public void run() {
                field.act();
            }
        };
        
        Timer timer = new Timer();
        timer.schedule(task, 0L, 1000L);
    }
}
