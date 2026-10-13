package com.example.springboot_twitter.comment;

import java.time.LocalDateTime;

public record CommentResponse (
        Long id,
        String content,
        LocalDateTime createAt,
        LocalDateTime updateAt

){
    public static CommentResponse from(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
