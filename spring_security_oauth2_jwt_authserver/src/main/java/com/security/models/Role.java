package com.security.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {
	
	@Id
	private int id;
	
	@Column
	private String roleName;
	
//	@ManyToMany
//	private List<ApplicationUser> user;
	
//	public List<ApplicationUser> getUser() {
//		return user;
//	}
//	public void setUser(List<ApplicationUser> user) {
//		this.user = user;
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

}
