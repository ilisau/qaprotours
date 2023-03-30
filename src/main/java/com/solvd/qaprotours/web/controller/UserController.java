package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.domain.exception.NoFreePlacesException;
import com.solvd.qaprotours.domain.exception.ServiceNotAvailableException;
import com.solvd.qaprotours.domain.exception.TourAlreadyStartedException;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.user.PasswordDto;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import com.solvd.qaprotours.web.mapper.PasswordMapper;
import com.solvd.qaprotours.web.mapper.TicketMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lisov Ilya
 */
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final UserMapper userMapper;
    private final PasswordMapper passwordMapper;
    private final String TICKET_SERVICE = "ticketService";

    @PutMapping
    @PreAuthorize("canAccessUser(#userDto.getId())")
    public Mono<Void> update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userClient.update(user);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("canAccessUser(#userId)")
    public Mono<UserDto> getById(@PathVariable String userId) {
        return userClient.getById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("canAccessUser(#userId)")
    public Mono<Void> delete(@PathVariable String userId) {
        return userClient.delete(userId);
    }

    @PutMapping("/{userId}/password")
    @PreAuthorize("canAccessUser(#userId)")
    public Mono<Void> updatePassword(@PathVariable String userId,
                                     @Validated @RequestBody PasswordDto passwordDto) {
        Password password = passwordMapper.toEntity(passwordDto);
        return userClient.updatePassword(userId, password);
    }

    @GetMapping("/{userId}/tickets")
    @PreAuthorize("canAccessUser(#userId)")
    @CircuitBreaker(name = TICKET_SERVICE, fallbackMethod = "getEmptyTicketList")
    public Flux<TicketDto> getTickets(@PathVariable String userId) {
        return ticketService.getAllByUserId(userId)
                .map(ticketMapper::toDto);
    }

    @PostMapping("/{userId}/tickets/{tourId}")
    @PreAuthorize("canAccessUser(#userId)")
    @CircuitBreaker(name = TICKET_SERVICE, fallbackMethod = "handleServiceError")
    public Mono<Void> addTicket(@PathVariable String userId,
                                @PathVariable Long tourId,
                                @RequestParam Integer peopleAmount) {
        return ticketService.create(userId, tourId, peopleAmount);
    }

    private List<TicketDto> getEmptyTicketList(Exception e) {
        if (e.getClass().equals(AccessDeniedException.class)) {
            throw new AuthException(e.getMessage());
        }
        return new ArrayList<>();
    }

    private void handleServiceError(Exception e) {
        if (e.getClass().equals(AccessDeniedException.class)) {
            throw new AuthException(e.getMessage());
        }
        if (e.getClass().equals(NoFreePlacesException.class)) {
            throw new NoFreePlacesException(e.getMessage());
        }
        if (e.getClass().equals(TourAlreadyStartedException.class)) {
            throw new TourAlreadyStartedException(e.getMessage());
        }
        throw new ServiceNotAvailableException();
    }

}
