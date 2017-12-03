import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

/**
* The server for BomPad. 
* @author Team 2
* @version 11/9/2017
*/
public class Server{
   //Keeps track of number of players/turns
   private int turnTracker = 0;
   private Integer numAssignment = 0;
   //an ArrayList containing all of the ObjectOutputStreams associated with all of the clients.
   private Vector<ObjectOutputStream> outputs = new Vector<ObjectOutputStream>();
   private Vector<Player> gamePlayers;
   private boolean startGame = false;
   private int numPlayers = 4;
   private Lilypad[][] lilypads;
   
   public static void main(String [] args){
      new Server();
   }
   /**
   * Constructs a Server for BomPad.
   */
   public Server(){
   
      try{
         ServerSocket ss = new ServerSocket(16789);
         Socket cs = null;
         
         while(outputs.size() < 4){
            System.out.println("Waiting for a player to connect...");
            cs = ss.accept();
            System.out.println("Have a player: " + cs);
            
            ThreadedServer ts = new ThreadedServer( cs );
            ts.start();
         }//end while loop
      }//end try
      
      catch( BindException be ){
         System.out.println("Server already running.");
      }
      
      catch( IOException ioe ){
         ioe.printStackTrace();
      } // end catch blocks

   }//end constructor 
   
   /**
   * Inner classs creates a Thread for the Threaded server.
   */
   class ThreadedServer extends Thread {
      private int assignedNum;
      private Socket cs = null;
      private String welcomeMsg;
      
      /**
      * Constuctor for a ThreadedServer thread.
      * @param clientSocket The client socket being connected.
      */
      public ThreadedServer( Socket clientSocket ){
         cs = clientSocket;
         //Sets the turn of this thread.
         assignedNum = numAssignment;
         welcomeMsg = "You have connected to the chat.";
         assignedNum = numAssignment;
         numAssignment++;
         System.out.println("The turn assigned to this player is: " + assignedNum);
      }//end ThreadedServer constructor.
      
      /**
      * The run method for the ThreadedServer class listens for input from the client.
      */
      @Override
      public void run(){
            System.out.println("entered run");   
            try{
               OutputStream out = cs.getOutputStream();
               ObjectOutputStream oos = new ObjectOutputStream(out);
               outputs.add(oos);
               Integer turnMsg = Integer.valueOf(turn);
               oos.writeObject(turnMsg);
               oos.flush();
               oos.writeObject(welcomeMsg);
               System.out.println("We have written turn to client.");
               InputStream in = cs.getInputStream();
               ObjectInputStream oins = new ObjectInputStream(in);
               AssignedNumber assigned = new AssignedNumber(assignedNum);
               System.out.println("Server line 86 is assigning turn " + assigned.getAssigned() + " to the client.");
               oos.writeObject(assigned);
               oos.flush();
               //Wait for four players before starting.
                  if(outputs.size() == 4){
                     GameStartPacket gsp = new GameStartPacket(null, true);
                     for(int i = 0; i < outputs.size(); i++){
                        outputs.get(i).writeObject(gsp);
                        outputs.get(i).flush();
                        System.out.println("Line 90, Server has sent out all game start packets.");
                     }
                  }
               System.out.println(" Waiting to receive from you");
               do{
                  Object unidentifiedObject = oins.readObject();
                  System.out.println("Server has read something." + unidentifiedObject);
                  //Checks if the received object is a String or an ArrayList.
                  System.out.println("Server has read something");
               //Checks if the received object is a String or an ArrayList.
                  if(unidentifiedObject instanceof String){
                     System.out.println("Server has determined it read a String.");
                     //If it is, Server writes objects back out to all of the clients without question.
                     for(int i = 0; i < outputs.size(); i++){
                        System.out.println("Server is going to send String to all clients.");
                        outputs.get(i).writeObject(unidentifiedObject);
                        outputs.get(i).flush();
                     }
                     System.out.println("Server has sent String to all clients.");
                  }
               //Checks if the received object is an arraylist AND only does something if it is the proper turn...
                  if(unidentifiedObject instanceof Vector){
                     System.out.println("Server has determined it read a Vector.");
                     //Sets Server's vector of players to what it receives.
                     gamePlayers = (Vector<Player>) unidentifiedObject;
                     //Calls change turn method.
                     changeTurn();
                     //After change turn process, write the current turn out to all of the clients.
                     for(int i = 0; i < outputs.size(); i++){
                        outputs.get(i).writeObject(Integer.valueOf(turnTracker));
                        outputs.get(i).flush();
                     }
                     //After change turn process, write the Vector back out to all of the clients.
                     for(int i = 0; i < outputs.size(); i++){
                        outputs.get(i).writeObject(unidentifiedObject);
                        outputs.get(i).flush();
                     }
                  }
               //If object is determined to be an Integer, the server knows it is a turn request
                  if(unidentifiedObject instanceof Integer){
                     System.out.println("Server has determined it read an Integer.");
                     Integer intObject = (Integer) unidentifiedObject;
                     if (intObject.intValue() == -1){
                        for(int i = 0; i < outputs.size(); i++){
                           System.out.println("Server 121: Current turn is: " + turnTracker); 
                           outputs.get(i).writeObject(Integer.valueOf(turnTracker));
                           outputs.get(i).flush();
                        }
                     }
                     if(intObject.intValue() == -2) {
                         for(int i = 0; i < outputs.size(); i++){
                           outputs.get(i).writeObject(gamePlayers);
                           outputs.get(i).flush();
                        }
                     }

                  }
                  //if unidentified object is a Player, add it to the array list of players.
                  if(unidentifiedObject instanceof Player){
                     Player p = (Player) unidentifiedObject;
                     gamePlayers.add(p);
                  }
               //If unidentified object is a Boolean, start the game.
                  if(unidentifiedObject instanceof Boolean){
                     System.out.println("Server has determined it read a Boolean.");
                     turnTracker = 0;
                     gamePlayers.get(turnTracker).setTurn(true);
                     for(int i = 0; i < outputs.size(); i++){
                        outputs.get(i).writeObject(gamePlayers);
                     }
                  }
               //If unidentified object is detrmined to be a GameStartPacket...
                  if(unidentifiedObject instanceof GameStartPacket){
                     System.out.println("Server has read a GameStartPacket from the client.");
                     GameStartPacket gsp = (GameStartPacket) unidentifiedObject;
                     gamePlayers = gsp.getPlayers();
                     outputs.get(turnTracker).writeObject(Integer.valueOf(turnTracker));
                     System.out.println("Game has started with the first person's turn.");
                  }
               //If unidentified object is determined to be a boardPacket...
                  if(unidentifiedObject instanceof BoardPacket){
                     BoardPacket bp = (BoardPacket) unidentifiedObject;
                     lilypads = bp.getLilypads();
                  }
               //If UFO is determined to be a move request...
                  if(unidentifiedObject instanceof MoveRequestPacket){
                  System.out.println("Server has determined it received a move request.");
                     MoveRequestPacket mrp = (MoveRequestPacket) unidentifiedObject;
                     MoveResponsePacket mRespPack = isValidMove(mrp.getNewLoc());
                     for(int i = 0; i < outputs.size(); i++){
                           outputs.get(i).writeObject(mRespPack);
                           outputs.get(i).flush();
                     }
                  }
               }while ( oins!=null);//end while loop listening for input.
               
               //Close everything with all of the client connections.
               for(int i = 0; i < outputs.size(); i++){
                  outputs.get(i).close();
               }
               oins.close();
               cs.close();
            }
            
            catch(ClassNotFoundException cnfe){
               cnfe.printStackTrace();
            }//end inner try/catch 
            
            catch(IOException ioe){
               ioe.printStackTrace();
            }//end IOE catch block

      }//end run method.
      
      /**
      * A method which returns the adjacent spaces to a location in an arraylist.
      * @param x The x coordinate of a point (player location)
      * @param y The y coordinate of a point (player location)
      */
      public ArrayList<Lilypad> getAdjacent(Point p){
         int xPos = (int)p.getX();
         int yPos = (int)p.getY();
         ArrayList<Lilypad> adjacent = new ArrayList<Lilypad>();
         adjacent.add(lilypads[xPos-1][yPos]);
         adjacent.add(lilypads[xPos-1][yPos-1]);
         adjacent.add(lilypads[xPos-1][yPos+1]);
         adjacent.add(lilypads[xPos][yPos+1]);
         adjacent.add(lilypads[xPos][yPos-1]);
         adjacent.add(lilypads[xPos][yPos]);
         adjacent.add(lilypads[xPos+1][yPos]);
         adjacent.add(lilypads[xPos+1][yPos+1]);
         adjacent.add(lilypads[xPos+1][yPos-1]);
         return adjacent;
      }
      
      /**
      * A method which determines the life status of a player.
      * @param p the location of the player being checked for life.
      */
      public boolean lifeStatus(Point p){
         int availableSpace = 0;
         boolean alive;
         ArrayList<Lilypad> surrounding = getAdjacent(p);
         /*Removes the space that the frog is sitting on. Check getAdjacent method
         for the fifth index of the array list generated.*/
         surrounding.remove(5);
         for(int i = 0; i < surrounding.size(); i++){
            if(surrounding.get(i).isValid()){
               availableSpace++;
            }
         }
         if(availableSpace == 0){alive = false;}
         else{alive = true;}
         return alive;
      }

      
      /**
      * A method which changes the turns.
      */
      public void changeTurn(){
         //sets the turn of the player to false.
         gamePlayers.get(turnTracker).setTurn(false);
         //checks life status of all players before switching turns.
         for(int i = gamePlayers.size()-1; i >= 0; i--){
            System.out.println("Player #" + i + " check");
            if(!(lifeStatus(gamePlayers.get(i).getCurrentLocation()))){
               death(i);//Call to death method.
               if(turnTracker > i){
                  turnTracker--;
                  if(turnTracker < 0){
                     turnTracker = 0;
                  }
               }
            }
         }
         //Win condition.
         if(gamePlayers.size() == 1){
            //JOptionPane.showMessageDialog(null, gamePlayers.get(0).getName() + " has won the game.");
         }
         //Increments the current turn so that it jumps to the next player.
         turnTracker++;
         if(turnTracker >= gamePlayers.size()){
           turnTracker = 0;
         }//end if statement
         //highlightSpaces(players.get(turnTracker).getCurrentLocation());
      }
      
      /**
      * A method for killing off a player.
      * @param playerNum the number of the player to be killed.
      */
      public void death(int playerNum){
         if(gamePlayers.size() > 1){
            gamePlayers.get(playerNum).setIsDead(true);
            //Point p = gamePlayers.get(playerNum).getCurrentLocation();
            //int posX = (int) p.getX();
            //int posY = (int) p.getY();
            //lilypads[posX][posY].setEnabled(false);
            gamePlayers.remove(playerNum);
            outputs.remove(playerNum);
            numPlayers--;
         }
      }
      
      /**
      * A metho which checks the validity of a requested move.
      * @param p The point of the requested location.
      * @return A MoveResponsePacket with answer of whether or not a move is a valid one.
      */
      public MoveResponsePacket isValidMove(Point p){
         MoveResponsePacket mrp = new MoveResponsePacket(false);
         System.out.println("Server has created a new response packet.");
         Point oldLoc = gamePlayers.get(turnTracker).getCurrentLocation();
         ArrayList<Lilypad> alpads = getAdjacent(oldLoc);
         for(int i = 0; i < alpads.size(); i++)
         {
            if(alpads.get(i).getPoint() == p){
               mrp.setResponse(true);
               System.out.println("Server has set the response to true.");
            }
         }
         System.out.println("Server has returned the response packet.");
         return mrp;
      }
   }//end class ThreadedServer
   
}//end class