package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.domain.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@FeignClient(value = "user-service", path = "/api/v1/users")
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

}
