package com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoStatusUpdateRequestDTO {

    @NotNull(message = "Status Cannot Be Null!")
    private TaskStatus status;

}
