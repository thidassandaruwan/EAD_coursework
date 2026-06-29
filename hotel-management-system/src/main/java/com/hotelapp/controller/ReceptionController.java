package com.hotelapp.controller;

import com.hotelapp.model.*;
import com.hotelapp.view.AdminUI;
import com.hotelapp.view.ReceptionUI;

import javax.swing.*;
import java.util.List;

public class ReceptionController {
    private final ReceptionUI view;
    private final ReceptionModel model;

    public ReceptionController(ReceptionUI view, ReceptionModel model){
        this.view = view;
        this.model = model;

        setupSidePanelNavigationListners();

        // open room tab by default
        syncRoomTabView();
    }

    private void syncRoomTabView() {
        // get all rooms
        List<Room> rooms = model.getAllRooms();
        // create the room tab view
        view.updateMainPanel(view.createRoomTab(rooms));
        // setup navigation listeners
        setupRoomTabNavigationListeners();
        // setup filter seach action listeners
        setupRoomSearchAndFilter();
    }

    private void setupSidePanelNavigationListners(){
        // roomButton actionlistner
        view.getRoomButton().addActionListener(e -> syncRoomTabView());

        // Customer button actionlistner
        view.getBookRoomButton().addActionListener(e -> {
            view.updateMainPanel(view.createBookRoomTab());
            setupBookRoomSearchAndFilter();
        });

        // Customer button actionlistner
        view.getCustomerButton().addActionListener(e -> {
            List<CustomerRecord> customerRecords = model.getAllCustomerRecords();
            view.updateMainPanel(view.createCustomerTab(customerRecords));
            // setup filter seach action listeners
            setupCustomerSearch();
        });
    }

    // room actionlisteners
    private void setupRoomTabNavigationListeners(){
        if (view.getEditRoomBtns() != null){
            for (JButton editButton:view.getEditRoomBtns()){
                editButton.addActionListener(e -> {
                    // get the clicked button
                    JButton clickedButton = (JButton) e.getSource();
                    // get the room object stored inside the clickedbutton ( the object is in a commom object form, hence the Employee casting)
                    Room selectedRoom = (Room) clickedButton.getClientProperty("roomData");

                    // create the edit room page
                    view.updateMainPanel(view.createEditRoomTab(selectedRoom));
                    // implement editroom form buttons funcionality
                    setupEditRoomFormListeners();
                });
            }
        }
    }

    private void setupRoomSearchAndFilter() {
        // search button
        view.getRoomSearchButton().addActionListener(e -> {
            String keyword = view.getRoomIdSearchField().getText();
            // Pass "Any" to all filters to ignore them
            List<Room> results = model.getFilteredRooms(keyword, "Any", "Any", "Any");

            view.updateMainPanel(view.createRoomTab(results));

            // Restore search input, RESET all dropdowns
            view.getRoomIdSearchField().setText(keyword);
            view.getRoomTierFilter().setSelectedItem("Any");
            view.getRoomSpaceFilter().setSelectedItem("Any");
            view.getRoomStatusFilter().setSelectedItem("Any");

            setupRoomTabNavigationListeners();
            setupRoomSearchAndFilter();
        });

        // Room FILTER Button (Dropdowns Only)
        view.getRoomFilterButton().addActionListener(e -> {
            String tier = (String) view.getRoomTierFilter().getSelectedItem();
            String space = (String) view.getRoomSpaceFilter().getSelectedItem();
            String status = (String) view.getRoomStatusFilter().getSelectedItem();

            // Pass empty string to the ID search to ignore it
            List<Room> results = model.getFilteredRooms("", tier, space, status);

            view.updateMainPanel(view.createRoomTab(results));

            // CLEAR search input, restore dropdown selections
            view.getRoomIdSearchField().setText("");
            view.getRoomTierFilter().setSelectedItem(tier);
            view.getRoomSpaceFilter().setSelectedItem(space);
            view.getRoomStatusFilter().setSelectedItem(status);

            setupRoomTabNavigationListeners();
            setupRoomSearchAndFilter();
        });
    }

    private void setupEditRoomFormListeners() {
        // go back actionlisner
        view.getBackToRoomBtn().addActionListener(e -> {
            view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
            setupRoomTabNavigationListeners();
        });

        // save room button
        view.getSaveEditRoomBtn().addActionListener(e -> {
            int roomId = Integer.parseInt(view.getEditRoomIdField().getText());
            String status = view.getEditRoomStatusField().getSelectedItem().toString();


            if (model.updateRoom(roomId, status)) {
                JOptionPane.showMessageDialog(null, "Room updated successfully!");
                view.updateMainPanel(view.createRoomTab(model.getAllRooms()));
                setupRoomTabNavigationListeners();
            } else {
                JOptionPane.showMessageDialog(null, "Error updating room.");
            }
        });
    }

    // book room tab
    // setup filter rooms according to date
    private void setupBookRoomSearchAndFilter() {
        view.getBookFilterButton().addActionListener(e -> {
            // retreve the dates from dateFields
            java.time.LocalDate checkInDate = view.getBookCheckInDate().getDate();
            java.time.LocalDate checkOutDate = view.getBookCheckOutDate().getDate();

            // date validation
            if (checkInDate == null || checkOutDate == null) {
                JOptionPane.showMessageDialog(null, "Please select both Check-In and Check-Out dates.", "Missing Dates", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // validate date ranges
            if (!checkOutDate.isAfter(checkInDate)) {
                JOptionPane.showMessageDialog(null, "Check-Out date must be after Check-In date.", "Invalid Dates", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert LocalDates to String for SQL
            String checkInStr = checkInDate.toString();
            String checkOutStr = checkOutDate.toString();

            // Get filter values
            String tier = (String) view.getBookTierFilter().getSelectedItem();
            String space = (String) view.getBookSpaceFilter().getSelectedItem();

            // get available rooms
            List<Room> availableRooms = model.getAvailableRooms(checkInStr, checkOutStr, tier, space);

            // update the UI
            view.addAvailableRooms(availableRooms, checkInStr, checkOutStr);

            // setup actionlisteners for Book buttons
            setupBookRoomActionListeners();
        });
    }

    // serpearte method to setup book rooom buttons since the room rows are only generated after Filter button is clicked
    private void setupBookRoomActionListeners() {
        if (view.getBookRoomBtns() != null) {
            for (JButton bookBtn : view.getBookRoomBtns()) {
                bookBtn.addActionListener(e -> {
                    JButton clickedButton = (JButton) e.getSource();

                    // Retrieve the BookingFormInfo object attached to the button in ReceptionUI
                    BookingFormInfo bookingInfo = (BookingFormInfo) clickedButton.getClientProperty("bookingFormInfo");
                    // switch to booking form
                    view.updateMainPanel(view.createBookingForm(bookingInfo));
                    // setup functionality
                    setupBookingFormListeners();
                });
            }
        }
    }

    private void setupBookingFormListeners() {
        // go back button action listener
        view.getBackToBookRoomButton().addActionListener(e -> {
            view.updateMainPanel(view.createBookRoomTab());
            setupBookRoomSearchAndFilter();
        });

        // book room button functionality
        view.getBookingPageBookButton().addActionListener(e -> {
            String custName = view.getBookingPageCustNameField().getText().trim();
            int roomId = Integer.parseInt(view.getBookingPageRoomIDField().getText());
            String checkIn = view.getBookingPageCheckInField().getText();
            String checkOut = view.getBookingPageCheckOutField().getText();

            // Extract numeric price from the formatted label string (e.g., "LKR 5000.00" -> "5000.00")
            String priceStr = view.getBookingPagePriceField().getText().replace("LKR ", "").trim();
            double price = Double.parseDouble(priceStr);

            // customer name validation
            if (custName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a customer name.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Execute the insert query
            if (model.bookRoom(custName, roomId, checkIn, checkOut, price)) {
                JOptionPane.showMessageDialog(null, "Reservation confirmed for " + custName + "!");

                // Return to a fresh booking tab view
                view.updateMainPanel(view.createBookRoomTab());
                setupBookRoomSearchAndFilter();
            } else {
                JOptionPane.showMessageDialog(null, "Error saving reservation to database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // customer search controller
    private void setupCustomerSearch() {
        view.getCustomerSearchButton().addActionListener(e -> {
            String keyword = view.getCustSearchField().getText();
            String type = (String) view.getCustomerSearchTypeField().getSelectedItem();

            // Fetch filtered data
            List<CustomerRecord> results = model.searchCustomerRecords(type, keyword);

            // Refresh UI
            view.updateMainPanel(view.createCustomerTab(results));

            // Restore inputs
            view.getCustSearchField().setText(keyword);
            view.getCustomerSearchTypeField().setSelectedItem(type);

            // Re-bind listener
            setupCustomerSearch();
        });
    }
}
