package com.petprojects.community.provider;

import com.petprojects.community.dto.PostDraft;
import com.petprojects.community.entity.Post;
import com.petprojects.community.entity.Tag;
import com.petprojects.community.entity.User;
import com.petprojects.community.repository.PostRepository;
import com.petprojects.community.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This Utility component is created for conversions between post and postDraft
 */
@Component
public class PostProvider {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostProvider(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    /**
     * convert postDraft to post
     * when a new post is created or existing post is modified
     *
     * @param postDraft
     * @return
     */
    public Post convertToPost(PostDraft postDraft, User user) {
        Post post;
        // new post
        if (postDraft.getId() == null) {
            post = new Post();
            post.setViewCount(0);
            post.setLikeCount(0);
            post.setUser(user);
            post.setGmtCreated(System.currentTimeMillis());
        }
        // modify existing post
        else {
            // 这里逻辑上不会出现返回null的情况
            post = postRepository.findById(postDraft.getId()).get();
        }
        UtilProvider.copyProperties(postDraft, post, Arrays.asList("title", "content"));
        post.setGmtModified(System.currentTimeMillis());
        String[] tagNames = postDraft.getTagNames().split(", ");
        Set<Tag> tags = new HashSet<>();
        Arrays
                .stream(tagNames)
                .forEach(tagName -> tags.add(tagRepository.findTagByTagName(tagName)));
        post.setTags(tags);
        return post;
    }

    /**
     * convert post to postDraft
     * when existing post will be modified.
     *
     * @param post
     * @return
     */
    public PostDraft convertToPostDraft(Post post) {
        PostDraft postDraft = new PostDraft();
        UtilProvider.copyProperties(post, postDraft, Arrays.asList("id", "title", "content"));

        List<String> tagNameList
                = post
                .getTags()
                .stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());

        postDraft.setTagNames(String.join(", ", tagNameList));
        return postDraft;
    }
}
