import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;

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
    
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnection();
    }

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

    private class ServerSideConnection implements Runnable{
        private Socket socket;
        private DataOutputStream dataOut;
        private DataInputStream dataIn;
        private int playerID;
        private String name1;
        private String name2;

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

        public void sendGridPos(int gridPos){
            try{
                dataOut.writeInt(gridPos);
                dataOut.flush();
            }catch(Exception e){e.printStackTrace();};
        }
        public void sendResult(boolean isWin){
            try{
                dataOut.writeBoolean(isWin);
                dataOut.flush();
                dataOut.writeBoolean(isDraw);
                dataOut.flush();

            }catch(Exception e){e.printStackTrace();};
        }
        public void sendCanStart(boolean canStart){
            try{
                dataOut.writeBoolean(canStart);
                dataOut.flush();
            }catch(Exception e){e.printStackTrace();};
        }
    }
}
