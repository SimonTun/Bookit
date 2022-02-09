package com.bookit.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookitApplicationTests {

    @Autowired
    private Repository repo;

    @Test
    void contextLoads() {
    }

    @Test
    void firstTest() {
        Booking test = repo.getBooking(1);
        Assertions.assertEquals("12:00:00", test.getStartTime());
    }

    @Test
    void AddNewEmptyBookingAndConfirmStartDate() {

//        Testar följande metoder:
//        getBooking(int id)
//        addEmptyBooking(Booking booking)
//        getEmptyBookings()

        int numberOfEmptyBookings = repo.getEmptyBookings().size();
        int id = repo.addEmptyBooking(new Booking(1, "2022-02-28", "13:35:00", "14:15:00"));

        Assertions.assertEquals(numberOfEmptyBookings + 1, repo.getEmptyBookings().size());
        Assertions.assertEquals("2022-02-28", repo.getBooking(id).getDate());
    }

    @Test
    void addNewCustomerToNewEmptyBooking() {

        // new newcustomer nedan funkar inte. Orsakar krasch

        int newCustomerId = repo.addNewCustomer(new Customer(8805050375L, "Simon","Stark", null,null ));
        int newBookingId = repo.addEmptyBooking(new Booking(1, "2022-02-28", "13:35:00", "14:15:00"));
        System.out.println("New customerID: " + newCustomerId);
        System.out.println("New BookingID: " + newBookingId);
        System.out.println("CustomerId from getBooking(): " + repo.getBooking(newBookingId).getCustomerId());


        repo.addCustomerToBooking(newCustomerId, newBookingId);

        Assertions.assertEquals(repo.getBooking(newBookingId).getCustomerId(),newCustomerId);


    }

}
