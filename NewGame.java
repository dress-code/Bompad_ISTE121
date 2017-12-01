import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class NewGame extends JFrame{
   private Pond pond;
   private int currentTurn;
   
   public newGame(Pond p){
      pond = p;
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
      
      //jtaChat.append("You have entered the chat.\n");
      Sound sound = new Sound();
      sound.start();
      
      //add classes(panels) to frame
      this.add(pond, BorderLayout.CENTER);
      this.add(cc, BorderLayout.WEST);
      
      //sets frame
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.pack();
      this.setLocationRelativeTo(null);
      this.setResizable(false);
      this.setTitle("Bompad");
      this.setVisible(true);

   }//end constructor
   
   public void setTurn(int t){
      
   }
}