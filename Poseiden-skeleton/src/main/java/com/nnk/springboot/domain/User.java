package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 125)
    @Column(name = "username", length = 125, nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(max = 125)
    @Column(name = "password", length = 125, nullable = false)
    private String password;

    @NotBlank(message = "Full name is mandatory")
    @Size(max = 125)
    @Column(name = "full_name", length = 125, nullable = false)
    private String fullName;

    @NotBlank(message = "Role is mandatory")
    @Size(max = 125)
    @Column(name = "role", length = 125, nullable = false)
    private String role;
}
