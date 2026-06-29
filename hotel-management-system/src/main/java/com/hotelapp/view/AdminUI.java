package com.hotelapp.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import com.hotelapp.model.*;
import com.hotelapp.util.*;


public class AdminUI extends JPanel {
    // admin name to display the sidebar
    private final String adminName;

    // sidepannel navigation buttons
    private JButton dashboardButton;
    private JButton employeeButton;
    private JButton roomButton;
    private JButton customerButton;

    // mainpannel
    private final JPanel mainPanel;

    // Dashboard
    private JButton todayReportBtn;
    private JLabel todaySingleStRoomsLbl;
    private JLabel todaySinglePrRoomsLbl;
    private JLabel todayDoubleStRoomsLbl;
    private JLabel todayDoublePrRoomsLbl;
    private JLabel todayTotalLbl;

    private JButton monthlyReportBtn;
    private JLabel monthlySingleStRoomsLbl;
    private JLabel monthlySinglePrRoomsLbl;
    private JLabel monthlyDoubleStRoomsLbl;
    private JLabel monthlyDoublePrRoomsLbl;
    private JLabel monthlyTotalLbl;

    // Employee tab realted
    //  employee search funcitonality realted
    private JTextField empNameSearchField;
    private JButton empSearchButton;
    private JComboBox<String> empRoleFilter;
    private JButton empFilterButton;
    // new employee
    private JButton newEmployeeTabBtn;
    private JButton saveNewEmployeeBtn;
    private JButton backToEmployeesBtn;
    private JTextField newEmpUsernameField;
    private JTextField newEmpPasswordField;
    private JComboBox<String> newEmpRoleField;
    // edit employee
    private List<JButton> editEmployeeBtns;
    private JButton saveEditEmployeeBtn;
    private JButton removeEmployeeBtn;
    private JTextField editEmpUsernameField;
    private JTextField editEmpPasswordField;
    private JComboBox<String> editEmpRoleField;

    // Room tab related
    // room search funcitonality realted
    private JTextField roomIdSearchField;
    private JButton roomSearchButton;
    private JComboBox<String> roomTierFilter;
    private JComboBox<String> roomSpaceFilter;
    private JComboBox<String> roomStatusFilter;
    private JButton roomFilterButton;
    // new room
    private JButton newRoomTabBtn;
    private JButton saveNewRoomBtn;
    private JButton backToRoomBtn;
    private JComboBox<String> newRoomTierField;
    private JComboBox<String> newRoomSpaceField;
    private JTextField newRoomPriceField;
    // edit room
    private List<JButton> editRoomBtns;
    private JButton saveEditRoomBtn;
    private JButton removeRoomBtn;
    private JTextField editRoomIdField;
    private JComboBox<String> editRoomTierField;
    private JComboBox<String> editRoomSpaceField;
    private JComboBox<String> editRoomStatusField;
    private JTextField editRoomPriceField;

    // customerRecord tab related
    // customerRecord search funcitonality realted
    private JTextField custSearchField;
    private JComboBox<String> customerSearchTypeField;
    private JButton customerSearchButton;

    public AdminUI(String adminName) {
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
        this.todayReportBtn = UIFactory.createButton("Generate Report", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        todayReportBtn.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));
        topRow.add(this.todayReportBtn);

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
        this.monthlyReportBtn = UIFactory.createButton("Generate Report", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        monthlyReportBtn.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));
        topRow.add(this.monthlyReportBtn);

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
        // refresh the modifyEmpButtons (because if the employee tab is opened multiple times, the previous data might be outdated
        this.editEmployeeBtns = new ArrayList<>();

        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));

        // add filter / search row
        JPanel searchPanel = new JPanel(new GridLayout(1, 9, 5, 0));
        searchPanel.setBorder(UIFactory.createPadding(5));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        searchPanel.setBackground(Theme.COLOR_BEIGE);
        // add search
        empNameSearchField = new JTextField(20);
        UIFactory.styleTextField(empNameSearchField);
        empNameSearchField.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(empNameSearchField);
        // add searchbutton
        empSearchButton = UIFactory.createButton("Search", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        empSearchButton.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(empSearchButton);
        // create space between filter and search
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        // add filter
        empRoleFilter = UIFactory.createComboBox(new String[]{"Any", "Receptionist", "Admin"});
        empRoleFilter.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(empRoleFilter);
        // add filterButton
        empFilterButton = UIFactory.createButton("Filter", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        empFilterButton.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(empFilterButton);

        // add heders row
        JPanel header = new JPanel(new GridLayout(1, 3, 0, 0));
        header.setBackground(Theme.COLOR_GREY);
        header.setBorder(UIFactory.createPadding(10));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        header.add(UIFactory.createLabel("Employee Name", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        header.add(UIFactory.createLabel("Employee Role", Theme.COLOR_BLUE, Theme.FONT_SERIF_BOLD));
        this.newEmployeeTabBtn = UIFactory.createButton("Add New Employee", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        header.add(this.newEmployeeTabBtn);

        employeePanel.add(header);
        employeePanel.add(searchPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(UIFactory.createPadding(30));

        // iterate to the number of employees there are
        for (Employee employee:employees){
            // print employee name and role
            contentPanel.add(createEmployeeRow(employee));
            contentPanel.add(UIFactory.createSpace(0, 10));
        }
        // creates soaks up all empty space if there are little rows to show much white space left. Prevent from weird element resizings
        contentPanel.add(Box.createVerticalGlue());
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
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.add(UIFactory.createLabel(employee.username(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(employee.role(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));

        JButton editEmployeeBtn = UIFactory.createButton("Edit", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        // store the current employee Object inside the button, so that we can refer to each employee by each button
        editEmployeeBtn.putClientProperty("employeeData", employee);
        // add each button to editempbtn list
        this.editEmployeeBtns.add(editEmployeeBtn);
        row.add(editEmployeeBtn);

        return row;
    }

    // new Employee tab
    public JPanel createNewEmployeeTab(){
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

    // edit Room tab
    public JPanel createEditEmployeeTab(Employee employee){
        JPanel editEmployeePanel = new JPanel(new GridLayout(16, 1, 0, 0));
        editEmployeePanel.setBackground(Theme.COLOR_BEIGE);
        editEmployeePanel.setBorder(UIFactory.createPadding(30));

        // USERNAME
        //label
        editEmployeePanel.add(UIFactory.createLabel("Username: ", Color.BLACK, Theme.FONT_SERIF_BOLD));
        //inputField
        editEmpUsernameField = new JTextField(20);
        UIFactory.styleTextField(editEmpUsernameField);
        editEmpUsernameField.setText(employee.username());
        editEmpUsernameField.setEditable(false);
        editEmployeePanel.add(editEmpUsernameField);

        // create whitespace in between
        editEmployeePanel.add(UIFactory.createSpace(0, 20));

        // PASSWORD
        //label
        editEmployeePanel.add(UIFactory.createLabel("Password: ", Color.black, Theme.FONT_SERIF_BOLD));
        //inputField
        editEmpPasswordField = new JTextField(20);
        UIFactory.styleTextField(editEmpPasswordField);
        editEmpPasswordField.setText(employee.password());
        editEmployeePanel.add(editEmpPasswordField);

        // create whitespace in between
        editEmployeePanel.add(UIFactory.createSpace(0, 20));

        // Role
        //label
        editEmployeePanel.add(UIFactory.createLabel("Role: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        editEmpRoleField = UIFactory.createComboBox(new String[]{"Receptionist", "Admin"});
        editEmpRoleField.setSelectedItem(employee.role());
        editEmployeePanel.add(editEmpRoleField);

        // buttons
        // create whitespace in between
        editEmployeePanel.add(UIFactory.createSpace(0, 10));

        saveEditEmployeeBtn = UIFactory.createButton("Save Employee", Theme.COLOR_BEIGE, Theme.COLOR_GREEN);
        editEmployeePanel.add(saveEditEmployeeBtn);

        // create whitespace in between
        editEmployeePanel.add(UIFactory.createSpace(0, 20));

        removeEmployeeBtn = UIFactory.createButton("Remove Employee", Theme.COLOR_BEIGE, Theme.COLOR_RED);
        editEmployeePanel.add(removeEmployeeBtn);

        // create whitespace in between
        editEmployeePanel.add(UIFactory.createSpace(0, 10));

        backToEmployeesBtn = UIFactory.createButton("Go Back", Theme.COLOR_RED, Theme.COLOR_BEIGE);
        editEmployeePanel.add(backToEmployeesBtn);

        return editEmployeePanel;
    }

    // create roomTab
    public JPanel createRoomTab(List<Room> rooms){
        // refresh the edit room button list when the room tab loads
        this.editRoomBtns = new ArrayList<>();

        JPanel roomPannel = new JPanel();
        roomPannel.setLayout(new BoxLayout(roomPannel, BoxLayout.Y_AXIS));

        // add filter / search row
        JPanel searchPanel = new JPanel(new GridLayout(1, 9, 5, 0));
        searchPanel.setBorder(UIFactory.createPadding(5));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        searchPanel.setBackground(Theme.COLOR_BEIGE);
        // add search
        roomIdSearchField = new JTextField(20);
        UIFactory.styleTextField(roomIdSearchField);
        roomIdSearchField.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(roomIdSearchField);
        // add searchbutton
        roomSearchButton = UIFactory.createButton("Search", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        roomSearchButton.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(roomSearchButton);
        // create space between filter and search
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        // tier filter
        roomTierFilter = UIFactory.createComboBox(new String[]{"Any", "Standard", "Premium"});
        roomTierFilter.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(roomTierFilter);
        // space filter
        roomSpaceFilter = UIFactory.createComboBox(new String[]{"Any", "Single", "Double"});
        roomSpaceFilter.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(roomSpaceFilter);
        // status filter
        roomStatusFilter = UIFactory.createComboBox(new String[]{"Any", "Ready", "Booked", "Need Cleaning", "Maintenance"});
        roomStatusFilter.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(roomStatusFilter);
        // add filterButton
        roomFilterButton = UIFactory.createButton("Filter", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        roomFilterButton.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(roomFilterButton);

        // creates header
        roomPannel.add(UIFactory.createsHeaderRow(new String[]{"Room ID", "Size", "Tier", "Status", "Change Status"}));
        roomPannel.add(searchPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(UIFactory.createPadding(30));

        for (Room room:rooms){
            contentPanel.add(createRoomRow(room));
            contentPanel.add(UIFactory.createSpace(0, 10));
        }
        // creates soaks up all empty space if there are little rows to show much white space left. Prevent from weird element resizings
        contentPanel.add(Box.createVerticalGlue());
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
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.add(UIFactory.createLabel(String.valueOf(room.roomId()), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.space(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.tier(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.status(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));

        JButton editRoomBtn = UIFactory.createButton("Edit", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        // put each room object in each edit room button
        editRoomBtn.putClientProperty("roomData", room);
        // add the editroom btn to the editroom button list
        this.editRoomBtns.add(editRoomBtn);

        row.add(editRoomBtn);

        return row;
    }

    // new Room tab
    public JPanel createNewRoomTab(){
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
        newRoomPanel.add(UIFactory.createLabel("Room Space: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        newRoomSpaceField = UIFactory.createComboBox(new String[]{"Single", "Double"});
        newRoomPanel.add(newRoomSpaceField);

        // create whitespace in between
        newRoomPanel.add(UIFactory.createSpace(0, 20));

        // Price
        //label
        newRoomPanel.add(UIFactory.createLabel("Room Price: ", Color.black, Theme.FONT_SERIF_BOLD));
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

    // edit Room tab
    public JPanel createEditRoomTab(Room room){
        JPanel editRoomPanel = new JPanel(new GridLayout(20, 1, 0, 0));
        editRoomPanel.setBackground(Theme.COLOR_BEIGE);
        editRoomPanel.setBorder(UIFactory.createPadding(30));

        // Room ID
        //label
        editRoomPanel.add(UIFactory.createLabel("Room ID: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        editRoomIdField = new JTextField(20);
        editRoomIdField.setText(String.valueOf(room.roomId()));
        editRoomIdField.setEditable(false);
        UIFactory.styleTextField(editRoomIdField);
        editRoomPanel.add(editRoomIdField);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        // Tier
        //label
        editRoomPanel.add(UIFactory.createLabel("Room Tier: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        editRoomTierField = UIFactory.createComboBox(new String[]{"Standard", "Premium"});
        editRoomTierField.setSelectedItem(room.tier());
        editRoomPanel.add(editRoomTierField);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        // Space
        // Label
        editRoomPanel.add(UIFactory.createLabel("Room Space: ", Color.black, Theme.FONT_SERIF_BOLD));

        //combobox options
        editRoomSpaceField = UIFactory.createComboBox(new String[]{"Single", "Double"});
        editRoomSpaceField.setSelectedItem(room.space());
        editRoomPanel.add(editRoomSpaceField);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        // Status
        //label
        editRoomPanel.add(UIFactory.createLabel("Room Status: ", Color.black, Theme.FONT_SERIF_BOLD));
        //combobox options
        editRoomStatusField = UIFactory.createComboBox(new String[]{"Ready", "Booked", "Need Cleaning", "Maintenance"});
        editRoomStatusField.setSelectedItem(room.status());
        editRoomPanel.add(editRoomStatusField);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        // Price
        //label
        editRoomPanel.add(UIFactory.createLabel("Room Price: ", Color.black, Theme.FONT_SERIF_BOLD));
        //inputField
        editRoomPriceField = new JTextField(20);
        editRoomPriceField.setText(String.format("%.2f", room.price()));
        UIFactory.styleTextField(editRoomPriceField);
        editRoomPanel.add(editRoomPriceField);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        saveEditRoomBtn = UIFactory.createButton("Save Room", Theme.COLOR_BEIGE, Theme.COLOR_GREEN);
        editRoomPanel.add(saveEditRoomBtn);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 20));

        removeRoomBtn = UIFactory.createButton("Remove Room", Theme.COLOR_BEIGE, Theme.COLOR_RED);
        editRoomPanel.add(removeRoomBtn);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        backToRoomBtn = UIFactory.createButton("Go Back", Theme.COLOR_RED, Theme.COLOR_BEIGE);
        editRoomPanel.add(backToRoomBtn);

        return editRoomPanel;
    }

    // create customerTab
    public JPanel createCustomerTab(List<CustomerRecord> customerRecords){
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));

        // add filter / search row
        JPanel searchPanel = new JPanel(new GridLayout(1, 9, 5, 0));
        searchPanel.setBorder(UIFactory.createPadding(5));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        searchPanel.setBackground(Theme.COLOR_BEIGE);
        // push the saerch/filter componenets to right
        // create space between filter and search
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        searchPanel.add(UIFactory.createSpace(100, 0));
        // add search
        custSearchField = new JTextField(20);
        UIFactory.styleTextField(custSearchField);
        custSearchField.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(custSearchField);
        // select search type
        customerSearchTypeField = UIFactory.createComboBox(new String[]{"Customer", "RecordID", "Check-In", "Check-Out"});
        customerSearchTypeField.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(customerSearchTypeField);
        // add searchbutton
        customerSearchButton = UIFactory.createButton("Search", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        customerSearchButton.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(customerSearchButton);

        // add top row
        customerPanel.add(UIFactory.createsHeaderRow(new String[]{"Custmer Record ID", "Customer Name", "Room ID", "Check-in Date", "Check-out Date", "Bill"}));
        customerPanel.add(searchPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(UIFactory.createPadding(30));

        for (CustomerRecord customerRecord:customerRecords){
            //                                  recordid, name ,    roomid,         checkin         checkOut ,  price
            contentPanel.add(createCustomerRow(customerRecord));
            contentPanel.add(UIFactory.createSpace(0, 10));
        }

        // creates soaks up all empty space if there are little rows to show much white space left. Prevent from weird element resizings
        contentPanel.add(Box.createVerticalGlue());

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
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
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

    // getters for employee search functionality
    public JTextField getEmpNameSearchField(){ return this.empNameSearchField; }
    public JButton getEmpSearchButton(){ return this.empSearchButton; }
    public JComboBox<String> getEmpRoleFilter(){ return this.empRoleFilter; }
    public JButton getEmpFilterButton(){ return this.empFilterButton; }
    // getters for new employee tab
    public JButton getNewEmployeeTabBtn(){ return this.newEmployeeTabBtn; }
    public JButton getSaveNewEmployeeBtn(){ return this.saveNewEmployeeBtn; }
    public JButton getBackToEmployeesBtn(){ return this.backToEmployeesBtn; }
    public JTextField getNewEmpUsername(){ return this.newEmpUsernameField; }
    public JTextField getNewEmpPassword(){ return this.newEmpPasswordField; }
    public JComboBox<String> getNewEmpRole(){ return this.newEmpRoleField; }
    // getters for edit employee tab
    public List<JButton> getEditEmployeeBtns(){ return this.editEmployeeBtns; }
    public JButton getSaveEditEmployeeBtn(){ return this.saveEditEmployeeBtn; }
    public JButton getRemoveEmployeeBtn(){return this.removeEmployeeBtn;}
    public JTextField getEditEmpUsernameField(){ return this.editEmpUsernameField; }
    public JTextField getEditEmpPasswordField(){ return this.editEmpPasswordField; }
    public JComboBox<String> getEditEmpRoleField(){ return this.editEmpRoleField; }

    // getters for room search functionality
    public JTextField getRoomIdSearchField(){ return this.roomIdSearchField; }
    public JButton getRoomSearchButton(){ return this.roomSearchButton; }
    public JComboBox<String> getRoomTierFilter(){return this.roomTierFilter; }
    public JComboBox<String> getRoomSpaceFilter(){ return this.roomSpaceFilter; }
    public JComboBox<String> getRoomStatusFilter(){ return this.roomStatusFilter; }
    public JButton getRoomFilterButton(){ return this.roomFilterButton; }
    // getters for new Room tab
    public JButton getNewRoomTabBtn(){ return this.newRoomTabBtn; }
    public JButton getSaveNewRoomBtn(){ return this.saveNewRoomBtn; }
    public JButton getBackToRoomBtn(){ return this.backToRoomBtn; }
    public JComboBox<String> getNewRoomTierField(){ return this.newRoomTierField; }
    public JComboBox<String> getNewRoomSpaceField(){ return this.newRoomSpaceField; }
    public JTextField getNewRoomPriceField(){ return this.newRoomPriceField; }
    // getters for edit room tab
    public List<JButton> getEditRoomBtns(){ return this.editRoomBtns; }
    public JButton getSaveEditRoomBtn(){ return this.saveEditRoomBtn; }
    public JButton getRemoveRoomBtn(){ return this.removeRoomBtn; }
    public JTextField getEditRoomIdField(){ return this.editRoomIdField; }
    public JComboBox<String> getEditRoomTierField(){ return this.editRoomTierField; }
    public JComboBox<String> getEditRoomSpaceField(){ return this.editRoomSpaceField; }
    public JComboBox<String> getEditRoomStatusField(){ return this.editRoomStatusField; }
    public JTextField getEditRoomPriceField(){ return this.editRoomPriceField; }

    // getters for customerrecord search functionality
    public JTextField getCustSearchField(){ return this.custSearchField; }
    public JComboBox<String> getCustomerSearchTypeField(){ return this.customerSearchTypeField; }
    public JButton getCustomerSearchButton(){ return this.customerSearchButton; }

}