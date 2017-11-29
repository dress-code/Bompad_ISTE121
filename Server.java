import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* The server for BomPad. 
* @author Team 2
* @version 11/9/2017
*/
public class Server{
   //Keeps track of number of players/turns
   int turnTracker = 0;
   //an ArrayList containing all of the ObjectOutputStreams associated with all of the clients.
   ArrayList<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
   
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
         
         while(true){
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
         turnTracker++;
         turn = turnTracker;
         
         try{
            OutputStream out = cs.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            outputs.add(oos);
         }
         catch( IOException ioe ){
            ioe.printStackTrace();
         } // end out try/catch
      }//end ThreadedServer constructor.
      
      /**
      * The run method for the ThreadedServer class listens for input from the client.
      */
      @Override
      public void run(){
            System.out.println("entered run");   
            try{
               InputStream in = cs.getInputStream();
               ObjectInputStream oins = new ObjectInputStream(in);
            
               do{
                  Object unidentifiedObject = (String)oins.readObject();
                  //Checks if the received object is a String or an ArrayList.
                  if(unidentifiedObject instanceof String){
                     //If it is, Server writes objects back out to all of the clients without question.
                     for(int i = 0; i < outputs.size(); i++){
                        outputs.get(i).writeObject(unidentifiedObject);
                        outputs.get(i).flush();
                     }
                  }
                  
                  //Checks if the received object is an arraylist AND only does something if it is the proper turn...
                  if(unidentifiedObject instanceof ArrayList && turnTracker == turn){
                     //If it is, write the ArrayList back out to all of the clients.
                     for(int i = 0; i < outputs.size(); i++){
                        outputs.get(i).writeObject(unidentifiedObject);
                        outputs.get(i).flush();
                        turnTracker++;
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