package panel;
import java.awt.*;
import javax.swing.*;

public class GridPanel extends JPanel{
    public JButton gridButtons[] = new JButton[9];
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

    private void gridButtonsStyling(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
}