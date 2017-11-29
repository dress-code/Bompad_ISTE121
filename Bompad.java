import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
<<<<<<< HEAD
* Class Bompad constructs all of the necessary components for a game of Bompad.
* @author Team 2
* @version 11/9/2017
=======
<<<<<<< HEAD
* Class Bompad constructs all of the necessary components for a game of Bompad.
* @author Team 2
* @version 11/9/2017
=======
* A class containing the constructor for a BomPad game.
* @author - Team 2
* @version 10/31/2017
>>>>>>> Doug
>>>>>>> Anna's-branch.-
*/

public class Bompad extends JFrame{

   private JButton jbSend;
   private JTextArea jtaChat;
   private JTextField jtfMsg; 
   private String outgoing;
   private String ipAddress;
   private String playerName;
   private String timeStamp;
   private ArrayList<Player> players;
   
   //Main method calls the constructor for a new BomPad game.
   public static void main(String [] args)
   {
      new Bompad();
   }//end main
   
   /**
<<<<<<< HEAD
   * Construcots a game of Bompad.
=======
<<<<<<< HEAD
   * Construcots a game of Bompad.
=======
   * Constructor for a BomPad game.
>>>>>>> Doug
>>>>>>> Anna's-branch.-
   */
   public Bompad()
   {
      ipAddress = JOptionPane.showInputDialog(null, "What is the IP address of your server?");
      playerName = JOptionPane.showInputDialog(null, "What is your name?");
      //MENUS
      JMenuBar jmb = new JMenuBar();
      setJMenuBar(jmb);
      
      JMenu jmFile = new JMenu("File");
      JMenu jmHelp = new JMenu("Help");
      
      //menu items and actionlisteners
      //EXIT
      JMenuItem jmiExit = new JMenuItem("Exit");
      jmiExit.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            //informs server we quit
            JOptionPane.showMessageDialog(null, "Thanks for playing Bompad!");
            System.exit(0);
         }
      });
      
      //ABOUT
      JMenuItem jmiAbout = new JMenuItem("About");
      jmiAbout.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            //details on version, authors
            JOptionPane.showMessageDialog(null, "Bompad was created by Anna Jacobsen, Doug Kaelin, Zach Georges, & Alexa Lewis.");
         }
      });
      
      //INSTRUCT
      JMenuItem jmiInstruct = new JMenuItem("Instructions");
      jmiInstruct.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            //set instructions for Bompad
            String instruct = "Goal: Be the last frog able to make a valid move.\n\n"
            + "On your turn, you may either hop to a new space or sink a lilypad.\n\n"
            + "To sink a lilypad: Click on any space with a lilypad on it. This\n"
            + "will remove the lilypad from play. Random lilypads will have bombs\n"
            + "hidden underneath. Click one of these, and all of the lilypads around\n"
            + "the clicked space will also be sunk. Click at your own risk.\n\n"
            + "To move your frog: Click any lilypad adjacent to your frog. A lilypad occupied\n"
            + "by another frog, a border space, or an empty water space are not valid.\n";
            JOptionPane.showMessageDialog(null, instruct);   
         }
      });
      
      //add menus and menuitems to menubar
      jmb.add(jmFile);
      jmb.add(jmHelp);
      jmFile.add(jmiExit);
      jmHelp.add(jmiAbout);
      jmHelp.add(jmiInstruct);
      
      //instantiate outer classes
      Pond pond = new Pond();
      Chat chat = new Chat();
      ClientConnection cc = new ClientConnection();
      cc.start();
      jtaChat.append("You have entered the chat.\n");
      jbSend.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){
            timeStamp = new SimpleDateFormat("hh.mm.ss").format(new java.util.Date());
            outgoing = timeStamp + " " + playerName + ": " + jtfMsg.getText();
            jtfMsg.setText("");
            System.out.println(outgoing);
            cc.write(outgoing);
         }
      }); 
      Sound sound = new Sound();
      sound.start();
      
      //add classes(panels) to frame
      this.add(pond, BorderLayout.CENTER);
      this.add(chat, BorderLayout.WEST);
      
      //sets frame
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.pack();
      this.setLocationRelativeTo(null);
      this.setResizable(false);
      this.setTitle("Bompad");
      this.setVisible(true);
   }//end constructor
   
   class ClientConnection extends Thread
   {
      private Socket s;
      private OutputStream os;
      private InputStream is;
      private ObjectOutputStream oos;
      private ObjectInputStream ois;
      
      /**
      * ClientConnection constructor.
      */
      public ClientConnection()
      {
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
         //code for the run method.
         try{
            while(ois != null){
               System.out.println("We have received something.");
               //Reads the object in, assigning it to a generic Object
               Object object = ois.readObject();
               //If statement checks if the object received is a String or an ArrayList.
               if(object instanceof String){
                  String msg = (String) object;
                  System.out.println(msg);
                  jtaChat.append(msg + "\n");
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
            oos.writeObject(outgoing);
            oos.flush();
            System.out.println("We have written the object.");
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         }
      }
   }
   
   /**
   * Inner class Chat creates the chat and has methods for updating.
   */
   class Chat extends JPanel implements ActionListener {
   
      /**
      * Constructor for a chat object.
      */
      public Chat()
      {
         this.setLayout(new GridLayout(2,1));
         
         //displays all the messages
         jtaChat = new JTextArea(10,30);
         JScrollPane scrollPane = new JScrollPane(jtaChat);
         scrollPane.setViewportView(jtaChat);
         jtaChat.setEditable(false);
         jtaChat.setLineWrap(true);
   
         
         JPanel jpSend = new JPanel(new FlowLayout());
         jtfMsg = new JTextField(10);
         jpSend.add(jtfMsg);
         jbSend = new JButton("Send");
         jpSend.add(jbSend);
         
         this.add(scrollPane);
         this.add(jpSend);
         this.setSize(200,100);
         
      }//end constructor for the Chat.
      
      //just to get the compiler to be quiet.
      public void actionPerformed(ActionEvent ae){};
      
   }//end class Chat.

}//end Bompad class