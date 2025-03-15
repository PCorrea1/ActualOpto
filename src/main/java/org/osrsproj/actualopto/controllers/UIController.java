package org.osrsproj.actualopto.controllers;

import org.osrsproj.actualopto.services.OptimalGear;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {

    @GetMapping("/")
    public String showForm(Model model) {
        return "index";
    }

    @PostMapping("/recommend")
    public String handleFormSubmission(
            @RequestParam("username") String username,
            @RequestParam("bossName") String bossName,
            @RequestParam("budget") String budget,
            Model model) {

        System.out.println("Username: " + username);
        System.out.println("Boss Name: " + bossName);
        System.out.println("Budget: " + budget);

        String response = OptimalGear.runOptimalGear(username, bossName, budget);

        model.addAttribute("recommendation", "Gear: Dragon Scimitar (placeholder)");
        return "index";
    }
}
