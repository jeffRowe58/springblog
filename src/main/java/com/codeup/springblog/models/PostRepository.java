package com.codeup.springblog.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findById(long id);

    @Query("from Post a where a.title like %:term%")
    Post findByTitle(String term);

    void deleteById(long id);
}
