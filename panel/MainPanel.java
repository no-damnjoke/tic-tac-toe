package panel;

import javax.swing.*;
import java.awt.*;
public class MainPanel extends JPanel{
    public GridPanel gridPanel = new GridPanel();
    public InputPanel inputPanel = new InputPanel();
    public MainPanel(){
        super.setLayout(new BorderLayout());
        super.add(gridPanel, BorderLayout.CENTER);
        super.add(inputPanel, BorderLayout.PAGE_END );
    }
}
