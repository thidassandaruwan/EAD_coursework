package com.hotelapp.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceptionModel {
    public List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();
        // get all rooms query
        String sql = "SELECT * FROM Room";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("roomId"),
                        rs.getString("space"),
                        rs.getString("tier"),
                        rs.getString("status"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error handling rooms: " + e.getMessage());
        }
        return rooms;
    }

    // get searched filtered rooms (same thing as getFilteredEmps just more filters
    public List<Room> getFilteredRooms(String roomIdStr, String tier, String space, String status) {
        List<Room> roomList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT roomId, space, tier, status, price FROM Room WHERE 1=1 ");

        if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
            sql.append("AND roomId = ? ");
        }
        if (!"Any".equals(tier)) sql.append("AND tier = ? ");
        if (!"Any".equals(space)) sql.append("AND space = ? ");
        if (!"Any".equals(status)) sql.append("AND status = ? ");

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(roomIdStr.trim()));
            }
            if (!"Any".equals(tier)) pstmt.setString(paramIndex++, tier);
            if (!"Any".equals(space)) pstmt.setString(paramIndex++, space);
            if (!"Any".equals(status)) pstmt.setString(paramIndex, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    roomList.add(new Room(rs.getInt("roomId"), rs.getString("space"),
                            rs.getString("tier"), rs.getString("status"), rs.getDouble("price")));
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error filtering rooms: " + e.getMessage());
        }
        return roomList;
    }

    // return list of rooms filtered by their availability within the checkin and checkOUt dates. also considers the room tier and space
    public List<Room> getAvailableRooms(String checkIn, String checkOut, String tier, String space) {
        List<Room> availableRooms = new ArrayList<>();

        // query get roomIds which are booked within the given dates and then get all rooms that are not those IDs
        StringBuilder sql = new StringBuilder(
                "SELECT r.* FROM Room r " +
                        "WHERE r.roomId NOT IN (" +
                        "    SELECT c.roomId FROM CustomerRecord c " +
                        "    WHERE c.checkIn < ? AND c.checkOut > ?" +
                        ")"
        );

        // Add optional tier and space filters
        if (!"Any".equals(tier)) sql.append(" AND r.tier = ?");
        if (!"Any".equals(space)) sql.append(" AND r.space = ?");

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // Set date parameters for inner SQL query
            pstmt.setString(1, checkOut);
            pstmt.setString(2, checkIn);

            // next query injection index
            int paramIndex = 3;
            // if tier isn't any
            if (!"Any".equals(tier)) pstmt.setString(paramIndex++, tier);
            // if space isn't any
            if (!"Any".equals(space)) pstmt.setString(paramIndex, space);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // create a room object with result and add each room to available room list
                    availableRooms.add(new Room(
                            rs.getInt("roomId"),
                            rs.getString("space"),
                            rs.getString("tier"),
                            rs.getString("status"),
                            rs.getDouble("price")
                    ));
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error fetching available rooms: " + e.getMessage());
        }

        return availableRooms;
    }

    // update room
    public boolean updateRoom(int id, String status) {
        String sql = "UPDATE Room SET status = ? WHERE roomId = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
            return false;
        }
    }

    //book a room
    public boolean bookRoom(String customerName, int roomId, String checkIn, String checkOut, double price) {
        String insertCustomer = "INSERT INTO CustomerRecord (customerName, roomId, checkIn, checkOut, price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmtCust = conn.prepareStatement(insertCustomer)) {

            pstmtCust.setString(1, customerName);
            pstmtCust.setInt(2, roomId);
            pstmtCust.setString(3, checkIn);
            pstmtCust.setString(4, checkOut);
            pstmtCust.setDouble(5, price);

            int rowsAffected = pstmtCust.executeUpdate();
            return rowsAffected > 0;

        }
        catch (SQLException e)
        {
            System.err.println("Database error during booking: " + e.getMessage());
            return false;
        }
    }

    // get all cusotemr records
    public List<CustomerRecord> getAllCustomerRecords() {
        List<CustomerRecord> customerRecords = new ArrayList<>();
        // get all customer records
        String sql = "SELECT * FROM CustomerRecord";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // create customer record objects and add them to the records list
            while (rs.next()) {
                customerRecords.add(new CustomerRecord(
                        rs.getInt("recordId"),
                        rs.getString("customerName"),
                        rs.getInt("roomId"),
                        rs.getString("checkIn"),
                        rs.getString("checkOut"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error handling customer records: " + e.getMessage());
        }
        return customerRecords;
    }

    public List<CustomerRecord> searchCustomerRecords(String type, String keyword) {
        List<CustomerRecord> recordList = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            // return all customers if the search keyword empty
            return getAllCustomerRecords();
        }

        String sql = "SELECT recordId, customerName, roomId, checkIn, checkOut, price FROM CustomerRecord WHERE ";

        switch (type) {
            case "Customer": sql += "customerName LIKE ?"; break;
            case "RecordID": sql += "recordId = ?"; break;
            case "Check-In": sql += "checkIn = ?"; break;
            case "Check-Out": sql += "checkOut = ?"; break;
            default: return recordList;
        }

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // inject the query values corrosponding to the column type
            if (type.equals("Customer")) {
                pstmt.setString(1, "%" + keyword.trim() + "%");
            } else if (type.equals("RecordID")) {
                pstmt.setInt(1, Integer.parseInt(keyword.trim()));
            } else {
                pstmt.setString(1, keyword.trim()); // For dates
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recordList.add(new CustomerRecord(rs.getInt("recordId"), rs.getString("customerName"),
                            rs.getInt("roomId"), rs.getString("checkIn"), rs.getString("checkOut"), rs.getDouble("price")));
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error searching customers: " + e.getMessage());
        }
        return recordList;
    }
}
