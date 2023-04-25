package com.ojas.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
