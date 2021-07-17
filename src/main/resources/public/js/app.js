var DataApp = angular.module('DataApp', [ 'ngRoute','wc.Directives', 'ngAnimate','toaster','ui.bootstrap']);

DataApp.config(['$routeProvider', function($routeProvider){
    $routeProvider
    .when('/', {
      templateUrl: 'templates/top.html',
      controller: 'TopController'
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



DataApp.controller('AccountsController', ['$uibModal','$scope', '$http', '$window', '$location',
'$httpParamSerializerJQLike', 
	 function($uibModal, $scope, $http, $window, $location, $httpParamSerializerJQLike){
	   sessionStorage.removeItem('jwt');
	
       $scope.login = function(){

          $scope.msg = '';
	      if(!$scope.username) {$scope.msg = "Username is required." ; return;};
	      if(!$scope.password) {$scope.msg = "Password is required." ; return;};
    		
    	  $http({
    	          method: 'POST',
    	          headers : {
                      'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                  },
                  transformRequest: $httpParamSerializerJQLike,
    	          url: 'accounts/login',
    	          data: { username: $scope.username, password: $scope.password }
    	        }).then(function successCallback(response){
    	        	var resdata = response.data;
    	        	sessionStorage.setItem('jwt', resdata.jwt);
    	        	
    	        	$window.location.href = 'main.html';
    	        }, function errorCallback() {
      	        	$scope.message = 'Login Error. Please try again.'
    	      });
    	};

  	   $scope.register = function(){

  		 $uibModal.open({
    		templateUrl : 'templates/registermodal.html',
    	    controller: function ($scope, $uibModalInstance) {

    	      $scope.ok = function () {

                $scope.msg=''
	            if(!$scope.mdusername) {$scope.msg = "Username is required." ; return;};
	            if(!$scope.mdpassword) {$scope.msg = "Password is required." ; return;};
    	    		
    	        $http({
    	             method: 'POST',
    	              headers : {
    	                   'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
    	               },
    	               transformRequest: $httpParamSerializerJQLike,
    	               url: 'accounts/register',
    	               data: { username: $scope.mdusername, password: $scope.mdpassword }
    	           }).then(function successCallback(){
                       $scope.mdusername=''
                       $scope.mdpassword=''
                       $scope.msg ='New account was created. Please login.'

    	            }, function errorCallback() {
                       $scope.mdusername=''
                       $scope.mdpassword=''
	                   $scope.msg = 'Registration Error. Please try again.'
	
    	        });

    	      };
    	        
    	 $scope.cancel = function () {
    	       $uibModalInstance.dismiss('cancel');
    	   };
    	 }
      });
  	};

      $scope.authentication = function(){
          var url = $location.absUrl();
 	      var urlParm = url.split('?')[1];

          $scope.msg = ''
	      if(!$scope.username) {$scope.msg = "Username is required." ; return;};
	      if(!$scope.password) {$scope.msg = "Password is required." ; return;};
    	  	
    	  $http({
    	        method: 'POST',
    	        headers : {
                    'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                },
                transformRequest: $httpParamSerializerJQLike,
    	        url: 'accounts/login',
    	        data: { username: $scope.username, password: $scope.password }
    	      }).then(function successCallback(response){
                  var resdata = response.data;
     	          sessionStorage.setItem('jwt', resdata.jwt);

    	          $window.location.href = 'authorization.html?' + urlParm;

    	      }, function errorCallback() {
      	          $scope.login_msg = 'Login Error. Please try again.'
    	      
             });
    	};

    }]);


DataApp.controller('AuthController', ['$scope', '$http', '$httpParamSerializerJQLike', '$window', '$location',
	 function($scope, $http, $httpParamSerializerJQLike, $window, $location){
      var jwt = sessionStorage.getItem('jwt');

	  var url = $location.absUrl();
 	  var urlParm = url.split('?')[1];
 	  var tmp = urlParm.split('&')[0];
 	  var originalUrl = decodeURIComponent(tmp.split('=')[1]); 

	  $scope.authorization = function(){

      	  $http({
      	          method: 'POST',
      	          headers : {
                        'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                   },
                  transformRequest: $httpParamSerializerJQLike,
      	          url: 'auth/authentication',
   	              data: { jwt: jwt}

      	        }).then(function successCallback(response){
      	        	var resdata = response.data;
      	        	var nextUrl = originalUrl + '&username=' + resdata.username + '&confirmation=' + resdata.password ;
       	        	$window.location.href = nextUrl;

      	      });
      	};


	  $scope.authcancel = function(){
      	     var nextUrl = originalUrl + '&error=access_denied';
       	     $window.location.href = nextUrl ;
      	};

    }]);


DataApp.controller('TopController', ['$uibModal','$scope', '$http', '$httpParamSerializerJQLike', '$window', '$location',
	 function($uibModal, $scope, $http, $httpParamSerializerJQLike, $window, $location){

      $scope.userdelete = function(){

  		$uibModal.open({
    		templateUrl : 'templates/userdeletemodal.html',
    	    controller: function ($scope, $uibModalInstance) {
	
    	      $scope.ok = function () {

    	        $http({
    	             method: 'DELETE',
    	              headers : {
    	                   'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
    	               },
    	               transformRequest: $httpParamSerializerJQLike,
    	               url: 'accounts/delete',
                       data: { jwt: sessionStorage.getItem('jwt')}

    	           }).then(function successCallback(){
    	        	  $window.location.href = 'index.html';

    	            }, function errorCallback() {
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


DataApp.controller('BodyController', ['$uibModal','$scope', '$http', '$location'
,'$httpParamSerializerJQLike', 'toaster' ,'$window',
	function($uibModal, $scope, $http, $location, $httpParamSerializerJQLike, toaster, $window){

    $scope.pageLimit = 10; 
    $scope.limitBegin = 0; 

	$http({
          method: 'POST',
          headers : {
              'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
          },
          transformRequest: $httpParamSerializerJQLike,
          url: 'menu/bodydata',
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


     $scope.deletedata = function(id, index){
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
    	    	          url:  'menu/deletedata',
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
    	    
　　　　　　　$scope.ok = function () {

	             $scope.msg_height = ''
	             $scope.msg_weight = ''
	             $scope.msg_other = ''

	             if(!$scope.mdheight) {$scope.msg_height = "Height is required." ; return;};
	             if(isNaN($scope.mdheight)) {$scope.msg_height = "Height must be numeric." ; return;};
	             if(!$scope.mdweight) {$scope.msg_weight = "Weight is required." ; return;};
	             if(isNaN($scope.mdweight)) {$scope.msg_weight = "Weight must be numeric." ; return;};

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
                                 body: " Height:" + $scope.mdheight + " Weight:" + $scope.mdweight + "<br>refreshing in 3sec..",
                                 bodyOutputType: 'trustedHtml'});
                        setTimeout(
                           function () {
	    	                   $window.location.reload();
                          },
                           "3000"
                        );

    	            }, function errorCallback(response) {
	
    	        	var sts = response.status;
    	        	  if(sts == 400){
	                   var resdata = response.data;

      	        	   $scope.msg_height = resdata.Height;
      	        	   $scope.msg_weight = resdata.Weight;
      	        	   $scope.msg_other = resdata.Other;

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


	$http({
          method: 'POST',
          headers : {
              'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
          },
          transformRequest: $httpParamSerializerJQLike,
          url: 'menu/blooddata',
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

　　　　　　　$scope.ok = function () {

	           $scope.msg_value = ''
	           $scope.msg_other = ''
 	           if(!$scope.mdblood) {$scope.msg_value = "New value is required." ; return;};
	           if(isNaN($scope.mdblood)) {$scope.msg_value = "New value must be numeric." ; return;};

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

      	        	   $scope.msg_value = resdata.Value;
      	        	   $scope.msg_other = resdata.Other;

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
