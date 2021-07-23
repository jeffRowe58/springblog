package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.AdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AdController {
    private final AdRepository adDoa;

    public AdController(AdRepository adDoa){
        this.adDoa = adDoa;
    }
    @GetMapping("/ads")
    public String index(Model model){
        model.addAttribute("ads", adDoa.findAll());
        return "ads/index";
    }
    @GetMapping("/ads/{n}")
    public String viewOne(@PathVariable long n, Model model) {
        Ad ad = adDoa.findById(n);
        model.addAttribute("ad", ad);
        return "ads/show";
    }

    @GetMapping("/ads/first/{title}")
    public String viewOneByTitle(@PathVariable String title, Model model) {
        Ad ad = adDoa.findByTitle(title);
        model.addAttribute("ad", ad);
        return "ads/show";
    }
}
