package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repository.AdRepository;
import com.codeup.springblog.repository.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdController {
    private final AdRepository adDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public AdController(AdRepository adDoa, UserRepository userDao, EmailService emailService){
        this.adDao = adDoa;
        this.userDao = userDao;
        this.emailService = emailService;
    }
    @GetMapping("/ads")
    public String index(Model model){
        model.addAttribute("ads", adDao.findAll());
        return "ads/index";
    }
    @GetMapping("/ads/{n}")
    public String viewOne(@PathVariable long n, Model model) {
        Ad ad = adDao.findById(n);
        model.addAttribute("ad", ad);
        Boolean isAdOwner = false;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser"){
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isAdOwner = currentUser.getId() == ad.getUser().getId();
        }
        model.addAttribute("ad", adDao.findById(n));
        model.addAttribute("isAdOwner", isAdOwner);
        return "ads/show";
    }

    @GetMapping("/ads/first/{title}")
    public String viewOneByTitle(@PathVariable String title, Model model) {
        Ad ad = adDao.findByTitle(title);
        model.addAttribute("ad", ad);
        return "ads/show";
    }

    @GetMapping("/ads/create")
    public String createAdForm(Model model){
        model.addAttribute("ad", new Ad());
        return "ads/create";
    }

    @PostMapping("ads/create")
    public String createAd(@RequestParam String title, @RequestParam String description) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(currentUser.getId());
        Ad ad = new Ad(title, description, user);
        adDao.save(ad);
        return "redirect:/ads";
    }

    @GetMapping("/ads/{id}/edit")
    public String editGet(@PathVariable long id, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ad ads = adDao.getById(id);
        if(currentUser.getId() == ads.getUser().getId()){
            model.addAttribute("ad", ads);
            return "/ads/edit";
        }else {
            return "redirect:/ads/" + id;
        }
    }

    @PostMapping("/ads/{id}/edit")
    public String editPost(@PathVariable long id, @RequestParam(name = "title") String title, @RequestParam(name = "description") String description){
        Ad editAd = adDao.getById(id);
        editAd.setTitle(title);
        editAd.setDescription(description);
        adDao.save(editAd);
        return "redirect:/posts";
    }

}
