package com.solvd.qaprotours.web.security.jwt;

import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Ermakovich Kseniya
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements ReactiveUserDetailsService {

    private final UserClient userClient;
    private final UserMapper userMapper;

    @Override
    public Mono<UserDetails> findByUsername(final String email) {
        return userClient.getByEmail(email)
                .map(userMapper::toEntity)
                .map(JwtUserDetailsFactory::create);
    }

}
