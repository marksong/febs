package com.febs.auth.core.po;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

import com.febs.common.orm.hibernate.AuditEntity;

@Entity(name="auth_user")
public class User extends AuditEntity {

	private static final long serialVersionUID = 2107273616559043718L;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String nickName;

	public User() {
		super();
	}
	
	public User(String username, String password, String email, String nickName) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.nickName = nickName;
	}

	@NotBlank
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="nick_name")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
