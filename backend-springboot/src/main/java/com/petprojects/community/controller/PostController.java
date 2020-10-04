package com.petprojects.community.controller;

import com.petprojects.community.dto.PostDraft;
import com.petprojects.community.entity.Post;
import com.petprojects.community.entity.PrimaryReply;
import com.petprojects.community.entity.TagType;
import com.petprojects.community.entity.User;
import com.petprojects.community.provider.PostProvider;
import com.petprojects.community.repository.PostRepository;
import com.petprojects.community.repository.PrimaryReplyRepository;
import com.petprojects.community.repository.TagTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final TagTypeRepository tagTypeRepository;
    private final PostRepository postRepository;
    private final PostProvider postProvider;
    private final PrimaryReplyRepository primaryReplyRepository;

    @Autowired
    public PostController(TagTypeRepository tagTypeRepository, PostRepository postRepository,
                          PostProvider postProvider, PrimaryReplyRepository primaryReplyRepository) {
        this.tagTypeRepository = tagTypeRepository;
        this.postRepository = postRepository;
        this.postProvider = postProvider;
        this.primaryReplyRepository = primaryReplyRepository;
    }

    @GetMapping("/new") // create a new post
    public String newPost(Model model) {
        List<TagType> tagTypes = tagTypeRepository.findAll();
        model.addAttribute("tagTypes", tagTypes);
        model.addAttribute("postDraft", new PostDraft());
        return "edit";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable(name = "id") Integer postId,
                           @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                           @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                           Model model) {
        Post post = postRepository.findById(postId).orElse(null);
        model.addAttribute("post", post);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("gmtModified").descending());
        Page<PrimaryReply> primaryReplies = primaryReplyRepository.findAllByPost(post, pageable);
        model.addAttribute("primaryReplies", primaryReplies);
        return "post";
    }

    @GetMapping("/edit/{id}") // modify an existing post whose id = {id}
    public String editExitingPost(Model model, @PathVariable(name = "id") Integer postId) {
        List<TagType> tagTypes = tagTypeRepository.findAll();
        model.addAttribute("tagTypes", tagTypes);

        // TODO 逻辑上这里不会出现null的情况, 除非数据库数据异常, 未来可能需要优化
        Post post = postRepository.findById(postId).orElse(null);
        PostDraft postDraft = postProvider.convertToPostDraft(post);
        model.addAttribute("postDraft", postDraft);
        return "edit";
    }

    @PostMapping("/save") // update or save post
    private String savePost(PostDraft postDraft, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postProvider.convertToPost(postDraft, user);
        postRepository.save(post);
        return "redirect:/profile";
    }

}
