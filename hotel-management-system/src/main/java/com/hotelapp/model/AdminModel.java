package com.hotelapp.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {
    public AdminDashboardStats getDashboardStats(boolean currentDayOnly) {
        // check if need to query customer records for today or all month
        String sql = "SELECT " +
                "SUM(c.price) AS total, " +
                "SUM(CASE WHEN r.tier = 'Standard' AND r.space = 'Single' THEN 1 ELSE 0 END) AS standardSingle, " +
                "SUM(CASE WHEN r.tier = 'Standard' AND r.space = 'Double' THEN 1 ELSE 0 END) AS standardDouble, " +
                "SUM(CASE WHEN r.tier = 'Premium' AND r.space = 'Single' THEN 1 ELSE 0 END) AS premiumSingle, " +
                "SUM(CASE WHEN r.tier = 'Premium' AND r.space = 'Double' THEN 1 ELSE 0 END) AS premiumDouble " +
                "FROM CustomerRecord c " +
                "JOIN Room r ON c.roomId = r.roomId ";

        if (currentDayOnly)
        {
            sql = sql + "WHERE checkIn = CURRENT_DATE()" ;
        }
        else
        {
            sql = sql + "WHERE MONTH(checkIn) = MONTH(CURRENT_DATE()) AND YEAR(checkIn) = YEAR(CURRENT_DATE())";
        }

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            // if there any results, return the "total" from the query
            if (rs.next())
            {
                return new AdminDashboardStats(
                        rs.getInt("standardSingle"),
                        rs.getInt("standardDouble"),
                        rs.getInt("premiumSingle"),
                        rs.getInt("premiumDouble"),
                        rs.getDouble("total")
                );
            }
        }
        catch (SQLException e) {
            System.err.println("Error querying dashboard stats" + e.getMessage());
        }
        return new AdminDashboardStats(0, 0, 0, 0, 0);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        // get all employees
        String sql = "SELECT * FROM Employee";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // add employee to employeeList
                employeeList.add(new Employee(rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }
        } catch (SQLException e) {
            System.err.println("Database error handling employees: " + e.getMessage());
        }
        return employeeList;
    }

    // Insert a new employee into the database
    public boolean addEmployee(Employee newEmployee) {
        String sql = "INSERT INTO Employee (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEmployee.username());
            pstmt.setString(2, newEmployee.password());
            pstmt.setString(3, newEmployee.role());

            int rowsAffected = pstmt.executeUpdate();
            // Returns true if the insert was successful
            return (rowsAffected > 0);

        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }

    // Insert a new employee into the database
    public boolean addRoom(Room newRoom) {
        String sql = "INSERT INTO Room (space, tier, status, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newRoom.space());
            pstmt.setString(2, newRoom.tier());
            pstmt.setString(3, "Ready");
            pstmt.setDouble(4, newRoom.price());

            int rowsAffected = pstmt.executeUpdate();
            // Returns true if the insert was successful
            return (rowsAffected > 0);

        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

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
}
