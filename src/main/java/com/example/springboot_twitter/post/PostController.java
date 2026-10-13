package com.example.springboot_twitter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController

public class PostController {
    private final PostRepository postRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/posts")

    public Post createPost(@RequestBody PostRequest request) {
        Post newPost = Post.builder()
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRepository.save(newPost);
        return newPost;
    }

    @GetMapping("/api/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow();
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        return postRepository.findById(id)
                .map(post -> {
                    post.updateContent(request.content());
                    return postRepository.save(post);
                }).orElseThrow();
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/api/posts/search")
    public Slice<Post> searchPosts(
            @RequestParam(required = false) Long lastPostId,
            @RequestParam(defaultValue = "3") int size
    ) {
        int page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (lastPostId == null) {
            return postRepository.findSlicesBy(pageable);
        } else {
            return postRepository.findSliceByIdLessThan(lastPostId, pageable);
        }
    }
}