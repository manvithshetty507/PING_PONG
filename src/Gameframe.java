
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Gameframe extends JFrame{

    GamePanel panel;
    Gameframe(){

        panel = new GamePanel();
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
