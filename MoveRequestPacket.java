import java.awt.*;
import java.io.*;
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