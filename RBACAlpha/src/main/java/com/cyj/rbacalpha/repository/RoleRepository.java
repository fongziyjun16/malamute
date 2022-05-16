package com.cyj.rbacalpha.repository;

import com.cyj.rbacalpha.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByName(String name);

}
