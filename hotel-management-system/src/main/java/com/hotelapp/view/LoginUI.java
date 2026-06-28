package com.hotelapp.view;

import com.hotelapp.util.Theme;
import com.hotelapp.util.UIFactory;
import com.hotelapp.util.UIFactory.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class LoginUI extends JPanel {
    private final BaseFrame baseFrame;

    // input components
    private final JTextField textUsername;
    private final JPasswordField textPassword;
    private final JCheckBox checkShowPassword;
    private final JButton loginButton;

    public LoginUI(BaseFrame baseFrame){
        // assign baseFrame so that other methods can use it
        this.baseFrame = baseFrame;

        // layout and bgColor for the main pannel
        setLayout(new GridBagLayout());
        setBackground(Theme.COLOR_BEIGE);

        //sub jpannel with the login UI
        JPanel loginBox = new JPanel();
        loginBox.setLayout(new GridLayout(8, 1, 0, 0));
        loginBox.setBackground(Theme.COLOR_BEIGE);
        // curve the edges and set paddings for the sub jpannel
        loginBox.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Color.BLACK, 4,true), UIFactory.createPadding(50)));

        // USERNAME  
        //label
        loginBox.add(UIFactory.createLabel("Username: ", Color.BLACK, Theme.FONT_SERIF_BOLD));
        //inputField
        textUsername = new JTextField(20);
        UIFactory.styleTextField(textUsername);
        loginBox.add(textUsername);

        // create whitespace in between
        loginBox.add(UIFactory.createSpace(0, 20));

        // PASSWORD 
        //label
        loginBox.add(UIFactory.createLabel("Password: ", Color.black, Theme.FONT_SERIF_BOLD));
        //inputField
        textPassword = new JPasswordField(20);
        UIFactory.styleTextField(textPassword);
        loginBox.add(textPassword);
        // add show password checkbox
        checkShowPassword = UIFactory.createCheckBox("Show Password", Theme.COLOR_BEIGE, Color.BLACK, Theme.FONT_SERIF_PLAIN);
        loginBox.add(checkShowPassword);

        // create whitespace in between
        loginBox.add(UIFactory.createSpace(0, 20));

        loginButton = UIFactory.createButton("Login", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        loginBox.add(loginButton);
        
        // add loginbox pannel to the main pannel
        add(loginBox);
    }

    // getters
    public String getUsername(){return textUsername.getText();}
    public String getPassword(){return new String(textPassword.getPassword());}
    public JCheckBox getCheckShowPassword(){return checkShowPassword;}
    public JPasswordField getTextPassword(){return textPassword;}
    public JButton getLoginButton(){return loginButton;}

    // show message method
    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
}   
