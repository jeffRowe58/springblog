package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class Roll_DiceController {
    @GetMapping(value = "/roll-dice")
    public String diceRoll(){
        return "roll-dice";
    }

    @GetMapping(value ="/roll-dice/{num}")
    public String numberGuess (@PathVariable int num, Model model){
        int randomNum = (int) (Math.random() * ((6-1) + 1) +1);

        model.addAttribute("num", "This is your " + num);
        model.addAttribute("random", randomNum);
        model.addAttribute("correct", num == randomNum);

        if(num == randomNum){
            model.addAttribute("rightGuess", "You guessed " + num + " and that was correct");
        }else{
            model.addAttribute("wrongGuess", "You guessed " + num + " the correct number was " + randomNum + ". Please try again.");
        }
        return "roll-dice";
    }

}
