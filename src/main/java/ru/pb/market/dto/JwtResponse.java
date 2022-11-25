package ru.pb.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data

public class JwtResponse {
    private String token;
    private List<String> roles;

    public JwtResponse(String token, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.roles = authorities.stream().map(Object::toString).toList();
    }
}
