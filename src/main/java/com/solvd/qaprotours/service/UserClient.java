package com.solvd.qaprotours.service;

import com.google.gson.Gson;
import com.solvd.qaprotours.domain.exception.UserClientException;
import com.solvd.qaprotours.web.dto.ErrorDto;
import com.solvd.qaprotours.web.dto.jwt.JwtTokenDto;
import com.solvd.qaprotours.web.dto.user.PasswordDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@FeignClient(value = "user-client", path = "/api/v1/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserDto getById(@PathVariable String id);

    @GetMapping("/email/{email}")
    UserDto getByEmail(@PathVariable String email);

    @PutMapping
    void update(@RequestBody UserDto user);

    @PostMapping("/{userId}/password")
    void updatePassword(@PathVariable String userId, @RequestBody String newPassword);

    @PutMapping("/{userId}/password")
    void updatePassword(@PathVariable String userId, @RequestBody PasswordDto password);

    @PostMapping
    void create(@RequestBody UserDto user);

    @PostMapping("/activate")
    void activate(@RequestBody JwtTokenDto token);

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id);

    @Component
    class ClientErrorDecoder implements ErrorDecoder {

        @SneakyThrows
        @Override
        public Exception decode(String methodKey, Response response) {
            String json = new BufferedReader(new InputStreamReader(response.body().asInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            ErrorDto errorResponse = new Gson().fromJson(json, ErrorDto.class);
            return new UserClientException(errorResponse.getMessage(), errorResponse.getDetails());
        }

    }

}
