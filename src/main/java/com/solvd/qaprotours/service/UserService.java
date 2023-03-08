package com.solvd.qaprotours.service;

import com.google.gson.Gson;
import com.solvd.qaprotours.domain.exception.UserClientException;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.dto.ErrorDto;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@FeignClient(value = "user-client", path = "/api/v1/users")
public interface UserService {

    @GetMapping("/{id}")
    User getById(@PathVariable Long id);

    @GetMapping("/email/{email}")
    User getByEmail(@PathVariable String email);

    @PutMapping
    void update(@RequestBody User user);

    @PostMapping("/{userId}/password")
    void updatePassword(@PathVariable Long userId, @RequestBody String newPassword);

    @PutMapping("/{userId}/password")
    void updatePassword(@PathVariable Long userId, @RequestBody Password password);

    @PostMapping
    void create(@RequestBody User user);

    @PostMapping("/activate")
    void activate(@RequestBody JwtToken token);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @Component
    class ClientErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            ErrorDto errorResponse = new Gson().fromJson(response.body().toString(), ErrorDto.class);
            return new UserClientException(errorResponse.getMessage(), errorResponse.getDetails());
        }

    }

}
