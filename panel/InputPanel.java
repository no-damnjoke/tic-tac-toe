package panel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The InputPanel class extends the JPanel class to create a panel that contains a text field and a submit button.
 * The text field is used to input text, and the submit button is used to submit the text.
 * 
 * To access the text entered in the text field, use the getName() method.
 * To check if the text field is empty, use the isEmptyName() method.
 * To enable or disable the submit button, use the setButtonEnable() method.
 * To add an ActionListener to the submit button, use the setButtonListener() method.
 * To enable or disable the text field, use the editTextField() method.
 * 
 * @author Reyes Mak
 * @version 1.0
 * 
 */
public class InputPanel extends JPanel{
    private TextField textField = new TextField("", 35);
    private JButton submitButton= new JButton("Submit");

    /**
     * Constructs a new InputPanel object.
     * Adds a text field and a submit button to the panel using a GridBagLayout.
     * 
     * To access the text entered in the text field, use the getName() method.
     * To check if the text field is empty, use the isEmptyName() method.
     * To enable or disable the submit button, use the setButtonEnable() method.
     * To add an ActionListener to the submit button, use the setButtonListener() method.
     * To enable or disable the text field, use the editTextField() method.
     */
    public InputPanel(){
        super.setLayout(new GridBagLayout());
        super.add(textField);
        super.add(submitButton);
    }

    /**
     * Checks if the text field is empty.
     * 
     * @return true if the text field is empty, false otherwise
     */
    public Boolean isEmptyName(){
        return textField.getText().trim().equals("");
    }

    /**
     * Enables or disables the text field.
     * 
     * @param editable true to enable the text field, false to disable it
     */
    public void editTextField(Boolean editable){
        textField.setEditable(editable);
    }

    /**
     * Gets the text entered in the text field.
     * 
     * @return the text entered in the text field
     */
    public String getName(){
        return textField.getText();
    }

    /**
     * Adds an ActionListener to the submit button.
     * 
     * @param listener the ActionListener to add
     */
    public void setButtonListener(ActionListener listener){
        submitButton.addActionListener(listener);
    }

    /**
     * Enables or disables the submit button.
     * 
     * @param isEnable true to enable the submit button, false to disable it
     */
    public void setButtonEnable(Boolean isEnable){
        submitButton.setEnabled(isEnable);
    }
}
