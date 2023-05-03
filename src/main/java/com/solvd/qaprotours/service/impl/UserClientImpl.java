package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.UserClientException;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.ErrorDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.mapper.PasswordMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private final WebClient.Builder webClientBuilder;
    private final UserMapper userMapper;
    private final PasswordMapper passwordMapper;
    private final JwtTokenMapper jwtTokenMapper;

    @Override
    public Mono<UserDto> getById(final String id) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .get()
                .uri("http://user-client/api/v1/users/" + id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<UserDto> getByEmail(final String email) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .get()
                .uri("http://user-client/api/v1/users/email/" + email)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    @Override
    public Mono<Void> update(final User user) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .put()
                .uri("http://user-client/api/v1/users")
                .bodyValue(userMapper.toDto(user))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> updatePassword(final String userId,
                                     final String newPassword) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .post()
                .uri("http://user-client/api/v1/users/"
                        + userId
                        + "/password")
                .bodyValue(newPassword)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> updatePassword(final String userId,
                                     final Password password) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .put()
                .uri("http://user-client/api/v1/users/"
                        + userId
                        + "/password")
                .bodyValue(passwordMapper.toDto(password))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> create(final User user) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .post()
                .uri("http://user-client/api/v1/users")
                .bodyValue(userMapper.toDto(user))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> activate(final JwtToken token) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .post()
                .uri("http://user-client/api/v1/users/activate")
                .bodyValue(jwtTokenMapper.toDto(token))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> delete(final String id) {
        return webClientBuilder
                .filter(errorHandler())
                .build()
                .delete()
                .uri("http://user-client/api/v1/users/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public static ExchangeFilterFunction errorHandler() {
        Function<ErrorDto, Mono<ClientResponse>> error = errorBody ->
                Mono.error(
                        new UserClientException(
                                errorBody.getMessage(),
                                errorBody.getDetails()
                        )
                );
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(ErrorDto.class)
                        .flatMap(error);
            } else {
                return Mono.just(clientResponse);
            }
        });
    }

}
