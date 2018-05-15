(function(){
	angular
	.module("CodeSniffer")
	.controller("DashboardController", dashboard);

	function dashboard($location,$rootScope,$http,$scope,$routeParams,SemesterService,UserService) {
		var vm = this;
		vm.showCourse = showCourses;
		vm.logout = logout;

		var user_id = $routeParams.uid;
		console.log("userid "+user_id);
		
		function init() {
			var user_promise = UserService.findUserById(user_id);
			user_promise.then(function (user) {
				if(user !=null){
					$scope.username = user.data.fName;
					$scope.userType = user.data.userType;
				}
				else{
					vm.semError="Not able to find user";
				}
			},
			function (error) {
				vm.semError="Not able to find user";
			});
			
			var promise = SemesterService.findSemestersByUserId(user_id);
			promise.then(function (semesters) {
				if(semesters !=null){
					$scope.semesters = semesters.data;
					console.log("semesters ");
					console.log(semesters.data);
				}
				else{
					vm.semError="You are not enrolled for any semester";
				}
			},
			function (error) {
				vm.semError="You are not enrolled for any semester";
			});
        }
        init();

        function logout() {
            UserService.logOut(vm.uid).then($location.url('/'))
        }

		function showCourses(item){
			var semID = item.currentTarget.getAttribute("id");
			console.log($scope.userType);

			if($scope.userType == "PROFESSOR"){
				$location.url("/user/"+user_id+"/semester/"+semID+"/faculty/course");
			}
			else if($scope.userType == "STUDENT"){
				$location.url("/user/"+user_id+"/semester/"+semID+"/student/course");
			}
		};


	}
})();