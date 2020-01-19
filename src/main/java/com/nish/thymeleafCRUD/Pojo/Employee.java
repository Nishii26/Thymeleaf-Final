package com.nish.thymeleafCRUD.Pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class Employee {
	private String    name;
    private String    email_;
    private String    department_;
    private String    gender;
    private Long      contactno;
    private Timestamp doj;
    private String    username;
    private String    password;
    private String    usertype;
    private Integer   id;
  
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getContactno() {
		return contactno;
	}
	public void setContactno(Long contactno) {
		this.contactno = contactno;
	}
	public Timestamp getDoj() {
		return doj;
	}
	public void setDoj(Timestamp doj) {
		this.doj = doj;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail_() {
		return email_;
	}
	public void setEmail_(String email_) {
		this.email_ = email_;
	}
	public String getDepartment_() {
		return department_;
	}
	public void setDepartment_(String department_) {
		this.department_ = department_;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}
