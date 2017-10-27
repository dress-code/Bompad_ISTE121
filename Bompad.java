import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   Blabalbalbalbalbalbabbaabllaaaaaaaahhh JAVADOCS
*/

public class Bompad extends JFrame{
   
   public static void main(String [] args)
   {
      new Bompad();
   }//end main
   
   public Bompad()
   {
      //MENUS
      JMenuBar jmb = new JMenuBar();
      setJMenuBar(jmb);
      
      JMenu jmFile = new JMenu("File");
      JMenu jmHelp = new JMenu("Help");
      
      //menu items and actionlisteners
      JMenuItem jmiExit = new JMenuItem("Exit");
      jmiExit.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            //informs server we quit
            JOptionPane.showMessageDialog(null, "Thanks for playing Bompad");
         }
      });
      
      JMenuItem jmiAbout = new JMenuItem("About");
      jmiAbout.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {
            //details on version, authors
            //JOptionPane.showMessageDialog(null, );
         }
      });
      
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
      
      //add classes to frame and set frame
      this.add(Pond, BorderLayout.CENTER);
      this.add(Chat, BorderLayout.SOUTH);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setLocationRelativeTo(null);
      this.pack();
      this.setVisible(true);
   }//end constructor
}//end class