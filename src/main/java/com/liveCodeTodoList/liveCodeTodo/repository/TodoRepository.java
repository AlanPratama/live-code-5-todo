package com.liveCodeTodoList.liveCodeTodo.repository;

import com.liveCodeTodoList.liveCodeTodo.entity.meta.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodoRepository extends JpaRepository<Todo, Integer>, JpaSpecificationExecutor<Todo> {
}
