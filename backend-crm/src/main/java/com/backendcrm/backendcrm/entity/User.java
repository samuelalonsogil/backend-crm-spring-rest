package com.backendcrm.backendcrm.entity;

import java.util.Collection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotNull
    @Size(min = 1, message = "username is required")
    private String username;

    @Column(name = "password")
    @NotNull
    @Size(min = 3, message = "password must be at least 3 characters")
    private String password;

    @Column(name = "enabled")
    @NotNull
    private boolean enabled;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 1, message = "can't be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 1, message = "can't be empty")
    private String lastName;

    @Column(name = "email")
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "enter a valid email")
    @Size(min = 1, message = "can't be empty")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id") )
    private Collection<Role> roles;

}
