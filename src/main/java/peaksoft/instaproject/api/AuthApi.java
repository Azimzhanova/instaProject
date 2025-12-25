package peaksoft.instaproject.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.instaproject.dto.auth.AuthResponse;
import peaksoft.instaproject.dto.auth.request.SignInRequest;
import peaksoft.instaproject.dto.auth.request.SignUpRequest;
import peaksoft.instaproject.dto.userDTO.request.UserRequest;
import peaksoft.instaproject.dto.userDTO.request.UserUpdateRequest;
import peaksoft.instaproject.enums.Role;
import peaksoft.instaproject.service.AuthService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthApi {
    private final AuthService authService;

    @PostMapping("/signIn")
    public AuthResponse signIn(@Valid @RequestBody SignInRequest signInRequest){
        return authService.signIn(signInRequest);
    }

    @PostMapping("/signUp-user")
    public AuthResponse signUser(@Valid @RequestBody SignUpRequest userSignUpRequest){
        return authService.signUp(new SignUpRequest(userSignUpRequest.userName(),
                userSignUpRequest.email(), userSignUpRequest.password(), userSignUpRequest.phoneNumber(), Role.USER));
    }
    }


