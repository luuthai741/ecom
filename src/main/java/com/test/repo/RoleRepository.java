package com.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.entities.security.Role;
import com.test.entities.security.RoleType;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(RoleType type);
}
