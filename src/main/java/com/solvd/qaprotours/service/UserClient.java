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

    Mono<UserDto> getById(String id);

    Mono<UserDto> getByEmail(String email);

    Mono<Void> update(User user);

    Mono<Void> updatePassword(String userId, String newPassword);

    Mono<Void> updatePassword(String userId, Password password);

    Mono<Void> create(User user);

    Mono<Void> activate(JwtToken token);

    Mono<Void> delete(String id);

}
