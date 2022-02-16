package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@org.springframework.stereotype.Repository
public class Repository {

    @Autowired
    private DataSource dataSource;

    public ArrayList<Timeslot> getEmptyTimeslots() {

        ArrayList<Timeslot> timeslots = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM TIMESLOT " +
                     "WHERE ID NOT IN (SELECT TIMESLOTID FROM BOOKING)")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                timeslots.add(rsTimeslot(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return timeslots;
    }

    public ArrayList<Timeslot> getEmptyTimeslotsOnDate(String date) {

        ArrayList<Timeslot> timeslots = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM TIMESLOT " +
//             PreparedStatement ps = conn.prepareStatement("SELECT ID, EmployeeId, BookingDate, TO_CHAR(STARTTIME, 'HH24:MI') AS STARTTIME, TO_CHAR(ENDTIME, 'HH24:MI') AS ENDTIME FROM TIMESLOT " +
                     "WHERE TIMESLOT.BOOKINGDATE = ? AND " +
                     "NOT EXISTS (SELECT NULL FROM BOOKING WHERE TIMESLOTID = TIMESLOT.ID) " +
                     "ORDER BY STARTTIME")) {
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                timeslots.add(rsTimeslot(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timeslots;
    }

    public ArrayList<Timeslot> getDistinctEmptyTimeslotsOnDate(String date) {

        ArrayList<Timeslot> timeslots = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT ID, EmployeeId, BookingDate, DISTINCT(STARTTIME), ENDTIME FROM TIMESLOT " +
                     "WHERE TIMESLOT.BOOKINGDATE = ? AND " +
                     "NOT EXISTS (SELECT NULL FROM BOOKING WHERE TIMESLOTID = TIMESLOT.ID)")) {
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                timeslots.add(rsTimeslot(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timeslots;
    }

    public int newBooking(int BookingrequestID, int timeslotId) {
        int generatedId = 0;   // Kan inte skapa int = null

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Booking (BookingrequestID, TimeslotId) VALUES (?,?) ", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, BookingrequestID);
            ps.setInt(2, timeslotId);
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

    public void newContent(ContentHolder content) {

        String value;
        int bookingRequestId = content.getBookingRequestId();

        storeTextMessage(bookingRequestId, content.getTextMessage());   // Spara textMessage till sql BOOKINGREQUEST, genom separat metod

        for (int i = 0; i < content.getContents().size(); i++) {
            if (content.getContents().get(i).isEnabled()) {
//                value = content.getContents().get(i).getSubjects().name();
                value = content.getContents().get(i).getSubjects().name();

                try (Connection conn = dataSource.getConnection();
                     PreparedStatement ps = conn.prepareStatement("INSERT INTO CONTENT (BookingRequestId, content) VALUES (?,?) ")) {
                    ps.setInt(1, bookingRequestId);
                    ps.setString(2, value);

                    ps.executeUpdate();


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        }

    }
    public void storeTextMessage(int bookingrequestId, String textmessage) {

//        UPDATE Customers
//        SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
//        WHERE CustomerID = 1;
//


        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE BOOKINGREQUEST SET TEXTMESSAGE = ? WHERE ID = ? ")) {
            ps.setString(1, textmessage);
            ps.setInt(2, bookingrequestId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    public void getCustomerInformation(int bookingrequestId, int timeslotId) {
//
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM TIMESLOT WHERE ID =?")) {
//            ps.setInt(1, bookingrequestId);
//            ResultSet rs = ps.executeQuery();
//
////            if (rs.next()) {
////                return rsTimeslot(rs);
////            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
////        return null;
//    }

    public int addNewCustomer(Customer customer) {
        int generatedId = -1;   // Kan inte skapa int = null

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

    public int addNewBookingRequestId(int customerId) {
        int generatedId = -1;   // Kan inte skapa int = null

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO BOOKINGREQUEST (CustomerId) VALUES (?) ", Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, customerId);

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

    public BookingContent getBookingContent(int timeslotId, int bookingrequestId) {
        Timeslot timeslot = getTimeslot(timeslotId);

        String date = timeslot.getDate();
        String startTime = timeslot.getStartTime().substring(0,5);
        String endTime = timeslot.getEndTime().substring(0,5);
        int employeeId = timeslot.getEmployeeId();
        String textMessage = getAnyStringFromDatabase("SELECT TEXTMESSAGE AS RESULT FROM BOOKINGREQUEST WHERE ID="+bookingrequestId +"");
        String employeeFirstName = getAnyStringFromDatabase("SELECT FIRSTNAME AS RESULT FROM EMPLOYEE WHERE ID="+employeeId +"");
        String employeeLastName = getAnyStringFromDatabase("SELECT LASTNAME  AS RESULT FROM EMPLOYEE WHERE ID="+employeeId +"");
        String picture = employeeId+".jpg";
        String videoLink = "https://teams.microsoft.com/l/meetup-join/19%3ameeting_OTZjZjRkOTYtYzlhMi00MjI4LTkwNjUtYzQ5NzFkOGIxNDg";

        return new BookingContent(date,startTime,endTime,null,textMessage,employeeFirstName,employeeLastName,picture,videoLink);

    }

    public String getAnyStringFromDatabase(String str) {  // Svarar med antalet lediga bookings

        // This method works with parameter eg. "SELECT COLUMNNAME FROM TABLENAME AS RESULT WHERE ID=X"

        String result ="";

        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(str)) {

            if (rs.next()) {
                result = rs.getString("RESULT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



    //Metoden nedan behövs egentligen inte. Metodens svar går att få från getEmptyTimeslots().size



    public int numberOfEmptyTimeslots() {  // Svarar med antalet lediga bookings

        int result = -1;

        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS COUNT FROM TIMESLOT " +
                     "WHERE ID NOT IN (SELECT TIMESLOTID FROM BOOKING)")) {

            if (rs.next()) {
                result = rs.getInt("COUNT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int numberOfBookings() {  // Svarar med antalet lediga bookings

        int result = -1;

        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS COUNT FROM BOOKING")) {

            if (rs.next()) {
                result = rs.getInt("COUNT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Timeslot getTimeslot(int id) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM TIMESLOT WHERE ID =?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rsTimeslot(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomer(int id) {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM CUSTOMER WHERE ID =?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rsCustomer(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public List<Content> getAllsubjects() {
//        List<Content> contents = new ArrayList<>();
//        try (Connection conn = dataSource.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM Content")) {
//
//            while (rs.next()){
//                contents.add(rsContent(rs));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return contents;
//    }

    //
//    private Content rsContent(ResultSet rs) throws SQLException {
//        return new Content(rs.getInt("id"),
//                rs.getString("subjects"));
//    }

    private Timeslot rsTimeslot(ResultSet rs) throws SQLException {
        return new Timeslot(rs.getInt("Id"),
                rs.getInt("employeeId"),
                rs.getString("bookingDate"),
                rs.getString("startTime"),
                rs.getString("endTime"));
    }

    private Customer rsCustomer(ResultSet rs) throws SQLException {
        return new Customer(rs.getInt("Id"),
                rs.getLong("SocialSecurityNumber"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("PhoneNumber"),
                rs.getString("Email"));

    }


}
