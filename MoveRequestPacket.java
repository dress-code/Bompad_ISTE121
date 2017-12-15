import java.awt.*;
import java.io.*;
/**
*  MoveRequestPacket makes an Object that contains a new Lilypad.
*  This Lilypad is the oe that the player is requesting to move to
*  @author Team 2
*  @version 12/3/2017
*/
public class MoveRequestPacket implements Serializable{
   private Point newLilypad;
   
   /**
   * Parameterized constructor for MoveRequestPacket
   * @param p - the location of a lilypad that the player wants to move to
   */
   public MoveRequestPacket(Point p)
   {
      newLilypad = p;
   }
   
   /**
   * A method that returns the location of a lilypad that the player wants to move to
   * @return the location of the player's requested lilypad   
   */
   public Point getNewLoc(){
      return newLilypad;
   }
}