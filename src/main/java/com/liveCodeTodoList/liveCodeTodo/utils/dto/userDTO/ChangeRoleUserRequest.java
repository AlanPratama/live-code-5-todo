package com.liveCodeTodoList.liveCodeTodo.utils.dto.userDTO;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.UserRole;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapErrors;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleUserRequest {

    @NotNull(message = "Role Cannot Be Null!")
    private UserRole role;

}
