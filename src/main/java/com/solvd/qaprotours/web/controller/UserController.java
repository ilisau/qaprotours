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
    public void update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
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

    @PutMapping("/{userId}/password")
    public void updatePassword(@PathVariable Long userId,
                               @Validated PasswordDto passwordDto) {
        userService.updatePassword(userId, passwordDto.getOldPassword(), passwordDto.getNewPassword());
    }

    @GetMapping("/{userId}/tours")
    public List<TicketDto> getTickets(@PathVariable Long userId) {
        List<Ticket> tours = ticketService.getTickets(userId);
        return ticketMapper.toDto(tours);
    }

    @GetMapping("/{userId}/tours/{ticketId}")
    public TicketDto getTicket(@PathVariable Long userId,
                               @PathVariable Long ticketId) {
        Ticket ticket = ticketService.getById(ticketId);
        return ticketMapper.toDto(ticket);
    }

    @PostMapping("/{userId}/tours/{tourId}")
    public void addTicket(@PathVariable Long userId,
                          @PathVariable Long tourId,
                          @RequestParam Integer peopleAmount) {
        ticketService.addTicket(userId, tourId, peopleAmount);
    }

    @DeleteMapping("/{userId}/tours/{tourId}")
    public void deleteTicket(@PathVariable Long userId,
                             @PathVariable Long tourId) {
        ticketService.deleteTicket(userId, tourId);
    }

    @PostMapping("/{userId}/tours/{tourId}/confirm")
    public void confirmTicket(@PathVariable Long userId,
                              @PathVariable Long tourId) {
        ticketService.confirmTicket(userId, tourId);
    }

}
