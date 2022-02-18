package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;

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

    @GetMapping("/bookIt")
    public String bookIt(Model model, HttpSession session, @RequestParam(required = false) String date) throws ParseException {
        ArrayList<Timeslot> timeslots;





        if (date == null || date.length() == 0) {
            System.out.println("no date = today");
            System.out.println(date);
            System.out.println(service.getTodaysDate());


            timeslots = service.prepareTimeslotArrayForPresentationOnWeb(service.getTodaysDate());
        } else {
            timeslots = service.prepareTimeslotArrayForPresentationOnWeb(date);
            System.out.println("Else-date" +date);
        }

        boolean hasValues = timeslots.size() > 0;
        System.out.println(date);
        model.addAttribute("hasValues", hasValues);
        model.addAttribute("timeslots", timeslots);
        return "bookIt";
    }

    @GetMapping("/confirm")
    public String confirm(Model model, HttpSession session, @RequestParam() int id) throws ParseException {
        int bookingRequestId = (int) session.getAttribute("bookingRequestId");


        Timeslot timeslot = repository.getTimeslot(id);
        model.addAttribute("timeslot", timeslot);

        BookingContent bookingContent=repository.createBookingContent(id,bookingRequestId);

        model.addAttribute("bookingContent", bookingContent);
        return "confirm";
    }

    @GetMapping("/")
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
        int bookingRequestId= repository.addNewBookingRequestId(customerId);

        session.setAttribute("customerId", customerId);
        session.setAttribute("bookingRequestId",bookingRequestId);
        return "redirect:/subjects";
    }

    @GetMapping("/subjects")
    public String subjects(Model model, HttpSession session){
        List <Content> contents = new ArrayList<>();

        for (SUBJECT subject : SUBJECT.values()){
            contents.add(new Content(subject));
        }

        int customerId = (int) session.getAttribute("customerId");
        int bookingRequestId =(int)session.getAttribute("bookingRequestId");
        ContentHolder contentHolder = new ContentHolder(bookingRequestId,contents,"");

        model.addAttribute("contentHolder", contentHolder);
        return "subjectForm";
    }

    @PostMapping("/bookIt")
    public String allSubject (@ModelAttribute ContentHolder contentHolder) {
        repository.newContent(contentHolder);
        return "bookIt";
    }
}
