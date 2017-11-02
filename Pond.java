import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
* A class containing the constructors and methods associated with a board of lilypads.
* @Author - Team 2
* @version - 1
*/
public class Pond extends JPanel{
   
   //Declares and instantiates an ArrayList of LilyPads.
   private ArrayList<LilyPad> lilypads = new ArrayList<LilyPad>();
   private Player testPlayer = new Player("Doug", 1);
   /**
   * Constructor for a "pond" board of 64 LilyPads in an 8 x 8 grid layout.
   */
   public Pond()
   {
      this.setLayout(new GridLayout(8,8));
      for(int i = 0; i < 64; i++)
      {
         LilyPad lp = new LilyPad();
         /*Generates a random number. If the number matches a 
         predetermined value, then the LilyPad is set as a bonus space.*/
         if( ( 1 + (int)(Math.random() * 10)) == 1)
         {
            lp.setBonus(true);
         }
         lilypads.add(lp);
         if(i==0){
            lp.setIcon(new ImageIcon(testPlayer.getImage()));
         }
         this.add(lilypads.get(i));
      }
   }//end constructor
}//end Pond class