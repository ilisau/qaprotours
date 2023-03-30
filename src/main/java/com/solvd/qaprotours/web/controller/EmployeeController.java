package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final UserClient userClient;
    private final UserMapper userMapper;

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setRole(User.Role.EMPLOYEE);
        return userClient.create(user);
    }

}
