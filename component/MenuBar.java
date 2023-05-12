package component;
import javax.swing.*;
import java.awt.event.*;

/**
 * The MenuBar class extends the JMenuBar class to create a customized menu bar for a JFrame.
 * This menu bar includes two menus - Control and Help, with corresponding menu items for each.
 * The Control menu includes an Exit menu item, while the Help menu includes an Instruction menu item.
 * When the Exit menu item is selected, the program exits. When the Instruction menu item is selected,
 * a JOptionPane displays a message with instructions for the game.
 *
 * @author Reyes Mak
 * @version 1.0
 * 
 */
public class MenuBar extends JMenuBar{
    
    /**
     * Constructs a new MenuBar object.
     *
     * @param frame the JFrame to which this menu bar will be added
     */
    public MenuBar (JFrame frame){
        final JMenu controlMenu = new JMenu("Control");
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem exitItem = new JMenuItem("Exit");
        final JMenuItem instructionItem = new JMenuItem("Instruction");
        final String instructionMessage = "Some information about the game: Criteria for a valid move:\n-The move is not occupied by any mark.\n-The move is made in the player's turn.\n-The move is made within the 3 x 3 board.\nThe game would continue and switch among the opposite player until it reaches either one of the following conditions:\n-Player 1 wins.\n-Player 2 wins.\n-Draw.";

        exitItem.addActionListener(new ActionListener() {
            /**
             * Invoked when the Exit menu item is selected.
             * Disposes of the current JFrame and exits the program.
             *
             * @param e the ActionEvent object
             */
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                System.exit(0);
            }
        });

        instructionItem.addActionListener(new ActionListener() {
            /**
             * Invoked when the Instruction menu item is selected.
             * Displays a JOptionPane with instructions for the game.
             *
             * @param e the ActionEvent object
             */
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
