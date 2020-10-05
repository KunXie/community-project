package com.petprojects.community.controller;

import com.petprojects.community.entity.Post;
import com.petprojects.community.entity.PostList;
import com.petprojects.community.entity.TagCountView;
import com.petprojects.community.enums.IndexTabEnum;
import com.petprojects.community.repository.PostListRepository;
import com.petprojects.community.repository.PostRepository;
import com.petprojects.community.repository.TagCountViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    private final PostRepository postRepository;
    private final PostListRepository postListRepository;
    private final TagCountViewRepository tagCountViewRepository;

    @Autowired
    public IndexController(PostRepository postRepository, PostListRepository postListRepository, TagCountViewRepository tagCountViewRepository) {
        this.postRepository = postRepository;
        this.postListRepository = postListRepository;
        this.tagCountViewRepository = tagCountViewRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<TagCountView> tagCountViews = tagCountViewRepository.findTop10ByOrderByTagCountDesc();
        model.addAttribute("recommendedTags", tagCountViews);
        return "index";
    }

    @CrossOrigin("http://localhost:63342")/*TODO Debug*/
    @ResponseBody
    @GetMapping(value = "/posts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PostList> getPostList(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNumber,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                      @RequestParam(value = "section", defaultValue = "interesting") String section) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("viewCount").descending().and(Sort.by("likeCount").descending()));
        Page<PostList> postLists;
        if (section.equals(IndexTabEnum.HOT.getValue())) {
            postLists = postListRepository.findAll(pageable);
        } else if (section.equals(IndexTabEnum.WEEK.getValue())) {
            postLists = postListRepository.findAllByGmtModifiedGreaterThanEqual(System.currentTimeMillis() - 7 * 1000 * 24 * 60 * 60L, pageable);
        } else if (section.equals(IndexTabEnum.MONTH.getValue())) {
            postLists = postListRepository.findAllByGmtModifiedGreaterThanEqual(System.currentTimeMillis() - 30 * 1000 * 24 * 60 * 60L, pageable);
        } else { // "interesting"
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("gmtModified").descending().and(Sort.by("replyCount").descending()));
            postLists = postListRepository.findAll(pageable);
        }
        return postLists;
    }
}
