package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookitRestController {

    @Autowired
    private Repository repo;

    @GetMapping("/test/{id}")
    public Timeslot getBook(@PathVariable int id) {
        return repo.getTimeslot(id);
    }

    @GetMapping("/emptyTimeslots")
    public ArrayList<Timeslot> emptyTimeslots() {
        return repo.getEmptyTimeslots();
    }

    @GetMapping("/employeeTimeslots/{id}")
    public ArrayList<Timeslot> employeeTimeslots(@PathVariable int id) {
        return repo.getEmployeesBookings(id);
    }


}


