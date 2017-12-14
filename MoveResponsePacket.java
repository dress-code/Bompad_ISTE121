import java.io.*;
/**
*  MoveResponsePacket makes an Object that contains a boolean.
*  This boolean indicates whether or not the move is valid for the player to make
*  @author Team 2
*  @version 12/3/2017
*/
public class MoveResponsePacket implements Serializable{
   private boolean response;
   public MoveResponsePacket(boolean b){
      response = b;
   }
   
   public boolean getResponse(){
      return response;
   }
   
   public void setResponse(boolean b){
      response = b;
   }
}