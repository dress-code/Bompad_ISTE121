import java.util.*;
import java.io.*;
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