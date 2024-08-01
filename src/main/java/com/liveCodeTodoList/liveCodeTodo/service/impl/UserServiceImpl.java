package com.liveCodeTodoList.liveCodeTodo.service.impl;

import com.liveCodeTodoList.liveCodeTodo.repository.UserRepository;
import com.liveCodeTodoList.liveCodeTodo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

}
