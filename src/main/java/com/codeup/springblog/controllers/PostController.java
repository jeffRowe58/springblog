package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String post(){
        return "blog insert";
    }
    @GetMapping("/posts/{id}")
    @ResponseBody
    public int postID(@PathVariable int id){
        return id;
    }
    @GetMapping("/posts/create")
    @ResponseBody
    public String postGetCreate(){
        return "Holding for get creation";
    }
    @GetMapping("/posts/create")
    @ResponseBody
    public String postPostCreate(){
        return "Placeholder";
    }
}
