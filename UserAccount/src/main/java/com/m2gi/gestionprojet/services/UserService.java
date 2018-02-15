package com.m2gi.gestionprojet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.m2gi.gestionprojet.modele.Users;
import com.m2gi.gestionprojet.repository.UserRepository;

@RestController
@RequestMapping(value="/api/user")
public class UserService {

	@Autowired
	UserRepository userrepo;

	/*
	 * Get Methods
	 */
	@RequestMapping(value="/",method = RequestMethod.GET)
	@ResponseBody
	public List<Users> getAllUsers(){
		//System.out.println("gestallusers() ==> "+userrepo.findAll().toString());
		return userrepo.findAll();
	}
	@RequestMapping(value="/{id}",method = RequestMethod.GET,produces={"application/json"})
	@ResponseBody
	public Users getUser(@PathVariable long id){
		//System.out.println("user="+userrepo.getOne(new Long(id)));
		return  userrepo.getOne(new Long(id));
	}

	/*
	 * Post Methods
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public Users addUser(@RequestParam(value="username") String username){
		Users user=new Users(username,"000A000");
		userrepo.saveAndFlush(user);
		return userrepo.getOne(new Long(user.getId()));
	}

	/*
	 * Put Methods
	 */
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public Users updateUser(@RequestBody Users user) throws Exception {
		
		if(userrepo.getOne(new Long(user.getId()))!=null){
			if(user.getPasswords()== null){
				user.setPasswords("000A000");
			}
			userrepo.saveAndFlush(user);
		}else{
			throw new Exception("Users "+user.getId()+" does not exists");
		}

		return userrepo.getOne(new Long(user.getId()));
	}

	/*
	 * Delete Methods
	 */
	@RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
	public List<Users> deleteStudent(@PathVariable long id) throws Exception {

		Users user = userrepo.getOne(new Long(id));

		if(user!=null){
			userrepo.deleteById(new Long(user.getId()));
		}else{
			throw new Exception("Users "+id+" does not exists");
		}
		return userrepo.findAll();
	}


}
