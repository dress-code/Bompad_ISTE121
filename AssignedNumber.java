import java.io.*;

/**
* AssignedNumber class assigns a player based on when they connected to the network
* @author - Team 2
* @version 12/3/2017
*/
public class AssignedNumber implements Serializable{
   private int assignedNum;
   
   /**
   * AssignedNumber constructor
   * @param i - the number a player gets assigned to
   */
   public AssignedNumber(int i){
      assignedNum = i;
   }
   
   /**
   * This method returns the number a player was assigned to
   * @return a player's assigned number
   */
   public Integer getAssigned(){
      return assignedNum;
   }
}