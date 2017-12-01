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
   private int myTurn;
   //Icon for an empty lilypad and open water spot.
   private Icon emptyPad = new ImageIcon("empty.png");
   private Icon water = new ImageIcon("water.png");
   private int numPlayers = 4;
   private ClientConnection connection;
  
   /**
   * Constructor for a "pond" board of 64 LilyPads in an 8 x 8 grid layout.
   */
   public Pond(ClientConnection cc){
   
      connection = cc;
      this.setLayout(new GridLayout(10,10));
      //Creates Player objects and adds them to the ArrayList.
      for(int p = 1; p < 5; p++){
         Player player = new Player(p);
         players.add(player);
      }
      
      //Creates lilypads and adds them to the 2-D array.
      for(int i = 0; i < 10; i++){
         for(int j = 0; j < 10; j++){
            Lilypad lp = new Lilypad(i,j);
            lp.addMouseListener(new CustomMouseListener()); 
         /*Generates a random number. If the number matches a 
         predetermined value, then the LilyPad is set as a bonus space.*/
            if( ( 1 + (int)(Math.random() * 9)) == 1)
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
      
      highlightSpaces(players.get(0).getCurrentLocation());
      //players.get(0).setTurn(true);
      myTurn = connection.getTurn();
      System.out.println("My turn is: " + myTurn);
      
   }//end constructor
   
   /**
   * A method which returns the adjacent spaces to a location in an arraylist.
   * @param x The x coordinate of a point (player location)
   * @param y The y coordinate of a point (player location)
   */
   public ArrayList<Lilypad> getAdjacent(Point p){
      int xPos = (int)p.getX();
      int yPos = (int)p.getY();
      ArrayList<Lilypad> adjacent = new ArrayList<Lilypad>();
      adjacent.add(lilypads[xPos-1][yPos]);
      adjacent.add(lilypads[xPos-1][yPos-1]);
      adjacent.add(lilypads[xPos-1][yPos+1]);
      adjacent.add(lilypads[xPos][yPos+1]);
      adjacent.add(lilypads[xPos][yPos-1]);
      adjacent.add(lilypads[xPos][yPos]);
      adjacent.add(lilypads[xPos+1][yPos]);
      adjacent.add(lilypads[xPos+1][yPos+1]);
      adjacent.add(lilypads[xPos+1][yPos-1]);
      return adjacent;
   }
   
   /**
   * A method which bombs the spaces all around a bonus bomb tile.
   * @param x the row of the bomb lily pad.
   * @param y the column of the bomb lily pad.
   */
   public void bombSpaces(Point p){
      ArrayList<Lilypad> explosion = getAdjacent(p);
      for(int i = 0; i < explosion.size(); i++)
      {
         if(explosion.get(i).isValid()){
            explosion.get(i).setValid(false);
            explosion.get(i).setIcon(water);
         }
      }
      sound("explosion.au");
   
   }//end method bombSpaces()
   
   /**
   * A method for highlighting available moves.
   * @param p The current location of the player.
   */
   public void highlightSpaces(Point p){
      ArrayList<Lilypad> highlight = getAdjacent(p);
      for(int i = 0; i < highlight.size(); i++)
      {
         if(highlight.get(i).isValid()){
            highlight.get(i).setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.white));
         }
      }
   }
   
   /**
   * A method which unhighlights spaces for a move.
   * @param p the old position of the player before his move.
   */
   public void unhighlightSpaces(Point p){
      ArrayList<Lilypad> unhighlight = getAdjacent(p);
      for(int i = 0; i < unhighlight.size(); i++){
         unhighlight.get(i).setBorder(null);
      }
   }
   
   /**
   * A method which determines the life status of a player.
   * @param p the location of the player being checked for life.
   */
   public boolean lifeStatus(Point p){
      System.out.println("We have entered the lifeStatus method.");
      int availableSpace = 0;
      boolean alive;
      ArrayList<Lilypad> surrounding = getAdjacent(p);
      /*Removes the space that the frog is sitting on. Check getAdjacent method
      for the fifth index of the array list generated.*/
      surrounding.remove(5);
      for(int i = 0; i < surrounding.size(); i++){
         if(surrounding.get(i).isValid()){
            availableSpace++;
         }
      }
      if(availableSpace == 0){alive = false;}
      else{alive = true;}
      System.out.println("Available spaces: " + availableSpace);
      return alive;
   }
   
   /**
   * A method which changes the turns.
   */
   /*public void changeTurn(){
      //sets the turn of the player to false.
      players.get(myTurn).setTurn(false);
      //checks life status of all players before switching turns.
      for(int i = players.size()-1; i >= 0; i--){
         System.out.println("Player #" + i + " check");
         if(!(lifeStatus(players.get(i).getCurrentLocation()))){
            death(i);
            if(currentTurn > i){
               currentTurn--;
               if(currentTurn < 0){
                  currentTurn = 0;
               }
            }
         }
      }
      //Win condition.
      if(players.size() == 1){
         JOptionPane.showMessageDialog(null, players.get(0).getName() + " has won the game.");
      }
      //Increments the current turn so that it jumps to the next player.
      currentTurn++;
      if(currentTurn >= players.size()){
         currentTurn = 0;
      }//end if statement
      players.get(currentTurn).setTurn(true);
      highlightSpaces(players.get(currentTurn).getCurrentLocation());
   }*/
   
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
   
   /**
   *A method which moves a player
   */
   public void move(Point newPoint, Player player){
   
      /*Takes the old location of the frog and changes 
      the space it was on to valid and an empty icon.*/
      int row = (int)player.getCurrentLocation().getX();
      int col = (int)player.getCurrentLocation().getY();
      lilypads[row][col].setIcon(emptyPad);
      lilypads[row][col].setValid(true);
      
      /*Sets the new location of the frog and changes the
      icon to that of player*/
      int newRow = (int)newPoint.getX();
      int newCol = (int)newPoint.getY();
      lilypads[newRow][newCol].setIcon(player.getIcon());
      lilypads[newRow][newCol].setValid(false);
      player.setCurrentLocation(newPoint);
      sound("frog-move.au");
   }//end move
   
   /**
   *A method which updates the board
   */
   public void update(){
      ArrayList<Player> newPlayer = connection.getPlayer();
      if(newPlayer.size()==0){
         System.out.println("It's the first turn so the arraylist is empty");
      }
      else{
         for(int i=0; i<players.size(); i++){
            move(newPlayer.get(i).getCurrentLocation(), players.get(i));
         }
      }
   //       for(int i=0; i<lilypads.length; i++){
   //          for(int j=0; j<lilypads.length; j++){
   //             iterate through lilypads
   //             if(lilypads[i][j].isValid()==true){
   //                lilypads[i][j].setIcon(emptyPad);
   //             }
   //          }
   //       }
   }//end update
   
   
   /**
   * A method which provides the sound effects for a Bompad game.
   * @param fileName the name of the .au file containing the sound effect.
   */
   public void sound(String fileName){
      try{
         File soundFile = new File(fileName);
         AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
         Clip clip = AudioSystem.getClip();
         clip.open(ais);
         clip.start();
      }
      catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
      catch(LineUnavailableException lue){lue.printStackTrace();}
      catch(IOException ioe){ioe.printStackTrace();}
   }//end method sound
      
   public class CustomMouseListener implements MouseListener {
   
      public void mouseClicked(MouseEvent e) {
         connection.write(players);
         update();
         //left click moves the Player.               
         if (e.getButton() == MouseEvent.BUTTON1 && connection.getTurn() == myTurn) {
            int row = -1;
            int column = -1;
         
            try{
            
               Lilypad clicked = (Lilypad) e.getComponent();
               row = clicked.getRow();
               column = clicked.getCol();
               Point newLoc = new Point(row, column);
               //Gets the location of the player before a move is made.
               Point oldLoc = players.get(myTurn).getCurrentLocation();
               int oldRow = (int)oldLoc.getX();
               int oldCol = (int)oldLoc.getY();
               //Gets an array list of adjacent lilypads.
               ArrayList<Lilypad> alpads = getAdjacent(oldLoc);
               //Iterates through the adjacent lilypads and checks if any of them matches the clicked lilypad.
               for(int i = 0; i < alpads.size(); i++){
                  Point compare = alpads.get(i).getPoint();
                  if( (compare.getX() == newLoc.getX()) && (compare.getY() == newLoc.getY())){
                     move(newLoc, players.get(myTurn));
                  }
               }
               connection.write(players);
               
               
               /*Point thePad = new Point(row, column);
               int playerX = (int)players.get(currentTurn).getCurrentLocation().getX();
               int playerY = (int)players.get(currentTurn).getCurrentLocation().getY();
               ArrayList<Lilypad> surrounding = getAdjacent(playerX, playerY);
               if(lilypads[row][column].isValid())
               {       
                  System.out.println("Row: " + row + " Col: " + column);
                  
                  ArrayList<Point> playerPoints = new ArrayList<Point>();
                  for(int i=0; i < numPlayers; i++){
                     Point thePos = players.get(i).getCurrentLocation();
                     playerPoints.add(thePos);
                  }
                  
                  //if the player is not dead and the pad doesn't already have a frog...
                  if(players.get(currentTurn).getIsDead() == false && !(playerPoints.contains(thePad)) && surrounding.indexOf((Lilypad)e.getComponent())!=-1){
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
                     sound("frog-move.au");
                     //ends the turn of the player and increments the class variable so it becomes the next player's turn.
                     changeTurn();
                  }//end if statement 1b
               }//end if statement checking validity.
               else{
                  JOptionPane.showMessageDialog(lilypads[4][4], "Please choose a valid lilypad.");
               }*/
               
            }//end try block
             
            catch(ArrayIndexOutOfBoundsException aioobe){
               JOptionPane.showMessageDialog(lilypads[4][4], "Please choose a valid lilypad.");
            }//end catch block
         }
         
         //Right click sinks a lilypad.
         else if (e.getButton() == MouseEvent.BUTTON3 && connection.getTurn() == myTurn){
            /*int row = -1;
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
                  //If a lilpad is a bomb, calls the bombSpaces method.
                  if(lilypads[row][column].isBonus()){
                     bombSpaces(thePad);
                     unhighlightSpaces(players.get(currentTurn).getCurrentLocation());
                  }
                  else{
                     lilypads[row][column].setIcon(water);
                     lilypads[row][column].setValid(false);
                     System.out.println("Lilypad at " + row + " " + column + " set as invalid.");
                     unhighlightSpaces(players.get(currentTurn).getCurrentLocation());
                     sound("splash_sound.au");                  }
                  //ends the turn of the player and increments the class variable so it becomes the next player's turn.
                  changeTurn();
               }//end if
            }//end if
            else{
               JOptionPane.showMessageDialog(null, "Oops! That lilypads has been sunk!");
            }*/
         }//end else if
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