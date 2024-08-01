package com.liveCodeTodoList.liveCodeTodo.service.impl;

import com.liveCodeTodoList.liveCodeTodo.entity.enums.TaskStatus;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.Todo;
import com.liveCodeTodoList.liveCodeTodo.entity.meta.User;
import com.liveCodeTodoList.liveCodeTodo.repository.TodoRepository;
import com.liveCodeTodoList.liveCodeTodo.repository.UserRepository;
import com.liveCodeTodoList.liveCodeTodo.service.TodoService;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.AccessDeniedException;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.AuthenticationException;
import com.liveCodeTodoList.liveCodeTodo.utils.advisers.exception.NotFoundException;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoStatusUpdateRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.dto.todoDTO.TodoUpdateRequestDTO;
import com.liveCodeTodoList.liveCodeTodo.utils.specification.UserTodoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    public Todo create(TodoRequestDTO request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(LocalDate.parse(request.getDueDate()))
                .createdAt(LocalDate.now())
                .status(TaskStatus.PENDING)
                .user(user)
                .build();

        return todoRepository.save(todo);
    }

    @Override
    public Page<Todo> getAll(Pageable pageable, TaskStatus status, String sortBy, String order) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        Specification<Todo> specification = UserTodoSpecification.getSpecification(user, status, sortBy, order);

        return todoRepository.findAll(specification, pageable);
    }

    @Override
    public Todo getOne(Integer id) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo With ID " + id + " Is Not Found!"));

        if (!Objects.equals(user.getId(), todo.getUser().getId())) throw new AccessDeniedException("This Todo Is Not Your!");

        return todo;
    }

    @Override
    public Todo update(TodoUpdateRequestDTO request, Integer id) {
        Todo todo = this.getOne(id);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(LocalDate.parse(request.getDueDate()));
        todo.setStatus(request.getStatus());

        return todoRepository.save(todo);
    }

    @Override
    public Todo updateStatus(TodoStatusUpdateRequestDTO request, Integer id) {
        Todo todo = this.getOne(id);
        todo.setStatus(request.getStatus());

        return todoRepository.save(todo);
    }

    @Override
    public void delete(Integer id) {
        Todo todo = this.getOne(id);
        todoRepository.delete(todo);
    }
}
