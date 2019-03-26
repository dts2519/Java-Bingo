package bingo1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class timeWindow extends JFrame
{
    private JLabel chooseText;
    
    final Integer[] INTERVAL = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    final JComboBox<Integer> intervalChoice = new JComboBox<Integer>(INTERVAL);
    
    public timeWindow()
    {
        super("Choose an interval...");
        JPanel p = new JPanel();
        
        p.setLayout(null);
        getContentPane().add(p);
   
        chooseText = new JLabel("Choose an interval...");
        chooseText.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        chooseText.setBounds(150, 10, 500, 100);
        p.add(chooseText);
        
        intervalChoice.setBounds(220, 125, 200, 60);
        intervalChoice.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        p.add(intervalChoice);
        
        HandlerClass handoraa = new HandlerClass();
        intervalChoice.addActionListener(handoraa);
    }
    
    private class HandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent ebento) 
        {
            Bingo.delay = intervalChoice.getSelectedIndex() + 4;
            Bingo.timeFlag = false;
            Bingo.inkColorB.setEnabled(true);
            Bingo.timeSet.setEnabled(true);
            
            if (! Bingo.gamePaused)
            {
                Bingo.newGame.setEnabled(true);
            }
            
            dispose();
        }
    }
}
