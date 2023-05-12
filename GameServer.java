import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;

/**
 * The GameServer class represents a server for a two-player game.
 * The server accepts two connections from clients and coordinates the communication
 * between the two clients.
 * 
 * @author Reyes Mak
 * @version 1.0
 */
public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private int player1GridPos;
    private int player2GridPos;
    private ServerSideConnection player1;
    private ServerSideConnection player2;
    private CountDownLatch latch;
    private boolean isPlayer1Win;
    private boolean isPlayer2Win;
    private boolean isDraw;
    
    /**
     * The main method creates an instance of the GameServer class and starts the server.
     * @param args An array of strings representing command line arguments
     */

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnection();
    }

    /**
     * The constructor initializes the instance variables of the GameServer class.
     */
    public GameServer(){
        numPlayers = 0;
        isPlayer1Win = false;
        isPlayer2Win = false;
        isDraw = false;
        latch = new CountDownLatch(2);
        try{
            ss = new ServerSocket(9999);
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * The acceptConnection method accepts connections from two clients.
     */
    public void acceptConnection(){
        try{
            while (numPlayers < 2){
                Socket s = ss.accept();
                numPlayers++;
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if (numPlayers == 1){
                    player1 = ssc;
                }
                else{
                    player2 = ssc;
                }
                Thread t = new Thread(ssc);
                t.start();
            }
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * The ServerSideConnection class is a nested class representing a connection to a client.
     */
    private class ServerSideConnection implements Runnable{
        private Socket socket;
        private DataOutputStream dataOut;
        private DataInputStream dataIn;
        private int playerID;
        private String name1;
        private String name2;

        /**
         * The constructor initializes the instance variables of the ServerSideConnection class.
         * @param s A Socket object representing the socket connection to the client
         * @param id An integer representing the ID of the client
         */
        public ServerSideConnection(Socket s, int id){
            socket = s;
            playerID = id;
            name1 = "";
            name2 = "";
            try{
                dataOut = new DataOutputStream(socket.getOutputStream());
                dataIn = new DataInputStream(socket.getInputStream());
            }catch(Exception e){e.printStackTrace();}
        }

        /**
         * The run method is called when the thread for the ServerSideConnection object is started.
         */
        public void run(){
            try{
                dataOut.writeInt(playerID);
                if (playerID == 1){
                    name1 = dataIn.readUTF();
                } else {
                    name2 = dataIn.readUTF();
                }
                if (!name1.isEmpty()) {
                    latch.countDown();
                }
                if (!name2.isEmpty()) {
                    latch.countDown();
                }
        
                latch.await();
                sendCanStart(true);
                while (true){
                    if (playerID == 1){
                        try{
                            player1GridPos = dataIn.readInt();
                            isPlayer1Win = dataIn.readBoolean();
                            isDraw = dataIn.readBoolean();
                            player2.sendGridPos(player1GridPos);
                            player2.sendResult(isPlayer1Win);   
                            }catch (Exception e){
                                player2.socket.close();
                                System.exit(0);}
                    }
                    else{
                        try{
                            player2GridPos = dataIn.readInt();
                            isPlayer2Win = dataIn.readBoolean();
                            isDraw = dataIn.readBoolean();
                            player1.sendGridPos(player2GridPos);
                            player1.sendResult(isPlayer2Win);
                        }
                        catch (Exception e){
                            player1.socket.close();
                            System.exit(0);}
                    }
                }
            }catch(Exception e){e.printStackTrace();};
        }
                
        /**
         * Sends the position of the button that was clicked to another client.
         * @param gridPos the position of the button that was clicked
         */
        public void sendGridPos(int gridPos){
            try{
                dataOut.writeInt(gridPos);
                dataOut.flush();
            }catch(Exception e){e.printStackTrace();};
        }
                
        /**
         * Sends the game result to the client.
         */
        public void sendResult(boolean isWin){
            try{
                dataOut.writeBoolean(isWin);
                dataOut.flush();
                dataOut.writeBoolean(isDraw);
                dataOut.flush();

            }catch(Exception e){e.printStackTrace();};
        }

        /**
         * Sends a signal to clients indicating that the game can start.
         */
        public void sendCanStart(boolean canStart){
            try{
                dataOut.writeBoolean(canStart);
                dataOut.flush();
            }catch(Exception e){e.printStackTrace();};
        }
    }
}
