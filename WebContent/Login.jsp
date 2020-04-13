<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
 <link href="css/login.css" rel="stylesheet" type="text/css" >
</head>

<body>
 <div class="sidenav">
         <div class="login-main-text">
            <h2>Application<br> Login Page</h2>
            <p>Login or register from here to access.</p>
         </div>
      </div>
      <div class="main">
         <div class="col-md-6 col-sm-12">
            <div class="login-form">
		      <%String ErrorMes = (String)request.getAttribute("ErrorMes");  %>
              <%if(ErrorMes != null) {%>
                 <span style="color:red;"><%= ErrorMes %></span>
              <%}%>
               <form action="./Login" method="post">
                  <div class="form-group">
                     <label>User Name</label>
                     <input type="text" class="form-control" name="username" placeholder="User Name">
                  </div>
                  <div class="form-group">
                     <label>Password</label>
                     <input type="password" class="form-control" name="password" placeholder="Password">
                  </div>
                  <button type="submit" class="btn btn-black">Login</button>
                  <button type="submit" class="btn btn-secondary">Register</button>
               </form>
            </div>
         </div>
      </div>

 <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
 <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</body>
</html>