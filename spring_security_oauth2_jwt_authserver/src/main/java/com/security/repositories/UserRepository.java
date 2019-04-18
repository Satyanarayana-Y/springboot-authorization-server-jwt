package com.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.security.models.ApplicationUser;

@Repository
public interface UserRepository extends CrudRepository<ApplicationUser, Long> {
	ApplicationUser findByUsername(String username);
}
