package peaksoft.instaproject.exception.handler;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.instaproject.dto.exceptionDTO.ExceptionResponse;
import peaksoft.instaproject.exception.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(AlreadyExistsException.class) //для обработки исключения в методах контролера
    @ResponseStatus(HttpStatus.CONFLICT) //409
    //@ResponseStatus - определяет, какой статус должен вернуться клиенту при выбросе исключений
    public ExceptionResponse handlerAlreadyExistsException(AlreadyExistsException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.CONFLICT).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ExceptionResponse handlerBadRequestException(BadRequestException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
    public ExceptionResponse handlerBadCredentialsException(BadCredentialsException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.UNAUTHORIZED).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ExceptionResponse handlerNoSuchElementException(NoSuchElementException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.NOT_FOUND).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ExceptionResponse handlerNotFoundException(NotFoundException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.NOT_FOUND).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(AccessIsDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ExceptionResponse handlerAccessIsDeniedException(AccessIsDeniedException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.FORBIDDEN).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(UserMismatchException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handelUserMismatchException(UserMismatchException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.FORBIDDEN).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    //for working our validation annotation:
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerUsernameNotFoundException(UsernameNotFoundException e) {
        return ExceptionResponse.builder().httpStatus((HttpStatus.NOT_FOUND)).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handlerJwtAuthenticationException(JwtAuthenticationException e) {
        return ExceptionResponse.builder().httpStatus(HttpStatus.UNAUTHORIZED).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }
}
