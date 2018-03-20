package com.m2gi.gestionprojet.services;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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

	private static HashMap<Long,String> tokens= new HashMap<Long,String>();

	
	/*
	 * Get Methods
	 */
	@CrossOrigin
	@RequestMapping(value="/",method = RequestMethod.GET)
	public HashMap<Long,String> getAllTokens(){
		return tokens;
	}
	
	/*
	 * Post Methods
	 */
	@CrossOrigin
	@RequestMapping(value="/auth",method = RequestMethod.POST)
	public String Authentification(@RequestBody String cred/*@RequestParam(value="username") String username,@RequestParam(value="password") String password*/){		
		//JSONObject obj;
		try {
			//System.out.println(cred);
			JsonParser springParser = JsonParserFactory.getJsonParser();
			Map<String, Object> result = springParser.parseMap(cred);
			//obj = new JSONObject(cred);
			
			String username = (String)result.get("username");
			String password = (String)result.get("password");

			byte[] valueDecoded = Base64.getDecoder().decode(password.getBytes());
			password = new String(valueDecoded);
			//System.out.println("password decoded:"+password);
			Users user=  userrepo.getByUserNameandPassword(username, password);
			if(user != null){
				if(!tokens.containsKey(user.getId())){
					tokens.put(user.getId(), generatetoken());
				}	
			}
			return "{ "+"\"id\":\""+user.getId()+"\" ,"+
						"\"token\":\""+tokens.get(user.getId())+"\" }";
		} catch (Exception e) {
			return "{ \"error\": \"Username ou Mot de passe incorrect\" }";
		}
	}
	@CrossOrigin
	@RequestMapping(value="/isauth",method = RequestMethod.POST)
	public String isAuthentified(@RequestBody String cred/*@RequestParam(value="username") String username,@RequestParam(value="token") String token*/){		
		try {
			JsonParser springParser = JsonParserFactory.getJsonParser();
			Map<String, Object> result = springParser.parseMap(cred);
			//obj = new JSONObject(cred);
			
			String id = (String)result.get("id");
			String token = (String)result.get("token");
			
			Users user=  userrepo.getOne(new Long(id));
			
			if(user != null){
				if(tokens.get(user.getId()).equals(token)){
					return "{ \"isAuth\":"+true+" }";
				}
			}
			return "{ \"isAuth\":"+false+" }";
		} catch (Exception e) {
			return "{ \"error\":\"Username ou token incorrect\" }";
		}
	}
	@CrossOrigin
	@RequestMapping(value="/decon",method = RequestMethod.POST)
	public String Deconnecte(@RequestBody String cred/*@RequestParam(value="username") String username,@RequestParam(value="token") String token*/){		
		try {
			JsonParser springParser = JsonParserFactory.getJsonParser();
			Map<String, Object> result = springParser.parseMap(cred);
			//obj = new JSONObject(cred);
			
			String id = (String)result.get("id");
			String token = (String)result.get("token");
			
			Users user=  userrepo.getOne(new Long(id));
			
			if(user != null){
				if(tokens.get(user.getId()).equals(token)){
					tokens.remove(user.getId());
					return "{ \"deconnected\":"+true+" }";
				}
			}
			return "{ \"deconnected\":"+false+" }";
		} catch (Exception e) {
			return "{ \"error\":\"Username non connecté ou token incorrect\" }";
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
