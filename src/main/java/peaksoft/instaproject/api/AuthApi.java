package peaksoft.instaproject.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.instaproject.dto.auth.response.AuthResponse;
import peaksoft.instaproject.dto.auth.request.SignInRequest;
import peaksoft.instaproject.dto.auth.request.SignUpRequest;
import peaksoft.instaproject.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final AuthService authService;

    @PostMapping("/signUp-user")
    public AuthResponse signUser(@Valid @RequestBody SignUpRequest userSignUpRequest){
        return authService.signUp(userSignUpRequest);
    }

    @PostMapping("/signIn")
    public AuthResponse signIn(@Valid @RequestBody SignInRequest signInRequest){
        return authService.signIn(signInRequest);
    }
    }


