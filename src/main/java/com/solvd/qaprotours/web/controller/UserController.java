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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
    private static final String TICKET_SERVICE = "ticketService";

    /**
     * Updates a user.
     *
     * @param user user's data
     * @return empty response
     */
    @PutMapping
    @MutationMapping
    @PreAuthorize("canAccessUser(#user.getId())")
    public Mono<Void> updateUser(
            @Validated(OnUpdate.class) @RequestBody @Argument final UserDto user
    ) {
        User u = userMapper.toEntity(user);
        return userClient.update(u);
    }

    /**
     * Gets a user by id.
     *
     * @param id user's id
     * @return user's data
     */
    @GetMapping("/{id}")
    @QueryMapping
    @PreAuthorize("canAccessUser(#id)")
    public Mono<UserDto> getUserById(@PathVariable @Argument final String id) {
        return userClient.getById(id);
    }

    /**
     * Deletes a user by id.
     *
     * @param id user's id
     * @return empty response
     */
    @DeleteMapping("/{id}")
    @QueryMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("canAccessUser(#id)")
    public Mono<Void> deleteUser(@PathVariable @Argument final String id) {
        return userClient.delete(id);
    }

    /**
     * Updates a user's password.
     *
     * @param id       user's id
     * @param password password's data
     * @return empty response
     */
    @PutMapping("/{id}/password")
    @MutationMapping
    @PreAuthorize("canAccessUser(#id)")
    public Mono<Void> updatePassword(
            @PathVariable @Argument final String id,
            @Validated @RequestBody @Argument final PasswordDto password
    ) {
        Password p = passwordMapper.toEntity(password);
        return userClient.updatePassword(id, p);
    }

    /**
     * Gets a user's tickets.
     *
     * @param id user's id
     * @return list of tickets
     */
    @GetMapping("/{id}/tickets")
    @QueryMapping
    @PreAuthorize("canAccessUser(#id)")
    @CircuitBreaker(name = TICKET_SERVICE,
            fallbackMethod = "getEmptyTicketList")
    public Flux<TicketDto> ticketsByUserId(
            @PathVariable @Argument final String id
    ) {
        return ticketService.getAllByUserId(id)
                .map(ticketMapper::toDto);
    }

    /**
     * Adds a ticket to a user.
     * @param userId user's id
     * @param tourId tour's id
     * @param peopleAmount amount of people in ticket
     * @return empty response
     */
    @PostMapping("/{userId}/tickets/{tourId}")
    @MutationMapping
    @PreAuthorize("canAccessUser(#userId)")
    @CircuitBreaker(name = TICKET_SERVICE,
            fallbackMethod = "handleServiceError")
    public Mono<Void> addTicket(@PathVariable @Argument final String userId,
                                @PathVariable @Argument final Long tourId,
                                @RequestParam
                                @Argument final Integer peopleAmount) {
        return ticketService.create(userId, tourId, peopleAmount);
    }

    private List<TicketDto> getEmptyTicketList(final Exception e) {
        if (e.getClass().equals(AccessDeniedException.class)) {
            throw new AuthException(e.getMessage());
        }
        return new ArrayList<>();
    }

    private void handleServiceError(final Exception e) {
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
