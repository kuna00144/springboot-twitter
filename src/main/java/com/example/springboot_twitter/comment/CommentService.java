package com.example.springboot_twitter.comment;


import com.example.springboot_twitter.post.Post;
import com.example.springboot_twitter.post.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentResponse createComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));

        Comment comment = Comment.builder()
                .content(request.content())
                .post(post)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Comment newComment = commentRepository.save(comment);

//        if (Objects.equals(postId, 20L)) {
//            throw new RuntimeException("애플리케이션 오류 발생");
//        }

        post.increaseCommentCount();


        return CommentResponse.from(newComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByIdDesc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }


    public CommentResponse updateComment(Long postId, Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        comment.updateContent(request.content());



        return CommentResponse.from(comment);
    }


    public void deleteComment(Long postId, Long commentId) {

        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        Post post = comment.getPost();
        post.decreaseCommentCount();

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments() {

        return commentRepository.findAll()
                .stream()
                .map(comment -> {
                    Post post = comment.getPost();
                    log.info("댓글 ID: {}, 게시글 ID: {}, 게시글 내용: {}", comment.getId(), post.getId(), post.getContent());
                    return CommentResponse.from(comment);
                })
                .toList();
        
    }
}
