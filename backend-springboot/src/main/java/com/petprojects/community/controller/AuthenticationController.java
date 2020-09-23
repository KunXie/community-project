package com.petprojects.community.controller;

import com.petprojects.community.dto.SignInForm;
import com.petprojects.community.dto.SignUpForm;
import com.petprojects.community.entity.User;
import com.petprojects.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        model.addAttribute("errorInfo", "");
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(SignUpForm signUpForm, Model model,
                         HttpServletRequest request, HttpServletResponse response) {

        // check whether email address already exists
        User user = userService.findUserByEmailAddress(signUpForm.getEmailAddress());
        if (user != null) {
            // 使用redirect model 的值传不过去
            model.addAttribute("errorInfo", "Email Already exists");
            return "signUp";
        }

        // create a user a save it.
        user = new User();
        // TODO BeanUtils.copyProperties(signUpForm, user); doesn't work
        BeanUtils.copyProperties(signUpForm, user);;

        user.setGmtCreated(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreated());
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        String avatarUrl = String.format("https://picsum.photos/id/%d/460/460", new Random().nextInt(100));
        user.setAvatarUrl(avatarUrl);
        userService.save(user);

        // set cookie and session attribute
        Cookie tokenCookie = new Cookie("token", token);
        response.addCookie(tokenCookie);
        request.getSession().setAttribute("user", user);
        return "redirect:/profile";
    }

    @GetMapping("/sign-in")
    public String signIn(Model model) {
        model.addAttribute("signInForm", new SignInForm());
        return "signIn";
    }

    @PostMapping("/sign-in")
    public String signIn(SignInForm signInForm, // it doesn't understand it as a RequestBody
                         HttpServletRequest request,
                         HttpServletResponse response, Model model) {
        User user = userService.findUserByEmailAddressAndPassword(
                signInForm.getEmailAddress(), signInForm.getPassword());

        if (user == null) {
            model.addAttribute("SignInError", "email address and password doesn't match.");
            return "redirect:/authentication/sign-in";
        }

        // check token, add it when necessary
        String token = user.getToken();
        if (token == null || token.length() == 0) {
            token = UUID.randomUUID().toString();
            userService.updateTokenByUser(token, user);
        }
        // add user attribute to session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        // add token to cookie
        Cookie tokenCookie = new Cookie("token", token);
        response.addCookie(tokenCookie);

        return "redirect:/profile";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {
        // delete user attribute from session
        request.getSession().removeAttribute("user");
        // delete token cookie from session
        Cookie tokenCookie = new Cookie("token", null);
        response.addCookie(tokenCookie);

        return "redirect:/";
    }
}
