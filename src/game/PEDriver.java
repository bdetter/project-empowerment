package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PEDriver {

    public static PEPanel screen;
    public static JFrame frame;
    public static int width;
    public static int height;
    
    public static void main(String[] args) throws IOException 
    {
        screen = new PEPanel();
        frame = new JFrame("Project Empowerment");
        height = 650;
        width = 650;
        frame.setSize(width, height);
        frame.setLocation(300,0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(screen);
        frame.setVisible(true);
        frame.addKeyListener(new listen());
        
    }
    
    public static class listen implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e) 
        {          
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            int k = e.getKeyCode();
            screen.userInput(k);           
        }

        @Override
        public void keyReleased(KeyEvent e) 
        {
        }
        
    }
}
