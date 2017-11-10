import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
            String instruct = "";
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
}//end Bompad class