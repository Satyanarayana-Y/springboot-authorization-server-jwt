package com.security.service;

import java.util.List;

import com.security.models.ApplicationUser;


public interface ApplicationUserService {
	ApplicationUser save(ApplicationUser user);
    List<ApplicationUser> findAll();
    void delete(long id);
}
