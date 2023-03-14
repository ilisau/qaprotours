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
import com.solvd.qaprotours.web.dto.MailDataDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
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
    public JwtResponse login(Authentication authentication) {
        UserDto userDto = userClient.getByEmail(authentication.getEmail());
        User user = userMapper.toEntity(userDto);
        if (!passwordEncoder.matches(authentication.getPassword(), user.getPassword())) {
            throw new AuthException("wrong password");
        }
        if (!user.isActivated()) {
            throw new AuthException("user is not activated");
        }
        String accessToken = jwtService.generateToken(JwtTokenType.ACCESS, user);
        String refreshToken = jwtService.generateToken(JwtTokenType.REFRESH, user);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(JwtToken jwtToken) {
        Claims claims = jwtService.parse(jwtToken.getToken());
        JwtUserDetails userDetails = JwtUserDetailsFactory.create(claims);
        if (!jwtService.isTokenType(jwtToken.getToken(), JwtTokenType.REFRESH)) {
            throw new AuthException("invalid refresh token");
        }
        UserDto userDto = userClient.getByEmail(userDetails.getEmail());
        User user = userMapper.toEntity(userDto);
        String accessToken = jwtService.generateToken(JwtTokenType.ACCESS, user);
        String refreshToken = jwtService.generateToken(JwtTokenType.REFRESH, user);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public void sendRestoreToken(String email) {
        UserDto userDto = userClient.getByEmail(email);
        User user = userMapper.toEntity(userDto);
        Map<String, Object> params = new HashMap<>();
        String token = jwtService.generateToken(JwtTokenType.RESET, user);
        params.put("token", token);
        params.put("user.email", user.getEmail());
        params.put("user.name", user.getName());
        params.put("user.surname", user.getSurname());
        MailData mailData = new MailData(MailType.PASSWORD_RESET, params);
        MailDataDto dto = mailDataMapper.toDto(mailData);
        messageSender.sendMessage("mail", 0, user.getId().toString(), dto);
    }

    @Override
    public void restoreUserPassword(String token, String password) {
        if (!jwtService.validateToken(token)) {
            throw new InvalidTokenException("token is expired");
        }
        Claims claims = jwtService.parse(token);
        if (!jwtService.isTokenType(token, JwtTokenType.RESET)) {
            throw new InvalidTokenException("invalid reset token");
        }
        JwtUserDetails userDetails = JwtUserDetailsFactory.create(claims);
        userClient.updatePassword(userDetails.getId(), password);
    }

}
