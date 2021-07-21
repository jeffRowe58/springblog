package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class Roll_DiceController {
    @GetMapping(value = "/roll-dice")
    public String diceRoll(){
        return "roll-dice";
    }

    @GetMapping("/roll-dice/${num}")
    @ResponseBody
    public String numberGuess (@PathVariable int num){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        if(num == randomNum){
            String rightGuess = "<h1>You guess correct</h1>";
            return rightGuess;
        }else{
            String wrongGuess = "<h1>You guessed " + String.valueOf(num) + " the correct number was " + String.valueOf(randomNum) + ". Please try again.</h1>";
            return wrongGuess;
        }

    }

}
