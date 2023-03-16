package com.solvd.qaprotours.web.security.jwt;

import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Ermakovich Kseniya
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserClient userClient;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userClient.getByEmail(email)
                .map(userMapper::toEntity)
                .map(JwtUserDetailsFactory::create)
                .block();
    }

}
