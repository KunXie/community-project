package com.petprojects.community.controller;

import com.petprojects.community.dto.SignInForm;
import com.petprojects.community.dto.SignUpForm;
import com.petprojects.community.dto.ValidationInput;
import com.petprojects.community.entity.User;
import com.petprojects.community.provider.EmailProvider;
import com.petprojects.community.provider.UtilProvider;
import com.petprojects.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

    private final UserService userService;
    private final EmailProvider emailProvider;

    @Autowired
    public AuthenticationController(UserService userService, EmailProvider emailProvider) {
        this.userService = userService;
        this.emailProvider = emailProvider;
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
        BeanUtils.copyProperties(signUpForm, user);
        ;

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
            model.addAttribute("signInError", "email address and password doesn't match.");
            return "signIn"; // 使用redirect, model 就不能用了
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

    @GetMapping("/validation-code")
    @ResponseBody
    public ResponseEntity<Void> sendValidationCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // set validationCode
        String validationCode = emailProvider.sendValidationCode(user.getEmailAddress());
        session.setAttribute("validationCode", validationCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/validation-code",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> checkValidationCode(@RequestBody ValidationInput validationInput, // 上面的signIn method 不适用与@RequestBody
                                                   HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        HttpSession session = request.getSession();
        String validationCode = (String) session.getAttribute("validationCode");
        // TODO 这里的传输应该可以优化一下，不用map, 或许用status?
        if (validationCode.equals(validationInput.getValidationCode())) {
            result.put("result", "true");
        } else {
            result.put("result", "false");
        }
        return result;
    }

    @PostMapping("/update")
    public String updateUserInfo(User modifiedUser, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // TODO so far, you can only update first/lastname, username & password, avatarUrl will be supported in the future.
        UtilProvider.copyProperties(modifiedUser, user, Arrays.asList("firstname", "lastname", "username"));
        if (modifiedUser.getPassword() != null && modifiedUser.getPassword().length() > 0) {
            user.setPassword(modifiedUser.getPassword());
        }
        user.setGmtModified(System.currentTimeMillis());
        userService.update(user);
        // addAttribute  参数会显示在url上
        // addFlashAttribute Url参数不会显示在url上，redirect之后会马上删掉参数
        redirectAttributes.addFlashAttribute("tab", "personal-info");
        return "redirect:/profile";
    }
}
