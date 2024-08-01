package com.liveCodeTodoList.liveCodeTodo.utils.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String error;
    private String message;


}
