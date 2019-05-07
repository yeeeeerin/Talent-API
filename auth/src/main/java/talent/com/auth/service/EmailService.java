package talent.com.auth.service;

public interface EmailService {
    void sendMessage(String to, Long memberId);
}
