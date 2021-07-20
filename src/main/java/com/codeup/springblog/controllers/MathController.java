package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {
    @GetMapping("/{sign}/{x}/and/{y}")
    @ResponseBody
    public String mathMethod(@PathVariable String sign, @PathVariable int x, @PathVariable int y) {
        int res = 0;
        switch (sign) {
            case "add":
                res = x + y;
                break;
            case "subtract":
                res = x - y;
                break;
            case "multiply":
                res = x * y;
                break;
            case "divide":
                res = x / y;
                break;
        }
        return String.valueOf(res);
    }
}

