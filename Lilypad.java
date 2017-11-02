import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* A class containing constructors and methods associated with a LilyPad object.
* @author - Team 2
* @version 1
*/
public class Lilypad extends JButton
{

   //Declares and instantiates the necessary attributes.
   private boolean bonus = false;
   //private Icon empty = new ImageIcon(/*filepath to empty icon*/);
   
   /**
   * Constructor for a LilyPad object.
   */
   public Lilypad()
   {
      this.setPreferredSize(new Dimension(85,85));
      //Sets the new LilyPad icon to the empty space.
      //jbPad.setIcon(empty);
   }//end constructor
   
   /**
   * Mutator method for setting the bonus (boolean) attribute of a LilyPad object.
   * @param boolean b The value determining whether or not a LilyPad is a bonus.
   */
   public void setBonus(boolean b)
   {
      bonus = b;
   }//end setBonus
   
   /**
   * Accessor method for the bonus attribute.
   * @return True if the LilyPad is a bonus, false if not.
   */
   public boolean isBonus()
   {
      return bonus;
   }
}//end Lilypad class