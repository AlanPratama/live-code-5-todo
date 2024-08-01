package com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoUpdateRequestDTO {

    @NotBlank(message = "Title Cannot Be Blank!")
    private String title;

    @NotBlank(message = "Description Be Blank!")
    private String description;

    @NotBlank(message = "Due Date Cannot Be Blank!")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Due Date must be in ISO 8601 format (yyyy-MM-dd)")
    private String dueDate;

    @NotNull(message = "Status Cannot Be Blank!")
    private TaskStatus status;
}
