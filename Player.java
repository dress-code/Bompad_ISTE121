import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
	Player.java
	A class that create a playable character
   Authors: Douglas Kaelin, Zacharry Georges, Anna Jacobsen, Alexa Lewis
   Version: 0.0.2
*/
public class Player{
   
   private String name;
   private boolean turn;
   private Icon frog;
   private boolean isDead;
   private String chatColor;
   private Point currentLocation;
   
   
   /**
    *  This constructor makes a player based on the number that is past to it
    *  Using this number it decides whether to give the name 'Player 1' or 'Player 2'
    *  It also does a defult player for testing purposes
    *  
    *  @param person This is the int past to player that it uses to determin the player name
      
 
   */
   
   public Player(String pName, int playerNum){
      name = pName;
      turn = false;
      isDead = false;
      switch(playerNum){
         case 1:
            frog = new ImageIcon("red-frog.png");
            chatColor = "blue";
            break;
         case 2:
            frog = new ImageIcon("orange-frog.png");  
            chatColor = "green";
            break;
         case 3:
            frog = new ImageIcon("pink-frog.png"); 
            chatColor = "blue";
            break;
         case 4:
            frog = new ImageIcon("blue-frog.png");  
            chatColor = "green";
            break;
      } 
   }//end constructor 
   
   /**
   * This method gets whether or not it's the player object's turn
   * @return boolean If it is ture then it is the players turn, otherwise it is not
   */
   public boolean getTurn(){
      return turn;
   }
   
   /**
   * This method sets the turn of the player object
   * @param _turn Is a boolean to set the turns to
   */
   public void setTurn(boolean _turn){
      turn = _turn;
   }
   
   /**
   * This method gets whether or not it's the player object's is dead
   * @return boolean If it is ture then it is the players turn, otherwise it is not
   */
   public boolean getIsDead(){
      return isDead;
   }
   
   /**
   * This method sets the turn of the player object
   * @param _turn Is a boolean to set the turns to
   */
   public void setIsDead(boolean _isDead){
      isDead = _isDead;
   }
   
   /**
   * This method gets the player object's image
   * @return Image Is the image for that player object
   */
   public Icon getIcon(){
      return frog;
   }
   
   /**
   * A method which returns the point at which the Player is currently located.
   * @return the current location of the Player object.
   */
   public Point getCurrentLocation()
   {
      return currentLocation;
   }
   
   /**
   * A method that sets the current location of a Player object.
   * @param newLocation The new location of the Player object.
   */
   public void setCurrentLocation(Point newLocation)
   {
      currentLocation = newLocation;
   }
}