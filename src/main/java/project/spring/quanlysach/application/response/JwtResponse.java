package project.spring.quanlysach.application.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    public int id;
    private String fullName;
    private String email;
    private String phone;
    private String username;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;
    @JsonIgnore
    private final String type = "Bearer ";
}
