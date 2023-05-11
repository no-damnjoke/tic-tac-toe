import java.awt.*;
import javax.swing.*;
import component.MenuBar;
import panel.*;

public class ClientGUI {
    JFrame mainFrame;
    MainPanel mainPanel;
    JLabel messageTitle;
    MenuBar menuBar;


    public ClientGUI(){
        mainFrame = new JFrame("Tic Tac Toe");
        mainPanel = new MainPanel();
        messageTitle = new JLabel("Enter your player name...");
        menuBar = new MenuBar(mainFrame);
        mainFrame.add(messageTitle);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(messageTitle, BorderLayout.NORTH);
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setSize(400, 500);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setVisible(true);
    }

    public void displayWinMessage(){
        JOptionPane.showMessageDialog(mainFrame, "Congratulations. You Win.");
    }

    public void displayLoseMessage(){
        JOptionPane.showMessageDialog(mainFrame, "You lose.");
    }

    public void displayDrawMessage(){
        JOptionPane.showMessageDialog(mainFrame, "Draw.");
    }

    public void displayDisconnectedMessage(){
        JOptionPane.showMessageDialog(mainFrame, "Game Ends. One of the player left");
    }

}


//     public void changeGUI(GameStatus gameStatus){
//         if (gameStatus.checkGameStarted()){
//             gameStatus.setGameStarted(false);

//         }
//         else if (!gameStatus.checkIsNamed()){
//             System.out.println("Hello");
//             setUsername(mainPanel, messageTitle);
//             gameStatus.setIsNamed(true);
//         }
//         else if (gameStatus.checkPlayerTurn()){
//             if (gameStatus.getFreq() > 0){
//                 messageTitle.setText("Your opponent has moved, now is your turn.");
//             }
//         }
//     }
// }
