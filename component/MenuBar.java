package component;
import javax.swing.*;
import java.awt.event.*;

public class MenuBar extends JMenuBar{
    public MenuBar (JFrame frame){
        final JMenu controlMenu = new JMenu("Control");
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem exitItem = new JMenuItem("Exit");
        final JMenuItem instructionItem = new JMenuItem("Instruction");
        final String instructionMessage = "Some information about the game: Criteria for a valid move:\n-The move is not occupied by any mark.\n-The move is made in the player's turn.\n-The move is made within the 3 x 3 board.\nThe game would continue and switch among the opposite player until it reaches either one of the following conditions:\n-Player 1 wins.\n-Player 2 wins.\n-Draw.";
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                System.exit(0);
            }
        });
        instructionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(frame, instructionMessage);
            }
        });
        controlMenu.add(exitItem);
        helpMenu.add(instructionItem);
        super.add(controlMenu);
        super.add(helpMenu);
    }
}