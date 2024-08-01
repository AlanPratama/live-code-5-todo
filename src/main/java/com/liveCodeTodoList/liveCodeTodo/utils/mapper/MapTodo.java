package com.liveCodeTodoList.liveCodeTodo.utils.mapper;

import com.liveCodeTodoList.liveCodeTodo.entity.meta.Todo;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.SingleTodoResponseDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoResponseDTO;

public class MapTodo {

    public static TodoResponseDTO MapToTodoResponseDTO(Todo todo) {
        return TodoResponseDTO.builder()
                .id(todo.getId())
                .userId(todo.getUser().getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .dueDate(todo.getDueDate())
                .status(todo.getStatus())
                .createdAt(todo.getCreatedAt())
                .build();
    }

    public static SingleTodoResponseDTO MapToSingleTodoResponseDTO(Todo todo) {
        return SingleTodoResponseDTO.builder()
                .id(todo.getId())
                .userId(todo.getUser().getId())
                .username(todo.getUser().getUsername())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .dueDate(todo.getDueDate())
                .status(todo.getStatus())
                .createdAt(todo.getCreatedAt())
                .build();
    }

}
