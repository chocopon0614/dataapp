<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
 <link href="css/authorization.css" rel="stylesheet" type="text/css" >
</head>

<body>
<% session.setAttribute("username",(String)request.getParameter("username")); %>
<% session.setAttribute("password",(String)request.getParameter("password")); %>
	<form name="form_auth" action="./Authorization" method="post">
		<div class="wrap">
			<h1>Do you authorize that the application uses your account?</h1>
			<a class="button blue" href="javascript:form_auth.submit()">Cancel</a> 
			<a class="button red" href="javascript:form_auth.submit()">Authorization</a>
		</div>
	</form>
  
  <script src="js/authorization.js"></script>
</body>
</html>