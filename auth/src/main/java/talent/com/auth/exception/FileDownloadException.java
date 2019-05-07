package talent.com.auth.exception;

public class FileDownloadException extends RuntimeException {
    private static final long serialVersionUID = 5666695517085403337L;

    public FileDownloadException(String message) {
        super(message);
    }

    public FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}

