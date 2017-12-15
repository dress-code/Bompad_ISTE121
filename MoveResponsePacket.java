import java.io.*;

/**
*  MoveResponsePacket makes an Object that contains a boolean.
*  This boolean indicates whether or not the move is valid for the player to make
*  @author Team 2
*  @version 12/3/2017
*/
public class MoveResponsePacket implements Serializable{
   private boolean response;
   
   /**
   * Parameterized constructor for MoveResponsePacket
   * @param b - boolean determines if a move is valid or not
   */
   public MoveResponsePacket(boolean b){
      response = b;
   }
   
   /**
   * A method that returns a boolean true if the move is valid false otherwise
   * @return boolean for the validity of a move
   */
   public boolean getResponse(){
      return response;
   }
   
   /**
   * A method that sets the validity of a move 
   * @param b - boolean determines if a move is valid or not
   */
   public void setResponse(boolean b){
      response = b;
   }
}