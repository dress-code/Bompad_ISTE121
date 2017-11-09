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
   private ArrayList<Player> players = new ArrayList<Player>();
   private boolean winner = false;
   Point lilyPadCoord;
   String action = "";
   
   
   Icon emptyPad = new ImageIcon("empty.png");
   
   /**
   * Constructor for a "pond" board of 64 LilyPads in an 8 x 8 grid layout.
   */
   public Pond(){
   
      this.setLayout(new GridLayout(10,10));
      for(int p=1; p<5; p++){
         Player player = new Player("testing", p);
         players.add(player);
      }
      
      for(int i = 0; i < 10; i++){
         for(int j=0; j<10; j++){
            Lilypad lp = new Lilypad(i,j);
            lp.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent ae){
                  int x = lp.getRow();
                  int y = lp.getCol();
                  lilyPadCoord = new Point(x, y);
                  action = "move";
               }
            });
         /*Generates a random number. If the number matches a 
         predetermined value, then the LilyPad is set as a bonus space.*/
            if( ( 1 + (int)(Math.random() * 10)) == 1)
            {
               lp.setBonus(true);
            }
            lilypads[i][j]=lp;
            lp.setValid(true);
            if(i==1 && j==1){
               lp.setIcon(players.get(0).getIcon());
            }
            else if(i==1 && j==8){
               lp.setIcon(players.get(1).getIcon());
            }
            else if(i==8 && j==1){
               lp.setIcon(players.get(2).getIcon());
            }
            else if(i==8 && j==8){
               lp.setIcon(players.get(3).getIcon());
            }
            else{
               lp.setIcon(emptyPad);
            }
            this.add(lilypads[i][j]);
         }
      }
      for(int i=0; i<10; i++){
            Icon borderLeft = new ImageIcon("border-left.png");
            lilypads[i][0].setIcon(borderLeft);
            lilypads[i][0].setValid(false);
      }
      for(int i=0; i<10; i++){
            Icon borderTop = new ImageIcon("border-top.png");
            lilypads[0][i].setIcon(borderTop);
            lilypads[0][i].setValid(false);
      }
      for(int i=0; i<10; i++){
            Icon borderRight = new ImageIcon("border-right.png");
            lilypads[i][9].setIcon(borderRight);
            lilypads[i][9].setValid(false);
      }
      for(int i=0; i<10; i++){
            Icon borderBottom = new ImageIcon("border-bottom.png");
            lilypads[9][i].setIcon(borderBottom);
            lilypads[9][i].setValid(false);
      }
      Icon water = new ImageIcon("water.png");
      lilypads[9][0].setIcon(water);
      lilypads[9][9].setIcon(water);
      lilypads[0][0].setIcon(water);
      lilypads[0][9].setIcon(water);
      
   }//end constructor
   
   /**
   * newGame method starts a new game.
   */
   public void newGame()
   {
      //Code for turn system. Will continue as long as there is no winner for the game.
      /*while(winner == false){
         for(int i = 0; i < players.size(); i++){
            if(players.get(i).getIsDead() == false){
               players.get(i).setTurn(true);
               System.out.println("Player " + (i+1) + "'s turn. ");
               while(players.get(i).getTurn)
               {
                  if(
               }
               //gets the information from an action performed
               //sets the turn of the player to false.
               players.get(i).setTurn(false);
            }
         }//end turn for loop
      }//end game while loop*/
   }
}//end Pond class