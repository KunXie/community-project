package com.petprojects.community.controller;

import com.petprojects.community.entity.Post;
import com.petprojects.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class IndexController {

    private final PostRepository postRepository;

    @Autowired
    public IndexController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @CrossOrigin("http://localhost:63342")/*TODO Debug*/
    @ResponseBody
    @GetMapping(value = "/posts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> getPostList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer size,
                                  @RequestParam(value = "section", defaultValue = "interesting") String section) {
        // 这个page number 前端以1开头，后端以0开头
        Pageable pageable = PageRequest.of(pageNo-1, size, Sort.by("gmtModified").descending());
        return postRepository.findAll(pageable);
    }
}
