package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repository.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repository.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    private final UserRepository userDao;

    private final EmailService emailSvc;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailSvc){
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailSvc = emailSvc;
    }

    @GetMapping("/posts")
    public String viewPosts(Model model){
        List<Post> posts = postDao.findAll();

        model.addAttribute("posts", posts);
        return "posts/index";

    }
    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model){
        Post post = postDao.getById(id);
        Boolean isPostOwner = false;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser"){
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isPostOwner = currentUser.getId() == post.getUser().getId();
        }
        model.addAttribute("post", postDao.findById(id));
        model.addAttribute("isPostOwner", isPostOwner);
        return "posts/show";
    }



    @GetMapping(value = "/posts/create")
    public String createForm(Model model){
        model.addAttribute("post", new Post());
        return "/posts/create";
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public String createPost(@RequestParam String title, @RequestParam String body){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(currentUser.getId());
        Post post = new Post(title, body, user);
        postDao.save(post);
        emailSvc.prepareAndSend(post,"Your Post has been created", "Your post titled '" + title + "' has been create. Thanks for using our services", user.getEmail());
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editGet(@PathVariable long id, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.getById(id);
        if(currentUser.getId() == post.getUser().getId()){
            model.addAttribute("post", post);
            return "/posts/edit";
        }else {
            return "redirect:/posts/" + id;
        }
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
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.getById(id);
        if(currentUser.getId() == post.getUser().getId()){
            postDao.deleteById(id);
            return "redirect:/posts";
        }else
            return "redirect:/posts/" + id;
    }
}
