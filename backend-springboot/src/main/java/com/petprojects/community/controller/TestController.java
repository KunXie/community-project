package com.petprojects.community.controller;
/**
 * 用来测试的controller 以后会删除
 */

import com.petprojects.community.provider.EmailProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    private EmailProvider emailProvider;

    @Autowired
    public TestController(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    @GetMapping("/email/{emailAddress}")
    public String sendEmail(@PathVariable(value = "emailAddress") String emailAddress) {
        log.debug(emailAddress);
        return emailProvider.sendValidationCode(emailAddress);
    }
}
