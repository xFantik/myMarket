package ru.pb.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.pb.market.data.Role;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String email;

    private List<Role> roles;


    public UserDto(String name, String email, List<Role> roles) {
        this.username = name;
        this.email = email;
        this.roles = roles;
    }
}
