package com.m2gi.gestionprojet.modele;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Users {
	
	@Id
	@Column(name="id")
	@JsonIgnore
	private long id;
	
	@Column(name="username")
	@JsonProperty(value = "username")
    private String username;
	
	@Column(name="passwords")
	@JsonIgnore
    private String passwords;

	@Column(name="email")
	@JsonProperty(value = "email")
	//@JsonIgnore
	private String email;
	
	@Column(name="firstname")
	@JsonProperty(value = "firstname")
	//@JsonIgnore
	private String firstname;
	
	@Column(name="lastname")
	@JsonProperty(value = "lastname")
	//@JsonIgnore
	private String lastname;
	
	@Column(name="country")
	@JsonProperty(value = "country")
	//@JsonIgnore
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
 
    public String getUsername() {
        return this.username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", username='" + this.username + '\'' +
                ", email='" + this.email + '\'' +
                ", firstname='" + this.firstname + '\'' +
                ", lastname='" + this.lastname + '\'' +
                ", country='" + this.country + '\'' +
                '}';
    }
}
