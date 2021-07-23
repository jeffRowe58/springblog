package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao){
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String viewPosts(Model model){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("This is post1", "This is post1s body"));
        posts.add(new Post("This is post2", "This is post2s body"));
        model.addAttribute("posts", posts);
        return "posts/index";

    }
    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model){
        Post post = new Post("Jeff buys bicycle", "No one knows why.");
        model.addAttribute("post", post);
        return "posts/show";
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

    @PostMapping("/posts/index/edit")
    public String editPost(Model model){
        return null;
    }
    @PostMapping("/posts/index/delete")
    public String deletePost(Model model){

    }
}
