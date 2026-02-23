package site.danilomoura.encurtadorurl.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_urls")
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<URLClick> urlClicks;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    public URL() {
    }

    public URL(String url, String slug, Instant expires_at) {
        this.url = url;
        this.slug = slug;
        this.expiresAt = expires_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<URLClick> getUrlClicks() {
        return urlClicks;
    }

    public void setUrlClicks(List<URLClick> urlClicks) {
        this.urlClicks = urlClicks;
    }

    public void addUrlClicks(URLClick urlClick) {
        if (urlClicks.isEmpty()) {
            urlClicks = new ArrayList<>();
        }

        urlClicks.add(urlClick);
    }
}
