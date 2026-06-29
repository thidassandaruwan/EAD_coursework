package com.hotelapp.view;

import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.github.lgooddatepicker.components.DatePicker;
import com.hotelapp.model.*;
import com.hotelapp.util.*;


public class ReceptionUI extends JPanel{
    // admin name to display the sidebar
    private final String receptionName;

    // sidepannel navigation buttons
    private JButton roomButton;
    private JButton bookRoomButton;
    private JButton customerButton;

    // mainpannel
    private final JPanel mainPanel;

    // Room tab related
    // room search funcitonality realted
    private JTextField roomIdSearchField;
    private JButton roomSearchButton;
    private JComboBox<String> roomTierFilter;
    private JComboBox<String> roomSpaceFilter;
    private JComboBox<String> roomStatusFilter;
    private JButton roomFilterButton;
    // edit room
    private List<JButton> editRoomBtns;
    private JButton backToRoomBtn;
    private JButton saveEditRoomBtn;
    private JTextField editRoomIdField;
    private JComboBox<String> editRoomStatusField;

    // bookroom related
    // room search funcitonality realted
    private DatePicker bookCheckInDate;
    private DatePicker bookCheckOutDate;
    private JComboBox<String> bookTierFilter;
    private JComboBox<String> bookSpaceFilter;
    private JButton bookFilterButton;
    // book room
    private List<JButton> bookRoomBtns;
    private JPanel availableRoomPanel;
    // room booking page
    private JTextField bookingPageCustNameField;
    private JTextField bookingPageRoomIDField;
    private JTextField bookingPageRoomTierField;
    private JTextField bookingPageRoomSpaceField;
    private JTextField bookingPageCheckInField;
    private JTextField bookingPageCheckOutField;
    private JTextField bookingPagePriceField;
    private JButton bookingPageBookButton;
    private JButton backToBookRoom;

    // customerRecord tab related
    // customerRecord search funcitonality realted
    private JTextField custSearchField;
    private JComboBox<String> customerSearchTypeField;
    private JButton customerSearchButton;

    public ReceptionUI(String receptionName) {
        this.receptionName = receptionName;

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
        JLabel welcomeLabel = new JLabel("Welcome " + this.receptionName);
        welcomeLabel.setFont(Theme.FONT_SERIF_BOLD);
        welcomeLabel.setForeground(Theme.COLOR_GREY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePannel.add(welcomeLabel);
        sidePannel.add(UIFactory.createSpace(0, 50));

        // create navigation buttons
        roomButton = UIFactory.createButton("Rooms", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        bookRoomButton = UIFactory.createButton("Book Room", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        customerButton = UIFactory.createButton("Customers", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);

        sidePannel.add(roomButton);
        sidePannel.add(bookRoomButton);
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
        JTextField editRoomTierField = new JTextField(room.tier());
        UIFactory.styleTextField(editRoomTierField);
        editRoomTierField.setEditable(false);
        editRoomPanel.add(editRoomTierField);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        // Space
        // Label
        editRoomPanel.add(UIFactory.createLabel("Room Space: ", Color.black, Theme.FONT_SERIF_BOLD));

        //combobox options
        JTextField editRoomSpaceField = new JTextField(room.space());
        UIFactory.styleTextField(editRoomSpaceField);
        editRoomSpaceField.setEditable(false);
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
        JTextField editRoomPriceField = new JTextField(20);
        editRoomPriceField.setText(String.format("%.2f", room.price()));
        UIFactory.styleTextField(editRoomPriceField);
        editRoomPriceField.setEditable(false);
        editRoomPanel.add(editRoomPriceField);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        saveEditRoomBtn = UIFactory.createButton("Save Room", Theme.COLOR_BEIGE, Theme.COLOR_GREEN);
        editRoomPanel.add(saveEditRoomBtn);

        // create whitespace in between
        editRoomPanel.add(UIFactory.createSpace(0, 10));

        backToRoomBtn = UIFactory.createButton("Go Back", Theme.COLOR_RED, Theme.COLOR_BEIGE);
        editRoomPanel.add(backToRoomBtn);

        return editRoomPanel;
    }

    // create Boook room tab
    public JPanel createBookRoomTab(){
        JPanel bookPannel = new JPanel();
        bookPannel.setLayout(new BoxLayout(bookPannel, BoxLayout.Y_AXIS));

        // add filter / search row
        JPanel searchPanel = new JPanel(new GridLayout(1, 9, 5, 0));
        searchPanel.setBorder(UIFactory.createPadding(5));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        searchPanel.setBackground(Theme.COLOR_BEIGE);
        // add checnIndate
        bookCheckInDate = new DatePicker();
        bookCheckInDate.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(bookCheckInDate);

        bookCheckOutDate = new DatePicker();
        bookCheckOutDate.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(bookCheckOutDate);

        // tier filter
        bookTierFilter = UIFactory.createComboBox(new String[]{"Any", "Standard", "Premium"});
        bookTierFilter.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(bookTierFilter);

        // space filter
        bookSpaceFilter = UIFactory.createComboBox(new String[]{"Any", "Single", "Double"});
        bookSpaceFilter.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(bookSpaceFilter);

        // add filterButton
        bookFilterButton = UIFactory.createButton("Filter", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        bookFilterButton.setFont(Theme.FONT_SERIF_BOLD_SMALL);
        searchPanel.add(bookFilterButton);

        bookPannel.add(UIFactory.createsHeaderRow(new String[]{"Check-In-Date", "Check-Out-Date", "Tier", "Space"}));
        bookPannel.add(searchPanel);

        // avaiblerooms will appear in this pannel dynamically
        this.availableRoomPanel = new JPanel();
        this.availableRoomPanel.setLayout(new BoxLayout(availableRoomPanel, BoxLayout.Y_AXIS));
        bookPannel.add(this.availableRoomPanel);

        return  bookPannel;
    }

    public void addAvailableRooms(List<Room> rooms, String checkIn, String checkOut){
        // refresh the buttons
        this.bookRoomBtns = new ArrayList<>();
        // clear anything in the availableRoompanel
        this.availableRoomPanel.removeAll();


        // add the room headers
        JPanel roomHeaderPanel = new JPanel();
        roomHeaderPanel.setLayout(new BoxLayout(roomHeaderPanel, BoxLayout.Y_AXIS));
        roomHeaderPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 30));
        roomHeaderPanel.add(UIFactory.createsHeaderRow(new String[]{"Room ID", "Size", "Tier", "Book Room"}));
        this.availableRoomPanel.add(roomHeaderPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(UIFactory.createPadding(30));
        // add each room to the content panel
        for (Room room : rooms){
            contentPanel.add(createBookRoomRow(room, checkIn, checkOut));
            contentPanel.add(UIFactory.createSpace(0, 10));
        }
        contentPanel.add(Box.createVerticalGlue());
        // add scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        this.availableRoomPanel.add(scrollPane);

        this.availableRoomPanel.repaint();
        this.availableRoomPanel.revalidate();
    }

    // create room row
    private JPanel createBookRoomRow(Room room, String checkInDate, String checkOutDate){
        JPanel row = new JPanel(new GridLayout(1, 5, 0, 0));
        row.setBackground(Theme.COLOR_BEIGE);
        row.setBorder(BorderFactory.createCompoundBorder(UIFactory.createBorder(Theme.COLOR_BLUE, 2, true), UIFactory.createPadding(10)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.add(UIFactory.createLabel(String.valueOf(room.roomId()), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.space(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));
        row.add(UIFactory.createLabel(room.tier(), Theme.COLOR_BLUE, Theme.FONT_SERIF_PLAIN));

        JButton bookRoomBtn = UIFactory.createButton("Book", Theme.COLOR_BEIGE, Theme.COLOR_BLUE);
        // create a bookingForm info object and put it inside the button
        BookingFormInfo bookingFormInfo = new BookingFormInfo(room.roomId(), room.tier(), room.space(), room.price(), checkInDate, checkOutDate);
        bookRoomBtn.putClientProperty("bookingFormInfo", bookingFormInfo);
        // add the editroom btn to the editroom button list
        this.bookRoomBtns.add(bookRoomBtn);

        row.add(bookRoomBtn);

        return row;
    }

    public JPanel createBookingForm(BookingFormInfo bookingFormInfo){
        // layouts
        JPanel bookingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        bookingPanel.setBackground(Theme.COLOR_BEIGE);
        bookingPanel.setBorder(UIFactory.createPadding(20));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 30));
        formPanel.setBackground(Theme.COLOR_BEIGE);

        bookingPageCustNameField = new JTextField(20);
        UIFactory.styleTextField(bookingPageCustNameField);
        formPanel.add(createBookInputElement("Customer Name", bookingPageCustNameField));

        bookingPageRoomIDField = new JTextField(20);
        UIFactory.styleTextField(bookingPageRoomIDField);
        bookingPageRoomIDField.setText(String.valueOf(bookingFormInfo.roomId()));
        bookingPageRoomIDField.setEditable(false);
        formPanel.add(createBookInputElement("Room ID", bookingPageRoomIDField));

        bookingPageRoomTierField = new JTextField(20);
        UIFactory.styleTextField(bookingPageRoomTierField);
        bookingPageRoomTierField.setText(bookingFormInfo.roomTier());
        bookingPageRoomTierField.setEditable(false);
        formPanel.add(createBookInputElement("Room Ter", bookingPageRoomTierField));

        bookingPageRoomSpaceField = new JTextField(20);
        UIFactory.styleTextField(bookingPageRoomSpaceField);
        bookingPageRoomSpaceField.setText(bookingFormInfo.roomSpace());
        bookingPageRoomSpaceField.setEditable(false);
        formPanel.add(createBookInputElement("Room Space", bookingPageRoomSpaceField));

        bookingPageCheckInField = new JTextField(20);
        UIFactory.styleTextField(bookingPageCheckInField);
        bookingPageCheckInField.setText(bookingFormInfo.checkInDate());
        bookingPageCheckInField.setEditable(false);
        formPanel.add(createBookInputElement("Check In Date", bookingPageCheckInField));

        bookingPageCheckOutField = new JTextField(20);
        UIFactory.styleTextField(bookingPageCheckOutField);
        bookingPageCheckOutField.setText(bookingFormInfo.checkOutDate());
        bookingPageCheckOutField.setEditable(false);
        formPanel.add(createBookInputElement("Check Out Date", bookingPageCheckOutField));

        // get the dates and convert to real dates
        LocalDate checkIn = LocalDate.parse(bookingFormInfo.checkInDate());
        LocalDate checkOut = LocalDate.parse(bookingFormInfo.checkOutDate());
        // check the number of days
        long numberOfDays = ChronoUnit.DAYS.between(checkIn, checkOut);
        // if the checkin and checkout is the same day and "numberOfDays" is 0 set number of days to one
        numberOfDays = (numberOfDays <= 0) ? 1 : numberOfDays;

        JTextField numberOfDaysFiled = new JTextField(20);
        UIFactory.styleTextField(numberOfDaysFiled);
        numberOfDaysFiled.setText(String.valueOf(numberOfDays));
        numberOfDaysFiled.setEditable(false);
        formPanel.add(createBookInputElement("Days", numberOfDaysFiled));

        bookingPagePriceField = new JTextField(20);
        UIFactory.styleTextField(bookingPagePriceField);
        bookingPagePriceField.setText(String.format("LKR %.2f", (numberOfDays * bookingFormInfo.roomPrice())));
        bookingPagePriceField.setEditable(false);
        formPanel.add(createBookInputElement("Total", bookingPagePriceField));


        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBackground(Theme.COLOR_BEIGE);
        bookingPageBookButton = UIFactory.createButton("Book Room", Theme.COLOR_BEIGE, Theme.COLOR_GREEN);
        backToBookRoom = UIFactory.createButton("Go Back", Theme.COLOR_RED, Theme.COLOR_BEIGE);
        buttonPanel.add(UIFactory.createSpace(0, 20)); // create white space
        buttonPanel.add(bookingPageBookButton);
        buttonPanel.add(backToBookRoom);


        // set vertical space to 80%
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.75;
        gbc.fill = GridBagConstraints.BOTH;
        // add the FormaPanel
        bookingPanel.add(formPanel, gbc);

        // set vertical space to 20%
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        // add buttonPannel
        bookingPanel.add(buttonPanel, gbc);
        return bookingPanel;
    }

    // create bookForm input panel with label
    private JPanel createBookInputElement(String lblText, JTextField textField){
        JPanel elementPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        elementPanel.setBackground(Theme.COLOR_BEIGE);
        elementPanel.add(UIFactory.createLabel(lblText, Color.BLACK, Theme.FONT_SERIF_PLAIN));
        elementPanel.add(textField);

        return elementPanel;
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

    // getters
    // sidepannel navigation buttons
    public JButton getRoomButton(){ return this.roomButton; }
    public JButton getBookRoomButton(){ return this.bookRoomButton; }
    public JButton getCustomerButton(){ return this.customerButton; }

    // Room tab related
    // room search funcitonality realted
    public JTextField getRoomIdSearchField(){ return this.roomIdSearchField; }
    public JButton getRoomSearchButton(){ return this.roomSearchButton; }
    public JComboBox<String> getRoomTierFilter(){ return this.roomTierFilter; }
    public JComboBox<String> getRoomSpaceFilter(){ return this.roomSpaceFilter; }
    public JComboBox<String> getRoomStatusFilter(){ return this.roomStatusFilter; }
    public JButton getRoomFilterButton(){ return this.roomFilterButton; }
    // edit room
    public List<JButton> getEditRoomBtns(){ return this.editRoomBtns; }
    public JButton getSaveEditRoomBtn(){ return this.saveEditRoomBtn; }
    public JButton getBackToRoomBtn(){ return this.backToRoomBtn; }
    public JTextField getEditRoomIdField(){ return this.editRoomIdField; }
    public JComboBox<String> getEditRoomStatusField(){ return this.editRoomStatusField; }

    // bookroom tab related
    public DatePicker getBookCheckInDate(){ return this.bookCheckInDate; }
    public DatePicker getBookCheckOutDate(){ return this.bookCheckOutDate; }
    public JComboBox<String> getBookTierFilter(){ return this.bookTierFilter; }
    public JComboBox<String> getBookSpaceFilter(){ return this.bookSpaceFilter; }
    public JButton getBookFilterButton(){ return this.bookFilterButton; }
    // search booking roompage
    public List<JButton> getBookRoomBtns(){ return this.bookRoomBtns; }
    // booking room
    public JTextField getBookingPageCustNameField(){ return this.bookingPageCustNameField; }
    public JTextField getBookingPageRoomIDField(){ return this.bookingPageRoomIDField; }
    public JTextField getBookingPageRoomTierField(){ return this.bookingPageRoomTierField; }
    public JTextField getBookingPageRoomSpaceField(){ return this.bookingPageRoomSpaceField; }
    public JTextField getBookingPageCheckInField(){ return this.bookingPageCheckInField; }
    public JTextField getBookingPageCheckOutField(){ return this.bookingPageCheckOutField; }
    public JTextField getBookingPagePriceField(){ return this.bookingPagePriceField; }
    public JButton getBookingPageBookButton(){ return this.bookingPageBookButton; }
    public JButton getBackToBookRoomButton(){return this.backToBookRoom; }

    // customerRecord tab related
    // customerRecord search funcitonality realted
    public JTextField getCustSearchField(){ return this.custSearchField; }
    public JComboBox<String> getCustomerSearchTypeField(){ return this.customerSearchTypeField; }
    public JButton getCustomerSearchButton(){ return this.customerSearchButton; }

}
