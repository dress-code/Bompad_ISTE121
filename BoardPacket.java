import java.io.*;

/**
* BoardPacket makes an object that contains a 2D array of Lilypad objects (the game board) 
* @author Team 2
* @version 12/3/2017
*/
public class BoardPacket implements Serializable{
   private Lilypad[][] lilypads;
   
   /**
   * Parameterized constructor of BoardPacket
   * @param lps - the 2D array of Lilypad objects (the game board)
   */
   public BoardPacket(Lilypad[][] lps){
      lilypads = lps;
   }
   
   /**
   * This method returns the game board
   * @return the 2D array of Lilypad objects
   */
   public Lilypad[][] getLilypads(){
      return lilypads;
   }
}