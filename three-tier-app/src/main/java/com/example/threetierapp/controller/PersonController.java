package com.example.threetierapp.controller;

import com.example.threetierapp.model.Person;
import com.example.threetierapp.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PersonController {

    private final PersonRepository repo;

    public PersonController(PersonRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("people", repo.findAll());
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

        if (repo.existsByEmail(person.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already exists");
            return "redirect:/new";
        }

        repo.save(person);
        redirectAttributes.addFlashAttribute("successMessage", "Person added successfully!");
        return "redirect:/new";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }
}
