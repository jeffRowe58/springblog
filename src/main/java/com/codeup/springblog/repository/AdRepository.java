package com.codeup.springblog.repository;

import com.codeup.springblog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdRepository extends JpaRepository<Ad, Long> {
    Ad findById(long id);

    @Query("from Ad a where a.title like %:term%")
    Ad findByTitle(String term);

}
