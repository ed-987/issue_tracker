package com.model;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;

@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String email;

  private Boolean dark_mode;
  
  private HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
  
  public User() {
  }

public User(String name, Boolean dark_mode, HashMap<String, Boolean> columns) {
	this.name = name;
	this.dark_mode = dark_mode;
	this.columns = columns;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Boolean getDark_mode() {
	return dark_mode;
}

public void setDark_mode(Boolean dark_mode) {
	this.dark_mode = dark_mode;
}

public HashMap<String, Boolean> getColumns() {
	return columns;
}

public void setColumns(HashMap<String, Boolean> columns) {
	this.columns = columns;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}



  
}
