package com.petprojects.community.controller;

import com.petprojects.community.entity.PrimaryReply;
import com.petprojects.community.entity.SubReply;
import com.petprojects.community.entity.User;
import com.petprojects.community.repository.PrimaryReplyRepository;
import com.petprojects.community.repository.SubReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reply")
public class ReplyController {
    private final PrimaryReplyRepository primaryReplyRepository;
    private final SubReplyRepository subReplyRepository;

    @Autowired
    public ReplyController(PrimaryReplyRepository primaryReplyRepository, SubReplyRepository subReplyRepository) {
        this.primaryReplyRepository = primaryReplyRepository;
        this.subReplyRepository = subReplyRepository;
    }

    @PostMapping("/primary") // save primary reply
    public String savePrimaryReply(PrimaryReply primaryReply) {
        /*TODO DEBUG*/System.out.println("reply: " + primaryReply);

        primaryReply.setGmtCreated(System.currentTimeMillis());
        primaryReply.setGmtModified(System.currentTimeMillis());
        primaryReply.setReplyCount(0);
        primaryReply.setLikeCount(0);
        primaryReplyRepository.save(primaryReply);
        return "redirect:/post/" + primaryReply.getPost().getId();
    }

    @PostMapping("/sub")
    public String saveSubReply(
            @RequestParam(value = "primaryReplyId") Integer primaryReplyId,
            SubReply subReply) {
        PrimaryReply primaryReply = primaryReplyRepository.getOne(primaryReplyId);
        subReply.setGmtCreated(System.currentTimeMillis());
        subReply.setGmtModified(System.currentTimeMillis());
        subReply.setReplyCount(0);
        subReply.setLikeCount(0);
        subReply.setPrimaryReply(primaryReply);
        subReplyRepository.save(subReply);
        return "redirect:/post/" + primaryReply.getPost().getId();
    }
}
