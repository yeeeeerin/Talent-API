package talent.com.auth.exception;

public class NonAuthenticatedException extends RuntimeException {

    private static final long serialVersionUID = 2834229257505526726L;

    public NonAuthenticatedException() {
        super("메일에서 인증을 먼저 진행해주세요.");
    }
}
