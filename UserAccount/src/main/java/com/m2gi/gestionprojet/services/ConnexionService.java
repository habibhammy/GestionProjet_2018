package com.m2gi.gestionprojet.services;

import java.util.Base64;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.m2gi.gestionprojet.modele.Users;
import com.m2gi.gestionprojet.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
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
		byte[] valueDecoded = Base64.getDecoder().decode(password.getBytes());
		password = new String(valueDecoded);
		//System.out.println("password decoded:"+password);
		
		try {
			Users user=  userrepo.getByUserNameandPassword(username, password);
			if(user != null){
				if(!tokens.containsKey(user.getUsername())){
					tokens.put(user.getUsername(), generatetoken());
				}	
			}
			return "{ \"token\":\""+tokens.get(user.getUsername())+"\" }";
		} catch (Exception e) {
			return "{ \"error\": \"Username ou Mot de passe incorrect\"";
		}
	}
	
	@RequestMapping(value="/isauth",method = RequestMethod.POST)
	public String isAuthentified(@RequestParam(value="username") String username,@RequestParam(value="token") String token){		
		try {
			Users user=  userrepo.getByUserName(username);
			
			if(user != null){
				if(tokens.get(user.getUsername()).equals(token)){
					return "{ \"isAuth\":\""+true+"\" }";
				}
			}
			return "{ \"isAuth\":\""+false+"\" }";
		} catch (Exception e) {
			return "{ \"error\":\"Username ou token incorrect\" }";
		}
	}

	@RequestMapping(value="/decon",method = RequestMethod.DELETE)
	public String Deconnecte(@RequestParam(value="username") String username,@RequestParam(value="token") String token){		

		try {
			Users user=  userrepo.getByUserName(username);
			if(user != null){
				if(tokens.get(user.getUsername()).equals(token)){
					tokens.remove(user.getUsername());
					return "{ \"deconnected\":\""+true+"\" }";
				}
			}
			return "{ \"deconnected\":\""+false+"\" }";
		} catch (Exception e) {
			return "{ \"error\":\"Username ou token incorrect\" }";
		}
	}

	private static String generatetoken() {
		int length = 126;
		boolean generer = false;
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890²&é'(-è_çà)=&é~{[|`^@]}*-.!:;,<>ù%^¨$£¤?§°+"; 
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
