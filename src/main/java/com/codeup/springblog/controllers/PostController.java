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
        List<Post> posts = postDao.findAll();

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

    @GetMapping("/posts/{id}/edit")
    public String editGet(@PathVariable long id, Model model){
        model.addAttribute("editPost", postDao.findById(id));
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, @RequestParam(name = "title") String title, @RequestParam(name = "body") String body){
        Post editPost = postDao.getById(id);
        editPost.setTitle(title);
        editPost.setBody(body);
        postDao.save(editPost);
        return "redirect:/posts";
    }
    @GetMapping("/posts/{id}/show")
    public String showEdit (@PathVariable long id, Model model){
        model.addAttribute("showEditId", id);
        return "redirect:/posts/{id}/show";
    }
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id, Model model){
        postDao.deleteById(id);
        return "redirect:/posts";
    }
}
