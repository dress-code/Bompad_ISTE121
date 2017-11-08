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
   //private ArrayList<Lilypad> lilypads = new ArrayList<Lilypad>();
   private Lilypad[][] lilypads = new Lilypad[10][10];
   private ArrayList<Player> testPlayer = new ArrayList<Player>();
   /**
   * Constructor for a "pond" board of 64 LilyPads in an 8 x 8 grid layout.
   */
   public Pond(){
   
      this.setLayout(new GridLayout(10,10));
      for(int p=1; p<5; p++){
         Player player = new Player("testing", p);
         testPlayer.add(player);
      }
      
      for(int i = 0; i < 10; i++){
         for(int j=0; j<10; j++){
         
            Lilypad lp = new Lilypad();
         /*Generates a random number. If the number matches a 
         predetermined value, then the LilyPad is set as a bonus space.*/
            if( ( 1 + (int)(Math.random() * 10)) == 1)
            {
               lp.setBonus(true);
            }
            lilypads[i][j]=lp;
         
            if(i==1 && j==1){
               Image img = testPlayer.get(0).getImage();
               Image resizedImage = img.getScaledInstance(70, 70, 0);
               lp.setIcon(new ImageIcon(resizedImage));
            }
            else if(i==1 && j==8){
               Image img = testPlayer.get(1).getImage();
               Image resizedImage = img.getScaledInstance(70, 70, 0);
               lp.setIcon(new ImageIcon(resizedImage));
            }
            else if(i==8 && j==1){
               Image img = testPlayer.get(2).getImage();
               Image resizedImage = img.getScaledInstance(70, 70, 0);
               lp.setIcon(new ImageIcon(resizedImage));
            }
            else if(i==8 && j==8){
               Image img = testPlayer.get(3).getImage();
               Image resizedImage = img.getScaledInstance(70, 70, 0);
               lp.setIcon(new ImageIcon(resizedImage));
            }
            else{
               try{
                  Image imge = ImageIO.read(getClass().getResource("empty.png"));
                  Image resizeImage = imge.getScaledInstance(70, 70, 0);
                  lp.setIcon(new ImageIcon(resizeImage));
               }
               catch(IOException ie){
                  ie.printStackTrace();
               }
            }
            this.add(lilypads[i][j]);
         }
      }
      for(int i=0; i<10; i++){
         try{
            Image imge = ImageIO.read(getClass().getResource("border-left.png"));
            lilypads[i][0].setIcon(new ImageIcon(imge));
         }
         catch(IOException ie){
            ie.printStackTrace();
         }
      }
      for(int i=0; i<10; i++){
         try{
            Image imge = ImageIO.read(getClass().getResource("border-top.png"));
            lilypads[0][i].setIcon(new ImageIcon(imge));
         }
         catch(IOException ie){
            ie.printStackTrace();
         }
      }
      for(int i=0; i<10; i++){
         try{
            Image imge = ImageIO.read(getClass().getResource("border-right.png"));
            lilypads[i][9].setIcon(new ImageIcon(imge));
         }
         catch(IOException ie){
            ie.printStackTrace();
         }
      }
      for(int i=0; i<10; i++){
         try{
            Image imge = ImageIO.read(getClass().getResource("border-bottom.png"));
            lilypads[9][i].setIcon(new ImageIcon(imge));
         }
         catch(IOException ie){
            ie.printStackTrace();
         }
      }
   }//end constructor
}//end Pond class