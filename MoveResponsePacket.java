import java.io.*;

public class MoveResponsePacket implements Serializable{
   private boolean response;
   public MoveResponsePacket(boolean b){
      response = b;
   }
   
   public boolean getResponse(){
      return response;
   }
   
   public void setResponse(boolean b){
      response = b;
   }
}