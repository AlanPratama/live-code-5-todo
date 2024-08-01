package com.liveCodeTodoList.liveCodeTodo.utils.response;

import lombok.*;
import org.springframework.http.HttpStatus;

//@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private String message;
    private HttpStatus status;
    private T data;
}
