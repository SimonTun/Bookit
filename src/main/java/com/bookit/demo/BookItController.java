package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class BookItController {

    @Autowired
    Repository repository;

    @Autowired
    BookitService service;

    @GetMapping("/")
    public String bookIt(Model model, @RequestParam (required = false) String date) {

        ArrayList<Timeslot> timeslots = new ArrayList<>();



        if (date == null || date.length() == 0) {
            System.out.println("no date = today");
            timeslots = repository.getEmptyTimeslotsOnDate(service.getTodaysDate());
        }
        else
        timeslots = repository.getEmptyTimeslotsOnDate(date);



        model.addAttribute("timeslots", timeslots);


        return "bookIt";

    }

    @GetMapping("/start")
    public String bookItStart() {

        return "startPage";

    }


    @GetMapping("/customer")
    public String privat(Model model, HttpSession session) {
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }


    @PostMapping("/customerForm")
    public String privatForm (Model model, @ModelAttribute Customer customer) {
        model.addAttribute("customer",customer);
        int customerId =repository.addNewCustomer(customer);
       int bookingId= repository.addNewBookingRequestId(customerId);

        model.addAttribute("customerId",customerId);
        model.addAttribute("bookingId",bookingId);

        return "confirmation";
    }


    @GetMapping("/subjects")
    public String subjects(Model model, HttpSession session){

        model.addAttribute("contents", new Content());
      model.getAttribute("customerId");
        model.getAttribute("bookingId");

        return "subjectForm";
    }


    @PostMapping("/bookIt")
    public String allSubject (Model model, @ModelAttribute Content content) {

    System.out.println(content.id);
        System.out.println(content.bookingRequestId);
        System.out.println(content.getSubjects());
        System.out.println(content.getTextMessage());

        return "confirmation";
    }

    @GetMapping("/subjects")
    public String subjects(Model model, HttpSession session){

        model.addAttribute("contents", new Content());
        return "subjectForm";
    }

}
