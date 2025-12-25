package peaksoft.instaproject.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
public class SimpleResponse {
    HttpStatus httpStatus;
    String message;
}
