import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Sound extends Thread{

   AudioInputStream ais;
   boolean started = false;
   
   public Sound(){
      try{
         File backgroundSound = new File("frog_background.au");
         ais = AudioSystem.getAudioInputStream(backgroundSound);
      }
      catch(FileNotFoundException fnfe){
         fnfe.printStackTrace();
      }
      catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
      catch(IOException ioe){
         ioe.printStackTrace();
      }
   }
   
   public void run(){
      try{
         Clip clip = AudioSystem.getClip();
         clip.open(ais);
         clip.loop(Clip.LOOP_CONTINUOUSLY);
      }
      catch(LineUnavailableException lue){lue.printStackTrace();}
      catch(IOException ioe){ioe.printStackTrace();}
   }

}//end class Sound