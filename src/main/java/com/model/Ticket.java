package com.model;

import java.text.SimpleDateFormat;
import java.util.Date;

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
  private Date created;
  private Date updated;
  
  @Transient
  private Boolean flag;
  
//  @Transient
//  private String id_display;
    
  public Ticket() {
	this.status = "New";
	this.created = new Date();
  }
  
  public Ticket(String title, String description, String user, String status) {
	//this.id = id;
	this.title = title;
	this.description = description;
	this.user = user;
	this.status = status;
	this.created = new Date();
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


public Boolean getFlag() {
	return flag;
}

public void setFlag(Boolean flag) {
	this.flag = flag;
}

public String getId_display() {
	return "INC"+String.format("%06d",id);
}

public Date getCreated() {
	return created;
}

public void setCreated(Date created) {
	this.created = created;
}

public Date getUpdated() {
	return updated;
}

public void setUpdated(Date updated) {
	this.updated = updated;
}

public String getCreatedDateFormat() {
	if(created != null) {
		SimpleDateFormat ft = 
		new SimpleDateFormat ("yyyy.MM.dd");
		return ft.format(created);}
	else {
		return "2020.11.01";
	}
}

public String getCreatedDateTimeFormat() {
	if(created != null) {
		SimpleDateFormat ft = 
		new SimpleDateFormat ("yyyy.MM.dd HH:mm");
		return ft.format(created);}
	else {
		return "2020.11.01 00:00";
	}
}

@Override
public String toString() {
	return "Ticket [id=" + id + ", title=" + title + ", description=" + description + ", user=" + user + ", status="
			+ status + ", created=" + created + ", updated=" + updated + ", flag=" + flag + "]";
}



}
