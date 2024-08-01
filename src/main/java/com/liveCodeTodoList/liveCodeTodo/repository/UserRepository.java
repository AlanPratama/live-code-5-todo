package com.liveCodeTodoList.liveCodeTodo.repository;

import com.liveCodeTodoList.liveCodeTodo.entity.meta.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
