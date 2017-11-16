import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;

/**
* A class containing the constructors and methods associated with a board of lilypads.
* @Author - Team 2
* @version - 11/10/17
*/
public class Pond extends JPanel{
   
   //Declares and instantiates a 2-D array of LilyPads.
   private Lilypad[][] lilypads = new Lilypad[10][10];
   //Declares and insntantiates an ArrayList of Player objects.
   private ArrayList<Player> players = new ArrayList<Player>();
   //Is there a winner of the game?
   private boolean winner = false;
   Point lilyPadCoord;
   //Whose turn is it?
   int currentTurn = 0;
   //Icon for an empty lilypad and open water spot.
   Icon emptyPad = new ImageIcon("empty.png");
   Icon water = new ImageIcon("water.png");
   
   /**
   * Constructor for a "pond" board of 64 LilyPads in an 8 x 8 grid layout.
   */
   public Pond(){
   
      this.setLayout(new GridLayout(10,10));
      //Creates Player objects and adds them to the ArrayList.
      for(int p = 1; p < 5; p++){
         Player player = new Player("testing", p);
         players.add(player);
      }
      
      //Creates lilypads and adds them to the 2-D array.
      for(int i = 0; i < 10; i++){
         for(int j = 0; j < 10; j++){
            Lilypad lp = new Lilypad(i,j);
            lp.addMouseListener(new CustomMouseListener()); 
         /*Generates a random number. If the number matches a 
         predetermined value, then the LilyPad is set as a bonus space.*/
            if( ( 1 + (int)(Math.random() * 10)) == 1)
            {
               lp.setBonus(true);
            }
            lilypads[i][j] = lp;
            
            //sets all new lilypads as a valid space for movement.
            lp.setValid(true);
            
            //Sets the players at their respective starting locations.
            if(i == 1 && j == 1){
               lp.setIcon(players.get(0).getIcon());
            }
            else if(i == 1 && j == 8){
               lp.setIcon(players.get(1).getIcon());
            }
            else if(i == 8 && j == 1){
               lp.setIcon(players.get(2).getIcon());
            }
            else if(i == 8 && j == 8){
               lp.setIcon(players.get(3).getIcon());
            }
            else{
               lp.setIcon(emptyPad);
            }
            this.add(lilypads[i][j]);
         }
      }
      //sets border on left side.
      for(int i = 0; i < 10; i++){
         Icon borderLeft = new ImageIcon("border-left.png");
         lilypads[i][0].setIcon(borderLeft);
         lilypads[i][0].setValid(false);
      }
      //sets border on top.
      for(int i = 0; i < 10; i++){
         Icon borderTop = new ImageIcon("border-top.png");
         lilypads[0][i].setIcon(borderTop);
         lilypads[0][i].setValid(false);
      }
      //sets border on right side.
      for(int i = 0; i < 10; i++){
         Icon borderRight = new ImageIcon("border-right.png");
         lilypads[i][9].setIcon(borderRight);
         lilypads[i][9].setValid(false);
      }
      //Sets border on bottom.
      for(int i = 0; i < 10; i++){
         Icon borderBottom = new ImageIcon("border-bottom.png");
         lilypads[9][i].setIcon(borderBottom);
         lilypads[9][i].setValid(false);
      }
      //Sets corners of the pond.
      lilypads[9][0].setIcon(new ImageIcon("bottom-left.png"));
      lilypads[9][9].setIcon(new ImageIcon("bottom-right.png"));
      lilypads[0][0].setIcon(new ImageIcon("top-left.png"));
      lilypads[0][9].setIcon(new ImageIcon("top-right.png"));
      
   }//end constructor
   
   public class CustomMouseListener implements MouseListener {
      public void mouseClicked(MouseEvent e) {
         //left click moves the Player.
         
         if(currentTurn == 4){
            currentTurn = 0;
         }//end if statement
               
         players.get(currentTurn).setTurn(true);
         if (e.getButton() == MouseEvent.BUTTON1 && players.get(currentTurn).getTurn()) { 
               int row = -1;
               int column = -1;
               //Is there a better way to do this than going over the entire board?
               //Yes, changed to a more efficient option.
               try{
                 row = ((Lilypad)e.getComponent()).getRow();
                 column = ((Lilypad)e.getComponent()).getCol();
                 if(lilypads[row][column].isValid())
                 {       
                     System.out.println("Row: " + row + " Col: " + column);
                     
                     //if the player is not dead...
                        if(players.get(currentTurn).getIsDead() == false){
                           System.out.println("Player " + (currentTurn+1) + "'s turn. ");
                           lilypads[row][column].setIcon(players.get(currentTurn).getIcon());
                           
                           //Gets the position of the player before the move is made and sets the icon to the empty tile.
                           Point oldPos = players.get(currentTurn).getCurrentLocation();
                           int xCoor =(int) oldPos.getX();
                           int yCoor =(int) oldPos.getY();
                           lilypads[xCoor][yCoor].setIcon(emptyPad);
                           
                           //Sets the position of the player to the new location.
                           players.get(currentTurn).setCurrentLocation(new Point(row,column));;
                   
                           //ends the turn of the player and increments the class variable so it becomes the next player's turn.
                           players.get(currentTurn).setTurn(false);
                           currentTurn++;
                        }//end if statement 1b
                  }//end if statement checking validity.
                  else{
                     JOptionPane.showMessageDialog(lilypads[4][4], "Please choose a valid lilypad.");
                  }
             }//end try block
             
             catch(ArrayIndexOutOfBoundsException aioobe){
                  JOptionPane.showMessageDialog(lilypads[4][4], "Please choose a valid lilypad.");
             }//end catch block

         }//end outermost if statement.
   
         //Right click sinks a lilypad.
         else if (e.getButton() == MouseEvent.BUTTON3 && players.get(currentTurn).getTurn()){
            int row = -1;
            int column = -1;
            row = ((Lilypad)e.getComponent()).getRow();
            column = ((Lilypad)e.getComponent()).getCol();
            if(lilypads[row][column].isValid()){       
               System.out.println("Row: " + row + " Col: " + column);
                     
               //if the player is not dead...
               if(players.get(currentTurn).getIsDead() == false){
                  System.out.println("Player " + (currentTurn+1) + "'s turn. ");
                  //Sinks the lilypad.
                  lilypads[row][column].setIcon(water);
                  lilypads[row][column].setValid(false);
                  //ends the turn of the player and increments the class variable so it becomes the next player's turn.
                  players.get(currentTurn).setTurn(false);
                  currentTurn++;
               }//end if
             }//end if
             else{
               JOptionPane.showMessageDialog(null, "Oops! That lilypads has been sunk!");
             }
        }//end else if
        else{
          JOptionPane.showMessageDialog(lilypads[4][4],"It is not your turn.");
        }
   }        
   
      public void mousePressed(MouseEvent e) {
      }
   
      public void mouseReleased(MouseEvent e) {
      }
   
      public void mouseEntered(MouseEvent e) {
      }
   
      public void mouseExited(MouseEvent e) {
      }
   }
}//end Pond class