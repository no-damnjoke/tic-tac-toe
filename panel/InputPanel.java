package panel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputPanel extends JPanel{
    public TextField textField = new TextField("", 35);
    public JButton submitButton= new JButton("Submit");

    public InputPanel(){
        super.setLayout(new GridBagLayout());
        super.add(textField);
        super.add(submitButton);
    }

    public Boolean isEmptyName(){
        return textField.getText().trim().equals("");
    }

}
