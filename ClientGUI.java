import java.awt.*;
import javax.swing.*;
import component.MenuBar;
import panel.*;

/**
 * A GUI client for a Tic Tac Toe game.
 *
 * @author Reyes Mak
 * @version 1.0
 */
public class ClientGUI {

    public JFrame mainFrame;
    public MainPanel mainPanel;
    public JLabel messageTitle;
    public MenuBar menuBar;

    /**
     * Constructs a new ClientGUI object.
     * Initializes the main frame, main panel, message label, and menu bar.
     * Adds the message label, main panel, and menu bar to the main frame.
     */
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

    /**
     * Displays a message dialog to indicate that the player has won.
     */
    public void displayWinMessage(){
        JOptionPane.showMessageDialog(mainFrame, "Congratulations. You Win.");
    }

    /**
     * Displays a message dialog to indicate that the player has lost.
     */
    public void displayLoseMessage(){
        JOptionPane.showMessageDialog(mainFrame, "You lose.");
    }

    /**
     * Displays a message dialog to indicate that the game ended in a draw.
     */
    public void displayDrawMessage(){
        JOptionPane.showMessageDialog(mainFrame, "Draw.");
    }

    /**
     * Displays a message dialog to indicate that the game ended due to a disconnection.
     */
    public void displayDisconnectedMessage(){
        JOptionPane.showMessageDialog(mainFrame, "Game Ends. One of the player left");
    }
}
