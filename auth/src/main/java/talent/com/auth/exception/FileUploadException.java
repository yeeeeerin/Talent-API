package talent.com.auth.exception;

public class FileUploadException extends RuntimeException {
    private static final long serialVersionUID = -1311921173483008740L;

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
