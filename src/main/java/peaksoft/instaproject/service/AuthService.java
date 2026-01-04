package peaksoft.instaproject.service;


import peaksoft.instaproject.dto.auth.response.AuthResponse;
import peaksoft.instaproject.dto.auth.request.SignInRequest;
import peaksoft.instaproject.dto.auth.request.SignUpRequest;

public interface AuthService {
    AuthResponse signUp(SignUpRequest signUpRequest);
    AuthResponse signIn(SignInRequest signInRequest);
}
