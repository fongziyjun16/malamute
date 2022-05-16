package com.cyj.rbacalpha.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @JoinTable(
            name = "ROLE_AUTHORITY",
            joinColumns = { @JoinColumn(name = "RID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "AID", referencedColumnName = "ID") }
    )
    @ManyToMany
    private Set<Authority> authorities = new HashSet<>();
}
