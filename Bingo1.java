package bingo1;

import javax.swing.JFrame;

public class Bingo1 
{
    public static void main(String[] args) 
    {
        Bingo moira = new Bingo();
        moira.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        moira.setSize(1200, 1000); //Size of the window
        moira.setVisible(true);
    }
}

/* Version history:

Beta 1.3 (December 6, 2018) - Changed ink logic so that the numbers that can be inked correspond with what numbers have been lit up on the board
Beta 1.2 (December 5, 2018) - Added four corners and the number board - when a number is called the corresponding spot on the board will 'light up'
Beta 1.1.1 (December 4, 2018) - Fixed right diagonal bingo and timer on unpause
Beta 1.1 (December 4, 2018) - Added pause feature, a ball count, and fixed logic between games
Beta 1.0 (December 3, 2018) - Working version

Known bugs:
1. Occasionally the buttons will not re-enable when exiting a window (Beta 1.0)
*/