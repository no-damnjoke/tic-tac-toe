package panel;
import java.awt.*;
import javax.swing.*;

/**
 * The GridPanel class extends the JPanel class to create a panel that displays a 3 x 3 grid of JButtons.
 * The JButtons are stored in the gridButtons array for easy access.
 * The appearance of the JButtons is styled using the gridButtonsStyling method.
 * The JButtons are initially disabled.
 * 
 * To access the JButtons, use the gridButtons array.
 * To enable a JButton, use button.setEnabled(true).
 * To disable a JButton, use button.setEnabled(false).
 * 
 * @author Reyes Mak
 * @version 1.0
 * 
 */
public class GridPanel extends JPanel{
    /**
     * An array of JButtons that make up the 3 x 3 grid.
     * The first button is located at index 0, and the last button is located at index 8.
     */
    public JButton gridButtons[] = new JButton[9];
    
    /**
     * Constructs a new GridPanel object.
     * Initializes the gridButtons array with 9 JButtons, and styles the appearance of each button using the gridButtonsStyling method.
     * The JButtons are initially disabled.
     */
    public GridPanel(){
        GridLayout grid = new GridLayout(3, 3);
        for (int i = 0; i < 9; i++){
            gridButtons[i] = new JButton();
            gridButtonsStyling(gridButtons[i]);
            super.add(gridButtons[i]);
            gridButtons[i].setEnabled(false);
        }
        super.setLayout(grid);
    }

    /**
     * Styles the appearance of a JButton to make it suitable for use in a 3 x 3 grid.
     * Sets the button's background to be transparent, removes its content area fill, and adds a black border with a width of 1 pixel.
     * 
     * @param button the JButton to style
     */
    private void gridButtonsStyling(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
}
