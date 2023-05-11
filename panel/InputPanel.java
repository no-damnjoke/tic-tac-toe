package panel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputPanel extends JPanel{
    private TextField textField = new TextField("", 35);
    private JButton submitButton= new JButton("Submit");

    public InputPanel(){
        super.setLayout(new GridBagLayout());
        super.add(textField);
        super.add(submitButton);
    }
    public Boolean isEmptyName(){
        return textField.getText().trim().equals("");
    }

    public void editTextField(Boolean editable){
        textField.setEditable(editable);
    }

    public String getName(){
        return textField.getText();
    }

    public void setButtonListener(ActionListener listener){
        submitButton.addActionListener(listener);
    }

    public void setButtonEnable(Boolean isEnable){
        submitButton.setEnabled(isEnable);
    }

}
