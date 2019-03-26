//Caution: Direct download of this application will not work without the associated image files.

//Opens whenever the ink button is pressed inside the main game window

package bingo1;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class inkColorWindow extends JFrame
{
    private JLabel display1, display2, display3, display4, display5, display6, display7; //For the respective ink colors
    private JLabel chooseText;
    
    public inkColorWindow()
    {
        super("Choose a Color...");
        JPanel p = new JPanel();
        
        p.setLayout(null); //Allows for manual placement of each element
        getContentPane().add(p);
   
        chooseText = new JLabel("Choose a color...");
        chooseText.setFont(new Font("Sans Serif", Font.PLAIN, 48));
        chooseText.setBounds(180, 10, 400, 100);
        p.add(chooseText);
        
        mouseHandlerClass handler = new mouseHandlerClass();
        
        display1 = new JLabel();
        display1.setIcon(new ImageIcon(getClass().getResource("InkRed.PNG")));
        display1.setBounds(25, 100, 90, 90);
        p.add(display1);
        display1.addMouseListener(handler);
        
        display2 = new JLabel();
        display2.setIcon(new ImageIcon(getClass().getResource("InkOrange.PNG")));
        display2.setBounds(125, 100, 90, 90);
        p.add(display2);
        display2.addMouseListener(handler);
        
        display3 = new JLabel();
        display3.setIcon(new ImageIcon(getClass().getResource("InkYellow.PNG")));
        display3.setBounds(225, 100, 90, 90);
        p.add(display3);
        display3.addMouseListener(handler);
        
        display4 = new JLabel();
        display4.setIcon(new ImageIcon(getClass().getResource("InkGreen.PNG")));
        display4.setBounds(325, 100, 90, 90);
        p.add(display4);
        display4.addMouseListener(handler);
        
        display5 = new JLabel();
        display5.setIcon(new ImageIcon(getClass().getResource("InkBlue.PNG")));
        display5.setBounds(425, 100, 90, 90);
        p.add(display5);
        display5.addMouseListener(handler);
        
        display6 = new JLabel();
        display6.setIcon(new ImageIcon(getClass().getResource("InkIndigo.PNG")));
        display6.setBounds(525, 100, 90, 90);
        p.add(display6);
        display6.addMouseListener(handler);
        
        display7 = new JLabel();        
        display7.setIcon(new ImageIcon(getClass().getResource("InkViolet.PNG")));
        display7.setBounds(625, 100, 90, 90);
        p.add(display7);
        display7.addMouseListener(handler);
    }
    
    private class mouseHandlerClass implements MouseListener
    {
        Boolean inkClicked = false;
        
        public void mouseClicked(MouseEvent e) 
        {
            //Nothing (mandatory override)
        }

        public void mousePressed(MouseEvent e) //Sets the ink color to the respective color that was clicked
        {
            if (e.getSource() == display1)
            {
                Bingo.inkColor = 1;
                inkClicked = true;
            }
            else if (e.getSource() == display2)
            {
                Bingo.inkColor = 2;
                inkClicked = true;
            }
            else if (e.getSource() == display3)
            {
                Bingo.inkColor = 3;
                inkClicked = true;
            }
            else if (e.getSource() == display4)
            {
                Bingo.inkColor = 4;
                inkClicked = true;
            }
            else if (e.getSource() == display5)
            {
                Bingo.inkColor = 5;
                inkClicked = true;
            }
            else if (e.getSource() == display6)
            {
                Bingo.inkColor = 6;
                inkClicked = true;
            }
            else if (e.getSource() == display7)
            {
                Bingo.inkColor = 7;
                inkClicked = true;
            }
            
            if (inkClicked) //If an ink color has been selected, closes the window and re-enables the buttons
            {
                Bingo.inkFlag = false;
                Bingo.inkColorB.setEnabled(true);
                Bingo.timeSet.setEnabled(true);
                
                if (! Bingo.gamePaused)
                {
                    Bingo.newGame.setEnabled(true);
                }
                
                dispose();
            }
        }

        public void mouseReleased(MouseEvent e) 
        {
            //Nothing (mandatory override)
        }

        public void mouseEntered(MouseEvent e)
        {
            //Nothing (mandatory override)
        }

        public void mouseExited(MouseEvent e) 
        {
            //Nothing (mandatory override)
        }
        
    }
}
