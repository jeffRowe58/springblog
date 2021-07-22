package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    @GetMapping("/posts")
    public String viewPosts(Model model){
        List<Post> posts = new ArrayList<>();
        model.addAttribute("posts", posts);
        return "posts";
    }
    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model){
        Post post = new Post();
        model.addAttribute("post", post);
        return "post";
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
