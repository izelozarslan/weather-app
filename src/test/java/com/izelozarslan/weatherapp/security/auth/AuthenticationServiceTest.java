package com.izelozarslan.weatherapp.security.auth;

import com.izelozarslan.weatherapp.exception.userexceptions.exception.AuthenticationFailedException;
import com.izelozarslan.weatherapp.exception.userexceptions.exception.UserNotCreatedException;
import com.izelozarslan.weatherapp.kafka.service.KafkaService;
import com.izelozarslan.weatherapp.security.auth.config.JwtService;
import com.izelozarslan.weatherapp.security.auth.dto.AuthenticationRequestDTO;
import com.izelozarslan.weatherapp.security.auth.dto.AuthenticationResponseDTO;
import com.izelozarslan.weatherapp.security.auth.dto.RegisterRequestDTO;
import com.izelozarslan.weatherapp.security.token.TokenRepository;
import com.izelozarslan.weatherapp.security.user.Role;
import com.izelozarslan.weatherapp.security.user.User;
import com.izelozarslan.weatherapp.security.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private KafkaService kafkaService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(
                userRepository,
                tokenRepository,
                passwordEncoder,
                jwtService,
                authenticationManager,
                kafkaService
        );
    }


    @Test
    public void testRegister_SuccessfulRegistration() {
        // Arrange
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");

        User savedUser = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        Mockito.when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(savedUser);
        Mockito.when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        Mockito.when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponseDTO responseDTO = authenticationService.register(registerRequest);

        // Assert
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals("jwtToken", responseDTO.getAccessToken());
        Assertions.assertEquals("refreshToken", responseDTO.getRefreshToken());

        verify(userRepository).existsByEmail(registerRequest.getEmail());
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
        verify(jwtService).generateRefreshToken(any(User.class));
        verify(kafkaService).sendMessageInfo(anyString(), anyString());

        // Additional assertions
        Assertions.assertEquals(registerRequest.getFirstname(), savedUser.getFirstname());
        Assertions.assertEquals(registerRequest.getLastname(), savedUser.getLastname());
        Assertions.assertEquals(registerRequest.getEmail(), savedUser.getEmail());
        Assertions.assertEquals("encodedPassword", savedUser.getPassword());
        Assertions.assertEquals(Role.USER, savedUser.getRole());
    }


    @Test
    public void testRegister_EmailAlreadyTaken() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("johndoe@example.com");
        request.setPassword("password");

        // Mock userRepository.existsByEmail() to return true
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Assert
        Assertions.assertThrows(UserNotCreatedException.class, () -> {
            // Act
            authenticationService.register(request);
        });

        // Verify
        verify(userRepository).existsByEmail(request.getEmail());
        verify(kafkaService).sendMessageError("Error registering user - email already taken: " + request.getEmail(), "logs");
    }


    @Test
    public void testAuthenticate_SuccessfulAuthentication() {
        // Arrange
        AuthenticationRequestDTO authenticationRequest = new AuthenticationRequestDTO();
        authenticationRequest.setEmail("johndoe@example.com");
        authenticationRequest.setPassword("password");

        User user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email(authenticationRequest.getEmail())
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        Mockito.when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(java.util.Optional.of(user));
        Mockito.when(jwtService.generateToken(user)).thenReturn("jwtToken");
        Mockito.when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        // Act
        AuthenticationResponseDTO responseDTO = authenticationService.authenticate(authenticationRequest);

        // Assert
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals("jwtToken", responseDTO.getAccessToken());
        Assertions.assertEquals("refreshToken", responseDTO.getRefreshToken());

        verify(userRepository).findByEmail(authenticationRequest.getEmail());
        verify(jwtService).generateToken(user);
        verify(jwtService).generateRefreshToken(user);
        verify(authenticationManager).authenticate(any());
        verify(kafkaService).sendMessageInfo(anyString(), anyString());
    }

    @Test
    public void testRegister_UserNotCreatedException() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("johndoe@example.com");
        request.setPassword("password");

        when(userRepository.save(any(User.class))).thenThrow(new UserNotCreatedException("Failed to save user"));

        // Act & Assert
        assertThrows(UserNotCreatedException.class, () -> authenticationService.register(request));
        verify(userRepository, times(1)).save(any(User.class));
        verify(kafkaService, times(1)).sendMessageError(anyString(), anyString());
    }


    @Test
    public void testAuthenticate_InvalidEmailOrPassword() {
        // Arrange
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setEmail("invalid@example.com");
        request.setPassword("wrongpassword");

        doThrow(BadCredentialsException.class)
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act and Assert
        assertThrows(AuthenticationFailedException.class, () -> {
            authenticationService.authenticate(request);
        });

        verify(kafkaService).sendMessageError(anyString(), anyString());
    }

    @Test
    public void testAuthenticate_AuthenticationFailed() {
        // Arrange
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        UserRepository repository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);
        KafkaService kafkaService = mock(KafkaService.class);

        AuthenticationService authenticationService = new AuthenticationService(repository, tokenRepository, passwordEncoder, jwtService, authenticationManager, kafkaService);

        String email = "test@example.com";
        String password = "password";

        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setEmail(email);
        request.setPassword(password);

        // Mock AuthenticationManager
        doThrow(new InternalAuthenticationServiceException("Authentication failed"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act and Assert
        assertThrows(AuthenticationFailedException.class, () -> {
            authenticationService.authenticate(request);
        });

        // Verify KafkaService
        verify(kafkaService).sendMessageError("Error authenticating user: Authentication failed", "logs");
    }


}
