package com.liveCodeTodoList.liveCodeTodo.service;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.SingleTodoResponseDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoItemManagement {

    Page<TodoResponseDTO> getAll(
            Pageable pageable,
            TaskStatus status,
            String sortBy,
            String order
    );

    SingleTodoResponseDTO getOne(Integer id);


}
