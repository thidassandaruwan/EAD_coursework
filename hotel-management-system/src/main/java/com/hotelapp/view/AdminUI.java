package com.hotelapp.view;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import com.hotelapp.model.*;
import com.hotelapp.util.*;


public class AdminUI extends JPanel {
    // ui related
    private final BaseFrame baseFrame;
    private final String adminName;

    // functionality related componenets
    private JButton dashboardButton;
    private JButton employeeButton;
    private JButton roomButton;
    private JButton customerButton;

    // mainpannel
    private JPanel mainPanel;

    // Dashboard
    private JButton todayReportButton;
    private JLabel todaySingleStRoomsLbl;
    private JLabel todaySinglePrRoomsLbl;
    private JLabel todayDoubleStRoomsLbl;
    private JLabel todayDoublePrRoomsLbl;
    private JLabel todayTotalLbl;

    private JButton monthlyReportButton;
    private JLabel monthlySingleStRoomsLbl;
    private JLabel monthlySinglePrRoomsLbl;
    private JLabel monthlyDoubleStRoomsLbl;
    private JLabel monthlyDoublePrRoomsLbl;
    private JLabel monthlyTotalLbl;

    // Employee realted
    // new employee
    private JButton newEmployeeTabBtn;
    private JButton saveNewEmployeeBtn; // The submit button on the form
    private JButton backToEmployeesBtn; // The go back button
    private JTextField newEmpUsernameField;
    private JTextField newEmpPasswordField;
    private JComboBox<String> newEmpRoleField;
    // edit employee
    private JButton editEmployeeTabBtn;
    private JButton saveEditEmployeeBtn;
    private JButton removeEmployeeBtn;
    private JTextField editEmpUsernameField;
    private JTextField editEmpPasswordField;
    private JComboBox<String> editEmpRoleField;

    // Room related
    // new room
    private JButton newRoomTabBtn;
    private JButton saveNewRoomBtn; // The submit button on the form
    private JButton backToRoomBtn; // The go back button
    private JComboBox<String> newRoomTierField;
    private JComboBox<String> newRoomSpaceField;
    private JTextField newRoomPriceField;
    // edit room
    private JButton editRoomTabBtn;
    private JButton saveEditRoomBtn;
    private JButton removeRoomBtn;
    private JComboBox<String> editRoomTierField;
    private JComboBox<String> editRoomSpaceField;
    private JComboBox<String> editRoomStatusField;
    private JTextField editRoomPriceField;

    // customer record related

    // functionality realted
    private record dashboardStats(double standardRooms, double premiumRooms, double total){}
    //dashboard
    private dashboardStats dailyStats;
    private dashboardStats monthlySats;

    public AdminUI(BaseFrame baseFrame, String adminName) {
        this.baseFrame = baseFrame;
        this.adminName = adminName;

        // set windows layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // expand in both directions
        gbc.fill = GridBagConstraints.BOTH;
        // take full height
        gbc.weighty = 1.0;

        // limit sidepannel to 30% width
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        JPanel sidePanel = createSidePanel();
        sidePanel.setPreferredSize(new Dimension(0, 0));
        add(sidePanel, gbc);

        // limit mainPannel to 70% width
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        this.mainPanel = createMainPanel();
        this.mainPanel.setPreferredSize(new Dimension(0, 0));
        add(this.mainPanel, gbc);


    }

    // side pannel
    private JPanel createSidePanel(){
        JPanel sidePannel = new JPanel();
        sidePannel.setBackground(Theme.COLOR_BLUE);

        // set sidepanel layout
        sidePannel.setLayout(new BoxLayout(sidePannel, BoxLayout.Y_AXIS));

        // add welcome text to side pannel
        sidePannel.add(UIFactory.createSpace(0, 20));
        JLabel welcomeLabel = new JLabel("Welcome " + this.adminName);
        welcomeLabel.setFont(Theme.FONT_SERIF_BOLD);
        welcomeLabel.setForeground(Theme.COLOR_GREY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePannel.add(welcomeLabel);
        sidePannel.add(UIFactory.createSpace(0, 50));

        // create navigation buttons
        dashboardButton = UIFactory.createButton("Dashboard", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        employeeButton = UIFactory.createButton("Employees", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        roomButton = UIFactory.createButton("Rooms", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        customerButton = UIFactory.createButton("Customers", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);

        sidePannel.add(dashboardButton);
        sidePannel.add(employeeButton);
        sidePannel.add(roomButton);
        sidePannel.add(customerButton);
        return sidePannel;
    }

    // create mainPannel
    private JPanel createMainPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Theme.COLOR_BEIGE);
        mainPanel.setLayout(new BorderLayout());
        return mainPanel;
    }

    // create dashboard
    public JPanel createDashboard(){
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new GridLayout(1, 2, 30, 0));
        dashboard.setBackground(Theme.COLOR_BEIGE);
        dashboard.setBorder(UIFactory.createPadding(30));

        dashboard.add(createTodayInfoCard());
        dashboard.add(createMonthlyInfoCard());
        return dashboard;
    }

    // create infoCard
    private JPanel createTodayInfoCard(){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.COLOR_BLUE);
        card.setBorder(UIFactory.createPadding(20));

        // add the top row
        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 2, 10, 0));
        topRow.setBackground(Theme.COLOR_BEIGE);
        topRow.setBorder(UIFactory.createPadding(5));
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // add "TOday' label
        JLabel timeFrame = new JLabel("Today");
        timeFrame.setForeground(Theme.COLOR_BLUE);
        timeFrame.setFont(Theme.FONT_SERIF_BOLD);
        timeFrame.setHorizontalAlignment(SwingConstants.CENTER);
        topRow.add(timeFrame);
        // add genrate report
        this.todayReportButton = UIFactory.createButton("Generate Report", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        todayReportButton.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));
        topRow.add(this.todayReportButton);

        card.add(topRow);
        card.add(UIFactory.createSpace(0, 10));

        // seperate pannel for rest of the data in the card
        JPanel contentArea = new JPanel(new GridLayout(5, 1, 0, 20));
        contentArea.setBackground(Theme.COLOR_BEIGE);
        contentArea.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BEIGE, 2, true), UIFactory.createPadding(10)));
        contentArea.setOpaque(false);

        // add single standard rooms
        this.todaySingleStRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Standard Single Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.todaySingleStRoomsLbl));
        // add double standard rooms
        this.todayDoubleStRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Standard Double Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.todayDoubleStRoomsLbl));

        // add single premium room
        this.todaySinglePrRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Premium Single Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.todaySinglePrRoomsLbl));
        // add double premium room
        this.todayDoublePrRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Premium Double Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.todayDoublePrRoomsLbl));

        // add total row
        this.todayTotalLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Total", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.todayTotalLbl));

        card.add(contentArea);
        return card;
    }

    private JPanel createMonthlyInfoCard(){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.COLOR_BLUE);
        card.setBorder(UIFactory.createPadding(20));

        // add the top row
        JPanel topRow = new JPanel();
        topRow.setLayout(new GridLayout(1, 2, 10, 0));
        topRow.setBackground(Theme.COLOR_BEIGE);
        topRow.setBorder(UIFactory.createPadding(5));
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        // add "TOday' label
        JLabel timeFrame = new JLabel("Monthly");
        timeFrame.setForeground(Theme.COLOR_BLUE);
        timeFrame.setFont(Theme.FONT_SERIF_BOLD);
        timeFrame.setHorizontalAlignment(SwingConstants.CENTER);
        topRow.add(timeFrame);
        // add genrate report
        this.monthlyReportButton = UIFactory.createButton("Generate Report", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        monthlyReportButton.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));
        topRow.add(this.monthlyReportButton);

        card.add(topRow);
        card.add(UIFactory.createSpace(0, 10));

        // seperate pannel for rest of the data in the card
        JPanel contentArea = new JPanel(new GridLayout(5, 1, 0, 20));
        contentArea.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BEIGE, 2, true), UIFactory.createPadding(10)));
        contentArea.setOpaque(false);

        // add single standard room
        this.monthlySingleStRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Standard Single Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.monthlySingleStRoomsLbl));
        // add Double standard room
        this.monthlyDoubleStRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Standard Double Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.monthlyDoubleStRoomsLbl));

        // add single preimum room
        this.monthlySinglePrRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Premium Single Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.monthlySinglePrRoomsLbl));
        // add double premium room
        this.monthlyDoublePrRoomsLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Premium Double Rooms", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.monthlyDoublePrRoomsLbl));

        // add total row
        this.monthlyTotalLbl = UIFactory.createLabel("", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN);
        contentArea.add(createInfoLabelRow(UIFactory.createLabel("Total", Theme.COLOR_BEIGE, Theme.FONT_SERIF_PLAIN), this.monthlyTotalLbl));

        card.add(contentArea);

        return card;
    }

    // take info label and add them as a jpanel
    private JPanel createInfoLabelRow(JLabel lbl1, JLabel lbl2){
        JPanel infoRow = new JPanel(new GridLayout(1, 2, 10 , 0));
        infoRow.setOpaque(false);
        infoRow.add(lbl1);
        infoRow.add(lbl2);
        infoRow.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BEIGE, 2, true), UIFactory.createPadding(10)));

        return infoRow;
    }

    // update dashboard metrics
    public void updateDashboardMetrics(AdminDashboardStats dailyStats, AdminDashboardStats monthlyStats){
        // Update Today Metrics
        this.todaySingleStRoomsLbl.setText(String.valueOf(dailyStats.standardSingle()));
        this.todayDoubleStRoomsLbl.setText(String.valueOf(dailyStats.standardDouble()));
        this.todaySinglePrRoomsLbl.setText(String.valueOf(dailyStats.premiumSingle()));
        this.todayDoublePrRoomsLbl.setText(String.valueOf(dailyStats.premiumDouble()));
        this.todayTotalLbl.setText(String.format("LKR%.2f", dailyStats.totalRevenue()));

        // Update Monthly Metrics
        this.monthlySingleStRoomsLbl.setText(String.valueOf(monthlyStats.standardSingle()));
        this.monthlyDoubleStRoomsLbl.setText(String.valueOf(monthlyStats.standardDouble()));
        this.monthlySinglePrRoomsLbl.setText(String.valueOf(monthlyStats.premiumSingle()));
        this.monthlyDoublePrRoomsLbl.setText(String.valueOf(monthlyStats.premiumDouble()));
        this.monthlyTotalLbl.setText(String.format("LKR%.2f", monthlyStats.totalRevenue()));
    }

    // create employeeTab
    public JPanel createEmployeeTab(List<Employee> employees){
        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));

        // add top row
        JPanel header = new JPanel(new GridLayout(1, 3, 0, 0));
        header.setBackground(Theme.COLOR_GREY);
        header.setBorder(UIFactory.createPadding(10));

        header.add(UIFactory.createLabel("Employee Name", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Employee Role", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        this.newEmployeeTabBtn = UIFactory.createButton("Add New Employee", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        header.add(this.newEmployeeTabBtn);

        employeePanel.add(header);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(UIFactory.createPadding(30));

        // iterate to the number of employees there are
        for (Employee employee:employees){
            // print employee name and role
            contentPanel.add(createEmployeeRow(employee));
            contentPanel.add(UIFactory.createSpace(0, 10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        employeePanel.add(scrollPane);

        return  employeePanel;
    }

    private JPanel createEmployeeRow(Employee employee){
        JPanel row = new JPanel(new GridLayout(1, 3, 0, 0));
        row.setBackground(Theme.COLOR_BEIGE);
        row.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));

        row.add(UIFactory.createLabel(employee.username(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(employee.role(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createButton("Modify", Theme.COLOR_BEIGE, Theme.COLOR_BLUE));

        return row;
    }

    // new Employee tab
    public JPanel createNewEmployeeTab(){
        // layout and bgColor for the main pannel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        setBackground(Theme.COLOR_BEIGE);

        //sub jpannel with the login UI
        JPanel newEmpPanel = new JPanel();
        newEmpPanel.setLayout(new GridLayout(12, 1, 0, 0));
        newEmpPanel.setBackground(Theme.COLOR_BEIGE);
        newEmpPanel.setBorder(UIFactory.createPadding(80));

        // USERNAME
        //label
        newEmpPanel.add(UIFactory.createLabel("Username: ", Color.BLACK, Theme.FONT_SERIF_BOLD));
        //inputField
        newEmpUsernameField = new JTextField(20);
        UIFactory.styleTextField(newEmpUsernameField);
        newEmpPanel.add(newEmpUsernameField);

        // create whitespace in between
        newEmpPanel.add(UIFactory.createSpace(0, 20));

        // PASSWORD
        //label
        newEmpPanel.add(UIFactory.createLabel("Password: ", Color.black, Theme.FONT_SERIF_BOLD));
        //inputField
        newEmpPasswordField = new JTextField(20);
        UIFactory.styleTextField(newEmpPasswordField);
        newEmpPanel.add(newEmpPasswordField);

        // create whitespace in between
        newEmpPanel.add(UIFactory.createSpace(0, 20));

        // Role
        //label
        newEmpPanel.add(UIFactory.createLabel("Role: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        newEmpRoleField = UIFactory.createComboBox(new String[]{"Receptionist", "Admin"});
        newEmpPanel.add(newEmpRoleField);

        // create whitespace in between
        newEmpPanel.add(UIFactory.createSpace(0, 20));

        saveNewEmployeeBtn = UIFactory.createButton("Add Employee", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        newEmpPanel.add(saveNewEmployeeBtn);

        // create whitespace in between
        newEmpPanel.add(UIFactory.createSpace(0, 50));

        backToEmployeesBtn = UIFactory.createButton("Go Back", Theme.COLOR_RED, Theme.COLOR_BEIGE);
        newEmpPanel.add(backToEmployeesBtn);

        return newEmpPanel;
    }

    // create roomTab
    public JPanel createRoomTab(List<Room> rooms){
        JPanel roomPannel = new JPanel();
        roomPannel.setLayout(new BoxLayout(roomPannel, BoxLayout.Y_AXIS));

        // add top row
        JPanel header = new JPanel(new GridLayout(1, 5, 0, 0));
        header.setBackground(Theme.COLOR_GREY);
        header.setBorder(UIFactory.createPadding(10));

        header.add(UIFactory.createLabel("Room ID", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Size", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Tier", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Status", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        this.newRoomTabBtn = UIFactory.createButton("Add New Room", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        header.add(this.newRoomTabBtn);

        roomPannel.add(header);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(UIFactory.createPadding(30));

        for (Room room:rooms){
            contentPanel.add(createRoomRow(room));
            contentPanel.add(UIFactory.createSpace(0, 10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        roomPannel.add(scrollPane);

        return  roomPannel;
    }

    // create room row
    private JPanel createRoomRow(Room room){
        JPanel row = new JPanel(new GridLayout(1, 5, 0, 0));
        row.setBackground(Theme.COLOR_BEIGE);
        row.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));

        row.add(UIFactory.createLabel(String.valueOf(room.roomId()), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.space(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.tier(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.status(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createButton("Modify", Theme.COLOR_BEIGE, Theme.COLOR_BLUE));

        return row;
    }

    // new Employee tab
    public JPanel createNewRoomTab(){
        // layout and bgColor for the main pannel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        setBackground(Theme.COLOR_BEIGE);

        //sub jpannel with the login UI
        JPanel newRoomPanel = new JPanel(new GridLayout(12, 1, 0, 0));
        newRoomPanel.setBackground(Theme.COLOR_BEIGE);
        newRoomPanel.setBorder(UIFactory.createPadding(80));

        // Tier
        //label
        newRoomPanel.add(UIFactory.createLabel("Room Tier: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        newRoomTierField = UIFactory.createComboBox(new String[]{"Standard", "Premium"});
        newRoomPanel.add(newRoomTierField);

        // create whitespace in between
        newRoomPanel.add(UIFactory.createSpace(0, 20));

        // Space
        //label
        newRoomPanel.add(UIFactory.createLabel("Role: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        newRoomSpaceField = UIFactory.createComboBox(new String[]{"Single", "Double"});
        newRoomPanel.add(newRoomSpaceField);

        // create whitespace in between
        newRoomPanel.add(UIFactory.createSpace(0, 20));

        // Price
        //label
        newRoomPanel.add(UIFactory.createLabel("Price: ", Color.black, Theme.FONT_SERIF_BOLD));
        //inputField
        newRoomPriceField = new JTextField(20);
        UIFactory.styleTextField(newRoomPriceField);
        newRoomPanel.add(newRoomPriceField);

        // create whitespace in between
        newRoomPanel.add(UIFactory.createSpace(0, 20));

        saveNewRoomBtn = UIFactory.createButton("Add Room", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        newRoomPanel.add(saveNewRoomBtn);

        // create whitespace in between
        newRoomPanel.add(UIFactory.createSpace(0, 50));

        backToRoomBtn = UIFactory.createButton("Go Back", Theme.COLOR_RED, Theme.COLOR_BEIGE);
        newRoomPanel.add(backToRoomBtn);

        return newRoomPanel;
    }

    // create customerTab
    public JPanel createCustomerTab(List<CustomerRecord> customerRecords){
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));

        // add top row
        JPanel header = new JPanel(new GridLayout(1, 6, 0, 0));
        header.setBackground(Theme.COLOR_GREY);
        header.setBorder(UIFactory.createPadding(10));

        header.add(UIFactory.createLabel("Custmer ID", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Customer Name", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Room ID", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Check-in Date", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Check-out Date", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Bill", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));

        customerPanel.add(header);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(UIFactory.createPadding(30));

        for (CustomerRecord customerRecord:customerRecords){
            //                                  recordid, name ,    roomid,         checkin         checkOut ,  price
            contentPanel.add(createCustomerRow(customerRecord));
            contentPanel.add(UIFactory.createSpace(0, 10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        customerPanel.add(scrollPane);

        return  customerPanel;
    }

    // create customer row
    private JPanel createCustomerRow(CustomerRecord customerRecord){
        JPanel row = new JPanel(new GridLayout(1, 6, 0, 0));
        row.setBackground(Theme.COLOR_BEIGE);
        row.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));

        row.add(UIFactory.createLabel(String.valueOf(customerRecord.recordId()), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(customerRecord.custName(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(String.valueOf(customerRecord.roomId()), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(customerRecord.checkIn(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(customerRecord.checkOut(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(String.format("LKR%.2f", customerRecord.price()), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));

        return row;
    }

    // update MainPanel
    public void updateMainPanel(JPanel newView){
        mainPanel.removeAll();
        mainPanel.add(newView, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    //  getters for components
    // sidePanel buttons
    public JButton getDashboardButton(){return  this.dashboardButton;}
    public JButton getEmployeeButton(){return this.employeeButton;}
    public JButton getRoomButton(){return this.roomButton;}
    public JButton getCustomerButton(){return this.customerButton;}

    // TODO geters for generate report
    // getters for new employee tab
    public JButton getNewEmployeeTabBtn(){ return this.newEmployeeTabBtn; }
    public JButton getSaveNewEmployeeBtn(){ return this.saveNewEmployeeBtn; }
    public JButton getBackToEmployeesBtn(){ return this.backToEmployeesBtn; }
    public JTextField getNewEmpUsername(){ return this.newEmpUsernameField; }
    public JTextField getNewEmpPassword(){ return this.newEmpPasswordField; }
    public JComboBox<String> getNewEmpRole(){ return this.newEmpRoleField; }
    // getters for edit employee tab
    public JButton getEditEmployeeTabBtn(){ return this.editEmployeeTabBtn; }
    public JButton getSaveEditEmployeeBtn(){ return this.saveEditEmployeeBtn; }
    public JButton getRemoveEmployeeBtn(){return this.removeEmployeeBtn;}
    public JTextField getEditEmpUsernameField(){ return this.editEmpUsernameField; }
    public JTextField getEditEmpPasswordField(){ return this.editEmpPasswordField; }
    public JComboBox<String> getEditEmpRoleField(){ return this.editEmpRoleField; }

    // getters for new Room tab
    public JButton getNewRoomTabBtn(){ return this.newRoomTabBtn; }
    public JButton getSaveNewRoomBtn(){ return this.saveNewRoomBtn; }
    public JButton getBackToRoomBtn(){ return this.backToRoomBtn; }
    public JComboBox<String> getNewRoomTierField(){ return this.newRoomTierField; }
    public JComboBox<String> getNewRoomSpaceField(){ return this.newRoomSpaceField; }
    public JTextField getNewRoomPriceField(){ return this.newRoomPriceField; }
    // getters for edit room tab
    public JButton getEditRoomTabBtn(){ return this.editRoomTabBtn; }
    public JButton getSaveEditRoomBtn(){ return this.saveEditRoomBtn; }
    public JComboBox<String> getEditRoomTierField(){ return this.editRoomTierField; }
    public JComboBox<String> getEditRoomSpaceField(){ return this.editRoomSpaceField; }
    public JTextField getEditRoomPriceField(){ return this.editRoomPriceField; }

}