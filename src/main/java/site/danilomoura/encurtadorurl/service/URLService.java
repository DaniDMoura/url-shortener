package site.danilomoura.encurtadorurl.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import site.danilomoura.encurtadorurl.dto.URLClickResponse;
import site.danilomoura.encurtadorurl.dto.URLInfoResponse;
import site.danilomoura.encurtadorurl.dto.URLRequest;
import site.danilomoura.encurtadorurl.entity.URL;
import site.danilomoura.encurtadorurl.entity.URLClick;
import site.danilomoura.encurtadorurl.exception.URLExpiredException;
import site.danilomoura.encurtadorurl.exception.URLNotFoundException;
import site.danilomoura.encurtadorurl.repository.URLClickRepository;
import site.danilomoura.encurtadorurl.repository.URLRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


@Service
public class URLService {

    private static final Duration EXPIRATION_DURATION = Duration.ofDays(1);

    private final URLRepository urlRepository;
    private final URLClickRepository urlClickRepository;

    public URLService(URLRepository urlRepository, URLClickRepository urlClickRepository) {
        this.urlRepository = urlRepository;
        this.urlClickRepository = urlClickRepository;
    }


    @Transactional
    public String createShortURL(URLRequest urlRequest) {
        String slug = generateShortRandomSlug();
        Instant expires_at = Instant.now().plus(EXPIRATION_DURATION);

        URL newUrl = new URL(
                urlRequest.url(),
                slug,
                expires_at
        );

        URL url = urlRepository.save(newUrl);

        return url.getSlug();
    }

    public URLInfoResponse getURLInfo(String slug) {
        URL url = urlRepository.getBySlug(slug)
                .orElseThrow(URLNotFoundException::new);

        List<URLClickResponse> urlClicks = url.getUrlClicks().stream()
                .map(click -> new URLClickResponse(
                        click.getId(),
                        click.getIp(),
                        click.getUserAgent(),
                        click.getReferer(),
                        click.getAcceptLanguage(),
                        click.getClickedAt()))
                .toList();


        return new URLInfoResponse(
                url.getId(),
                url.getUrl(),
                url.getSlug(),
                url.getExpiresAt(),
                url.getCreatedAt(),
                urlClicks
        );
    }

    public String getUrlBySlug(String slug, HttpServletRequest request) {
        URL url = urlRepository.getBySlug(slug)
                .orElseThrow(URLNotFoundException::new);

        logClick(url, request);

        if (url.getExpiresAt().isBefore(Instant.now())) {
            throw new URLExpiredException();
        }

        return url.getUrl();
    }

    private void logClick(URL url, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");
        String acceptLanguage = request.getHeader("Accept-Language");

        urlClickRepository.save(
                new URLClick(
                        ip,
                        userAgent,
                        referer,
                        acceptLanguage,
                        url
                )
        );
    }

    private String generateShortRandomSlug() {
        return RandomStringUtils.randomAlphanumeric(5, 11);
    }
}
