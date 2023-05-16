package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.MailData;
import com.solvd.qaprotours.domain.MailType;
import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.domain.exception.InvalidTokenException;
import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.kafka.MessageSender;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetailsFactory;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailDataMapper mailDataMapper;
    private final UserMapper userMapper;
    private final MessageSender messageSender;

    @Override
    public Mono<JwtResponse> login(final Authentication authentication) {
        return userClient.getByEmail(authentication.getEmail())
                .flatMap(userDto -> {
                    User user = userMapper.toEntity(userDto);
                    if (!passwordEncoder.matches(authentication.getPassword(),
                            user.getPassword())) {
                        return Mono.error(new AuthException("wrong password"));
                    }
                    if (!user.isActivated()) {
                        return Mono.error(
                                new AuthException("user is not activated")
                        );
                    }
                    return Mono.just(user);
                })
                .onErrorResume(Mono::error)
                .flatMapMany(u -> {
                    String accessToken =
                            jwtService.generateToken(JwtTokenType.ACCESS, u);
                    String refreshToken =
                            jwtService.generateToken(JwtTokenType.REFRESH, u);
                    return Flux.just(accessToken, refreshToken).collectList();
                })
                .map(tokens -> new JwtResponse(tokens.get(0), tokens.get(1)))
                .next();
    }

    @Override
    public Mono<JwtResponse> refresh(final JwtToken jwtToken) {
        return Mono.just(jwtService.parse(jwtToken.getToken()))
                .map(JwtUserDetailsFactory::create)
                .flatMap(userDetails -> {
                    if (!jwtService.isTokenType(jwtToken.getToken(),
                            JwtTokenType.REFRESH)) {
                        return Mono.error(
                                new AuthException("invalid refresh token")
                        );
                    }
                    return Mono.just(userDetails);
                })
                .onErrorResume(Mono::error)
                .flatMap(userDetails ->
                        userClient.getByEmail(userDetails.getEmail()))
                .map(userMapper::toEntity)
                .flatMapMany(u -> {
                    String accessToken =
                            jwtService.generateToken(JwtTokenType.ACCESS, u);
                    String refreshToken =
                            jwtService.generateToken(JwtTokenType.REFRESH, u);
                    return Flux.just(accessToken, refreshToken).collectList();
                })
                .map(tokens -> new JwtResponse(tokens.get(0), tokens.get(1)))
                .next();
    }

    @Override
    public Mono<Void> sendRestoreToken(final String email) {
        return userClient.getByEmail(email)
                .map(userMapper::toEntity)
                .map(user -> {
                    Map<String, Object> params = new HashMap<>();
                    String token = jwtService.generateToken(JwtTokenType.RESET,
                            user);
                    params.put("token", token);
                    params.put("user.id", user.getId());
                    params.put("user.email", user.getEmail());
                    params.put("user.name", user.getName());
                    params.put("user.surname", user.getSurname());
                    return params;
                })
                .flatMap(params -> messageSender.sendMessage("mail",
                        0,
                        params.get("user.id").toString(),
                        mailDataMapper.toDto(
                                new MailData(MailType.PASSWORD_RESET, params))
                        )
                        .then())
                .then();
    }

    @Override
    public Mono<Void> restoreUserPassword(final String token,
                                          final String password) {
        if (!jwtService.validateToken(token)) {
            return Mono.error(() ->
                    new InvalidTokenException("token is expired"));
        }
        Claims claims = jwtService.parse(token);
        if (!jwtService.isTokenType(token, JwtTokenType.RESET)) {
            return Mono.error(() ->
                    new InvalidTokenException("invalid reset token"));
        }
        JwtUserDetails userDetails = JwtUserDetailsFactory.create(claims);
        return userClient.updatePassword(userDetails.getId(), password);
    }

}
