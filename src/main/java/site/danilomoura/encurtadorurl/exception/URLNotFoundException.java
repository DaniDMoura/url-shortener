package site.danilomoura.encurtadorurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class URLNotFoundException extends URLException {
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("URL Not Found");
        problemDetail.setDetail("The URL with the given slug does not exist.");
        return problemDetail;
    }
}
