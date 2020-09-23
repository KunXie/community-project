package com.petprojects.community.controller;

import com.petprojects.community.enums.ProfileTabEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping // TODO 这里的personal-info以后会改掉 成question
    public String profile(@RequestParam(value = "tab", defaultValue = "personal-info") String tab,
                          Model model) {
        model.addAttribute("tab", tab);
        return "profile";
    }

}
