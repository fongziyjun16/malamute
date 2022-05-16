package com.cyj.rbacalpha.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @JoinTable(
            name = "USER_ROLE",
            joinColumns = { @JoinColumn(name = "UID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "RID", referencedColumnName = "ID") }
    )
    @ManyToMany
    private Set<Role> roles = new HashSet<>();

}
