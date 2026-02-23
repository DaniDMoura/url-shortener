package site.danilomoura.encurtadorurl.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import site.danilomoura.encurtadorurl.dto.URLInfoResponse;
import site.danilomoura.encurtadorurl.dto.URLRequest;
import site.danilomoura.encurtadorurl.dto.URLResponse;
import site.danilomoura.encurtadorurl.service.URLService;

import java.net.URI;


@RestController
public class URLController {

    private final URLService urlService;

    public URLController(URLService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<URLResponse> createShortURL(@RequestBody @Valid URLRequest urlRequest) {

        String slug = urlService.createShortURL(urlRequest);

        String shortUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(slug)
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new URLResponse(shortUrl));
    }

    @GetMapping("/{slug}/info")
    public ResponseEntity<URLInfoResponse> getURLInfo(@PathVariable String slug) {
        return ResponseEntity.ok(
                urlService.getURLInfo(slug)
        );
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Void> redirect(@PathVariable String slug, HttpServletRequest request) {
        String url = urlService.getUrlBySlug(slug, request);
        URI location = URI.create(url);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(location)
                .build();
    }
}
