package com.m2gi.gestionprojet.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.m2gi.gestionprojet.modele.Users;
import com.m2gi.gestionprojet.repository.UserRepository;

@RestController
@RequestMapping(value="/api/connexion")
public class ConnexionService {

	@Autowired
	UserRepository userrepo;

	private static HashMap<String,String> tokens= new HashMap<String,String>();

	
	/*
	 * Get Methods
	 */
	@RequestMapping(value="/",method = RequestMethod.GET)
	public HashMap<String,String> getAllTokens(){
		return tokens;
	}
	
	/*
	 * Post Methods
	 */
	@RequestMapping(value="/auth",method = RequestMethod.POST)
	public String Authentification(@RequestParam(value="username") String username,@RequestParam(value="password") String password){		

		Users user=  userrepo.getByUserNameandPassword(username, password);
		if(user != null){
			if(!tokens.containsKey(user.getUsername())){
				tokens.put(user.getUsername(), generatetoken());
			}	
		}
		return tokens.get(user.getUsername());
	}
	
	@RequestMapping(value="/isauth",method = RequestMethod.POST)
	public boolean isAuthentified(@RequestParam(value="username") String username,@RequestParam(value="token") String token){		
		Users user=  userrepo.getByUserName(username);
		
		if(user != null){
			if(tokens.get(user.getUsername()).equals(token)){
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value="/decon",method = RequestMethod.DELETE)
	public boolean Deconnecte(@RequestParam(value="username") String username,@RequestParam(value="token") String token){		

		Users user=  userrepo.getByUserName(username);
		if(user != null){
			if(tokens.get(user.getUsername()).equals(token)){
				tokens.remove(user.getUsername());
				return true;
			}
		}
		return false;
	}

	private static String generatetoken() {
		int length = 126;
		boolean generer = false;
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890²&é\"'(-è_çà)=&é~{[|`\\^@]}/*-.!:;,<>ù%^¨$£¤?§°+"; 
		StringBuffer pass = null;
		while(!generer){
			pass = new StringBuffer();
			for(int x=0;x<length;x++)   {
				int i = (int)Math.floor(Math.random() * (chars.length() -1));
				pass.append(chars.charAt(i));
			}

			if(!tokens.containsValue(pass.toString())){
				generer = true;
			}
		}

		return pass.toString();
	}
}
