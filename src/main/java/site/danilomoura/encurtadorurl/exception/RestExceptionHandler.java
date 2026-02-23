package site.danilomoura.encurtadorurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(URLException.class)
    public ResponseEntity<ProblemDetail> handleLinkException(URLException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.toProblemDetail());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<Map<String, String>> fieldErrors = exception.getFieldErrors().stream()
                .map(error -> Map.of("name", error.getField(), "reason", error.getDefaultMessage()))
                .toList();


        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("fieldErrors", fieldErrors);


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
