package com.cyj.rbacalpha.repository;

import com.cyj.rbacalpha.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findAuthorityById(Integer id);

}
