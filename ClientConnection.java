import java.net.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
* Class ClientConnection contains the necessary code for 
* establishing a connection with a server and then communicate
* back and forth, always listening for input from the server.
* @author Team 2
* @version 11/29/2017
*/
public class ClientConnection extends JPanel implements Runnable
   {
      private Socket s;
      private OutputStream os;
      private InputStream is;
      private ObjectOutputStream oos;
      private ObjectInputStream ois;
      private Chat clientChat;
      private Pond board;
      private ArrayList<Player> players;
      private JButton jbSend;
      
      /**
      * Paramterized ClientConnection constructor.
      */
      public ClientConnection(String ipAddress, String playerName)
      {
         //instantiates the chat class variable to a new chat.
         clientChat = new Chat(playerName);
         //adds the client chat to this, a JPanel.
         this.add(clientChat);
         //Tries to establish a connection with a server.
         try{
            s = new Socket(ipAddress, 16789);
            os = s.getOutputStream();
            is = s.getInputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
         }
         catch(UnknownHostException uhe){
            uhe.printStackTrace();
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         }
      }
      
      /**
      * Run method for ClientConnection objects.
      */
      @Override
      public void run(){
         try{
            //while the object input stream is not null...
            while(ois != null){
               //Reads the object in, assigning it to a generic Object
               Object object = ois.readObject();
               //If statement checks if the object received is a String or an ArrayList.
               if(object instanceof String){
                  String msg = (String) object;
                  System.out.println(msg);
                  clientChat.updateChat(msg + "\n");
                  try{
                     File msgSound = new File("msgSound.au");
                     AudioInputStream ais = AudioSystem.getAudioInputStream(msgSound);
                     Clip clip = AudioSystem.getClip();
                     clip.open(ais);
                     clip.start();
                  }
                  catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
                  catch(LineUnavailableException lue){lue.printStackTrace();}
                  catch(IOException ioe){ioe.printStackTrace();}
               }//end if statement using instanceof to determine object type
               
               /*if the object is an instance of an arraylist, set the class variable
               ArrayList<Player> to the received ArrayList.*/
               if(object instanceof ArrayList){
                  players = (ArrayList<Player>) object;
                  System.out.println(players);
               }//end if statement using instanceof to determine object type.
               
            }//end while loop
         }//end try block
         catch(IOException ioe){ioe.printStackTrace();}
         catch(ClassNotFoundException cnfe){cnfe.printStackTrace();}
      }//end run method.
      
      /**
      * A method for writing objects to the server.
      */
      public void write(Object o){
         try{
            System.out.println("We are about to write the object.");
            oos.writeObject(o);
            oos.flush();
            System.out.println("We have written the object.");
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         }
      }
   
/**
* Class Chat creates the chat and has methods for updating.
*/
class Chat extends JPanel implements ActionListener {
   
   private String timeStamp;
   private JTextArea jtaChat;
   private JTextField jtfMsg; 
   private String playerName;
   
   /**
   * Constructor for a chat object.
   * @param playerName The name of the player in the chat.
   */
   public Chat(String name)
   {
      this.setLayout(new GridLayout(2,1));
      playerName = name;
      //displays all the messages
      jtaChat = new JTextArea(25,30);
      JScrollPane scrollPane = new JScrollPane(jtaChat);
      scrollPane.setViewportView(jtaChat);
      jtaChat.setEditable(false);
      jtaChat.setLineWrap(true);
   
         
      JPanel jpSend = new JPanel(new FlowLayout());
      jtfMsg = new JTextField(10);
      jpSend.add(jtfMsg);
      jbSend = new JButton("Send");
      jpSend.add(jbSend);
      jbSend.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){
            timeStamp = new SimpleDateFormat("hh:mm:ss a").format(new java.util.Date());
            String outgoing = getMsg();
            System.out.println(outgoing);
            ClientConnection.this.write(outgoing); //make chat srv inner class of CC?, then call this.
         }
      }); 
         
      this.add(scrollPane);
      this.add(jpSend);
      this.setSize(200,100);
         
   }//end constructor for the Chat.
      
   /**
   * A method for updating the chat.
   * @param The message to be appended to the chat.
   */
   public void updateChat(String msg){
      jtaChat.append(msg);
   }
   
   /**
   * An accessor method for getting the message from the JTextField.
   * @return The message to be sent to the server.
   */
   public String getMsg(){
      String msg = timeStamp + " " + playerName + ": " + jtfMsg.getText();
      jtfMsg.setText("");
      return msg;
   }
   
      //just to get the compiler to be quiet.
      public void actionPerformed(ActionEvent ae){};
      
}//end class Chat.
}//end class ClientConnection
