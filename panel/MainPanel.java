package panel;
import javax.swing.*;
import java.awt.*;

/**
 * The MainPanel class extends the JPanel class to create a panel to contain a grid panel and a input panel.
 * The grid panel displays a grid of elements, while the input panel
 * allows the user to input username for the game.
 * 
 * @author Reyes Mak
 * @version 1.0
 * 
 */

public class MainPanel extends JPanel{

    /**
     * The grid panel that displays the grid of elements.
     */
    public GridPanel gridPanel = new GridPanel();

    /**
     * The input panel that allows the user to input name for the game.
     */
    public InputPanel inputPanel = new InputPanel();

    /**
     * Constructs a new MainPanel with a BorderLayout.
     * Adds the grid panel to the center of the panel and the input panel to the bottom of the panel.
     */
    public MainPanel(){
        super.setLayout(new BorderLayout());
        super.add(gridPanel, BorderLayout.CENTER);
        super.add(inputPanel, BorderLayout.PAGE_END );
    }
}
