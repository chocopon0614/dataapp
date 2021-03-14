# Name
DataApp

<br>

# Features
 
This application can manage your health data and simply show it on table. <br>
The stored data is accessed by other application.

The connection with other application is conducted by Oauth2 Authorization Code Grant.<br>
The resource owner's information in this application is securely accessed by other application with token issued by the authorization server.

<br>

    |Other Application|   <---------------------------------->   |Authorization Server|
      |                              (authentication/authorization)　
      |                              (issued access token)
      |      
      | 
      | 
      -------------------->   |DataApp|
         (access token)


<br>

# Usage
Please access to the below link.<br>


<https://chocopon-forest.com/DataApp/#!/>

<br>

If you want to run by yourself, please build by maven install and run a created war file on Tomcat.

<br>

# Requirement
The application is running on AWS EC2. 
MySQL8.0 is used as database on docker on AWS EC2

<br>
 
# Technologies
* Maven 
* J2EE
* JAX-RS
* JPA
* JWT
* AngularJS
