package com.liveCodeTodoList.liveCodeTodo.utils.advisers;

import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.NotFoundException;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.ValidateException;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapErrors;
import com.liveCodeTodoList.liveCodeTodo.utils.response.ErrorResponse;
import com.liveCodeTodoList.liveCodeTodo.utils.response.WebResponse;
import com.liveCodeTodoList.liveCodeTodo.utils.response.WebResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        WebResponseError<?> responseError = MapErrors.renderErrors("Failed!", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        return new ResponseEntity(new WebResponse<>("Not Found", HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity handleValidationException(ValidateException e) {
        return new ResponseEntity(new WebResponse("Request Tidak Sesuai", HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity(new WebResponse("Access Denied", HttpStatus.FORBIDDEN, e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity(new WebResponse("Unauthorized", HttpStatus.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}