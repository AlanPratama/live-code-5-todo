package com.liveCodeTodoList.liveCodeTodo.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    public static <T> ResponseEntity<?> renderJson(T data, String message, HttpStatus httpStatus) {
        WebResponse<?> response = WebResponse.builder()
                .message(message)
                .status(httpStatus)
                .data(data)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> renderJson(T data, String message) {
        return renderJson(data, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<?> renderJson(T data) {
        return renderJson(data, "Success");
    }

}
