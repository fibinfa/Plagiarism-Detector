(function(){
	angular
	.module("CodeSniffer")
	.controller("CourseController", CourseController);

	function CourseController($location,$rootScope,$http,$scope,$routeParams,FacultyService,CourseService,UserService) {
		var semID = $routeParams.semid;
		var user_id = $routeParams.uid;

		var vm = this;
		vm.showAssignments = showAssignments;
		vm.getAllUnregisteredCourses = getAllUnregisteredCourses;
		vm.addCourse = addCourse;
		vm.showSubmissions = showSubmissions;
		vm.addAssignment = addAssignment;
		vm.showAddAssignments = showAddAssignments;
		vm.showAssgn = false;
		vm.updateThreshold = updateThreshold;
		vm.createCourse = createCourse;
		vm.logout = logout;

		function init() {
			var user_promise = UserService.findUserById(user_id);
			user_promise.then(function (user) {
				if(user !=null){
					$scope.username = user.fName;
				}
				else{
					vm.semError="Not able to find user";
				}
			},
			function (error) {
				vm.semError="Not able to find user";
			});
			
			var promise = CourseService.courses(semID, user_id);
			promise.then(function (SemCourses) {
				if(SemCourses !=null){
					console.log(SemCourses.data);
					$scope.courseList = SemCourses.data;		
				}
				else{
					vm.courseerror="No courses registered for this semester";
				}
			},
			function (error) {
				vm.courseerror="No courses registered for this semester";
			});
		}
        init();

        function logout() {
            UserService.logOut(vm.uid).then($location.url('/'))
        }


		function showAssignments(item){
			$scope.courseAssignments = {};
			var details = item.currentTarget.getAttribute('id');

			var courseID = details.substring(0,details.indexOf("-"));
			var courseName = details.substring(details.indexOf("-")+1,details.length);

			var promise = FacultyService.assignment(courseID);
			promise.then(function (assignments) {
				if(assignments !=null){
					console.log("assignments");
					console.log(assignments.data);
					$scope.courseAssignments[courseName] = assignments.data;
				}
				else{
					vm.error="Assignments not found";
				}
			},
			function (error) {
				vm.error="Assignments not found";
			});
		};

		function getAllUnregisteredCourses(){

			var promise = CourseService.getAllUnregisteredCourses(user_id,semID);
			promise.then(function (courses) {
				if(courses !=null){
					$scope.courses = courses.data;
					console.log(courses.data);
				}
				else{
					vm.error="No courses not found";
				}
			},
			function (error) {
				vm.error="Not able to fetch courses";
			});

		};

		function showSubmissions(item){
			var assgnID = item.currentTarget.getAttribute('id');

			$location.url("/user/"+user_id+"/semester/"+semID+"/faculty/course/"+$scope.courseID+"/assignment/"+assgnID+"/reports");
		};


		function addCourse(){
			var courseList = $scope.selectedCourse;
			var courseId = courseList.substring(0,1);
			var promise = CourseService.addCourses(user_id,semID, courseId);
			promise.then(function (res) {
				vm.response = "Added Successfully";
			},
			function (error) {
				vm.response="Not able to add course";
			});

		};

		function addAssignment(assgn){
			console.log(assgn);
			var promise = FacultyService.addAssignment($scope.courseID,assgn);
			promise.then(function (res) {
				vm.assgn_response = "Assignment added Successfully";
			},
			function (error) {
				vm.assgn_response="Not able to add assignment";
			});

		};
		
		function createCourse(newcourse){
			console.log(newcourse);
			var promise = FacultyService.createCourse(user_id, semID, newcourse);
			promise.then(function (res) {
				vm.assgn_response = "Course added Successfully";
			},
			function (error) {
				vm.assgn_response="Not able to add course";
			});

		};

		function showAddAssignments(item){
			var details = item.currentTarget.getAttribute('id');

			var courseID = details.substring(0,details.indexOf("-"));
			$scope.courseID = courseID;
			var courseName = details.substring(details.indexOf("-")+1,details.length);
			$scope.courseName = courseName;
			vm.showAssgn = true;
		};
		
		function updateThreshold(assgn){
			console.log("update threshold");
			console.log(assgn);
			var promise = FacultyService.updateThreshold(assgn);
			promise.then(function (res) {
				vm.assgn_response = "Threshold updated successfully";
			},
			function (error) {
				vm.assgn_response="Not able to update threshold";
			});

		};

	}
})();