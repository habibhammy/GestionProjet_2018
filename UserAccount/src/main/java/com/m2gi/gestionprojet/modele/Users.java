package com.m2gi.gestionprojet.modele;

import java.util.Date;

import javax.persistence.*;


@Entity
public class Users {
	
	@Id
	@Column(name="id")
	private long id;
	
	@Column
    private String username;
	
	
	@Column
    private String passwords;

	@Column
	private String email;
	
	@Column
	private String firstname;
	
	@Column
	private String lastname;
	
	@Column
	private String country;
 
    public Users() {
    }
 
    public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	
    public Users(String name,String mdp) {
        this.id = (new Date()).getTime();
        this.username = name;
        this.passwords = mdp;
    }
  
    public long getId() {
        return id;
    }
 
    public String getName() {
        return this.username;
    }
 
    public void setName(String name) {
        this.username = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", username='" + this.username + '\'' +
                '}';
    }
}
