package com.bookit.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;

@SpringBootTest
class BookitApplicationTests {

    @Autowired
    private Repository repo;

    @Autowired
    private BookitService service;


    @Test
    void addNewTimeslotAndConfirmEntries() {
        int id = repo.newTimeslot(2, "2057-01-30", "23:00:00", "23:45:00");

        System.out.println("\n--- A new timeslot with id nr " + id + " is successfully created ---\n");

        Timeslot test = repo.getTimeslot(id);

        Assertions.assertEquals(2, test.getEmployeeId());
        Assertions.assertEquals("2057-01-30", test.getDate());
        Assertions.assertEquals("23:00:00", test.getStartTime());
        Assertions.assertEquals("23:45:00", test.getEndTime());
    }

    @Test
    void addNewCustomerAndConfirmEntries() {
        int id = repo.addNewCustomer(new Customer(6505050101L, "Peter", "DenStore", "040-77 88 99", "peter@sj.se"));
        int bookingId = repo.addNewBookingRequestId(id);

        System.out.println("\n--- A new customer with bookingid nr " + bookingId + " is successfully created ---\n");
        System.out.println("\n--- A new customer with id nr " + id + " is successfully created ---\n");

        Customer test = repo.getCustomer(id);

        Assertions.assertEquals(6505050101L, test.getCustomerNumber());
        Assertions.assertEquals("Peter", test.getFirstName());
        Assertions.assertEquals("DenStore", test.getLastName());
        Assertions.assertEquals("040-77 88 99", test.getPhoneNumber());
        Assertions.assertEquals("peter@sj.se", test.getEmail());
    }

    @Test
    void parseTime() throws ParseException {

        Assertions.assertEquals("19:50", service.parseTimeToHHMM("19:50:51"));
    }


    @Test
    void parseTimesInTimeslotArray() throws ParseException {

        ArrayList<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(new Timeslot(1,2,"2019-01-01","15:20:00","15:25:00"));

        Assertions.assertEquals("15:20", service.parseTimeslotTimesToHHMM(timeslots).get(0).getStartTime());

    }



    @Test
    void createNewTimeslot() {
        int numberOfEmptyTimeslots = repo.numberOfEmptyTimeslots();
        int id = repo.newTimeslot(4, "2040-01-30", "21:00:00", "22:00:00");
        System.out.println("\n--- A new timeslot with id nr " + id + " is successfully created ---\n");
        Assertions.assertEquals(numberOfEmptyTimeslots + 1, repo.numberOfEmptyTimeslots());
    }




    @Test
    void createNewTimeslotAndCheckIfNewItAddedOnThatDate() {
        String date = "2040-01-30";
        int numberOfEmptyTimeslotsThatDay = repo.getEmptyTimeslotsOnDate(date).size();
        int id = repo.newTimeslot(4, date, "21:00:00", "22:00:00");
        System.out.println("\n--- A new timeslot with id nr " + id + " is successfully created ---\n");
        Assertions.assertEquals(numberOfEmptyTimeslotsThatDay + 1, repo.getEmptyTimeslotsOnDate(date).size());
    }


    @Test
    void twoWaysOfCountingNumberOfEmptyTimeslots() {
        Assertions.assertEquals(repo.getEmptyTimeslots().size(), repo.numberOfEmptyTimeslots());
    }


    @Test
    void addNewCustomerToNewEmptyTimeslotAndCreateNewBooking() {

        // Testet är godkänt när antalet bookings har ökat med 1

        int numberOfBookings = repo.numberOfBookings();

        int newCustomerId = repo.addNewCustomer(new Customer(8805050375L, "Simon", "Stark", null, null));
        int newTimeslotId = repo.newTimeslot(1, "2022-02-28", "13:35:00", "14:15:00");
        System.out.println("New customerID: " + newCustomerId);
        System.out.println("New TimeslotID: " + newTimeslotId);

        int newBookingID = repo.newBooking(newCustomerId, newTimeslotId);

        System.out.println("\n--- A new booking with id nr " + newBookingID + " is successfully created ---\n");

        Assertions.assertEquals(numberOfBookings + 1, repo.numberOfBookings());

    }

    @Test
    void hideDuplicateTimeslots() {

        // First: Call method with parameter of size 0

        ArrayList<Timeslot> timeslots = new ArrayList<>();
        ArrayList<Timeslot> result = service.hideDuplicateTimeslots(timeslots);

        Assertions.assertEquals(0, result.size());

        // Second: Add three new timeslots. Does the method remove the second object where there is a duplicate starttime?

        int num = repo.getEmptyTimeslotsOnDate("1918-01-28").size();

        repo.newTimeslot(1, "1918-01-28", "13:35:00", "14:15:00");
        repo.newTimeslot(1, "1918-01-28", "13:35:00", "14:15:00");
        repo.newTimeslot(1, "1918-01-28", "14:35:00", "14:15:00");


        Assertions.assertEquals(num + 2, service.hideDuplicateTimeslots(repo.getEmptyTimeslotsOnDate("1918-01-28")).size());


    }

    @Test
    void randomTimeslotGeneratorTest() throws ParseException {
        int num = repo.numberOfEmptyTimeslots();
        service.generateTimeslots(0);
        Assertions.assertEquals(repo.numberOfEmptyTimeslots() != num, repo.numberOfEmptyTimeslots() > num);

    }

}
