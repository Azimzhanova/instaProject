package peaksoft.instaproject.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.instaproject.config.jwt.JwtService;
import peaksoft.instaproject.dto.auth.AuthResponse;
import peaksoft.instaproject.dto.auth.request.SignInRequest;
import peaksoft.instaproject.dto.auth.request.SignUpRequest;
import peaksoft.instaproject.entity.User;
import peaksoft.instaproject.enums.Role;
import peaksoft.instaproject.exception.NotFoundException;
import peaksoft.instaproject.repository.UserRepository;
import peaksoft.instaproject.service.AuthService;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.findUserByEmail(signUpRequest.email()).isPresent()) {
            throw new BadCredentialsException(String.format("User with email %s already exists", signUpRequest.email()));
        }
        User user = User
                .builder()
                .userName(signUpRequest.userName())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .email(signUpRequest.email())
                .phoneNumber(signUpRequest.phoneNumber())
                .role(signUpRequest.role())
                .followersCount(0)
                .followingCount(0)
                .build();
        userRepository.save(user);

        String tokenUser = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .id(user.getId())
                .token(tokenUser)
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findUserByEmail(signInRequest.email()).orElseThrow(() ->
                new NotFoundException("User with email " + signInRequest.email() + " not found"));
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialsException(String.format("Passwords do not match"));
        }
        String token = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .id(user.getId())
                .token(token)
                .role(user.getRole())
                .build();
    }

    //init method
    @PostConstruct
    private void saveAdmin() {
        User user = new User();
        user.setUserName("admin");
        user.setPassword(passwordEncoder.encode("admin1234"));
        user.setEmail("admin@gmail.com");
        user.setPhoneNumber("+996500343536");
        user.setRole(Role.ADMIN);
        if (!userRepository.existsUserByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }
}
