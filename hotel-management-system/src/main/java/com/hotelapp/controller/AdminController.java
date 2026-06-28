package com.hotelapp.controller;

import com.hotelapp.model.*;
import com.hotelapp.view.AdminUI;

import javax.swing.*;
import java.util.List;

public class AdminController {
    private final AdminUI view;
    private final AdminModel model;

    public AdminController(AdminUI view, AdminModel model){
        this.view = view;
        this.model = model;

        setupSidePanelNavigationListners();

        // open dashboard by default
        syncDashboardView();
    }

    private void syncDashboardView() {
        // get monthly and daily sales stats
        AdminDashboardStats dailyStats = model.getDashboardStats(true);
        AdminDashboardStats monthlyStats = model.getDashboardStats(false);

        // open the Dashboard Ui by default
        view.updateMainPanel(view.createDashboard());

        // update the dashboard metrics data
        view.updateDashboardMetrics(dailyStats, monthlyStats);
    }

    private void setupSidePanelNavigationListners(){
        // Dashboard actionlistner
        view.getDashboardButton().addActionListener(e -> syncDashboardView());

        // Employees Tab actionlistner
        view.getEmployeeButton().addActionListener(e -> {
            List<Employee> employees = model.getAllEmployees();
            view.updateMainPanel(view.createEmployeeTab(employees));
            setupEmployeeTabNavigationListeners();
        });

        // roomButton actionlistner
        view.getRoomButton().addActionListener(e -> {
            List<Room> rooms = model.getAllRooms();
            view.updateMainPanel(view.createRoomTab(rooms));
            setupRoomTabNavigationListeners();
        });

        // Customer button actionlistner
        view.getCustomerButton().addActionListener(e -> {
            List<CustomerRecord> customerRecords = model.getAllCustomerRecords();
            view.updateMainPanel(view.createCustomerTab(customerRecords));
        });
    }

    private void setupEmployeeTabNavigationListeners(){
        if (view.getNewEmployeeTabBtn() != null) {
            view.getNewEmployeeTabBtn().addActionListener(e -> {
                view.updateMainPanel(view.createNewEmployeeTab());
                // once new employee tab opens, add the action listenrs to those buttons
                setupNewEmployeeFormListeners();
            });
        }
    }

    private void setupNewEmployeeFormListeners(){
        view.getBackToEmployeesBtn().addActionListener(e -> {
            view.updateMainPanel(view.createEmployeeTab(model.getAllEmployees()));
            // rebind the navigation buttons in employee tab
            setupEmployeeTabNavigationListeners();
        });

        // Handle 'Save Employee'
        view.getSaveNewEmployeeBtn().addActionListener(e -> {
            String username = view.getNewEmpUsername().getText();
            String password = view.getNewEmpPassword().getText();
            String role = (view.getNewEmpRole().getSelectedItem()).toString();

            if (!username.isEmpty() && !password.isEmpty() && !role.isEmpty())
            {
                // remove any white spaces
                username = username.trim();
                password = password.trim();
                // throw error if username or password has any spaces
                if ((username.contains(" ") || password.contains(" "))){
                    JOptionPane.showMessageDialog(null, "No spaces are allowed in username or password");
                }
                else
                {
                    Employee newEmp = new Employee(username, password, role);
                    if (model.addEmployee(newEmp)) {
                        // Success! show a success popup and clear the textfields
                        JOptionPane.showMessageDialog(null, "Employee " + username + " added successfully!");
                        view.getNewEmpUsername().setText("");
                        view.getNewEmpPassword().setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error adding employee. Username may already exist.");
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please fill out all fields.");
            }
        });
    }

    // room actionlisteners
    private void setupRoomTabNavigationListeners(){
        if (view.getNewRoomTabBtn() != null) {
            view.getNewRoomTabBtn().addActionListener(e -> {
                view.updateMainPanel(view.createNewRoomTab());
                // once new room tab opens, add the action listenrs to those buttons
                setupNewRoomFormActionListeners();
            });
        }
    }

    private void setupNewRoomFormActionListeners(){
        view.getBackToRoomBtn().addActionListener(e -> {
            view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
            // rebind the navigation buttons in employee tab
            setupEmployeeTabNavigationListeners();
        });

        // adding new rooms
        view.getSaveNewRoomBtn().addActionListener(e -> {
            String tier = view.getNewRoomTierField().getSelectedItem().toString();
            String space = view.getNewRoomSpaceField().getSelectedItem().toString();
            String priceString = view.getNewRoomPriceField().getText();

            if (!tier.isEmpty() && !space.isEmpty() && !priceString.isEmpty())
            {
                // remove any white spaces
                priceString.trim();
                // convert the price to double
                double price;
                // try convert the price to double, if any error throw a error pop up and clear the price field
                try{
                    price = Double.parseDouble(priceString);
                }
                catch (Exception priceError)
                {
                    JOptionPane.showMessageDialog(null, "Enter a valid price" );
                    view.getNewRoomPriceField().setText("");
                    return;
                }

                Room newRoom = new Room(0, space, tier, "Ready", price);
                if (model.addRoom(newRoom)) {
                    // Success! show a success popup and clear the textfields
                    JOptionPane.showMessageDialog(null, "New " + tier + " room added successfully!");
                    view.getNewRoomPriceField().setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error adding room");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please fill out all fields.");
            }
        });
    }
}
