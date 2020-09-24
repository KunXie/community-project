package com.petprojects.community.controller;

import com.petprojects.community.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping // TODO 这里的personal-info以后会改掉 成question
    public String profile( // tab 有三种参数 question, notification, personal-info
                           @RequestParam(value = "tab", defaultValue = "personal-info") String tab,
                           Model model,
                           HttpSession session) {

        model.addAttribute("tab", tab);
        model.addAttribute("user", session.getAttribute("user"));
        return "profile";
    }

}
