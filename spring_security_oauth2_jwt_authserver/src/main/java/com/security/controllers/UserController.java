package com.security.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.security.models.ApplicationUser;
import com.security.repositories.UserRepository;
import com.security.service.ApplicationUserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ApplicationUserService userService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public List<ApplicationUser> listUser(){
        return userService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ApplicationUser create(@RequestBody ApplicationUser user){
    	user.setPassword(bcrypt.encode(user.getPassword()));
        return userService.save(user);
    }
    
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody ApplicationUser user,@PathVariable("id")long id) {
    	
    	Optional<ApplicationUser> applicationUser = userRepository.findById(id);
    	
    	if(!applicationUser.isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
    	user.setId(id);
    	user.setPassword(bcrypt.encode(user.getPassword()));
    	userRepository.save(user);
    	return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Long id){
        userService.delete(id);
        return "success";
    }
    
    

}
