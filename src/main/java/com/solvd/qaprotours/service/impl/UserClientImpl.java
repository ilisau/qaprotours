package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.mapper.PasswordMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {
    //TODO change localhost on user-client

    private final WebClient.Builder webClientBuilder;
    private final UserMapper userMapper;
    private final PasswordMapper passwordMapper;
    private final JwtTokenMapper jwtTokenMapper;

    @Override
    public Mono<UserDto> getById(Long id) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/v1/users/" + id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<UserDto> getByEmail(String email) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/v1/users/email/" + email)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<Void> update(User user) {
        return webClientBuilder.build()
                .put()
                .uri("http://localhost:8081/api/v1/users")
                .bodyValue(userMapper.toDto(user))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> updatePassword(Long userId, String newPassword) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/v1/users/" + userId + "/password")
                .bodyValue(newPassword)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> updatePassword(Long userId, Password password) {
        return webClientBuilder.build()
                .put()
                .uri("http://localhost:8081/api/v1/users/" + userId + "/password")
                .bodyValue(passwordMapper.toDto(password))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> create(User user) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/v1/users")
                .bodyValue(userMapper.toDto(user))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> activate(JwtToken token) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/v1/users/activate")
                .bodyValue(jwtTokenMapper.toDto(token))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return webClientBuilder.build()
                .delete()
                .uri("http://localhost:8081/api/v1/users/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
