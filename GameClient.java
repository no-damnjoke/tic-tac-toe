import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class GameClient {
    // private GameStatus gameStatus;
    private ClientSideConnection csc;
    private ClientGUI gui;
    private boolean buttonsEnabled;
    private int gridPos;
    private int playerID;
    private char[] board;
    private char myMark;
    private char opponentMark;
    private boolean isWin;
    private boolean isDraw;
    private boolean isOpponentWin;
    


    public GameClient(){
        gui = new ClientGUI();
        board = new char[9];
        isWin = false;
        isDraw = false;
        isOpponentWin = false;
    }

    private void connectToServer(){
        csc = new ClientSideConnection();
    }

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
    private class ClientSideConnection{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

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
            }catch(Exception e){e.printStackTrace();}
        }

        public void sendGridPos(int gridPos){
            try{
                dataOut.writeInt(gridPos);
                dataOut.flush();
            }catch(Exception e){e.printStackTrace();};
        }

        public void receiveGridPos(){
            try{
                gridPos = dataIn.readInt();
            }catch(Exception e){
                e.printStackTrace();
                gui.displayDisconnectedMessage();
                buttonsEnabled = false;
                toggleButtons();
                e.printStackTrace();
                System.exit(0);};
        }
        public void receiveResult(){
            try{
                isOpponentWin = dataIn.readBoolean();
                isDraw = dataIn.readBoolean();
            }catch(Exception e){
                gui.displayDisconnectedMessage();
                buttonsEnabled = false;
                toggleButtons();
                e.printStackTrace();
                System.exit(0);};
        }
        public void receiveCanStart(){
            try{
                dataIn.readBoolean();
            }catch(Exception e){e.printStackTrace();};
        }
        public void sendName(String name){
            try{
                dataOut.writeUTF(name);
                dataOut.flush();
            }catch(Exception e){e.printStackTrace();};
        }
        public void sendResult(){
            try{
            dataOut.writeBoolean(isWin);
            dataOut.flush();
            dataOut.writeBoolean(isDraw);
            dataOut.flush();

        }catch(Exception e){e.printStackTrace();};
        }
    }
    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.connectToServer();
        client.submitUsername();
    }
}
        
