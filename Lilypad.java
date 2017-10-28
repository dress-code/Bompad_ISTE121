import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   JAVADOCS
   Lilypad buttons class
   create icons(Anna's field - HAVE FUN :) )
*/


public class Lilypad extends JButton
{
   boolean isSpecial = true;
   
   public Lilypad()
   {
      //new button
      JButton jbLily = new JButton();
      
      //set icons
      Icon lily = new ImageIcon(pic_file); //regular lilypad
      Icon sink = new ImageIcon(pic_file); //sunken lilypad
      Icon pieces = new ImageIcon(pic_file); //blown up lilypad
      
      //put icons on buttons
      jbLily.setIcon(lily); //default
      jbLily.setIcon(sink); //after player choose to sink lily
      jbLily.setIcon(pieces); //aftermath of boom
      
   }//end constructor
   
   public void setBonus(boolean b)
   {
      
   }//end setBonus
}//end Lilypad class