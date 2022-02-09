package com.bookit.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookitRestController {

    @Autowired
    private Repository repo;

    @GetMapping("/{id}")
    public Booking getBook(@PathVariable int id) {
        return repo.getBooking(id);
    }



}
