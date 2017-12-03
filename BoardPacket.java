import java.io.*;
public class BoardPacket implements Serializable{
   private Lilypad[][] lilypads;
   
   public BoardPacket(Lilypad[][] lps){
      lilypads = lps;
   }
   
   public Lilypad[][] getLilypads(){
      return lilypads;
   }
}