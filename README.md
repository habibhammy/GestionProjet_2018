## Gestion de projet : Comptes Utilisateurs API
### 1 - Introduction : 
##### 1-1 Descriptif
Ce projet est un service web (API) permettant la création, suppression, modification de comptes utilisateurs. Elle permet aussi une connexion/déconnexion d'un utilisateur.

##### 1-2 Auteurs

- HAMMY Mohammed El Habib
- **Email**: habib.hammy@gmail.com
- KABBOUCH Nasreddine
- **Email**: kabbouchnasreddine@gmail.com
### 2 - Mode d'utilisation :
#### 2-1 Prérequis:
Pour le bon fonctionnement de cette API, les programmes suivants sont nécessaires :

  * JRE 8 ou ultérieur
  * H2 database 1.4.196
  * Tomcat 8.5.14
   * IDE (Eclipse)
   * Maven 3.5.2
    
#### 2-2 Démarage:
Pour exécuter ce projet, suivez les instructions suivantes :

1. Dans Eclipse faite **{ [RightClick]>Maven>Update Porject }** puis **{ OK }** dans l'onglet qui s'ouvrira. Cela permettra d'importer toutes les dépendences du projet.

2. Puis faite **{ [RightClick]>Run As>Spring Boot App }** pour lancer le service.
 
### 3 - Descriptif Technique :
#### 3-1 Les routes :
Ce projet est un service web. il peut être requetté par d'autres applications via HTTP afin de remplir les fonctionnalités liées à la gestion des comptes utilisateurs. Il offre la possibilité de faire les requêtes suivantes :
 
 <span style="color:#193366">__1- [GET] "http://~/users/" :__</span>
 
 Cette route permet de récupérer l'ensemble des objets users 
 présent dans la base de données.
 
 Les users sont renvoyés sous format JSON :
    
```json
{
  "id": 100,
  "username": "alice",
  "email": "alice@exemple.com",
  "firstname": "alice",
  "lastname": "alice",
  "country": "FRANCE"
}
```
   
   <span style="color:#193366">__2- [GET] "http://~/users/{id}" :__</span>
   
   Cette route permet de récupérer l'objet user correspondant à l'id passé en paramètre.
   
   L'objet est renvoyé sous le même format JSON.
    
   <span style="color:#193366">__3- [POST] "http://~/users/add" :__</span>
   
   Cette route permet d'ajouter un utilisateur à la base de donnée en utilisant la méthode **POST**
   
   Elle prend en paramètre un username passé dans le header de la requête.
     
    
  <span style="color:#193366">__4- [PUT] "http://~/users/update":__</span>
  
  Cette route permet de modifier les informations d'un utilisateur dans la base de donnée en utilisant la méthode **PUT**
  
  Elle prend en paramètre un objet user sous format JSON dans le body de la requête.
     
   <span style="color:#193366">__5- [DELETE] "http://~/users/delete/{id}":__</span>
   
   Cette route permet de supprimer un utilisateur de la base de donnée en utilisant la méthode **DELETE**
   
   Elle prend l'id de l'utilisateur à supprimer comme paramètre dans l'URL de la requête.
     
   <span style="color:#193366">__6- [GET] "http://~/connexion/":__</span>
   
   Cette route permet de récupérer l'ensemble des correspendances des user-token générées.
     
   <span style="color:#193366">__7- [POST] "http://~/connexion/auth":__</span>
   
   Cette route permet à un utilisateur dans la base de donnée de s'identifier.
   
   Elle prend en paramètre un username et un mot de passe dans le header de la requête.
   
   Elle renvoie le token généré.
     
   <span style="color:#193366">__8- [POST] "http://~/connexion/isauth":__</span>
   
   Cette route permet de générer le token d'un utilisateur. Cela permettra de vérifier si un utilisateur est bien authentifié.
   
   Elle prend en paramètre un username et son token passé dans le header de la requête.
   
   Elle renvoie ***'TRUE'***  si l'utilisateur est bien connecté sinon elle retourne ***'FALSE'*** .
     
   <span style="color:#193366">__9- [DELETE] "http://~/connexion/decon":__</span>
   
   Cette route permet à un utilisateur de ce déconnecter.
   
   Elle prend en paramètre un username et son token passé dans le header de la requête.
   
   Elle renvoie ***'TRUE'*** si la déconnection s'est bie npassé sinon elle renvoie ***'FALSE'*** .
    
#### 3-2 Le model :
Le model utilisé dans ce projet est constitué de la classe **USERS**.
```java
public class Users {
	
	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="username")
	@JsonProperty(value = "username")
    private String username;
	
	@Column(name="passwords")
	@JsonIgnore
    private String passwords;

	@Column(name="email")
	@JsonProperty(value = "email")
	private String email;
	
	@Column(name="firstname")
	@JsonProperty(value = "firstname")
	private String firstname;
	
	@Column(name="lastname")
	@JsonProperty(value = "lastname")
	private String lastname;
	
	@Column(name="country")
	@JsonProperty(value = "country")
	private String country;
 }
```
#### 3-3 Les services:

Dans ce projet deux services différents coexistent pour gérer les différentes requêtes décrites.

Le service **UserService**
```java
@RestController
@RequestMapping(value="/user")
public class UserService {

  @Autowired
  UserRepository userrepo;

  @RequestMapping(value="/",method = RequestMethod.GET)
  @ResponseBody
  public List<Users> getAllUsers(){...}
  @RequestMapping(value="/{id}",method = RequestMethod.GET,produces={"application/json"})
  @ResponseBody
  public Users getUser(@PathVariable long id){...}

  @RequestMapping(value="/add",method = RequestMethod.POST)
  public Users addUser(@RequestParam(value="username") String username){...}

  @RequestMapping(value="/update",method = RequestMethod.PUT)
  public Users updateUser(@RequestBody Users user) throws Exception {...}

  @RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
  public List<Users> deleteStudent(@PathVariable long id) throws Exception {...}
}
```


Le service **ConnexionService**
```java

@RestController
@RequestMapping(value="/connexion")
public class ConnexionService {

  @Autowired
  UserRepository userrepo;

  private static HashMap<String,String> tokens= new HashMap<String,String>();

  @RequestMapping(value="/",method = RequestMethod.GET)
  public HashMap<String,String> getAllTokens(){...}
  
  @RequestMapping(value="/auth",method = RequestMethod.POST)
  public String Authentification(@RequestParam(value="username") String username,@RequestParam(value="password") String password){...}
  
  @RequestMapping(value="/isauth",method = RequestMethod.POST)
  public boolean isAuthentified(@RequestParam(value="username") String username,@RequestParam(value="token") String token){...}

  @RequestMapping(value="/decon",method = RequestMethod.DELETE)
  public boolean Deconnecte(@RequestParam(value="username") String username,@RequestParam(value="token") String token){...}

  private static String generatetoken() {...}
}
```
#### 3-4 La base de données:
La base de données a été géré par le moteur de base de données en mémoire H2. Les fichiers ***schema.sql*** et ***data.sql*** permettent d'initialiser la base de données à chaque déployement.

La classe ***UserRepository*** représente l'interface DAO responsable de la connexion à la base de données.
```java

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
```
