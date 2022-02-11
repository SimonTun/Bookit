package com.bookit.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookitApplicationTests {

    @Autowired
    private Repository repo;

    @Autowired
    private BookitService service;


    @Test
    void addNewTimeslotAndConfirmEntries() {
        int id = repo.newTimeslot(2, "2057-01-30","23:00:00", "23:45:00");

        System.out.println("\n--- A new timeslot with id nr " + id + " is successfully created ---\n");

        Timeslot test = repo.getTimeslot(id);

        Assertions.assertEquals(2, test.getEmployeeId());
        Assertions.assertEquals("2057-01-30", test.getDate());
        Assertions.assertEquals("23:00:00", test.getStartTime());
        Assertions.assertEquals("23:45:00", test.getEndTime());
    }

    @Test
    void addNewCustomerAndConfirmEntries() {
        int id = repo.addNewCustomer(new Customer(6505050101L, "Peter","DenStore", "040-77 88 99","peter@sj.se" ));

        System.out.println("\n--- A new customer with id nr " + id + " is successfully created ---\n");

        Customer test = repo.getCustomer(id);

        Assertions.assertEquals(6505050101L, test.getCustomerNumber());
        Assertions.assertEquals("Peter", test.getFirstName());
        Assertions.assertEquals("DenStore", test.getLastName());
        Assertions.assertEquals("040-77 88 99", test.getPhoneNumber());
        Assertions.assertEquals("peter@sj.se", test.getEmail());
    }

    @Test
    void createNewTimeslot(){
        int numberOfEmptyTimeslots = repo.numberOfEmptyTimeslots();
        int id = repo.newTimeslot(4, "2040-01-30","21:00:00", "22:00:00");
        System.out.println("\n--- A new timeslot with id nr " + id + " is successfully created ---\n");
        Assertions.assertEquals(numberOfEmptyTimeslots +1, repo.numberOfEmptyTimeslots());
    }


    @Test
    void createNewTimeslotAndCheckIfNewItAddedOnThatDate(){
        String date = "2040-01-30";
        int numberOfEmptyTimeslotsThatDay = repo.getEmptyTimeslotsOnDate(date).size();
        int id = repo.newTimeslot(4, date,"21:00:00", "22:00:00");
        System.out.println("\n--- A new timeslot with id nr " + id + " is successfully created ---\n");
        Assertions.assertEquals(numberOfEmptyTimeslotsThatDay +1, repo.getEmptyTimeslotsOnDate(date).size());
    }



    @Test
    void twoWaysOfCountingNumberOfEmptyTimeslots() {
        Assertions.assertEquals(repo.getEmptyTimeslots().size(), repo.numberOfEmptyTimeslots());
    }

    @Test
    void testday() {
        Assertions.assertEquals("2022-02-11", service.getTodaysDate());

    }

    @Test
    void addNewCustomerToNewEmptyTimeslotAndCreateNewBooking() {

        // Testet är godkänt när antalet bookings har ökat med 1

        int numberOfBookings = repo.numberOfBookings();

        int newCustomerId = repo.addNewCustomer(new Customer(8805050375L, "Simon","Stark", null,null ));
        int newTimeslotId = repo.newTimeslot(1, "2022-02-28", "13:35:00", "14:15:00");
        System.out.println("New customerID: " + newCustomerId);
        System.out.println("New TimeslotID: " + newTimeslotId);

        int newBookingID = repo.newBooking(newCustomerId, newTimeslotId);

        System.out.println("\n--- A new booking with id nr " + newBookingID + " is successfully created ---\n");


        Assertions.assertEquals(numberOfBookings + 1,repo.numberOfBookings());

    }

}
