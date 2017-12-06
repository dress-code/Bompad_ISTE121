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
   
   public GameStartPacket(Vector<Player> gp, boolean b){
      players = gp;
      startGame = b;
   }
   
   public Vector<Player> getPlayers(){
      return players;
   }
   
   public boolean shouldStartGame(){
      return startGame;
   }
}