package com.musafir.employyee.repo;

import com.musafir.employyee.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(String rolename);
}
