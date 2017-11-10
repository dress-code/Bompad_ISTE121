import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

/**
* The server for BomPad. 
* @author Team 2
* @version 11/9/2017
*/
public class Server{

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
            System.out.println("Have a player: "+cs);
            
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
   
   class ThreadedServer extends Thread {
   
      private Socket cs = null;
      
      /**
      * Constuctor for a ThreadedServer thread.
      * @param clientSocket The client socket being connected.
      */
      public ThreadedServer( Socket clientSocket ){
         cs = clientSocket;
      }//end ThreadedServer constructor.
      
      /**
      * The run method for the ThreadedServer class listens for input from the client.
      */
      @Override
      public void run(){
      
         try {
            InputStream in = cs.getInputStream();
            BufferedReader bin = new BufferedReader( new InputStreamReader( in ));
            
            OutputStream out = cs.getOutputStream();
            PrintWriter pout = new PrintWriter(new OutputStreamWriter(out));
            String clientMsg = null;
            
            do{
               //Reads the message from the client.
               clientMsg = bin.readLine();
               
               //Does something with the code.
               StringBuilder sb = new StringBuilder( clientMsg );
               sb = sb.reverse();
               
               //Send the message back to the client.
               pout.println( sb.toString() );
               //FLUSH.
               pout.flush();
               
               System.out.println("Received: "+ clientMsg + ", sent " + sb.toString() );
               
            } while (  ! clientMsg.equalsIgnoreCase("QUIT") );
            // close everything
            bin.close();
            pout.close();
            cs.close();
         
         }
         catch( IOException ioe ){
            ioe.printStackTrace();
         } // end catch - inner try

      }//end run method.

   }//end class ThreadedServer
}//end class