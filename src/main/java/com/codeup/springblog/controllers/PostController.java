package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String viewPosts(){
        return "blog insert";
    }
    @GetMapping("/posts/{id}")
    @ResponseBody
    public String singlePost(@PathVariable long id){
        return "View one post";
    }
    @GetMapping("/posts/create")
    @ResponseBody
    public String createForm(){
        return "view form to create post";
    }
    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    @ResponseBody
    public String createPost(){
        return "Creates new post";
    }
}
