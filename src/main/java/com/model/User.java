package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String email;
  
  private String nick;
  
  public User() {}
  
  public User(String name, String email, String nick) {
	//this.id = id;
	this.name = name;
	this.email = email;
	this.nick = nick;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNick() {
	return nick;
  }

  public void setNick(String nick) {
	this.nick = nick;
  }

  @Override
  public String toString() {
	return "User [id=" + id + ", name=" + name + ", email=" + email + ", nick=" + nick + "]";
}


  

}
