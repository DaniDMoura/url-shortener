package site.danilomoura.encurtadorurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class URLExpiredException extends URLException {
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GONE);
        problemDetail.setTitle("URL Expired");
        problemDetail.setDetail("The URL with the given slug is no longer available");
        return problemDetail;
    }
}
