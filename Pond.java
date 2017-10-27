import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
   JAVADOCS
   Pond(Board) class
*/

public class Pond extends JPanel{
   ArrayList<LilyPad> lilypads = new ArrayList<LilyPad>();
   
   public Pond()
   {
      this.setLayout(new GridLayout(8,8));
      for(int i = 0; i < 64; i++)
      {
         LilyPad lp = new LilyPad();
         
         //random number generator
         /*if(random == predetermined value)
         {
            lp.setBonus(true);
         }*/
         lilypads.add(lp);
      }
   }//end constructor
}//end Pond class