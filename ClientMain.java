import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import component.MenuBar;
import panel.*;

public class ClientMain {
    public static Boolean enterUsername(MainPanel mainPanel, JLabel messageTitle){
        if (!mainPanel.inputPanel.isEmptyName()){
            mainPanel.inputPanel.submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    mainPanel.inputPanel.submitButton.setEnabled(false);
                    mainPanel.inputPanel.textField.setEditable(false);
                    messageTitle.setText("WELCOME " + mainPanel.inputPanel.textField.getText());
                }
            });
            return true;     
        }
        return false;
    }
    public static void main(String arg[]){
        JFrame mainFrame = new JFrame("Tic Tac Toe");
        MainPanel mainPanel = new MainPanel();
        JLabel messageTitle = new JLabel("Enter your player name...");
        MenuBar menuBar = new MenuBar(mainFrame);
        mainFrame.add(messageTitle);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(messageTitle, BorderLayout.NORTH);
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setSize(400, 500);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setVisible(true);
        while (!enterUsername(mainPanel, messageTitle));
    }

}
