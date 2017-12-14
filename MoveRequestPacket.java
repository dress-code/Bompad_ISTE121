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
   
   public MoveRequestPacket(Point p)
   {
      newLilypad = p;
   }
   
   public Point getNewLoc(){
      return newLilypad;
   }
}