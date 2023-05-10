package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.dto.user.UserDto;
import reactor.core.publisher.Mono;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface UserClient {

    /**
     * Get user by id.
     * @param id user id
     * @return user
     */
    Mono<UserDto> getById(String id);

    /**
     * Get user by email.
     * @param email user email
     * @return user
     */
    Mono<UserDto> getByEmail(String email);

    /**
     * Update user.
     * @param user user
     * @return empty response
     */
    Mono<Void> update(User user);

    /**
     * Set new password to user password.
     *
     * @param userId      user id
     * @param newPassword new password
     * @return empty response
     */
    Mono<Void> updatePassword(String userId, String newPassword);

    /**
     * Update user password.
     * @param userId user id
     * @param password password
     * @return empty response
     */
    Mono<Void> updatePassword(String userId, Password password);

    /**
     * Create user.
     * @param user user
     * @return empty response
     */
    Mono<Void> create(User user);

    /**
     * Activate user.
     * @param token activation token
     * @return empty response
     */
    Mono<Void> activate(JwtToken token);

    /**
     * Delete user.
     * @param id user id
     * @return empty response
     */
    Mono<Void> delete(String id);

}
