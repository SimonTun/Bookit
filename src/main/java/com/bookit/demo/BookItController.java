package com.bookit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BookItController {

    @Autowired
    Repository repository;

    @GetMapping("/")
    public String bookIt() {

        return "bookIt";

    }


    @GetMapping("/customer")
    public String privat(Model model, HttpSession session) {
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }


    @PostMapping("/customerForm")
    public String privatForm (Model model, @ModelAttribute Customer customer) {
        model.addAttribute("customer",customer);
        repository.addCustomer(customer);
        return "confirmation";
    }

}
