package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {
    @GetMapping("/add/{x}/and/{y}")
    @ResponseBody
    public int addMethod(@PathVariable int x, @PathVariable int y){
        return x + y;
    }
    @GetMapping("/subtract/{x}/and/{y}")
    @ResponseBody
    public int subtractMethod(@PathVariable int x, @PathVariable int y){
        return x - y;
    }
    @GetMapping("/multiply/{x}/and/{y}")
    @ResponseBody
    public int productMethod(@PathVariable int x, @PathVariable int y){
        return x * y;
    }
    @GetMapping("/divide/{x}/and/{y}")
    @ResponseBody
    public int divideMethod(@PathVariable int x, @PathVariable int y){
        return x / y;
    }
}
