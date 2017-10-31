import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   JAVADOCS
   
*/
public class Chat extends JPanel{
   
   private JTextArea jtaChat;
   private JTextField jtfMsg; 
   
   public Chat()
   {
      this.setLayout(new GridLayout(2,1));
      
      //displays all the messages
      jtaChat = new JTextArea(10,0);
      JScrollPane scrollpane = new JScrollPane(jtaChat);
      
      JPanel jpSend = new JPanel(new FlowLayout());
         jtfMsg = new JTextField(10);
         jpSend.add(jtfMsg);
         JButton jbSend = new JButton("Send");
         jpSend.add(jbSend);
      
      this.add(scrollpane);
      this.add(jpSend);
      this.setSize(200,100);
      
   }//end constructor
}//end Chat class