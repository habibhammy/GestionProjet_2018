package com.m2gi.gestionprojet.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.*;

import com.m2gi.gestionprojet.modele.Users;

public interface UserRepository  extends JpaRepository<Users, Long> {
	@Modifying
	@Transactional
	@Query(value="delete from Users u where u.id = ?1")
	void deleteById(Long id);
	
	
	@Query(value="select u from Users u where u.username= ?1 and u.passwords = ?2")
	Users getByUserNameandPassword(String username, String password);
	
	
	@Query(value="select u from Users u where u.username= ?1")
	Users getByUserName(String username);
}
