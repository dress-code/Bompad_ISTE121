import java.net.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
* Class ClientConnection contains the necessary code for 
* establishing a connection with a server and then communicate
* back and forth, always listening for input from the server.
* @author Team 2
* @version 11/29/2017
*/
public class ClientConnection extends JPanel implements Runnable
{
      private Socket s;
      private OutputStream os;
      private InputStream is;
      private ObjectOutputStream oos;
      private ObjectInputStream ois;
      private Chat clientChat;
      private Pond board;
      private JButton jbSend;
      private String playerName;
      //The current turn of a player.
      private int turn;
      //Should the game start?
      private boolean startGame = false;
      //Declares and instantiates a 2-D array of LilyPads.
      private Lilypad[][] lilypads = new Lilypad[10][10];
      //Declares and insntantiates an ArrayList of Player objects.
      private Vector<Player> players = new Vector<Player>();
      //Whose turn is it?
      private int myTurn;
      private int currentTurn;
      private boolean moveResponse;
      
      /**
      * Paramterized ClientConnection constructor.
      * @param ipAddress - the Internet Protocol address that is used to connect to a network
      * @param _playerName - the name of a player 
      */
      public ClientConnection(String ipAddress, String _playerName)
      {
         playerName = _playerName;
         for(int p = 1; p < 5; p++){
            Player player = new Player(p);
            players.add(player);
         }
         //Tries to establish a connection with a server.
         try{
            s = new Socket(ipAddress, 16789);
            os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
            is = s.getInputStream();
            ois = new ObjectInputStream(is);
         }
         catch(UnknownHostException uhe){
            uhe.printStackTrace();
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         }
      }
      
      /**
      * Run method for ClientConnection objects.
      */
      @Override
      public void run(){
         try{
            //while the object input stream is not null...
            do {
               //Reads the object in, assigning it to a generic Object
               Object object = ois.readObject();
               //If statement checks if the object received is a String or an ArrayList.
               if(object instanceof String){
                  String msg = (String) object;
                  System.out.println(msg);
                  clientChat.updateChat(msg + "\n");
                  try{
                     File msgSound = new File("msgSound.au");
                     AudioInputStream ais = AudioSystem.getAudioInputStream(msgSound);
                     Clip clip = AudioSystem.getClip();
                     clip.open(ais);
                     clip.start();
                  }
                  catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
                  catch(LineUnavailableException lue){lue.printStackTrace();}
                  catch(IOException ioe){ioe.printStackTrace();}
               }//end if statement using instanceof to determine object type
               
               /*if the object is an instance of an arraylist, set the class variable
               ArrayList<Player> to the received ArrayList.*/
               if(object instanceof Vector){
                  Vector<Player> oldPlayers = players;
                  players = (Vector<Player>) object;
                  board.update(oldPlayers);
               }//end if statement using instanceof to determine object type.
               
               // If the object is an Integer, determine the turn.
               if(object instanceof Integer){
                  Integer turnReceived = (Integer) object;
                  currentTurn = turnReceived.intValue();
                  players.get(currentTurn).setTurn(true);
               }
               //If the object is a Boolean, determine whether the game should start
               if(object instanceof GameStartPacket){
                  GameStartPacket gspIn = (GameStartPacket) object;
                  startGame = gspIn.shouldStartGame();
                  if(startGame == true){
                     //instantiates the chat class variable to a new chat.
                     clientChat = new Chat(playerName);
                     board = new Pond();
                     //adds the client chat to this, a JPanel.
                     this.add(clientChat, BorderLayout.WEST);
                     this.add(board, BorderLayout.CENTER);
                     Sound sound = new Sound();
                     sound.start();
                  }
                  //Writes a boardpacket containing lilypad objects to the server
                  BoardPacket bp = new BoardPacket(lilypads);
                  oos.writeObject(bp);
                  oos.flush();
                  //Tells the server the game should start and sends the list of players
                  GameStartPacket gspOut = new GameStartPacket(players, true);
                  oos.writeObject(gspOut);
                  oos.flush();
               }
               if(object instanceof MoveResponsePacket){
                  MoveResponsePacket mrp = (MoveResponsePacket) object;
                  moveResponse = mrp.getResponse();
                  System.out.println("The client has received a MoveResponsePacket of " + moveResponse);
               }
               if(object instanceof AssignedNumber){
                  AssignedNumber an = (AssignedNumber) object;
                  myTurn = an.getAssigned();
                  System.out.println("131 Client - This client's assigned turn is: " + myTurn);
               }
               
            }while(ois != null);//end while loop
         }//end try block
         catch(IOException ioe){ioe.printStackTrace();}
         catch(ClassNotFoundException cnfe){cnfe.printStackTrace();}
      }//end run method.
      
      /**
      * A method for writing objects to the server.
      * @param o - an object (could be anything)
      */
      public void write(Object o){
         try{
            oos.writeObject(o);
            oos.flush();
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         }
      }
      
      /**
      * An accessor method for the turn read in.
      * @return The current turn.
      */
      public int getTurn(){
         return turn;
      }//end method getTurn
      
      /**
      * A method which requests the server to send over whose turn it currently is.
      */
      public void turnRequest(){
         Integer turnRequest = -1;
         write(turnRequest);
      }
      
      /**
      * A method which requests the server to send over a player number.
      */
      public void playerRequest(){
         Integer playerRequest = -2;
         write(playerRequest);
      }
   
   /**
   * Inner class Chat creates the chat and has methods for updating.
   */
   class Chat extends JPanel implements ActionListener {
      
      private String timeStamp;
      private JTextArea jtaChat;
      private JTextField jtfMsg; 
      private String playerName;
      
      /**
      * Constructor for a chat object.
      * @param name The name of the player in the chat.
      */
      public Chat(String name)
      {
         this.setLayout(new GridLayout(2,1));
         playerName = name;
         //displays all the messages
         jtaChat = new JTextArea(25,30);
         JScrollPane scrollPane = new JScrollPane(jtaChat);
         scrollPane.setViewportView(jtaChat);
         jtaChat.setEditable(false);
         jtaChat.setLineWrap(true);
      
            
         JPanel jpSend = new JPanel(new FlowLayout());
         jtfMsg = new JTextField(10);
         jpSend.add(jtfMsg);
         jbSend = new JButton("Send");
         jpSend.add(jbSend);
         jbSend.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               timeStamp = new SimpleDateFormat("hh:mm:ss a").format(new java.util.Date());
               String outgoing = getMsg();
               System.out.println(outgoing);
               ClientConnection.this.write(outgoing); //make chat srv inner class of CC?, then call this.
            }
         }); 
            
         this.add(scrollPane);
         this.add(jpSend);
         this.setSize(200,100);
            
      }//end constructor for the Chat.
         
      /**
      * A method for updating the chat.
      * @param The message to be appended to the chat.
      */
      public void updateChat(String msg){
         jtaChat.append(msg);
      }
      
      /**
      * An accessor method for getting the message from the JTextField.
      * @return The message to be sent to the server.
      */
      public String getMsg(){
         String msg = timeStamp + " " + playerName + ": " + jtfMsg.getText();
         jtfMsg.setText("");
         return msg;
      }
      
         //just to get the compiler to be quiet.
         public void actionPerformed(ActionEvent ae){};
         
   }//end class Chat.
   
   /**
   * A class containing the constructors and methods associated with a board of lilypads.
   * @Author - Team 2
   * @version - 11/10/17
   */
   class Pond extends JPanel{
      
      //Is there a winner of the game?
      private boolean winner = false;
      Point lilyPadCoord;
      //Icon for an empty lilypad and open water spot.
      private Icon emptyPad = new ImageIcon("empty.png");
      private Icon water = new ImageIcon("water.png");
     
      /**
      * Constructor for a "pond" board of 64 LilyPads in an 8 x 8 grid layout.
      */
      public Pond(){
      
         //connection = cc;
         this.setLayout(new GridLayout(10,10));
         //Creates Player objects and adds them to the ArrayList.
         
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
         //players.get(0).setTurn(true);
         //myTurn = ClientConnection.this.getTurn();
         
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
      * @param oldPlayers This is a vector of all the old player objects. this is used to geth the old postitions
      */
      public void update(Vector<Player> oldPlayers){
         for(int i=0; i<players.size(); i++){
            move(players.get(i).getCurrentLocation(), oldPlayers.get(i));
         }
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
            //left click moves the Player.               
            if (e.getButton() == MouseEvent.BUTTON1 && players.get(myTurn).getTurn()){
               try{
                     Lilypad clicked = (Lilypad) e.getComponent();
                     MoveRequestPacket mrp = new MoveRequestPacket(clicked.getPoint());
                     try{
                        oos.writeObject(mrp);
                        oos.flush();
                        Thread wait = new Thread();
                        wait.sleep(1000);
                        wait.join();
                     }
                     catch(IOException ioe){ioe.printStackTrace();}   
                     catch(InterruptedException ie){ie.printStackTrace();}                
                     if(moveResponse == true){
                        move(clicked.getPoint(), players.get(myTurn));
                        ClientConnection.this.write(players);
                        ClientConnection.this.playerRequest();
                        ClientConnection.this.turnRequest();
                     }
                     else{
                        JOptionPane.showMessageDialog(null, "Please choose again.");
                     }
               }//end try block
                
               catch(ArrayIndexOutOfBoundsException aioobe){
                  JOptionPane.showMessageDialog(lilypads[4][4], "Please choose a valid lilypad.");
               }//end catch block
            }//end left click.
            
            //Right click sinks a lilypad.
            else if (e.getButton() == MouseEvent.BUTTON3 && ClientConnection.this.getTurn() == myTurn){
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
}//end class ClientConnection
