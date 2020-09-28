package com.petprojects.community.service;

import com.petprojects.community.entity.Post;
import com.petprojects.community.entity.Tag;
import com.petprojects.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * save new post
     */
    @Transactional
    public void save(Post post) {
        // TODO 我在post_tag上限制了外键，当post上id为空时，联表存储会触发外键的限制条件
        // 这样存储两遍，仅是权宜之计，以后再优化
//        if (post.getId() == null) { // a new post
//            Set<Tag> tags = post.getTags();
//            post.setTags(null);
//            post = postRepository.saveAndFlush(post);
//            /*debug*/
//            System.out.println("post with out tags: " + post.getId());
//            post.setTags(tags);
//        }
        postRepository.saveAndFlush(post);
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }
}
