package talent.com.auth.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class TokenDto {

    private String token;

}
