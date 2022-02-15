package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.Subject;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class BookItController {

    @Autowired
    Repository repository;

    @Autowired
    BookitService service;

    @GetMapping("/generate")
    public String generate(@RequestParam(required = false, defaultValue = "1") int num) throws ParseException {
        service.generateTimeslots(num);
        return "redirect:/";
    }

    @GetMapping("/")
    public String bookIt(Model model, @RequestParam(required = false) String date) throws ParseException {

        ArrayList<Timeslot> timeslots;

        if (date == null || date.length() == 0) {
            System.out.println("no date = today");
            timeslots = service.prepareTimeslotArrayForPresentationOnWeb(service.getTodaysDate());
        } else
            timeslots = service.prepareTimeslotArrayForPresentationOnWeb(date);

        boolean hasValues = timeslots.size() > 0;

        model.addAttribute("hasValues", hasValues);
        model.addAttribute("timeslots", timeslots);

        return "bookIt";

    }

    @GetMapping("/start")
    public String bookItStart() {

        return "startPage";

    }


    @GetMapping("/customer")
    public String privat(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }


    @PostMapping("/customerForm")
    public String privatForm (Model model, @ModelAttribute Customer customer, HttpSession session) {
        model.addAttribute("customer",customer);
         int customerId =repository.addNewCustomer(customer);
        int bookingId= repository.addNewBookingRequestId(customerId);

        session.setAttribute("customerId", customerId);
        return "confirmation";
    }


    @GetMapping("/subjects")
    public String subjects(Model model, HttpSession session){

        List <Content> contents = new ArrayList<>();
        for (SUBJECT subject : SUBJECT.values()){
            contents.add(new Content(subject));
        }
            int customerId = (int) session.getAttribute("customerId");
        BookingRequest bookingRequest = new BookingRequest(customerId,contents,"");

        model.addAttribute("bookingRequest", bookingRequest);

        return "subjectForm";
    }


    @PostMapping("/bookIt")
    public String allSubject (@ModelAttribute BookingRequest bookingRequest) {

    System.out.println(bookingRequest);


        return "confirmation";
    }

}
