package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
