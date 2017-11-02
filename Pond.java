import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;

/**
* A class containing the constructors and methods associated with a board of lilypads.
* @Author - Team 2
* @version - 1
*/
public class Pond extends JPanel{
   
   //Declares and instantiates an ArrayList of LilyPads.
   private ArrayList<Lilypad> lilypads = new ArrayList<Lilypad>();
   private ArrayList<Player> testPlayer = new ArrayList<Player>();
   /**
   * Constructor for a "pond" board of 64 LilyPads in an 8 x 8 grid layout.
   */
   public Pond(){
   
      this.setLayout(new GridLayout(8,8));
      for(int p=1; p<5; p++){
         Player player = new Player("testing", p);
         testPlayer.add(player);
      }
      
      for(int i = 0; i < 64; i++){
      
         Lilypad lp = new Lilypad();
         /*Generates a random number. If the number matches a 
         predetermined value, then the LilyPad is set as a bonus space.*/
         if( ( 1 + (int)(Math.random() * 10)) == 1)
         {
            lp.setBonus(true);
         }
         lilypads.add(lp);
         
         if(i==0){
            Image img = testPlayer.get(0).getImage();
            Image resizedImage = img.getScaledInstance(85, 85, 0);
            lp.setIcon(new ImageIcon(resizedImage));
         }
         else if(i==7){
            Image img = testPlayer.get(1).getImage();
            Image resizedImage = img.getScaledInstance(85, 85, 0);
            lp.setIcon(new ImageIcon(resizedImage));
         }
         else if(i==56){
            Image img = testPlayer.get(2).getImage();
            Image resizedImage = img.getScaledInstance(85, 85, 0);
            lp.setIcon(new ImageIcon(resizedImage));
         }
         else if(i==63){
            Image img = testPlayer.get(3).getImage();
            Image resizedImage = img.getScaledInstance(85, 85, 0);
            lp.setIcon(new ImageIcon(resizedImage));
         }
         else{
            try{
               Image imge = ImageIO.read(getClass().getResource("empty.png"));
               Image resizeImage = imge.getScaledInstance(85, 85, 0);
               lp.setIcon(new ImageIcon(resizeImage));
            }
            catch(IOException ie){
               ie.printStackTrace();
            }
         }
         this.add(lilypads.get(i));
      }
   }//end constructor
}//end Pond class