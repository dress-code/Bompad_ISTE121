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
   private boolean valid = true;
   private int row;
   private int col;
   
   /**
   * Constructor for a LilyPad object.
   * @param _row the row location of the lilypad
   * @param _col the colum location of the liypad
   */

   public Lilypad(int _row, int _col){
      row = _row;
      col = _col;
      this.setPreferredSize(new Dimension(75,75));
   }//end constructor
   
   /**
   * An accessor method for the point of a lilypad object.
   * @return the point location of a lilypad object.
   */
   public Point getPoint(){
      Point point = new Point(row, col);
      return point;
   }
   
   /**
   * Accessor method for the vaid attribute.
   * @return True if the LilyPad is a valid false if not.
   */
   public int getRow(){
      return row;
   }//end getRow
   
   /**
   * Accessor method for the vaid attribute.
   * @return True if the LilyPad is a valid false if not.
   */
   public int getCol(){
      return col;
   }//end getCol

   
   /**
   * Mutator method for setting the valid (boolean) attribute of a LilyPad object.
   * @param boolean v The value determining whether or not a LilyPad is a valid.
   */
   public void setValid(boolean v){
      valid = v;
   }//end setValid
   
   /**
   * Accessor method for the vaid attribute.
   * @return True if the LilyPad is a valid false if not.
   */
   public boolean isValid(){
      return valid;
   }//end isValid
   
   /**
   * Mutator method for setting the bonus (boolean) attribute of a LilyPad object.
   * @param boolean b The value determining whether or not a LilyPad is a bonus.
   */
   public void setBonus(boolean b){
      bonus = b;
   }//end setBonus
   
   /**
   * Accessor method for the bonus attribute.
   * @return True if the LilyPad is a bonus, false if not.
   */
   public boolean isBonus(){
      return bonus;
   }//end isBonus   
}//end Lilypad class