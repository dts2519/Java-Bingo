//Caution: Direct download of this application will not work without the associated image files.

package bingo1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Bingo extends JFrame
{
    public static JButton newGame; 
    public static JButton inkColorB = new JButton("Change Ink Color"), timeSet = new JButton("Set Speed"), pause = new JButton("Pause");
    private JLabel[] number, numberInk, boardNumber;
    private Boolean[] numberInked;
    public static int delay = 10;
    public static int inkColor = 1; 
    public static Boolean timeFlag = false, inkFlag = false, gamePaused = false, gameActive = false;
    private int activeNumber = 0, numberGot = 0;
    
    private JLabel version = new JLabel("Java Bingo - Beta 1.3"); //Displayed at the top left corner of the screen
    
    private JLabel cardLabel, boardLabel, winLabel, ballLabel, ballNumber, nextBall, displayCount;
    private ImageIcon card, winAnim, bBall, iBall, nBall, gBall, oBall, startBall, numberBoard;
    private ImageIcon inkR, inkO, inkY, inkG, inkB, inkI, inkV, inkGold;
    
    private ArrayList<Integer> calledNumbers = new ArrayList<Integer>(), cardNumbers = new ArrayList<Integer>();
    
    private Timer mainTimer;
    
    private Random pick = new Random();
    
    public Bingo()
    {
        super("Java Bingo");
        JPanel p = new JPanel();
        
        p.setLayout(null); //Allows for manual placement of each element
        getContentPane().add(p);

        //The ball graphics appear at the left side of the screen, when a ball is drawn
        bBall = new ImageIcon(getClass().getResource("B ball.PNG"));
        iBall = new ImageIcon(getClass().getResource("I ball.PNG"));
        nBall = new ImageIcon(getClass().getResource("N ball.PNG"));
        gBall = new ImageIcon(getClass().getResource("G ball.PNG"));
        oBall = new ImageIcon(getClass().getResource("O ball.PNG"));  
        
        //Before the game begins, the ball is silver (no number)
        startBall = new ImageIcon(getClass().getResource("Start ball.PNG"));  
        
        //The player can 'mark' the bingo card with seven different colors of ink.
        //Each square on the card has two JLabels, one for the ink, and one for the number.
        inkR = new ImageIcon(getClass().getResource("InkRed.PNG"));
        inkO = new ImageIcon(getClass().getResource("InkOrange.PNG"));
        inkY = new ImageIcon(getClass().getResource("InkYellow.PNG"));
        inkG = new ImageIcon(getClass().getResource("InkGreen.PNG"));
        inkB = new ImageIcon(getClass().getResource("InkBlue.PNG"));
        inkI = new ImageIcon(getClass().getResource("InkIndigo.PNG"));
        inkV = new ImageIcon(getClass().getResource("InkViolet.PNG"));
        inkGold = new ImageIcon(getClass().getResource("InkGold.PNG"));
        
        winAnim = new ImageIcon(getClass().getResource("BingoWin.PNG"));
        
        version.setBounds(5, 5, 200, 20);
        p.add(version);
        
        card = new ImageIcon(getClass().getResource("BingoCard.PNG"));
        cardLabel = new JLabel();
        cardLabel.setIcon(card);
        cardLabel.setBounds(350, 200, 462, 557);
        p.add(cardLabel);
        
        numberBoard = new ImageIcon(getClass().getResource("Number Board.PNG"));
        boardLabel = new JLabel();
        boardLabel.setIcon(numberBoard);
        boardLabel.setBounds(845, 202, 312, 553);
        p.add(boardLabel);
        
        winLabel = new JLabel();
        winLabel.setBounds(300, 10, 574, 186);
        p.add(winLabel);
        
        ballLabel = new JLabel();
        ballLabel.setIcon(startBall);
        ballLabel.setBounds(50, 250, 230, 230);
        p.add(ballLabel);
        
        ballNumber = new JLabel("");
        ballNumber.setForeground(Color.WHITE);
        ballNumber.setFont(new Font("Sans Serif", Font.PLAIN, 48));
        ballNumber.setHorizontalAlignment(SwingConstants.CENTER);
        ballNumber.setBounds(70, 95, 90, 100);
        ballLabel.add(ballNumber);
        
        nextBall = new JLabel("Next Ball in 0:00");
        nextBall.setFont(new Font("Sans Serif", Font.PLAIN, 32));
        nextBall.setBounds(50, 350, 300, 300);
        p.add(nextBall);
        
        displayCount = new JLabel("0");
        displayCount.setHorizontalAlignment(SwingConstants.CENTER);
        displayCount.setFont(new Font("Sans Serif", Font.PLAIN, 36));
        displayCount.setBounds(50, 110, 230, 230);
        p.add(displayCount);
        
        newGame = new JButton("New Game");
        
        newGame.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        newGame.setBounds(150, 800, 250, 100);
        p.add(newGame);
        
        inkColorB.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        inkColorB.setBounds(450, 800, 250, 100);
        p.add(inkColorB);
        
        timeSet.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        timeSet.setBounds(750, 800, 250, 100);
        p.add(timeSet);
        
        pause.setFont(new Font("Sans Serif", Font.PLAIN, 24));
        pause.setBounds(50, 650, 250, 100);
        p.add(pause);
        pause.setEnabled(false);
        
        HandlerClass handler = new HandlerClass();
        newGame.addActionListener(handler);
        inkColorB.addActionListener(handler);
        timeSet.addActionListener(handler);
        pause.addActionListener(handler);
        
        mouseHandlerClass handler2 = new mouseHandlerClass();
        
        number = new JLabel[24];
        numberInk = new JLabel[24];
        numberInked = new Boolean[24]; //This determines whether the square has been inked
        
        //Each square on the card has a number label and an ink label. The number must be placed before the ink is (as it is on top)
        for (int i = 0; i < number.length; i++)
        {
            number[i] = new JLabel("");
            number[i].setFont(new Font("Sans Serif", Font.PLAIN, 48));
            number[i].setHorizontalAlignment(SwingConstants.CENTER);
            
            numberInk[i] = new JLabel();
            
            numberInked[i] = false;
        }
        
        //This applies to the number board, on the right side of the screen.
        boardNumber = new JLabel[75];
        
        //The numbers start out dark gray, but become white when their ball has been pulled.
        for (int i = 0; i < boardNumber.length; i++)
        {
            boardNumber[i] = new JLabel("" + (i + 1));
            boardNumber[i].setFont(new Font("Sans Serif", Font.PLAIN, 20));
            boardNumber[i].setForeground(Color.DARK_GRAY);
            boardNumber[i].setHorizontalAlignment(SwingConstants.CENTER);

            //The purpose of these blocks is just to position the numbers by column according to B/I/N/G/O.
            if (i < 15)
            {
                boardNumber[i].setBounds(15, (30 * i) + 60, 25, 25); //B (1-15)
            }
            else if (i < 30)
            {
                boardNumber[i].setBounds(71, (30 * (i - 15)) + 60, 25, 25); //I (16-30)
            }
            else if (i < 45)
            {
                boardNumber[i].setBounds(135, (30 * (i - 30)) + 60, 25, 25); //N (31-45)
            }
            else if (i < 60)
            {
                boardNumber[i].setBounds(203, (30 * (i - 45)) + 60, 25, 25); //G (46-60)
            }
            else //i >= 60
            {
                boardNumber[i].setBounds(269, (30 * (i - 60)) + 60, 25, 25); //O (61-75)
            }
            
            boardLabel.add(boardNumber[i]);
        }
        
        //Setting the position of the labels on the card. The labels of the same index go in the same position.
        
        //This is the B column
        number[0].setBounds(4, 99, 90, 90);
        number[1].setBounds(4, 190, 90, 90);
        number[2].setBounds(4, 281, 90, 90);
        number[3].setBounds(4, 372, 90, 90);
        number[4].setBounds(4, 463, 90, 90); 
        
        numberInk[0].setBounds(4, 99, 90, 90);
        numberInk[1].setBounds(4, 190, 90, 90);
        numberInk[2].setBounds(4, 281, 90, 90);
        numberInk[3].setBounds(4, 372, 90, 90);
        numberInk[4].setBounds(4, 463, 90, 90); 
        
        //The I column      
        number[5].setBounds(95, 99, 90, 90);
        number[6].setBounds(95, 190, 90, 90);
        number[7].setBounds(95, 281, 90, 90);
        number[8].setBounds(95, 372, 90, 90);
        number[9].setBounds(95, 463, 90, 90);
        
        numberInk[5].setBounds(95, 99, 90, 90);
        numberInk[6].setBounds(95, 190, 90, 90);
        numberInk[7].setBounds(95, 281, 90, 90);
        numberInk[8].setBounds(95, 372, 90, 90);
        numberInk[9].setBounds(95, 463, 90, 90);
        
        //N - there are only four here because of the Free space, in the center of the card.
        number[10].setBounds(186, 99, 90, 90);
        number[11].setBounds(186, 190, 90, 90);
        number[12].setBounds(186, 372, 90, 90);
        number[13].setBounds(186, 463, 90, 90);
        
        numberInk[10].setBounds(186, 99, 90, 90);
        numberInk[11].setBounds(186, 190, 90, 90);
        numberInk[12].setBounds(186, 372, 90, 90);
        numberInk[13].setBounds(186, 463, 90, 90);
        
        //G
        number[14].setBounds(277, 99, 90, 90);
        number[15].setBounds(277, 190, 90, 90);
        number[16].setBounds(277, 281, 90, 90);
        number[17].setBounds(277, 372, 90, 90);
        number[18].setBounds(277, 463, 90, 90);
        
        numberInk[14].setBounds(277, 99, 90, 90);
        numberInk[15].setBounds(277, 190, 90, 90);
        numberInk[16].setBounds(277, 281, 90, 90);
        numberInk[17].setBounds(277, 372, 90, 90);
        numberInk[18].setBounds(277, 463, 90, 90);
        
        //O
        number[19].setBounds(368, 99, 90, 90); 
        number[20].setBounds(368, 190, 90, 90);
        number[21].setBounds(368, 281, 90, 90);
        number[22].setBounds(368, 372, 90, 90);
        number[23].setBounds(368, 463, 90, 90);
        
        numberInk[19].setBounds(368, 99, 90, 90); 
        numberInk[20].setBounds(368, 190, 90, 90);
        numberInk[21].setBounds(368, 281, 90, 90);
        numberInk[22].setBounds(368, 372, 90, 90);
        numberInk[23].setBounds(368, 463, 90, 90);
        
        //Now the labels are added to the card and become clickable
        for (int i = 0; i < number.length; i++)
        {
            cardLabel.add(number[i]);
            cardLabel.add(numberInk[i]);
            number[i].addMouseListener(handler2);
        }
        
    }
    
    private class HandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == newGame) //Starts a new game, picking the numbers for the card
            {             
                int[] selection = new int[24];
                
                newGame.setEnabled(false); //Buttons disabled while the game is in progress and unpaused
                inkColorB.setEnabled(false);
                timeSet.setEnabled(false);
                pause.setEnabled(true);
                
                clearCard(); //If a game happened before, discards the previous card
                         
                getB(selection); //Gets the numbers for the card
                getI(selection);
                getN(selection);
                getG(selection);
                getO(selection);
                
                mainTimer = new Timer();
                mainTimer.schedule(new ballClass(), 0, 1 * 1000); //Runs every 1000 ms (1 second) although this is rather arbitrary
                
                gameActive = true;
            }
            else if (e.getSource() == inkColorB) //Opens the ink window, where the player can choose a new ink color
            {
                if (inkFlag == false) //inkFlag determines whether the ink color window is open. If it is, the window does not open a second time.
                {
                    inkFlag = true;
                    newGame.setEnabled(false); //Buttons disabled while the ink color window is open
                    inkColorB.setEnabled(false);
                    timeSet.setEnabled(false);
                    
                    inkColorWindow ink = new inkColorWindow();
                    ink.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ink.setSize(740, 300);
                    ink.setVisible(true);
                    
                    ink.addWindowListener(new java.awt.event.WindowAdapter() //This allows the user to click the X to close the window
                    {
                        public void windowClosing(java.awt.event.WindowEvent e)
                        {
                            inkFlag = false;
                            inkColorB.setEnabled(true);
                            timeSet.setEnabled(true);
                            
                            if (! gamePaused) //The only times the ink window can be open is if the game is paused or a game is not currently underway
                            {
                                newGame.setEnabled(true);
                            }
                        }                       
                    });
                }
            }
            else if (e.getSource() == timeSet) //The time window allows the user to choose between 5 and 15 second delay for a new ball.
            {
                if (timeFlag == false) //timeFlag determines whether the time window is open. If it is, the window does not open a second time.
                {
                    timeFlag = true;
                    newGame.setEnabled(false); //Buttons disabled while the time window is open
                    inkColorB.setEnabled(false);
                    timeSet.setEnabled(false);
                    
                    timeWindow tw = new timeWindow();
                    tw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    tw.setSize(700, 250);
                    tw.setVisible(true);
                    
                    tw.addWindowListener(new java.awt.event.WindowAdapter() //This allows the user to click the X to close the window
                    {
                        public void windowClosing(java.awt.event.WindowEvent e)
                        {
                            timeFlag = false;
                            inkColorB.setEnabled(true);
                            timeSet.setEnabled(true);
                            
                            if (! gamePaused) //The only times the time window can be open is if the game is paused or a game is not currently underway
                            {
                                newGame.setEnabled(true);
                            }
                        }                       
                    });
                }
            }
            else if (e.getSource() == pause) //Pauses or unpauses the game, depending on whether the game has been paused.
            {
                if (! gamePaused) //Not paused, so pauses
                {
                    gamePaused = true;
                    inkColorB.setEnabled(true); //While paused, the player can change ink color or the time interval
                    timeSet.setEnabled(true);
                    
                    pause.setText("Resume");
                    
                    mainTimer.cancel();
                }
                else //Game is paused, so unpauses
                {
                    gamePaused = false;
                    inkColorB.setEnabled(false);
                    timeSet.setEnabled(false);
                    
                    pause.setText("Pause");
                    
                    mainTimer = new Timer(); //Timer resumes
                    mainTimer.schedule(new ballClass(), 0, 1 * 1000);
                }
            }
        }
    }
    
    private class mouseHandlerClass implements MouseListener
    {
        public void mouseClicked(MouseEvent e) 
        {
            //Nothing (mandatory override)
        }

        public void mousePressed(MouseEvent e) 
        {
            for (int i = 0; i < number.length; i++)
            {
                if (e.getSource() == number[i])
                {
                    if (checkNumber(number[i])) //If the number has been called, ink the square
                    {
                        numberInked[i] = true;
                        switch(inkColor)
                        {
                            case 1:
                            {
                                numberInk[i].setIcon(inkR);
                                break;
                            }
                            case 2:
                            {
                                numberInk[i].setIcon(inkO);
                                break;
                            }
                            case 3:
                            {
                                numberInk[i].setIcon(inkY);
                                break;
                            }
                            case 4:
                            {
                                numberInk[i].setIcon(inkG);
                                break;
                            }
                            case 5:
                            {
                                numberInk[i].setIcon(inkB);
                                break;
                            }
                            case 6:
                            {
                                numberInk[i].setIcon(inkI);
                                break;
                            }
                            case 7:
                            {
                                numberInk[i].setIcon(inkV);
                                break;
                            }
                        }
                        
                        numberGot++;
                        if (numberGot > 3) //If at least four numbers have been inked, a bingo becomes possible, so check for it
                        {
                            checkBingo();
                        }
                    }
                }
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
    
    private class ballClass extends TimerTask
    {
        int ballTime;
        
        private ballClass() //Constructor
        {
            if (! gameActive) //i.e. the game has just started
            {
                ballTime = 5;      
            } 
            else //This only triggers if the game has been unpaused
            {
                char[] nbTime = nextBall.getText().substring(15).toCharArray();
                ballTime = ((nbTime[0] - 48) * 10) + (nbTime[1] - 48) + 1; //Subtract 48 because it is taking the ASCII value of the character. 0 is 48, 1 is 49, and so on.
            }
        }
        
        public void run() //1 second interval
        {                 
            //This block dynamically sets the text depending on the time left until the next ball.
            if (ballTime < 11)
            {
                if (ballTime == 0)
                {
                    if (delay < 10)
                    {
                        nextBall.setText("Next Ball in 0:0" + delay);
                    }
                    else
                    {
                        nextBall.setText("Next Ball in 0:" + delay);
                    }
                }
                else
                {
                    nextBall.setText("Next Ball in 0:0" + (ballTime - 1));
                }             
            }
            else
            {
                nextBall.setText("Next Ball in 0:" + (ballTime - 1));
            }
            ballTime--;
            
            //Ball is drawn if the time has reached the end of 0
            if (ballTime < 0)
            {
                ballTime = delay;
                drawBall();
            }
        }    
    }
    

    //Fills the B column of the bingo card with five different numbers, ranging from 1-15.
    public void getB (int[] selection)
    {
        for (int i = 0; i < 5; i++)
        {
            selection[i] = pick.nextInt(15) + 1;
        }
        
        while (selection[1] == selection[0]) //To make sure that the numbers are not identical to one another
        {
            selection[1] = pick.nextInt(15) + 1;
        }
        
        while (selection[2] == selection[0] || selection[2] == selection[1])
        {
            selection[2] = pick.nextInt(15) + 1;
        }
        
        while (selection[3] == selection[0] || selection[3] == selection[1] || selection[3] == selection[2])
        {
            selection[3] = pick.nextInt(15) + 1;
        }
        
        while (selection[4] == selection[0] || selection[4] == selection[1] || selection[4] == selection[2] || selection[4] == selection[3])
        {
            selection[4] = pick.nextInt(15) + 1;
        }
        
        for (int i = 0; i < 5; i++)
        {
            number[i].setText("" + selection[i]);
        }
    }
    
    //Same as B, but for the I numbers (16-30)
    public void getI (int[] selection)
    {
        for (int i = 5; i < 10; i++)
        {
            selection[i] = pick.nextInt(15) + 16;
        }
        
        while (selection[6] == selection[5])
        {
            selection[6] = pick.nextInt(15) + 16;
        }
        
        while (selection[7] == selection[5] || selection[7] == selection[6])
        {
            selection[7] = pick.nextInt(15) + 16;
        }
        
        while (selection[8] == selection[5] || selection[8] == selection[6] || selection[8] == selection[7])
        {
            selection[8] = pick.nextInt(15) + 16;
        }
        
        while (selection[9] == selection[5] || selection[9] == selection[6] || selection[9] == selection[7] || selection[9] == selection[8])
        {
            selection[9] = pick.nextInt(15) + 16;
        }
        
        for (int i = 5; i < 10; i++)
        {
            number[i].setText("" + selection[i]);
        }
    }
    
    //Etc. (31-45)
    public void getN (int[] selection)
    {
        for (int i = 10; i < 14; i++)
        {
            selection[i] = pick.nextInt(15) + 31;
        }
        
        while (selection[11] == selection[10])
        {
            selection[11] = pick.nextInt(15) + 31;
        }
        
        while (selection[12] == selection[10] || selection[12] == selection[11])
        {
            selection[12] = pick.nextInt(15) + 31;
        }
        
        while (selection[13] == selection[10] || selection[13] == selection[11] || selection[13] == selection[12])
        {
            selection[13] = pick.nextInt(15) + 31;
        }
        
        for (int i = 10; i < 15; i++)
        {
            number[i].setText("" + selection[i]);
        }
    }
    
    //Etc. (46-60)
    public void getG (int[] selection)
    {
        for (int i = 14; i < 19; i++)
        {
            selection[i] = pick.nextInt(15) + 46;
        }
        
        while (selection[15] == selection[14])
        {
            selection[15] = pick.nextInt(15) + 46;
        }
        
        while (selection[16] == selection[14] || selection[16] == selection[15])
        {
            selection[16] = pick.nextInt(15) + 46;
        }
        
        while (selection[17] == selection[14] || selection[17] == selection[15] || selection[17] == selection[16])
        {
            selection[17] = pick.nextInt(15) + 46;
        }
        
        while (selection[18] == selection[14] || selection[18] == selection[15] || selection[18] == selection[16] || selection[18] == selection[17])
        {
            selection[18] = pick.nextInt(15) + 46;
        }
        
        for (int i = 14; i < 19; i++)
        {
            number[i].setText("" + selection[i]);
        }
    }
    
    //Etc. (61-75)
    public void getO (int[] selection)
    {
        for (int i = 19; i < 24; i++)
        {
            selection[i] = pick.nextInt(15) + 61;
        }
        
        while (selection[20] == selection[19])
        {
            selection[20] = pick.nextInt(15) + 61;
        }
        
        while (selection[21] == selection[19] || selection[21] == selection[20])
        {
            selection[21] = pick.nextInt(15) + 61;
        }
        
        while (selection[22] == selection[19] || selection[22] == selection[20] || selection[22] == selection[21])
        {
            selection[22] = pick.nextInt(15) + 61;
        }
        
        while (selection[23] == selection[19] || selection[23] == selection[20] || selection[23] == selection[21] || selection[23] == selection[22])
        {
            selection[23] = pick.nextInt(15) + 61;
        }
        
        for (int i = 19; i < 24; i++)
        {
            number[i].setText("" + selection[i]);
        }
    }
    
    //Draws a ball, verifies that it has not been called already, and displays it on the screen. Also lights up the number on the board.
    public void drawBall()
    {
        int ball = pick.nextInt(75) + 1;
        Boolean equalToCalled = checkCalled(ball);
        
        while(equalToCalled)
        {
            ball = pick.nextInt(75) + 1;
            equalToCalled = checkCalled(ball);
        }
        
        setBallLabel(ball);
        activeNumber = ball;
        calledNumbers.add(ball);
        boardNumber[activeNumber - 1].setForeground(Color.WHITE);
        
        displayCount.setText("" + calledNumbers.size());
    }
    
    //Goes through the list of called numbers. If the number passed in is in the list, returns true. If not, returns false.
    public Boolean checkCalled(int drawnBall)
    {
        for(int i = 0; i < calledNumbers.size(); i++)
        {
            if (drawnBall == calledNumbers.get(i))
            {
                return true;
            }
        }
        return false;
    }
    
    //This sets the ball icon on the left side of the screen to reflect the number drawn
    public void setBallLabel(int drawnBall)
    {
        if (drawnBall < 16)
        {
            ballLabel.setIcon(bBall);
        }
        else if (drawnBall < 31)
        {
            ballLabel.setIcon(iBall);
        }
        else if (drawnBall < 46)
        {
            ballLabel.setIcon(nBall);
        }
        else if (drawnBall < 61)
        {
            ballLabel.setIcon(gBall);
        }
        else //drawnBall >= 61 
        {
            ballLabel.setIcon(oBall);
        } 
        ballNumber.setText("" + drawnBall);
    }
    
    //Takes the label, converts its text into a number, then sees if that number on the board is lit up.
    //If it is lit up, returns true. If not, returns false.
    public Boolean checkNumber(JLabel nLabel)
    {
        char[] numberClicked = nLabel.getText().toCharArray();
        //int actTens = activeNumber / 10, actUnit = activeNumber % 10;
        int clickTens = 0, clickUnit = 0;
        int digits = numberClicked.length;
        
        if (digits == 2) //Again converting from the ASCII values
        {
            clickTens = numberClicked[0] - 48;
            clickUnit = numberClicked[1] - 48;
        }
        else
        {
            clickUnit = numberClicked[0] - 48;
        }
        
        int clickedNumber = clickTens * 10 + clickUnit;
        
        if (boardNumber[clickedNumber - 1].getForeground() == Color.WHITE) //If the corresponding number is lit up
        {
            return true;
        }
        return false;
        
        //Below was a previous system that did not account for previously drawn balls
        
//        System.out.println("" + actTens + " " + actUnit + " " + digits);
//        for (int i = 0; i < numberClicked.length; i++)
//        {
//            System.out.println("" + numberClicked[i]);
//        }
        
//        if (digits == 1) //(1-9)
//        {
//            if (actTens > 0)
//            {
//                return false;
//            }
//            if (numberClicked[0] - 48 == actUnit) //Subtract 48 because it is comparing the ASCII value of the character. 0 is 48, 1 is 49, and so on.
//            {
//                return true;
//            }
//        }
//        else //digits == 2 (10-75)
//        {
//            if (actTens == 0)
//            {
//                return false;
//            }
//            if (actTens == numberClicked[0] - 48 && actUnit == numberClicked[1] - 48)
//            {
//                return true;
//            }
//        }
//        return false;
    }
    
    //Sees if a bingo exists, and if it does, calls for a bingo to be displayed
    public void checkBingo()
    {       
        if (numberInked[0])
        {
            if (numberInked[5]) //Row 1
            {
                if (numberInked[10] && numberInked[14] && numberInked[19])
                {
                    displayBingo(0, 5, 10, 14, 19);
                }
            }
            if (numberInked[6]) //Left diagonal
            {
                if (numberInked[17] && numberInked[23])
                {
                    displayBingo(0, 6, 17, 23);
                }
            }
            if (numberInked[1]) //B column
            {
                if (numberInked[2] && numberInked[3] && numberInked[4])
                {
                    displayBingo(0, 1, 2, 3, 4);
                }
            }
            if (numberInked[4]) //Four corners
            {
                if (numberInked[19] && numberInked[23])
                {
                    displayBingo(0, 4, 19, 23);
                }
            }
        }
        
        if (numberInked[1]) //Row 2
        {
            if (numberInked[6] && numberInked[11] && numberInked[15] && numberInked[20])
            {
                displayBingo(1, 6, 11, 15, 23);
            }
        }
        
        if (numberInked[2]) //Row 3
        {
            if (numberInked[7] && numberInked[16] && numberInked[21])
            {
                displayBingo(2, 7, 16, 21);
            }
        }
        
        if (numberInked[3]) //Row 4
        {
            if (numberInked[8] && numberInked[12] && numberInked[17] && numberInked[22])
            {
                displayBingo(3, 8, 12, 17, 22);
            }
        }
        
        if (numberInked[4])
        {
            if (numberInked[9]) //Row 5
            {
                if (numberInked[13] && numberInked[18] && numberInked[23])
                {
                    displayBingo(4, 9, 13, 18, 23);
                }
            }
            if (numberInked[8]) //Right diagonal
            {
                if (numberInked[15] && numberInked[19])
                {
                    displayBingo(4, 8, 15, 19);
                }
            }
        }
        
        if (numberInked[5]) //I column
        {
            if (numberInked[6] && numberInked[7] && numberInked[8] && numberInked[9])
            {
                displayBingo(5, 6, 7, 8, 9);
            }
        }
        
        if (numberInked[10]) //N column
        {
            if (numberInked[11] && numberInked[12] && numberInked[13])
            {
                displayBingo(10, 11, 12, 13);
            }
        }
        
        if (numberInked[14]) //G column
        {
            if (numberInked[15] && numberInked[16] && numberInked[17] && numberInked[18])
            {
                displayBingo(14, 15, 16, 17, 18);
            }
        }
        
        if (numberInked[19]) //O column
        {
            if (numberInked[20] && numberInked[21] && numberInked[22] && numberInked[23])
            {
                displayBingo(19, 20, 21, 22, 23);
            }
        }
    }
    
    //Lights up the spaces passed in in gold and displays the win animation at the top of the screen
    public void displayBingo(int space1, int space2, int space3, int space4)
    {
        numberInk[space1].setIcon(inkGold);
        numberInk[space2].setIcon(inkGold);
        numberInk[space3].setIcon(inkGold);
        numberInk[space4].setIcon(inkGold);
        
        winLabel.setIcon(winAnim);
        reset();
    }
    
    //Lights up the spaces passed in in gold and displays the win animation at the top of the screen
    public void displayBingo(int space1, int space2, int space3, int space4, int space5)
    {
        numberInk[space1].setIcon(inkGold);
        numberInk[space2].setIcon(inkGold);
        numberInk[space3].setIcon(inkGold);
        numberInk[space4].setIcon(inkGold);
        numberInk[space5].setIcon(inkGold);
        
        winLabel.setIcon(winAnim);
        reset();
    }
    
    //Allows for a new game
    public void reset()
    {
        mainTimer.cancel();
        
        newGame.setEnabled(true);
        inkColorB.setEnabled(true);
        timeSet.setEnabled(true);
    }
    
    //Clears the ink and numbers in preparation for a new game. Also clears the board, the ball, the top of the screen, and the number lists.
    public void clearCard()
    {
        for (int i = 0; i < numberInk.length; i++)
        {
            number[i].setText("");
            numberInk[i].setIcon(null);
            numberInked[i] = false;
        }

        winLabel.setIcon(null);
        ballLabel.setIcon(startBall);
        displayCount.setText("0");
        gameActive = false;
        
        calledNumbers.clear();
        
        for (int i = 0; i < boardNumber.length; i++)
        {
            boardNumber[i].setForeground(Color.DARK_GRAY);
        }
    }
    
    //Unnecessary as the ink color and delay are essentially flags and so can be accessed from other classes
    public void setInkColor(int newInk)
    {
        inkColor = newInk;
    }
    
    public int getInkColor()
    {
        return inkColor;
    }
    
    public void setDelay(int newDelay)
    {
        delay = newDelay;
    }
    
    public int getDelay()
    {
        return delay;
    }
}

