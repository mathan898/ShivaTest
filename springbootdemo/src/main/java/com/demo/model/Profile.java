package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class Profile {
	
	@Id
	// defining id as column name
	@Column
	private int id;
	// defining name as column name
	@Column
	private String name;
	// defining age as column name
	@Column
	private String profileName;
	// defining email as column name
	@Column
	private String email;
	
	public Profile(int id, String name, String profileName, String email) {
		this.id = id;
		this.name = name;
		this.profileName = profileName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
