package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao){
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/posts")
    public String viewPosts(Model model){
        List<Post> posts = postDao.findAll();

        model.addAttribute("posts", posts);
        return "posts/index";

    }
    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model){
        model.addAttribute("post", postDao.findById(id));
        return "posts/show";
    }



    @GetMapping(value = "/posts/create")
    public String createForm(){

        return "/posts/create";
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public String createPost(@RequestParam String title, @RequestParam String body){
        User user = userDao.findById(1L);
        Post post = new Post(title, body, user);
        postDao.save(post);

        return "redirect:/posts";
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
