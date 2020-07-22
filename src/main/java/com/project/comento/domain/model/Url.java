package com.project.comento.domain.model;

import lombok.*;
import javax.persistence.*;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "urls")
public class Url {

    @Id
    private long uuid;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Column(name = "original_url", nullable = false, unique = true)
    private String originalUrl;

    @Builder
    public Url(final long uuid, final String shortUrl, final String originalUrl) {
        this.uuid = uuid;
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
    }

}
