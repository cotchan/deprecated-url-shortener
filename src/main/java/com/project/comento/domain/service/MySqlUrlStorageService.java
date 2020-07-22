package com.project.comento.domain.service;

import com.project.comento.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MySqlUrlStorageService extends JpaRepository<Url, Long> {

    @Query("SELECT u FROM Url u WHERE u.originalUrl = ?1")
    Url findByOriginalUrl(String originalUrl);

    @Query("SELECT u FROM Url u WHERE u.shortUrl = ?1")
    Url findByShortUrl(String shortUrl);
}
