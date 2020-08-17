var DataApp = angular.module('DataApp', ['ui.bootstrap','ngRoute']);

DataApp.config(['$routeProvider', function($routeProvider){
    $routeProvider
    .when('/', {
      templateUrl: 'templates/top.html'
    })
    .when('/menu', {
      templateUrl: 'templates/menu.html',
      controller: 'MenuController'
    })
    .when('/top', {
      templateUrl: 'templates/top.html'
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



DataApp.controller('LoginController', ['$scope', '$http', '$window','$httpParamSerializerJQLike',
	 function($scope, $http, $window, $httpParamSerializerJQLike){
	   sessionStorage.removeItem('jwt');
	
       $scope.submit = function(){
    	  var method = "POST";	
    	  var url = 'api/login';	
    		
    	  $http({
    	          method: method,
    	          headers : {
                      'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                  },
                  transformRequest: $httpParamSerializerJQLike,
    	          url: url,
    	          data: { username: $scope.username, password: $scope.password }
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
    	
    	$scope.register = function(){
      	  var method = "POST";	
      	  var url = 'api/login/register';	
      		
      	  $http({
      	          method: method,
      	          headers : {
                        'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                    },
                    transformRequest: $httpParamSerializerJQLike,
      	          url: url,
      	          data: { username: $scope.mdusername, password: $scope.mdpassword }
      	        }).then(function successCallback(response){
      	        	$scope.register_message = 'Registerd your new ID. Please login.';
      	        }, function errorCallback(response) {
      	        	$scope.register_message = 'Error occurred. Please check your input.';
      	        });
      	};
      	
    }]);


DataApp.controller('MenuController', ['$uibModal','$scope', '$http', '$location','$httpParamSerializerJQLike', '$window',
	function($uibModal, $scope, $http, $location, $httpParamSerializerJQLike, $window){

	var method = "POST";	
	var url = 'api/menu/BodyData';	

	var jwt = sessionStorage.getItem('jwt');

    $scope.pageLimit = 10; 
    $scope.limitBegin = 0; 
 

	$http({
          method: method,
          headers : {
              'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
          },
          transformRequest: $httpParamSerializerJQLike,
          url: url,
          data: { jwt: jwt}
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

	    }, function errorCallback(response) {
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
    		templateUrl : 'templates/modal.html',
    	    controller: function ($scope, $uibModalInstance) {
    	          $scope.ok = function () {

    	        	  $http({
    	    	          method: 'DELETE',
    	    	          headers : {
    	                      'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
    	                  },
    	                  transformRequest: $httpParamSerializerJQLike,
    	    	          url:  'api/menu/trash',
    	    	          data: { jwt: jwt, id: id}
    	    	        }).then(function successCallback(response){
    	    	            $uibModalInstance.close();
    	    	            tabledata.splice(index,1);

    	    	        }, function errorCallback(response) {
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

    	var tabledata = $scope.data_source;

  		$uibModal.open({
    		templateUrl : 'templates/insertmodal.html',
    	    controller: function ($scope, $uibModalInstance) {

    	    	$scope.register = function () {
    	    		
    	        $http({
    	             method: 'POST',
    	              headers : {
    	                   'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
    	               },
    	               transformRequest: $httpParamSerializerJQLike,
    	               url: 'api/menu/insertdata',
    	       	       data: { jwt: jwt, height: $scope.mdheight, weight: $scope.mdweight }
    	           }).then(function successCallback(response){
	    	            $uibModalInstance.close();
	    	            $window.location.reload();
    	             	   
    	            }, function errorCallback(response) {
    	              $location.path('/error');
    	           
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
	var url = 'api/menu/BloodData';	

	var jwt = sessionStorage.getItem('jwt');

	$http({
          method: method,
          headers : {
              'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
          },
          transformRequest: $httpParamSerializerJQLike,
          url: url,
          data: { jwt: jwt}
        }).then(function successCallback(response){
        	var resdata = response.data;
        	
	        $scope.gtp = resdata[0].gtp;
	        $scope.hdl = resdata[0].hdl;
	        $scope.ldl = resdata[0].ldl;
	        $scope.tg = resdata[0].tg;
	        $scope.fpg = resdata[0].fpg;

	    }, function errorCallback(response) {
   	        $location.path('/error');
     })  
  	
  }]);

DataApp.controller('AuthLogin', ['$scope', '$http', '$window','$httpParamSerializerJQLike','$location',
	 function($scope, $http, $window, $httpParamSerializerJQLike, $location){
     	
     	
 	   var originalurl = $location.absUrl();
 	   var urlParm = originalurl.split('?')[1];

       $scope.authentication = function(){
    	  sessionStorage.removeItem('jwt');
     	  
    	  var method = "POST";	
     	  var url = 'api/login';	
     		
     	  $http({
     	          method: method,
     	          headers : {
                       'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                   },
                   transformRequest: $httpParamSerializerJQLike,
     	          url: url,
     	          data: { username: $scope.username, password: $scope.password }
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
      	  var url = 'api/Open/Authorization';
      	  
     	  var jwt = sessionStorage.getItem('jwt');

      	  $http({
      	          method: method,
      	          headers : {
                        'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                   },
                  transformRequest: $httpParamSerializerJQLike,
      	          url: url,
   	              data: { jwt: jwt}
      	        }).then(function successCallback(response){
      	        	var resdata = response.data;
      	        	var url = originalurl_temp + '&username=' + resdata.username + '&confirmation=' + resdata.password ;
       	        	$window.location.href = url;
      	        }, function errorCallback(response) {
      	        	console.log(response);

      	      });
      	};

    	
  }]);
