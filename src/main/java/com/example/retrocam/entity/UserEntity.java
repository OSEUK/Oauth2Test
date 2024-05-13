package com.example.retrocam.entity;

import com.example.retrocam.dto.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getRoleKey() {
        return this.role.getKey();
    }
}
