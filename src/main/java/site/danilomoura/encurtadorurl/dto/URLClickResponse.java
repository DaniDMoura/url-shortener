package site.danilomoura.encurtadorurl.dto;

import java.time.Instant;

public record URLClickResponse(
        Long id,
        String ip,
        String userAgent,
        String referer,
        String acceptLanguage,
        Instant clickedAt
) {
}
