package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.UserService;
import com.solvd.qaprotours.web.dto.user.PasswordDto;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import com.solvd.qaprotours.web.mapper.TicketMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final TicketService ticketService;
    private final UserMapper userMapper;
    private final TicketMapper ticketMapper;

    @PutMapping
    @PreAuthorize("canAccessUser(#userDto.getId())")
    public void update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.update(user);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("canAccessUser(#userId)")
    public UserDto getById(@PathVariable Long userId) {
        User user = userService.getById(userId);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("canAccessUser(#userId)")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }

    @PutMapping("/{userId}/password")
    @PreAuthorize("canAccessUser(#userId)")
    public void updatePassword(@PathVariable Long userId,
                               @Validated @RequestBody PasswordDto passwordDto) {
        userService.updatePassword(userId, passwordDto.getOldPassword(), passwordDto.getNewPassword());
    }

    @GetMapping("/{userId}/tickets")
    @PreAuthorize("canAccessUser(#userId)")
    public List<TicketDto> getTickets(@PathVariable Long userId) {
        List<Ticket> tickets = ticketService.getAllByUserId(userId);
        return ticketMapper.toDto(tickets);
    }

    @PostMapping("/{userId}/tickets/{tourId}")
    @PreAuthorize("canAccessUser(#userId)")
    public void addTicket(@PathVariable Long userId,
                          @PathVariable Long tourId,
                          @RequestParam Integer peopleAmount) {
        ticketService.create(userId, tourId, peopleAmount);
    }

}
