package com.izelozarslan.weatherapp.security.auth;

import com.izelozarslan.weatherapp.errormessages.UserErrorMessage;
import com.izelozarslan.weatherapp.exception.userexceptions.exception.AuthenticationFailedException;
import com.izelozarslan.weatherapp.exception.userexceptions.exception.UserNotCreatedException;
import com.izelozarslan.weatherapp.kafka.service.KafkaService;
import com.izelozarslan.weatherapp.security.auth.config.JwtService;
import com.izelozarslan.weatherapp.security.auth.dto.AuthenticationRequestDTO;
import com.izelozarslan.weatherapp.security.auth.dto.AuthenticationResponseDTO;
import com.izelozarslan.weatherapp.security.auth.dto.RegisterRequestDTO;
import com.izelozarslan.weatherapp.security.token.Token;
import com.izelozarslan.weatherapp.security.token.TokenRepository;
import com.izelozarslan.weatherapp.security.token.TokenType;
import com.izelozarslan.weatherapp.security.user.Role;
import com.izelozarslan.weatherapp.security.user.User;
import com.izelozarslan.weatherapp.security.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final KafkaService kafkaService;

    @Transactional
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        try{
            validateEmailNotTaken(request.getEmail());

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        kafkaService.sendMessageInfo("User registered: " + user.getEmail(), "logs");

        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();}
        catch (UserNotCreatedException e){
            kafkaService.sendMessageError("Error registering user: " + e.getMessage(), "logs");
            throw new UserNotCreatedException(UserErrorMessage.USER_NOT_CREATED.getMessage());
        }
    }


    private void validateEmailNotTaken(String email) {
        if (repository.existsByEmail(email)) {
            kafkaService.sendMessageError("Error registering user - email already taken: " + email, "logs");
            throw new UserNotCreatedException(UserErrorMessage.EMAIL_ALREADY_TAKEN.getMessage() + email);
        }
    }


    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            kafkaService.sendMessageInfo("User authenticated: " + user.getEmail(), "logs");

            return AuthenticationResponseDTO.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }catch (BadCredentialsException e){
            kafkaService.sendMessageError("Error authenticating user: " + e.getMessage(), "logs");
            throw new AuthenticationFailedException(UserErrorMessage.INVALID_EMAIL_OR_PASSWORD.getMessage() + e.getMessage());
        }catch (AuthenticationException e){
            kafkaService.sendMessageError("Error authenticating user: " + e.getMessage(), "logs");
            throw new AuthenticationFailedException(UserErrorMessage.AUTHENTICATION_FAILED.getMessage() + e.getMessage());

        }
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                kafkaService.sendMessageInfo("Token refreshed for user: " + user.getEmail(), "logs");
                var authResponse = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
