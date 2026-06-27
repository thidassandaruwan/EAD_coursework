package com.hotelapp;

import com.hotelapp.model.AuthModel;
import com.hotelapp.view.AdminUI;
import com.hotelapp.view.BaseFrame;
import com.hotelapp.view.LoginUI;
import com.hotelapp.controller.LoginController;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // creating the jframe on which the applicatoin will run on
                BaseFrame baseFrame = new BaseFrame();

//                // show login page by default
//                LoginUI loginUI = new LoginUI(baseFrame);
//                // instantiate a authmodel object
//                AuthModel authModel = new AuthModel();
//                //
//                new LoginController(baseFrame, loginUI, authModel);
//
//                baseFrame.setView(loginUI, "Login");
//
                baseFrame.setView(new AdminUI(baseFrame, "Thidas"), "AdminUI");
                baseFrame.setVisible(true);

            }
        });
    }
}