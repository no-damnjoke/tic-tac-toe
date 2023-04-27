package panel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GridPanel extends JPanel{
    JButton gridButtons[][] = new JButton[3][3];
    public GridPanel(){
        GridLayout grid = new GridLayout(3, 3);
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                gridButtons[i][j] = new JButton();
                gridButtonsInitiazation(gridButtons[i][j]);
                super.add(gridButtons[i][j]);
                gridButtons[i][j].setEnabled(false);
            }
        }
        super.setLayout(grid);
    }

    private static void gridButtonsInitiazation(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                
            }
        });
}
}
