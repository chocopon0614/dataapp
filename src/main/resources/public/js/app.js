var DataApp = angular.module('DataApp', ['ngAnimate','toaster', 'ui.bootstrap', 'ngRoute']);

DataApp.config(['$routeProvider', function($routeProvider){
    $routeProvider
    .when('/', {
      templateUrl: 'templates/top.html'
    })
    .when('/bodydata', {
      templateUrl: 'templates/bodydata.html',
      controller: 'BodyController'
    })
    .when('/blooddata', {
      templateUrl: 'templates/blooddata.html',
      controller: 'BloodController'
    })
    .when('/error', {
      templateUrl: 'templates/error.html'
    })
    .otherwise({
      redirectTo: '/'
    });
}]);



DataApp.controller('LoginController', ['$scope', '$http', '$window',
'$httpParamSerializerJQLike', 
	 function($scope, $http, $window, $httpParamSerializerJQLike){
	   sessionStorage.removeItem('jwt');
	
       $scope.submit = function(){
    	  var method = "POST";	
    	  var url = 'login/userlogin';	
    		
    	  $http({
    	          method: method,
    	          headers : {
                      'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                  },
                  transformRequest: $httpParamSerializerJQLike,
    	          url: url,
//fixme temporary fixed 
//    	          data: { username: $scope.username, password: $scope.password }
    	          data: { username: 'test1', password: 'password'}
    	        }).then(function successCallback(response){
    	        	var resdata = response.data;
    	        	var jwt = resdata.JWT;
    	        	sessionStorage.setItem('jwt', jwt);
    	        	
    	        	$window.location.href = 'main.html';
    	        }, function errorCallback(response) {
    	        	var sts = response.status;

    	        	if(sts == 400){
      	        	   $scope.login_message = 'Password is wrong.';
    	        	}else{
         	           $scope.login_message = 'Your ID does not exist.';
    	        	}
    	      });
    	};
    }]);


DataApp.controller('BodyController', ['$uibModal','$scope', '$http', '$location'
,'$httpParamSerializerJQLike', 'toaster' ,'$window',
	function($uibModal, $scope, $http, $location, $httpParamSerializerJQLike, toaster, $window){
	var method = "POST";	
	var url = 'menu/bodydata';	

    $scope.pageLimit = 10; 
    $scope.limitBegin = 0; 

	$http({
          method: method,
          headers : {
              'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
          },
          transformRequest: $httpParamSerializerJQLike,
          url: url,
          data: { jwt: sessionStorage.getItem('jwt')}
        }).then(function successCallback(response){
        	var resdata = response.data;
        	
        	resdata.forEach(function(elem, index){
          	  var d = new Date(elem.createTime);
              var yyyy = d.getUTCFullYear();
              var MM = ('0' + (d.getUTCMonth() + 1)).slice(-2);
              var dd = ('0' + d.getUTCDate()).slice(-2);
              var hh = ('0' + d.getUTCHours()).slice(-2);
              var mm = ('0' + d.getUTCMinutes()).slice(-2);
              var ss = ('0' + d.getUTCSeconds()).slice(-2);
              var displayTime = dd + '-' + MM + '-' + yyyy + ' ' + hh + ':' + mm + ':' + ss;

        	  resdata[index].createTime = displayTime;
        	});
        	
        	$scope.data_source = resdata;

	    }, function errorCallback() {
   	        $location.path('/error');
     })  

     $scope.pagerClick = function (num) {
        $scope.limitBegin = num * $scope.pageLimit;
     };
 
     $scope.pagerArr = function(num) {
        num = Math.ceil(num); 
        var array = [];
        for (var i = 0; i < num; i++) {
            array[i] = i; 
        }
        return array;
    };


    $scope.setId = function(id, index){
    	var tabledata = $scope.data_source;
    	
    	$uibModal.open({
    		templateUrl : 'templates/deletemodal.html',
    	    controller: function ($scope, $uibModalInstance) {
    	          $scope.ok = function () {
    	        	  $http({
    	    	          method: 'DELETE',
    	    	          headers : {
    	                      'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
    	                  },
    	                  transformRequest: $httpParamSerializerJQLike,
    	    	          url:  'menu/trash',
    	    	          data: { jwt: sessionStorage.getItem('jwt'), id: id}
    	    	        }).then(function successCallback(response){
                            var resdata = response.data;;
	
    	    	            $uibModalInstance.close();
    	    	            tabledata.splice(index,1);
          	                
                            var d = new Date(resdata.createTime);
                            var yyyy = d.getUTCFullYear();
                            var MM = ('0' + (d.getUTCMonth() + 1)).slice(-2);
                            var dd = ('0' + d.getUTCDate()).slice(-2);
                            var hh = ('0' + d.getUTCHours()).slice(-2);
                            var mm = ('0' + d.getUTCMinutes()).slice(-2);
                            var ss = ('0' + d.getUTCSeconds()).slice(-2);
                            var displayTime = dd + '-' + MM + '-' + yyyy + ' ' + hh + ':' + mm + ':' + ss;


                            toaster.success({title: "Deleted", 
                            body: "Time:" + displayTime + " Height:" + resdata.Height + " Weight:" + resdata.Weight});

    	    	        }, function errorCallback() {
    	    	            $uibModalInstance.close();

    	    	        });

    	          };
    	        
    	          $scope.cancel = function () {
    	            $uibModalInstance.dismiss('cancel');
    	          };
    	      }
          });
  	};

  	
  	$scope.insertdata = function(){

  		$uibModal.open({
    		templateUrl : 'templates/insertmodal.html',
    	    controller: function ($scope, $uibModalInstance) {

    	    	$scope.register = function () {
	
	             if(!$scope.mdheight) {$scope.error_message_height = "Height is required." ; return;};
	             if(isNaN($scope.mdheight)) {$scope.error_message_height = "Height must be numeric." ; return;};
	             if(!$scope.mdweight) {$scope.error_message_weight = "Weight is required." ; return;};
	             if(isNaN($scope.mdweight)) {$scope.error_message_weight = "Weight must be numeric." ; return;};
    	    		
    	        $http({
    	             method: 'POST',
    	              headers : {
    	                   'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
    	               },
    	               transformRequest: $httpParamSerializerJQLike,
    	               url: 'menu/insertdata',
    	       	       data: { jwt: sessionStorage.getItem('jwt'), height: $scope.mdheight, weight: $scope.mdweight }
    	           }).then(function successCallback(){
	    	            $uibModalInstance.close();
                        toaster.success({title: "inserted", 
                                 body: " Height:" + $scope.mdheight + " Weight:" + $scope.mdweight + "<br>refreshing in 5sec..",
                                 bodyOutputType: 'trustedHtml'});
                        setTimeout(
                           function () {
	    	                   $window.location.reload();
                          },
                           "5000"
                        );

    	            }, function errorCallback(response) {
	
    	        	var sts = response.status;
    	        	  if(sts == 400){
	                   var resdata = response.data;

      	        	   $scope.error_message_height = resdata.Height;
      	        	   $scope.error_message_weight = resdata.Weight;
      	        	   $scope.error_message_other = resdata.Other;
    	        	  }else{
    	              $location.path('/error');
    	        	 }
    	        });

    	  };
    	        
    	 $scope.cancel = function () {
    	       $uibModalInstance.dismiss('cancel');
    	   };
    	 }
      });
  	};
  	
  }]);


DataApp.controller('BloodController', ['$uibModal','$scope', '$http', '$location','$httpParamSerializerJQLike', '$window',
	function($uibModal, $scope, $http, $location, $httpParamSerializerJQLike, $window){

	var method = "POST";	
	var url = 'menu/blooddata';	


	$http({
          method: method,
          headers : {
              'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
          },
          transformRequest: $httpParamSerializerJQLike,
          url: url,
          data: { jwt: sessionStorage.getItem('jwt')}
        }).then(function successCallback(response){
        	var resdata = response.data;
        	
	        $scope.gtp = resdata[0].gtp;
	        $scope.hdl = resdata[0].hdl;
	        $scope.ldl = resdata[0].ldl;
	        $scope.tg = resdata[0].tg;
	        $scope.fpg = resdata[0].fpg;

	    }, function errorCallback() {
   	        $location.path('/error');
     })  


  	$scope.updatedata = function(name){
	    
        var bloodname = name;

  		$uibModal.open({
    		templateUrl : 'templates/updatemodal.html',
    	    controller: function ($scope, $uibModalInstance) {

    	    	$scope.register = function () {

 	             if(!$scope.mdblood) {$scope.error_message_value = "New value is required." ; return;};
	             if(isNaN($scope.mdblood)) {$scope.error_message_value = "New value must be numeric." ; return;};
   	    		
    	        $http({
    	             method: 'POST',
    	              headers : {
    	                   'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
    	               },
    	               transformRequest: $httpParamSerializerJQLike,
    	               url: 'menu/updatedata',
    	       	       data: { jwt: sessionStorage.getItem('jwt'), newvalue: $scope.mdblood, bloodname: bloodname }
    	           }).then(function successCallback(){
	    	            $uibModalInstance.close();
	    	            $window.location.reload();
    	             	   
    	            }, function errorCallback(response) {
	    	        
                     var sts = response.status;

    	        	  if(sts == 400){
	                   var resdata = response.data;

      	        	   $scope.error_message_value = resdata.Value;
      	        	   $scope.error_message_other = resdata.Other;

    	        	  }else{

    	              $location.path('/error');
    	        	 }
    	           
    	        });

    	  };
    	        
    	 $scope.cancel = function () {
    	       $uibModalInstance.dismiss('cancel');
    	   };
    	 }
      });
  	};
  	
  }]);

DataApp.controller('AuthLogin', ['$scope', '$http', '$window','$httpParamSerializerJQLike','$location',
	 function($scope, $http, $window, $httpParamSerializerJQLike, $location){
     	
     	
 	   var originalurl = $location.absUrl();
 	   var urlParm = originalurl.split('?')[1];

       $scope.authentication = function(){
    	  sessionStorage.removeItem('jwt');
     	  
    	  var method = "POST";	
     	  var url = 'login/userlogin';	
     		
     	  $http({
     	          method: method,
     	          headers : {
                       'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                   },
                   transformRequest: $httpParamSerializerJQLike,
     	          url: url,
//fixme temporary fixed 
//     	          data: { username: $scope.username, password: $scope.password }
    	          data: { username: 'test1', password: 'password'}
     	        }).then(function successCallback(response){
     	        	var resdata = response.data;
     	        	var jwt = resdata.JWT;
     	        	sessionStorage.setItem('jwt', jwt);
     	        	
   	        	    $window.location.href = 'authorization.html?' + urlParm;
     	        	
     	        }, function errorCallback(response) {
     	        	var sts = response.status;

     	        	if(sts == 400){
       	        	   $scope.login_message = 'Password is wrong.';
     	        	}else{
          	           $scope.login_message = 'Your ID does not exist.';
     	        	}
     	      });
     	};
     	
   }]);


DataApp.controller('AuthController', ['$scope', '$http', '$window','$httpParamSerializerJQLike','$location',
	 function($scope, $http, $window, $httpParamSerializerJQLike, $location){
    	
	  var url = $location.absUrl();
 	  var urlParm = url.split('?')[1];
 	  var originalurl = urlParm.split('&')[0];
 	  
 	  var originalurl_temp = decodeURIComponent(originalurl.split('=')[1]);
	
	  $scope.authorization = function(){
      	  var method = "POST";	
      	  var url = 'open/authorization';
      	  
      	  $http({
      	          method: method,
      	          headers : {
                        'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                   },
                  transformRequest: $httpParamSerializerJQLike,
      	          url: url,
   	              data: { jwt: sessionStorage.getItem('jwt')}
      	        }).then(function successCallback(response){
      	        	var resdata = response.data;
      	        	var url = originalurl_temp + '&username=' + resdata.username + '&confirmation=' + resdata.password ;
       	        	$window.location.href = url;
      	        }, function errorCallback(response) {
      	        	console.log(response);

      	      });
      	};

    	
  }]);
