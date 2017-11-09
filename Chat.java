import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
   JAVADOCS
   
*/
public class Chat extends JPanel implements ActionListener {
   
   private JTextArea jtaChat;
   private JTextField jtfMsg; 
   private static Socket clientSocket = null;
   private static PrintStream os = null;
   private static DataInputStream is = null;
   private static BufferedReader inputLine = null;
   private static boolean closed = false;
   
   public Chat()
   {
      this.setLayout(new GridLayout(2,1));
      
      //displays all the messages
      jtaChat = new JTextArea(10,0);
      JScrollPane scrollPane = new JScrollPane(jtaChat);
      scrollPane.setViewportView(jtaChat);
      jtaChat.setEditable(false);
      jtaChat.setLineWrap(true);

      
      JPanel jpSend = new JPanel(new FlowLayout());
      jtfMsg = new JTextField(10);
      jpSend.add(jtfMsg);
      JButton jbSend = new JButton("Send");
      jpSend.add(jbSend);
      jbSend.addActionListener(this);
      
      this.add(scrollPane);
      this.add(jpSend);
      this.setSize(200,100);
      
      
      String host = "localhost";
      int portNumber = 2222;
      
     /*
     * Open a socket on a given host and port. Open input and output streams.
     */
      try {
         clientSocket = new Socket(host, portNumber);
         inputLine = new BufferedReader(new InputStreamReader(System.in));
         os = new PrintStream(clientSocket.getOutputStream());
         is = new DataInputStream(clientSocket.getInputStream());
      } catch (UnknownHostException e) {
         System.err.println("Don't know about host " + host);
      } catch (IOException e) {
         System.err.println("Couldn't get I/O for the connection to the host "
            + host);
      }
   
    /*
     * If everything has been initialized, write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
      if (clientSocket != null && os != null && is != null) {
         
         /* Create a thread to read from the server. */
         new chatThread().start();     
      }
   }//end constructor
   
   
   
   /**
   * Create a thread to read from the server.
   * 
   * 
   */
   class chatThread extends Thread {
   
      public chatThread(){
      
      }
      public void run() {
      /*
      * Keep on reading from the socket till we receive "Bye" from the
      * server. Once we received that then we want to break.
      */
         String responseLine;
         try {
            while ((responseLine = is.readLine()) != null) {
               jtaChat.append(responseLine + '\n');
               if (responseLine.indexOf("*** Bye") != -1)
                  break;
            }
            closed = true;
         } catch (IOException e) {
            System.err.println("IOException:  " + e);
         }
      }
   }
   public void actionPerformed(ActionEvent aae) {
      if(aae.getActionCommand()=="Send"){
         if (clientSocket != null && os != null && is != null) {
         
         /* Create a thread to read from the server. */
            os.println(jtfMsg.getText());
            jtfMsg.setText("");
         }
      }
   }
}//end Chat class