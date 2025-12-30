package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
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
    @Column(name = "fullname", length = 125, nullable = false)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Size(max = 125)
    @Column(name = "role", length = 125, nullable = false)
    private String role;
}
