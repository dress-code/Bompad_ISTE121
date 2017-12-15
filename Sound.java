import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
/**
* A thread for playing the background frog sound on repeat.
* @author Anna Jacobsen (of Team 2)
* @version 11/22/2017
*/
public class Sound extends Thread{

   AudioInputStream ais;
   
   /**
   * Constructor for a Sound object. Instantiates AudioInputStream.
   */
   public Sound(){
      try{
         File backgroundSound = new File("GroovyBeat.wav");
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
   
   /**
   * Run method for a Sound object creates a new Clip object and then starts it in
   * a continuous loop.
   */
   @Override
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