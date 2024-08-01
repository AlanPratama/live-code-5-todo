package com.liveCodeTodoList.liveCodeTodo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponseError<T> {
    private String message;
    private HttpStatus status;
    private List<String> errors = new ArrayList<>();
}
