
import java.io.*;
public class AssignedNumber implements Serializable{
   private int assignedNum;
   
   public AssignedNumber(int i){
      assignedNum = i;
   }
   
   public Integer getAssigned(){
      return assignedNum;
   }
}