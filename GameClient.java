import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The GameClient class creates the client application that allows two players to play Tic Tac Toe online.
 * It contains a main method, which starts the application by creating an instance of the GameClient class,
 * and calls connectToServer and submitUsername methods.
 * The class also contains several methods that handle gameplay logic,
 * such as starting the game, updating the game board, and checking for a win or draw.
 * 
 * @author Reyes Mak
 * @version 1.0
 * 
 * */

public class GameClient {
    private ClientSideConnection csc; // Connection to the server
    private ClientGUI gui; // User interface for the game
    private boolean buttonsEnabled; // Whether or not buttons are enabled
    private int gridPos; // Grid position
    private int playerID; // ID of the player
    private char[] board; // Board representation
    private char myMark; // Mark for the current player
    private char opponentMark; // Mark for the other player
    private boolean isWin; // Whether or not the current player has won
    private boolean isDraw; // Whether or not the game is a draw
    private boolean isOpponentWin; // Whether or not the other player has won
    
    /**
     * The main method that creates a new GameClient object and connects to the server.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.connectToServer();
        client.submitUsername();
    }
    /**
     * Constructs a new GameClient object.
     */
    public GameClient(){
        gui = new ClientGUI();
        board = new char[9];
        isWin = false;
        isDraw = false;
        isOpponentWin = false;
    }

    /**
     * Connects to the server using the ClientSideConnection class.
     */
    private void connectToServer(){
        csc = new ClientSideConnection();
    }

    /**
     * Starts the game. Player 1 starts the game first
     * Player 2 waits for Player 1.
     */
    public void startGame(){
        if (playerID == 1){
                buttonsEnabled = true;
            }
            
        else{
            buttonsEnabled = false;
            Thread t = new Thread(new Runnable(){
                public void run(){
                    updateTurn();
                }
            });
            t.start();
        }
        toggleButtons();

    }

    /**
     * Submits the user's username to the server and starts the game when the user presses the "Submit" button.
     */
    public void submitUsername(){
        gui.mainPanel.inputPanel.setButtonListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (! gui.mainPanel.inputPanel.isEmptyName()){
                    gui.mainPanel.inputPanel.setButtonEnable(false);
                    gui.mainPanel.inputPanel.editTextField(false);
                    gui.messageTitle.setText("WELCOME " + gui.mainPanel.inputPanel.getName());
                    gui.mainFrame.setTitle("Tic Tac Toe-Player: " + gui.mainPanel.inputPanel.getName());
                    csc.sendName(gui.mainPanel.inputPanel.getName());
                    Thread t = new Thread(new Runnable() {
                        public void run(){
                            csc.receiveCanStart();
                            startGame();
                            myTurn();
                        }
                    });
                    t.start();
                }
            }
        });
    }

     /**
     * Handles the user's turn by waiting for the user to click on a button on the grid in his turn.
     */
    public void myTurn() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JButton button =(JButton) e.getSource();
                button.setText(Character.toString(myMark));
                gui.messageTitle.setText("Valid Move, Wait for your opponent.");
                buttonsEnabled = false;
                toggleButtons();
                for (int i = 0; i <9; i++){
                    if (button == gui.mainPanel.gridPanel.gridButtons[i]){
                        board[i] = myMark;
                        csc.sendGridPos(i);
                        checkResult();
                        csc.sendResult();
                        if (isWin){
                            gui.displayWinMessage();
                            System.exit(0);
                        }
                        else if (isDraw){
                            gui.displayDrawMessage();
                            System.exit(0);
                        }
                        break;
                    }
                }
                Thread t = new Thread(new Runnable(){
                    public void run(){
                        updateTurn();
                    }
                });
                t.start();
            }
        };
        for (int i = 0; i < 9; i++){
            gui.mainPanel.gridPanel.gridButtons[i].addActionListener(al);
        }
    }

   /**
    * Toggles the buttons on the game board based on their current state and sets the enabled state of empty buttons to the specified value.
    */
    public void toggleButtons(){
        for (int i = 0; i < 9; i++){
            if (gui.mainPanel.gridPanel.gridButtons[i].getText().isEmpty()){
                gui.mainPanel.gridPanel.gridButtons[i].setEnabled(buttonsEnabled);
            }
            else{
                gui.mainPanel.gridPanel.gridButtons[i].setEnabled(false);
            }
        }
    }

   /**
    * Waiting for the opponent and change GUI to update the turn.
    * Show Lose Message if the opponent has won.
    */
    public void updateTurn(){
        csc.receiveGridPos();
        gui.mainPanel.gridPanel.gridButtons[gridPos].setText(Character.toString(opponentMark));
        csc.receiveResult();
        if (isOpponentWin){
            gui.displayLoseMessage();
            System.exit(0);
        }
        else if (isDraw){
            gui.displayDrawMessage();
            System.exit(0);
        }
        else{
            gui.messageTitle.setText("Your opponent has moved, now is your turn");
            buttonsEnabled = true;
            toggleButtons();
        }

    }
    
   /**
    * Check if the player has won or drawn.
    */
    public void checkResult(){
        String mark0 = gui.mainPanel.gridPanel.gridButtons[0].getText();
        String mark1 = gui.mainPanel.gridPanel.gridButtons[1].getText();
        String mark2 = gui.mainPanel.gridPanel.gridButtons[2].getText();
        String mark3 = gui.mainPanel.gridPanel.gridButtons[3].getText();
        String mark4 = gui.mainPanel.gridPanel.gridButtons[4].getText();
        String mark5 = gui.mainPanel.gridPanel.gridButtons[5].getText();
        String mark6 = gui.mainPanel.gridPanel.gridButtons[6].getText();
        String mark7 = gui.mainPanel.gridPanel.gridButtons[7].getText();
        String mark8 = gui.mainPanel.gridPanel.gridButtons[8].getText();
        if ((mark0.equals(mark1) && mark1.equals(mark2) && mark2.equals(Character.toString(myMark))) || (mark0.equals(mark3) && mark3.equals(mark6) && mark6.equals(Character.toString(myMark))) 
        || (mark0.equals(mark4) && mark4.equals(mark8) && mark8.equals(Character.toString(myMark))) || (mark1.equals(mark4) && mark4.equals(mark7) && mark7.equals(Character.toString(myMark)))
        || (mark2.equals(mark4) && mark4.equals(mark6) && mark6.equals(Character.toString(myMark))) || (mark2.equals(mark5) && mark5.equals(mark8) && mark8.equals(Character.toString(myMark)))
        || (mark3.equals(mark4) && mark4.equals(mark5) && mark5.equals(Character.toString(myMark))) || (mark6.equals(mark7) && mark7.equals(mark8) && mark8.equals(Character.toString(myMark)))){
            isWin = true;
        }
        else if (!mark0.isEmpty() && !mark1.isEmpty() && !mark2.isEmpty() && !mark3.isEmpty()
        && !mark4.isEmpty() && !mark5.isEmpty() && !mark6.isEmpty() && !mark7.isEmpty() && !mark8.isEmpty() ){
            isDraw = true;
        }
    }

   /**
    * The ClientSideConnection class represents the connection between the client and the server.
    */
    private class ClientSideConnection{
    
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        /**
         * Constructs a new ClientSideConnection object and sets up the connection to the server.
         */
        public ClientSideConnection(){
            try{
                socket = new Socket("localhost", 9999);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                playerID = dataIn.readInt();
                if (playerID == 1){
                    myMark = 'X';
                    opponentMark = 'O';
                }
                else{
                    myMark = 'O';
                    opponentMark = 'X';
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

       /**
        * Receives the position of the button that was clicked by the opponent from the server.
        */
        public void receiveGridPos(){
            try{
             gridPos = dataIn.readInt();
            }catch(Exception e){
                gui.displayDisconnectedMessage();
                buttonsEnabled = false;
                toggleButtons();
                e.printStackTrace();
                System.exit(0);
            }
        }

        /**
         * Receives the game result from the server.
         */
        public void receiveResult(){
            try{
                isOpponentWin = dataIn.readBoolean();
                isDraw = dataIn.readBoolean();
            }catch(Exception e){
                gui.displayDisconnectedMessage();
                buttonsEnabled = false;
                toggleButtons();
                e.printStackTrace();
                System.exit(0);
            }
        }

        /**
         * Receives a signal from the server indicating that the game can start.
         */
        public void receiveCanStart(){
            try{
                dataIn.readBoolean();
            }catch(Exception e){
             e.printStackTrace();
            }
        }

        /**
         * Sends the position of the button that was clicked to the server.
         * @param gridPos the position of the button that was clicked
         */
        public void sendGridPos(int gridPos){
            try{
                dataOut.writeInt(gridPos);
                dataOut.flush();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        /**
         * Sends the name of the player to the server.
         * @param name the name of the player
         */
        public void sendName(String name){
            try{
                dataOut.writeUTF(name);
                dataOut.flush();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        /**
         * Sends the game result to the server.
         */
        public void sendResult(){
            try{
                dataOut.writeBoolean(isWin);
                dataOut.flush();
                dataOut.writeBoolean(isDraw);
                dataOut.flush();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
        
