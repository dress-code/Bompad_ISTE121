import java.util.*;
import java.io.*;
/**
*  GameStartPacket makes an Object that contains a Vector of Payers
*  and a boolean which indicates whether or not the game should start
*  @author Team 2
*  @version 12/3/2017
*/
public class GameStartPacket implements Serializable{
   private Vector<Player> players;
   private boolean startGame;
   
   /**
   * Parameterized constructor for GameStartPacket
   * @param gp a Vector of Player objects
   * @param b a boolean determines whether or not the game should start 
   */
   public GameStartPacket(Vector<Player> gp, boolean b){
      players = gp;
      startGame = b;
   }
   
   /**
   * A method that returns the list of player objects
   * @return a Vector of player objects
   */
   public Vector<Player> getPlayers(){
      return players;
   }
   
   /**
   * A method that determines whether the game starts or not
   * @return boolean true if game should start false if game should not start
   */
   public boolean shouldStartGame(){
      return startGame;
   }
}