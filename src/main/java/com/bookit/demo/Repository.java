package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class Repository {

    @Autowired
    private DataSource dataSource;

    public ArrayList<Booking> getEmptyBookings() {

        ArrayList<Booking> bookings = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Booking WHERE CustomerId is Null")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(rsBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return bookings;
    }

    public int addEmptyBooking(Booking booking) {
        int generatedId = 0;   // Kan inte skapa int = null

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Booking (EmployeeId, BookingDate, StartTime, EndTime) VALUES (?,?,?,?) ", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getEmployeeId());
            ps.setString(2, booking.getDate());
            ps.setString(3, booking.getStartTime());
            ps.setString(4, booking.getEndTime());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();  // Hämta av databasen genererat ID för den tillagda raden
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public int newTimeslot(int employeeId, String date, String startTime, String endTime) {
        int generatedId = -1;   // Kan inte skapa int = null

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Timeslot (EmployeeId, BookingDate, StartTime, EndTime) VALUES (?,?,?,?) ", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, employeeId);
            ps.setString(2, date);
            ps.setString(3, startTime);
            ps.setString(4, endTime);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();  // Hämta av databasen genererat ID för den tillagda raden
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public int addNewCustomer(Customer customer) {
        int generatedId = -1;   // Kan inte skapa int = null
        System.out.println("CustomerNumber: " +customer.getCustomerNumber());

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO CUSTOMER (SocialSecurityNumber, FirstName, LastName, Email, PhoneNumber) VALUES (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, customer.getCustomerNumber());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();  // Hämta av databasen genererat ID för den tillagda raden
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }


    public void createBooking(int customerId, int timeslotId) {

    }

    //Osäker på om metoden nedan behövs. Metodens svar går att få från getEmptyBookings().size

    public int countEmptyTimeslots() {  // Svarar med antalet lediga bookings

        int result = -1;

        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM TIMESLOT AS COUNT " +
                     "WHERE ID NOT IN (SELECT TIMESLOTID FROM BOOKING)")) {

            if (rs.next()) {
                result = rs.getInt("COUNT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

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

}
