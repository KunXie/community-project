package com.petprojects.community.controller;

import com.petprojects.community.dto.SignInForm;
import com.petprojects.community.entity.User;
import com.petprojects.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/sign-in")
    public String signIn(Model model) {
        model.addAttribute("signInForm", new SignInForm());
        return "signIn";
    }

    @PostMapping("/sign-in")
    public String signIn(SignInForm signInForm, // it doesn't understand it as a RequestBody
                         HttpServletRequest request,
                         HttpServletResponse response, Model model) {
        log.debug(signInForm.toString());
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
