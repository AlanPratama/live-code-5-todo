package com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {

    private Integer id;
    private String username;
    private String email;

}
