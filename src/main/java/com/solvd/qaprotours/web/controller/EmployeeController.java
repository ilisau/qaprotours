package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * Create a new employee.
     *
     * @param user employee's data
     * @return empty response
     */
    @PostMapping
    @MutationMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createEmployee(
            @Validated(OnCreate.class) @RequestBody @Argument final UserDto user
    ) {
        User u = userMapper.toEntity(user);
        u.setRole(User.Role.EMPLOYEE);
        return userClient.create(u);
    }

}
