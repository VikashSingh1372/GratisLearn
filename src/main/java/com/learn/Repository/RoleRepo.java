package com.learn.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.Models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
