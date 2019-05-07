package talent.com.auth.exception;

public class InvaildJwtException extends RuntimeException {

    private static final long serialVersionUID = 2834229257505526726L;

    public InvaildJwtException(String msg) {
        super(msg);
    }
}
