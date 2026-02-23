package site.danilomoura.encurtadorurl.dto;

import site.danilomoura.encurtadorurl.entity.URLClick;

import java.time.Instant;
import java.util.List;

public record URLInfoResponse(
        Long id,
        String url,
        String slug,
        Instant expiresAt,
        Instant createdAt,
        List<URLClickResponse> urlClicks

) {
}
