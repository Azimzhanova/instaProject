package peaksoft.instaproject.dto.auth.request;

public record SignInRequest (
        String email,
        String password
) {
}
