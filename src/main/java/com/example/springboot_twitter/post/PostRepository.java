package com.example.springboot_twitter.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
    Slice<Post> findSlicesBy(Pageable pageable);

    Slice<Post> findSliceByIdLessThan(Long lastId, Pageable pageable);
}
