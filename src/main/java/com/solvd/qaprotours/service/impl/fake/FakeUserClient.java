package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.user.UserDto;
import reactor.core.publisher.Mono;

public class FakeUserClient implements UserClient {

    @Override
    public Mono<UserDto> getById(final String id) {
        UserDto userDto = generateUserDto();
        return Mono.just(userDto);
    }

    @Override
    public Mono<UserDto> getByEmail(final String email) {
        UserDto userDto = generateUserDto();
        return Mono.just(userDto);
    }

    @Override
    public Mono<Void> update(final User user) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> updatePassword(final String userId,
                                     final String newPassword) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> updatePassword(final String userId,
                                     final Password password) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> create(final User user) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> activate(final JwtToken token) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(final String id) {
        return Mono.empty();
    }

    private UserDto generateUserDto() {
        String id = "1";
        String name = "Mike";
        String surname = "Ivanov";
        String email = "mike@example.com";
        String password = "12345678";
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setSurname(surname);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setActivated(true);
        return userDto;
    }

}
