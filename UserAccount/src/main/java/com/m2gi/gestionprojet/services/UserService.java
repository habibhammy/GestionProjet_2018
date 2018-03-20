package com.m2gi.gestionprojet.services;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
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
	@CrossOrigin
	@RequestMapping(value="/",method = RequestMethod.GET)
	@ResponseBody
	public List<Users> getAllUsers(){
		//System.out.println("gestallusers() ==> "+userrepo.findAll().toString());
		return userrepo.findAll();
	}
	@CrossOrigin
	@RequestMapping(value="/{id}",method = RequestMethod.GET,produces={"application/json"})
	@ResponseBody
	public Users getUser(@PathVariable long id){
		//System.out.println("user="+userrepo.getOne(new Long(id)));
		return  userrepo.getOne(new Long(id));
	}

	/*
	 * Post Methods
	 */
	@CrossOrigin
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public Users addUser(@RequestBody String cred/*@RequestParam(value="username") String username,@RequestParam(value="password") String password*/){
		
		JsonParser springParser = JsonParserFactory.getJsonParser();
		Map<String, Object> result = springParser.parseMap(cred);
		//obj = new JSONObject(cred);
		
		String username = (String)result.get("username");
		String password = (String)result.get("password");
		
		byte[] valueDecoded = Base64.getDecoder().decode(password.getBytes());
		password = new String(valueDecoded);		
		Users user=new Users(username,password);
		userrepo.saveAndFlush(user);
		return userrepo.getOne(new Long(user.getId()));
	}

	/*
	 * Put Methods
	 */
	@CrossOrigin
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
	@CrossOrigin
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
