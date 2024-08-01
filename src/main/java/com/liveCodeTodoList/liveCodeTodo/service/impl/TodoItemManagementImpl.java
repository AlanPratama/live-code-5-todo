package com.liveCodeTodoList.liveCodeTodo.service.impl;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.Todo;
import com.liveCodeTodoList.liveCodeTodo.repository.TodoRepository;
import com.liveCodeTodoList.liveCodeTodo.service.TodoItemManagement;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.NotFoundException;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.SingleTodoResponseDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoResponseDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.mapper.MapTodo;
import com.liveCodeTodoList.liveCodeTodo.utils.specification.AdminTodoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoItemManagementImpl implements TodoItemManagement {
    private final TodoRepository todoRepository;

    @Override
    public Page<TodoResponseDTO> getAll(Pageable pageable, TaskStatus status, String sortBy, String order) {
        Specification<Todo> specification = AdminTodoSpecification.getSpecification(status, sortBy, order);
        Page<Todo> tempTodo = todoRepository.findAll(specification, pageable);
        List<TodoResponseDTO> todoResponseDTOList = tempTodo.stream()
                .map(MapTodo::MapToTodoResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(todoResponseDTOList, pageable, tempTodo.getNumberOfElements());
    }

    @Override
    public SingleTodoResponseDTO getOne(Integer id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Todo With ID " + id + " Is Not Found!"));

        return MapTodo.MapToSingleTodoResponseDTO(todo);
    }
}
