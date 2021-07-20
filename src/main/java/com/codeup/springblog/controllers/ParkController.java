package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Park;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ParkController {
    @GetMapping("/parks")
    public String showParks(Model model){
        Park gc = new Park("Grand Canyon");
        Park rm = new Park("Rocky Mountain National Park");

        List<Park> parks = new ArrayList<>();

        parks.add(gc);
        parks.add(rm);

        model.addAttribute("parks", parks);

        return "parks";
    }
}
