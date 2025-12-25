package peaksoft.instaproject.exception;

public class AccessIsDeniedException extends RuntimeException {
    public AccessIsDeniedException(String message) {
        super(message);
    }
}
