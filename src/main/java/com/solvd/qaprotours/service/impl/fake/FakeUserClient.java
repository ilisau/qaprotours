package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.user.UserDto;
import reactor.core.publisher.Mono;

public class FakeUserClient implements UserClient {

    @Override
    public Mono<UserDto> getById(String id) {
        UserDto userDto = generateUserDto();
        return Mono.just(userDto);
    }

    @Override
    public Mono<UserDto> getByEmail(String email) {
        UserDto userDto = generateUserDto();
        return Mono.just(userDto);
    }

    @Override
    public Mono<Void> update(User user) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> updatePassword(String userId, String newPassword) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> updatePassword(String userId, Password password) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> create(User user) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> activate(JwtToken token) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.empty();
    }

    private UserDto generateUserDto() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(userName);
        userDto.setSurname(userSurname);
        userDto.setEmail(userEmail);
        return userDto;
    }

}
