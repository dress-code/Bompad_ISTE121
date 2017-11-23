import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import javax.sound.sampled.*;

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
   int numPlayers = 4;
  
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
      
      highlightSpaces(players.get(currentTurn).getCurrentLocation());
      
   }//end constructor
   
   /**
   * A method which checks the validity of a player's move.
   * @param plocal The current location of the player.
   */
   public boolean checkMove(Point plocal, int row, int col){
      boolean isGood=false;
      //north
      if(row==(int)(plocal.getX()-1) && col==(int)(plocal.getY())){
         isGood=true;
      }
      //northeast
      else if(row==(int)(plocal.getX()-1) && col==(int)(plocal.getY()+1)){
         isGood=true;
      }
      //east
      else if(row==(int)(plocal.getX()) && col==(int)(plocal.getY()+1)){
         isGood=true;
      }
      //southeast
      else if(row==(int)(plocal.getX()+1) && col==(int)(plocal.getY()+1)){
         isGood=true;
      }
      //south
      else if(row==(int)(plocal.getX()+1) && col==(int)(plocal.getY())){
         isGood=true;
      }
      //southwest
      else if(row==(int)(plocal.getX()+1) && col==(int)(plocal.getY()-1)){
         isGood=true;
      }
      //west
      else if(row==(int)(plocal.getX()) && col==(int)(plocal.getY()-1)){
         isGood=true;
      }
      //northwest
      else if(row==(int)(plocal.getX()-1) && col==(int)(plocal.getY()-1)){
         isGood=true;
      }
     
      return isGood;
   }
   
   /**
   * A method for highlighting available moves.
   * @param p The current location of the player.
   */
   public boolean highlightSpaces(Point p){
      int xPos = (int)(p.getX());
      int yPos = (int)(p.getY());
      boolean isMoveable = true;
      int availableSpaces = 0;
      if(lilypads[xPos-1][yPos].isValid()){
         lilypads[xPos-1][yPos].setBorder(BorderFactory.createMatteBorder(2,0,2,0,Color.white));
         availableSpaces++;
      }
      if(lilypads[xPos-1][yPos+1].isValid()){
         lilypads[xPos-1][yPos+1].setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.white));
         availableSpaces++;
      }
      if(lilypads[xPos-1][yPos-1].isValid()){
         lilypads[xPos-1][yPos-1].setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.white));
         availableSpaces++;
      }
      if(lilypads[xPos][yPos-1].isValid()){
         lilypads[xPos][yPos-1].setBorder(BorderFactory.createMatteBorder(0,2,0,2,Color.white));
         availableSpaces++;
      }
      if(lilypads[xPos][yPos+1].isValid()){
         lilypads[xPos][yPos+1].setBorder(BorderFactory.createMatteBorder(0,2,0,2,Color.white));
         availableSpaces++;
      }
      if(lilypads[xPos+1][yPos-1].isValid()){
         lilypads[xPos+1][yPos-1].setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.white));
         availableSpaces++;
      }
      if(lilypads[xPos+1][yPos+1].isValid()){
         lilypads[xPos+1][yPos+1].setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.white));
         availableSpaces++;
      }
      if(lilypads[xPos+1][yPos].isValid()){
         lilypads[xPos+1][yPos].setBorder(BorderFactory.createMatteBorder(2,0,2,0,Color.white));
         availableSpaces++;
      }
      if(availableSpaces == 0){isMoveable = false;}
      return isMoveable;
   }
   
   /**
   * A method which unhighlights spaces for a move.
   * @param p the old position of the player before his move.
   */
   public void unhighlightSpaces(Point p){
      int xPos = (int)(p.getX());
      int yPos = (int)(p.getY());
      lilypads[xPos-1][yPos].setBorder(null);
      lilypads[xPos-1][yPos-1].setBorder(null);
      lilypads[xPos-1][yPos+1].setBorder(null);
      lilypads[xPos][yPos+1].setBorder(null);
      lilypads[xPos][yPos-1].setBorder(null);
      lilypads[xPos+1][yPos-1].setBorder(null);
      lilypads[xPos+1][yPos].setBorder(null);
      lilypads[xPos+1][yPos+1].setBorder(null);
   }
   
   /**
   * A method which figures out the life status of a player.
   */
   public void lifeStatus(){
      boolean lifeStatus = highlightSpaces(players.get(currentTurn).getCurrentLocation());
      if(!lifeStatus){
         death(currentTurn);
      }
   }
   
   /**
   * A method which changes the turns.
   */
   public void changeTurn(){
      players.get(currentTurn).setTurn(false);
      currentTurn++;
      if(currentTurn == numPlayers){
         currentTurn = 0;
      }//end if statement
      lifeStatus();
      highlightSpaces(players.get(currentTurn).getCurrentLocation());
   }
   
   /**
   * A method for killing off a player.
   * @param playerNum the number of the player to be killed.
   */
   public void death(int playerNum){
      if(players.size() > 1){
         players.get(playerNum).setIsDead(true);
         Point p = players.get(playerNum).getCurrentLocation();
         int posX = (int) p.getX();
         int posY = (int) p.getY();
         lilypads[posX][posY].setEnabled(false);
         players.remove(playerNum);
         numPlayers--;
      }
   }
      
   public class CustomMouseListener implements MouseListener {
      public void mouseClicked(MouseEvent e) {
      
         //left click moves the Player.               
         players.get(currentTurn).setTurn(true);
         if (e.getButton() == MouseEvent.BUTTON1 && players.get(currentTurn).getTurn()) {
            int row = -1;
            int column = -1;
               //Is there a better way to do this than going over the entire board?
               //Yes, changed to a more efficient option.
            try{
               row = ((Lilypad)e.getComponent()).getRow();
               column = ((Lilypad)e.getComponent()).getCol();
               Point thePad = new Point(row, column);
               boolean check = checkMove(players.get(currentTurn).getCurrentLocation(), row, column);
               if(lilypads[row][column].isValid())
               {       
                  System.out.println("Row: " + row + " Col: " + column);
                  
                  ArrayList<Point> playerPoints = new ArrayList<Point>();
                  for(int i=0; i < numPlayers; i++){
                     Point thePos = players.get(i).getCurrentLocation();
                     playerPoints.add(thePos);
                  }
                  
                  //if the player is not dead and the pad doesn't already have a frog...
                  if(players.get(currentTurn).getIsDead() == false && !(playerPoints.contains(thePad)) && check==true){
                     System.out.println("Player " + (currentTurn+1) + "'s turn. ");
                     lilypads[row][column].setIcon(players.get(currentTurn).getIcon());
                           
                     //Gets the position of the player before the move is made and sets the icon to the empty tile.
                     Point oldPos = players.get(currentTurn).getCurrentLocation();
                     unhighlightSpaces(oldPos);
                     int xCoor =(int) oldPos.getX();
                     int yCoor =(int) oldPos.getY();
                     lilypads[xCoor][yCoor].setIcon(emptyPad);
                           
                     //Sets the position of the player to the new location.
                     players.get(currentTurn).setCurrentLocation(new Point(row,column));
                     try{
                        File backgroundSound = new File("frog-move.au");
                        AudioInputStream ais = AudioSystem.getAudioInputStream(backgroundSound);
                        Clip clip = AudioSystem.getClip();
                        clip.open(ais);
                        clip.start();
                     }
                     catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
                     catch(LineUnavailableException lue){lue.printStackTrace();}
                     catch(IOException ioe){ioe.printStackTrace();}
                     //ends the turn of the player and increments the class variable so it becomes the next player's turn.
                     changeTurn();
                  }//end if statement 1b
               }//end if statement checking validity.
               else{
                  JOptionPane.showMessageDialog(lilypads[4][4], "Please choose a valid lilypad.");
               }
            }//end try block
             
            catch(ArrayIndexOutOfBoundsException aioobe){
               JOptionPane.showMessageDialog(lilypads[4][4], "Please choose a valid lilypad.");
            }//end catch block
         }
         
         //Right click sinks a lilypad.
         else if (e.getButton() == MouseEvent.BUTTON3 && players.get(currentTurn).getTurn()){
            int row = -1;
            int column = -1;
            row = ((Lilypad)e.getComponent()).getRow();
            column = ((Lilypad)e.getComponent()).getCol();
            Point thePad = new Point(row, column);
            if(lilypads[row][column].isValid()){       
               System.out.println("Row: " + row + " Col: " + column);
               ArrayList<Point> playerPoints = new ArrayList<Point>();
               for(int i=0; i < numPlayers; i++){
                  Point thePos = players.get(i).getCurrentLocation();
                  playerPoints.add(thePos);
               }
               //if the player is not dead...
               if(players.get(currentTurn).getIsDead() == false && !(playerPoints.contains(thePad))){
                  System.out.println("Player " + (currentTurn+1) + "'s turn. ");
                  //Sinks the lilypad.
                  lilypads[row][column].setIcon(water);
                  lilypads[row][column].setValid(false);
                  unhighlightSpaces(players.get(currentTurn).getCurrentLocation());
                  try{
                     File backgroundSound = new File("splash_sound.au");
                     AudioInputStream ais = AudioSystem.getAudioInputStream(backgroundSound);
                     Clip clip = AudioSystem.getClip();
                     clip.open(ais);
                     clip.start();
                  }
                  catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
                  catch(LineUnavailableException lue){lue.printStackTrace();}
                  catch(IOException ioe){ioe.printStackTrace();}

                  //ends the turn of the player and increments the class variable so it becomes the next player's turn.
                  changeTurn();
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