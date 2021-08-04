package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repository.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repository.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
public class PostController {

    @Value("${file-upload-path}")
    private String uploadPath;

    @GetMapping("/fileupload")
    public String showUploadFileForm() {
        return "fileupload";}

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
    public String createPost(@RequestParam String title, @RequestParam String body, @RequestParam (name = "file") MultipartFile uploadedFile,
    @Valid Post validPost, Errors validation, Model model){

        if(validation.hasErrors()){
            model.addAttribute("errors", validation);
            model.addAttribute("post", validPost);
            return "/posts/create";
        }
        String filename = uploadedFile.getOriginalFilename();
        String filepath = Paths.get(uploadPath, filename).toString();
        File destinationFile = new File(filepath);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getById(currentUser.getId());
        if(!filename.isBlank()) {
            try {
                uploadedFile.transferTo(destinationFile);
                model.addAttribute("message", "File successfully uploaded!");
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Oops! Something went wrong! " + e);
            }
        }
        Post post = new Post(title, body, user, filepath);
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
