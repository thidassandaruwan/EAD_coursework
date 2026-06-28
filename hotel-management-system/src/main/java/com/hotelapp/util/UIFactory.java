package com.hotelapp.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UIFactory {
    // create white space
    public static Component createSpace(int width, int height){
        return Box.createRigidArea(new Dimension(width, height));
    }

    // create padding border
    public static  Border createPadding(int size){
        return BorderFactory.createEmptyBorder(size, size, size, size);
    }
    // create visible border
    public static  Border createBorder(Color color, int thickness, boolean rounded){
        return BorderFactory.createLineBorder(color, thickness, rounded);
    }

    // create navButton
    public static  JButton createButton(String buttonName, Color foreground, Color background){
        JButton button = new JButton(buttonName);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(Theme.FONT_SERIF_BOLD);
        button.setBorder(createBorder(background, 2, true));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // set hovering effects go the jbutton
        button.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event){
                button.setBackground(foreground);
                button.setForeground(background);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event){
                button.setBackground(background);
                button.setForeground(foreground);
            }
        });
        return button;
    }

    // create label method
    public static  JLabel createLabel(String text, Color textColor, Font font){
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    // style textfields
    public static void styleTextField(JTextField textField){
        textField.setFont(Theme.FONT_SERIF_PLAIN);
        textField.setMaximumSize(new Dimension(400, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(createBorder(Color.BLACK, 2, true), createPadding(5)));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // focus gained lost effects to the textFields
        textField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent event){
                textField.setBorder(BorderFactory.createCompoundBorder(createBorder(Theme.COLOR_BLUE, 2, true), createPadding(5)));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent event){
                textField.setBorder(BorderFactory.createCompoundBorder(createBorder(Color.BLACK, 2, true), createPadding(5)));
            }
        });
    }

    public static JComboBox<String> createComboBox(String[] options){
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(Theme.FONT_SERIF_PLAIN);
        comboBox.setMaximumSize(new Dimension(400, 40));
        comboBox.setBorder(BorderFactory.createCompoundBorder(createBorder(Color.BLACK, 2, true), createPadding(5)));
        comboBox.setFocusable(false);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // focus gained lost effects to the textFields
        comboBox.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent event){
                comboBox.setBorder(BorderFactory.createCompoundBorder(createBorder(Theme.COLOR_BLUE, 2, true), createPadding(5)));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent event){
                comboBox.setBorder(BorderFactory.createCompoundBorder(createBorder(Color.BLACK, 2, true), createPadding(5)));
            }
        });

        return comboBox;
    }

    public static JCheckBox createCheckBox(String text, Color background, Color foreground, Font font){
        JCheckBox checkShowPassword = new JCheckBox(text);
        checkShowPassword.setBackground(background);
        checkShowPassword.setForeground(foreground);
        checkShowPassword.setFont(font);
        checkShowPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkShowPassword.setFocusPainted(false);

        return checkShowPassword;
    }
}
