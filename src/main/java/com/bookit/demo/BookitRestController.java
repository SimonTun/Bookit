package com.bookit.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BookitRestController {

    @Autowired
    private Repository repo;

    @GetMapping("/{id}")
    public Timeslot getTimeslot(@PathVariable int id) {
        return repo.getTimeslot(id);
    }

    @GetMapping("/emptyTimeslots")
    public ArrayList<Timeslot> emptyTimeslots() {
        return repo.getEmptyTimeslots();
    }



}
