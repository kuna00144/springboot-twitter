package com.example.springboot_twitter.post;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void increaseCommentCount() {
        Post post = Post.builder().build();
        post.increaseCommentCount();

        assertEquals(1, post.getCommentCount());
    }
}