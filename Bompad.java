import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import sun.audio.*;
import java.io.*;

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
            //JOptionPane.showMessageDialog(null, );
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
      private Player player;
      //private Socket s;
      
      /**
      * ClientConnection constructor.
      */
      public ClientConnection(Player p)
      {
         player = p;
         //code for the client connection constructor
      }
      
      /**
      * Run method for ClientConnection objects.
      */
      @Override
      public void run()
      {
         //code for the run method.
      }
   }
   
   /**
   * Inner class Chat creates the chat and has methods for updating.
   */
   class Chat extends JPanel implements ActionListener {
   
   private JTextArea jtaChat;
   private JTextField jtfMsg; 
   private String outgoing;
   
   /**
   * Constructor for a chat object.
   */
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
      jbSend.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){
            outgoing = jtfMsg.getText();
         }
      }); 
      
      jpSend.add(jbSend);
      
      this.add(scrollPane);
      this.add(jpSend);
      this.setSize(200,100);
      
      }//end constructor for the Chat.
      
      //just to get the compiler to shut up.
      public void actionPerformed(ActionEvent ae){};
      
}//end class Chat.

}//end Bompad class