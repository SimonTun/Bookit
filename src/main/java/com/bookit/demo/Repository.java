package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

@org.springframework.stereotype.Repository
public class Repository {

    @Autowired
    private DataSource dataSource;


    public Booking getBooking(int id) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Booking WHERE ID =?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rsBooking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





    private Booking rsBooking (ResultSet rs) throws SQLException {
        return new Booking(rs.getInt("Id"),
                rs.getInt("customerId"),
                rs.getInt("employeeId"),
                rs.getString("bookingDate"),
                rs.getString("startTime"),
                rs.getString("endTime"));

    }

    public int addCustomer(Customer customer) {
        int generatedId = -1;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO CUSTOMER (SocialSecurityNumber, FirstName, LastName, Email, PhoneNumber) VALUES (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, customer.getCustomerNumber());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return generatedId;
    }



}
