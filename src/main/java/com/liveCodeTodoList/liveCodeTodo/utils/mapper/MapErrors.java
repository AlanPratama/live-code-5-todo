package com.liveCodeTodoList.liveCodeTodo.utils.mapper;

import com.liveCodeTodoList.liveCodeTodo.utils.response.WebResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class MapErrors {
    public static WebResponseError<?> renderErrors(String message, Errors errors) {
        WebResponseError<?> responseError = new WebResponseError<>();

        for (ObjectError error : errors.getAllErrors()) {
            responseError.getErrors().add(error.getDefaultMessage());
        }

        responseError.setStatus(HttpStatus.BAD_REQUEST);
        responseError.setMessage(message);

        return responseError;
    }

    public static WebResponseError<?> renderErrors(String message, String errors) {
        WebResponseError<?> responseError = new WebResponseError<>();

        responseError.getErrors().add(errors);
        responseError.setStatus(HttpStatus.BAD_REQUEST);
        responseError.setMessage(message);

        return responseError;
    }

}
