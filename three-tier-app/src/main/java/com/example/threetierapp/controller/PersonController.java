package com.example.threetierapp.controller;

import com.example.threetierapp.model.Person;
import com.example.threetierapp.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public String list(Model model) {
        long startTime = System.currentTimeMillis();
        model.addAttribute("people", personService.getAllPersons());
        long endTime = System.currentTimeMillis();
        
        model.addAttribute("loadTime", endTime - startTime);
        model.addAttribute("cacheStatus", personService.getCacheStatus());
        return "list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("person", new Person());
        return "form";
    }

    @PostMapping("/save")
    public String save(Person person, RedirectAttributes redirectAttributes) {
        if (person.getName() == null || person.getName().isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Name is required");
            return "redirect:/new";
        }

        if (person.getEmail() == null || person.getEmail().isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email is required");
            return "redirect:/new";
        }

        if (personService.existsByEmail(person.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already exists");
            return "redirect:/new";
        }

        personService.savePerson(person);
        redirectAttributes.addFlashAttribute("successMessage", "Person added successfully!");
        return "redirect:/new";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        personService.deletePerson(id);
        return "redirect:/";
    }
}