package com.example.retrocam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Role role;
    private String name;
    private String username;

    @Override
    public String toString() {
        return "UserDTO{" +
                "role=" + role +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
