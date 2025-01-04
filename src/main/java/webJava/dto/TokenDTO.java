package webJava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    //DB 상에서 email
    private String username;
    private String password;
    private String role;
}
