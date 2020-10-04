package com.petprojects.community.controller;

import com.petprojects.community.dto.PostDraft;
import com.petprojects.community.entity.Post;
import com.petprojects.community.entity.TagType;
import com.petprojects.community.entity.User;
import com.petprojects.community.provider.PostProvider;
import com.petprojects.community.repository.TagTypeRepository;
import com.petprojects.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final TagTypeRepository tagTypeRepository;
    private final PostService postService;
    private final PostProvider postProvider;

    @Autowired
    public PostController(TagTypeRepository tagTypeRepository, PostService postService, PostProvider postProvider) {
        this.tagTypeRepository = tagTypeRepository;
        this.postService = postService;
        this.postProvider = postProvider;
    }

    @GetMapping("/new") // create a new post
    public String newPost(Model model) {
        List<TagType> tagTypes = tagTypeRepository.findAll();
        model.addAttribute("tagTypes", tagTypes);
        model.addAttribute("postDraft", new PostDraft());
        return "edit";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable(name = "id") Integer postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/edit/{id}") // modify an existing post whose id = {id}
    public String editExitingPost(Model model, @PathVariable(name = "id") Integer postId) {
        List<TagType> tagTypes = tagTypeRepository.findAll();
        model.addAttribute("tagTypes", tagTypes);

        // TODO 逻辑上这里不会出现null的情况, 除非数据库数据异常, 未来可能需要优化
        Post post = postService.findById(postId);
        PostDraft postDraft = postProvider.convertToPostDraft(post);
        model.addAttribute("postDraft", postDraft);
        return "edit";
    }

    @PostMapping("/save") // update or save post
    private String savePost(PostDraft postDraft, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postProvider.convertToPost(postDraft, user);
        postService.save(post);
        return "redirect:/profile";
    }

}
