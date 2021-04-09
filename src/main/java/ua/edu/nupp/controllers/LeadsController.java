package ua.edu.nupp.controllers;

import ua.edu.nupp.models.*;
import ua.edu.nupp.dao.LeadDAO;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/leads")
public class LeadsController {

    private final LeadDAO leadDAO;

    @Autowired
    public LeadsController(LeadDAO leadDAO) {
        this.leadDAO = leadDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("leads", leadDAO.index());
        return "leads/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("lead", leadDAO.show(id));
        return "leads/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("lead") Lead lead) {
        return "leads/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("lead") @Valid Lead lead,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "leads/new";

        leadDAO.save(lead);
        return "redirect:/leads";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("lead", leadDAO.show(id));
        return "leads/edit";
    }
    
    
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("lead") @Valid Lead lead, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "leads/edit";

        leadDAO.update(id, lead);
        return "redirect:/leads";
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        leadDAO.delete(id);
        return "redirect:/leads";
    }
}