package com.security.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.models.ApplicationUser;
import com.security.models.Role;
import com.security.repositories.UserRepository;
import com.security.service.ApplicationUserService;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, ApplicationUserService {
	
	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		ApplicationUser user = userRepository.findByUsername(userId);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		System.out.println("roles sise = " + user.getRoles().size());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private List<SimpleGrantedAuthority> getAuthority(ApplicationUser user) {
		List<Role> rolesList = user.getRoles();
		List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
		for(Role role : rolesList) {
			authoritiesList.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authoritiesList;
	}

	public List<ApplicationUser> findAll() {
		List<ApplicationUser> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
    public ApplicationUser save(ApplicationUser user) {
        return userRepository.save(user);
    }
}
