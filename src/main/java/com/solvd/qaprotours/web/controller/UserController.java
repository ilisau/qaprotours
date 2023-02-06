package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.domain.user.UserTour;
import com.solvd.qaprotours.service.UserService;
import com.solvd.qaprotours.service.UserTourService;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.dto.user.UserTourDto;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.mapper.UserTourMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lisov Ilya
 */
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserTourService userTourService;
    private final UserMapper userMapper;
    private final UserTourMapper userTourMapper;

    @PutMapping
    public void update(@Validated @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.update(user);
    }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Long userId) {
        User user = userService.getById(userId);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }

    @GetMapping("/{userId}/tours")
    public List<UserTourDto> getTours(@PathVariable Long userId) {
        List<UserTour> tours = userTourService.getTours(userId);
        return userTourMapper.toDto(tours);
    }

    @PostMapping("/{userId}/tours/{tourId}")
    public void addTour(@PathVariable Long userId, @PathVariable Long tourId) {
        userTourService.addTour(userId, tourId);
    }

    @PostMapping("/{userId}/tours/{tourId}/confirm")
    public void confirmTour(@PathVariable Long userId, @PathVariable Long tourId) {
        userTourService.confirmTour(userId, tourId);
    }

}
