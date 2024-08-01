package com.liveCodeTodoList.liveCodeTodo.service;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.Todo;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoStatusUpdateRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    Todo create(TodoRequestDTO request);
    Page<Todo> getAll(
            Pageable pageable,
            TaskStatus status,
            String sortBy,
            String order
    );

    Todo getOne(Integer id);

    Todo update(TodoUpdateRequestDTO request, Integer id);
    Todo updateStatus(TodoStatusUpdateRequestDTO request, Integer id);

    void delete(Integer id);
}
