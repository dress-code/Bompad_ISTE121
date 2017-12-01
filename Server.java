import java.io.*;
import java.net.*;
import java.util.*;

/**
* The server for BomPad. 
* @author Team 2
* @version 11/9/2017
*/
public class Server{
   //Keeps track of number of players/turns
   private int turnTracker = 0;
   private int numAssignment = 0;
   //an ArrayList containing all of the ObjectOutputStreams associated with all of the clients.
   private Vector<ObjectOutputStream> outputs = new Vector<ObjectOutputStream>();
   private Vector<Player> gamePlayers;
   private boolean startGame = false;
   
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
      private int turn;
      private Socket cs = null;
      
      /**
      * Constuctor for a ThreadedServer thread.
      * @param clientSocket The client socket being connected.
      */
      public ThreadedServer( Socket clientSocket ){
         cs = clientSocket;
         //Sets the turn of this thread.
         turn = numAssignment;
         numAssignment++;
         System.out.println("The turn assigned to this player is: " + turn);
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
               System.out.println("We have written turn to client.");
               InputStream in = cs.getInputStream();
               ObjectInputStream oins = new ObjectInputStream(in);
               //Wait for four players before starting.
               while(startGame == false){
                  if(outputs.size() == 4){
                     startGame = true;
                  }
               }
               System.out.print("Go run client "+turn);
               oos.writeObject(Boolean.valueOf(startGame));
               oos.flush();
               System.out.println(" Waiting to receive from you");
               do{
                  Object unidentifiedObject = oins.readObject();
                  System.out.println("Server has read something.");
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
                     //If it is, write the ArrayList back out to all of the clients.
                     for(int i = 0; i < outputs.size(); i++){
                        gamePlayers = (Vector<Player>) unidentifiedObject;
                        outputs.get(i).writeObject(unidentifiedObject);
                        outputs.get(i).flush();
                     }
                     if(turnTracker < 4){
                        turnTracker++;
                        System.out.println("Server 111: turn is now" + turnTracker);
                     }
                     else{
                        turnTracker = 0;
                     }

                  }
                  //If object is determined to be an Integer, the server knows it is a turn request
                  if(unidentifiedObject instanceof Integer){
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

   }//end class ThreadedServer
   
}//end class