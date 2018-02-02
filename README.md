## Gestion de projet : Comptes Utilisateurs API
### 1 - Introduction : 
##### Déscriptif
Ce projet est un service web (API) permettant la création, suppression, modification de comptes utilisateurs. Elle permet aussi une connexion/déconnexion d'un utilisateur.

##### Auteurs

- HAMMY Mohammed El Habib
- **Email**: habib.hammy@gmail.com
- KABBOUCH Nasreddine
- **Email**: kabbouchnasreddine@gmail.com
### 2 - Mode d'utilisation :
#### 2-1 Prérequis:
Pour le bon fonctionnement de cette API, les programmes suivants sont nécéssaires :

  * JRE 8 ou ultérieur
  * H2 database 1.4.196
  * Tomcat 8.5.14
   * IDE (Eclipse)
   * Mavean 3.5.2
    
#### 2-2 Démarage:
Pour executer ce projet, suivez les instructions suivante :

1- Dans Eclipse faite { [RightClick]>Maven>Update Porject } puis { OK } dans l'ongletr qui s'ouvrira. Cela permettra d'importer tout les dépendence du projet.

2- Puis faite { [RightClick]>Run As>Spring Boot App } pour lancer le service.
 
### 3 - Descriptif Technique :
#### 3-1 Les routes :
Ce projet est un service web. il peut être requetté par d'autre programme va HTTP afin de remplir les fonctionnalités liée à la gestion des comptes utilisateurs. Il offre la possibilité de faire les requettes suivantes :
 
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
   
   2- [GET] "http://~/users/{id}":
     
   Cette route permet de récupérer L'objet users correspendant à l'id passé en paramétre.
   L'objet est renvoyé sous le même format JSON.
    
   3- [POST] "http://~/users/add":
     Cette route permet d'ajouter un utilisateur à la base de donnée.
     Elle prend en paramétre un objet users sous format JSON dans le body de la requête.
     
    
   4- [PUT] "http://~/users/update":
    
   5- [DELETE] "http://~/users/delete/{id}":
    
   6- [GET] "http://~/connexion/":
    
   7- [POST] "http://~/connexion/auth":
    
   8- [POST] "http://~/connexion/isauth":
    
   9- [DELETE] "http://~/connexion/decon":
    
#### 3-2 Le model :

#### 3-3 Les services:

#### 3-4 La base de données:
