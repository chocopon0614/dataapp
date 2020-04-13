<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
 <link href="css/style.css" rel="stylesheet" type="text/css" >
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <script type="text/javascript">
        var UserId = '<%=request.getAttribute("UserId") %>';
        var DataList_tmp = '';
        
        $.ajax({	
        	type: 'POST',
        	async: false,
            timeout: 10000,
            url: "https://dataappcloud.mybluemix.net/api/Open/TableData",
            data: { "UserId": UserId},
            dataType: 'json',
        	cache: false,
        }).done(function(data){
        	DataList_tmp = JSON.stringify(data);
        }).fail(function(e){
        }).always(function(e){
        });
        
    DataList = JSON.parse(DataList_tmp);
 </script>
</head>

<body onload="constructTable('#thead', '#tbody', DataList)">
	<div class="container">
		<div class="row">
			<div class="col-xl-3">
              <p class="font1">HELLO! <%=request.getAttribute("UserName") %> </p><br>
			</div>
		</div>	

		<div class="row">
			<div class="col-xl-12">
              <table class="table table-hover">
		        <thead class="thead-dark" id ="thead"> </thead>
		        <tbody id ="tbody"></tbody>
              </table> 
			</div>
		</div>
	</div>

 <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
 <script src="js/jsontotable.js" type="text/javascript"></script>
</body>
</html>