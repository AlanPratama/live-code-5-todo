package com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class SingleTodoResponseDTO {
    private Integer id;
    private Integer userId;
    private String username;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private LocalDate createdAt;
}
