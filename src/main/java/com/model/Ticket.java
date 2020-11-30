package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;

@Entity // This tells Hibernate to make a table out of this class
public class Ticket {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  private String title;

  private String description;
  
  private String user;
  
  private String status;
  
  @Transient
  private Boolean flag;
  
  @Transient
  private String id_display;
    
  public Ticket() {
	this.status = "New";
  }
  
  public Ticket(String title, String description, String user, String status) {
	//this.id = id;
	this.title = title;
	this.description = description;
	this.user = user;
	this.status = status;
}

public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUser() {
	return user;
  }

  public void setUser(String user) {
	this.user = user;
  }
  
  public String getStatus() {
	return status;
  }

  public void setStatus(String status) {
	this.status = status;
  }		

@Override
public String toString() {
	return "Ticket [id=" + id + ", title=" + title + ", description=" + description + ", user=" + user + ", status="
			+ status + ", flag=" + flag + "]";
}

public Boolean getFlag() {
	return flag;
}

public void setFlag(Boolean flag) {
	this.flag = flag;
}

public String getId_display() {
	return id_display;
}

public void setId_display(String id_display) {
	this.id_display = id_display;
}

public void create_display() {
	this.id_display = "INC"+String.format("%06d",this.id);
}

}
