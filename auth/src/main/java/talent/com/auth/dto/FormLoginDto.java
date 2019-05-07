package talent.com.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormLoginDto {

    @JsonProperty(value = "userId")
    private String id;

    @JsonProperty(value = "password")
    private String password;


}
