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
    private JLabel display1, display2, display3, display4, display5, display6, display7;
    private JLabel chooseText;
    
    public inkColorWindow()
    {
        super("Choose a Color...");
        JPanel p = new JPanel();
        
        p.setLayout(null);
        getContentPane().add(p);
   
        chooseText = new JLabel("Choose a color...");
        chooseText.setFont(new Font("Sans Serif", Font.PLAIN, 48));
        chooseText.setBounds(180, 10, 400, 100);
        p.add(chooseText);
        
        mouseHandlerClass handoraa = new mouseHandlerClass();
        
        display1 = new JLabel();
        display1.setIcon(new ImageIcon(getClass().getResource("InkRed.PNG")));
        display1.setBounds(25, 100, 90, 90);
        p.add(display1);
        display1.addMouseListener(handoraa);
        
        display2 = new JLabel();
        display2.setIcon(new ImageIcon(getClass().getResource("InkOrange.PNG")));
        display2.setBounds(125, 100, 90, 90);
        p.add(display2);
        display2.addMouseListener(handoraa);
        
        display3 = new JLabel();
        display3.setIcon(new ImageIcon(getClass().getResource("InkYellow.PNG")));
        display3.setBounds(225, 100, 90, 90);
        p.add(display3);
        display3.addMouseListener(handoraa);
        
        display4 = new JLabel();
        display4.setIcon(new ImageIcon(getClass().getResource("InkGreen.PNG")));
        display4.setBounds(325, 100, 90, 90);
        p.add(display4);
        display4.addMouseListener(handoraa);
        
        display5 = new JLabel();
        display5.setIcon(new ImageIcon(getClass().getResource("InkBlue.PNG")));
        display5.setBounds(425, 100, 90, 90);
        p.add(display5);
        display5.addMouseListener(handoraa);
        
        display6 = new JLabel();
        display6.setIcon(new ImageIcon(getClass().getResource("InkIndigo.PNG")));
        display6.setBounds(525, 100, 90, 90);
        p.add(display6);
        display6.addMouseListener(handoraa);
        
        display7 = new JLabel();        
        display7.setIcon(new ImageIcon(getClass().getResource("InkViolet.PNG")));
        display7.setBounds(625, 100, 90, 90);
        p.add(display7);
        display7.addMouseListener(handoraa);
    }
    
    private class mouseHandlerClass implements MouseListener
    {
        Boolean inkClicked = false;
        
        public void mouseClicked(MouseEvent ebento) 
        {
            //Nothing
        }

        public void mousePressed(MouseEvent ebento) 
        {
            if (ebento.getSource() == display1)
            {
                Bingo.inkColor = 1;
                inkClicked = true;
            }
            else if (ebento.getSource() == display2)
            {
                Bingo.inkColor = 2;
                inkClicked = true;
            }
            else if (ebento.getSource() == display3)
            {
                Bingo.inkColor = 3;
                inkClicked = true;
            }
            else if (ebento.getSource() == display4)
            {
                Bingo.inkColor = 4;
                inkClicked = true;
            }
            else if (ebento.getSource() == display5)
            {
                Bingo.inkColor = 5;
                inkClicked = true;
            }
            else if (ebento.getSource() == display6)
            {
                Bingo.inkColor = 6;
                inkClicked = true;
            }
            else if (ebento.getSource() == display7)
            {
                Bingo.inkColor = 7;
                inkClicked = true;
            }
            
            if (inkClicked)
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

        public void mouseReleased(MouseEvent ebento) 
        {
            //Nejthing
        }

        public void mouseEntered(MouseEvent ebento)
        {
            //Shou ga nai
        }

        public void mouseExited(MouseEvent ebento) 
        {
            //Netei kolina
        }
        
    }
}
