package com.security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.security.models.ApplicationUser;
import com.security.service.ApplicationUserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ApplicationUserService userService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public List<ApplicationUser> listUser(){
        return userService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ApplicationUser create(@RequestBody ApplicationUser user){
        return userService.save(user);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Long id){
        userService.delete(id);
        return "success";
    }

}
