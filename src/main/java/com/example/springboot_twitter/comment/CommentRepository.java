package com.example.springboot_twitter.comment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByIdDesc(Long postId);

    Optional<Comment> findByIdAndPostId(Long commentId, Long postId);

    @EntityGraph(attributePaths = "post", type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> findAll();

}
