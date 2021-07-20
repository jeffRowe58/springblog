package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "<h1>Hello from Spring!</h1>";
    }
    @GetMapping("/hello/{name}")
    @ResponseBody
    public String sayHello(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    @GetMapping("/number/{num}")
    @ResponseBody
    public int displayNumber(@PathVariable int num){
        return num;
    }
    @RequestMapping(path = "hello/in/{color}", method = RequestMethod.GET)
    @ResponseBody
    public String helloInColor(@PathVariable String color){
        return "<h1 style=\"color: " + color + "\">Hello in " + color + "!</h1>";
    }
}
