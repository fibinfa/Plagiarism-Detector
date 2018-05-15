(function() {
	angular
	.module("CodeSniffer")
	.controller("StudentCourseController", StudentCourseController);

	function StudentCourseController($location, $rootScope, $http, $scope, $routeParams, StudentService, CourseService, UserService) {
		var semID = $routeParams.semid;
		var user_id = $routeParams.uid;

		var vm = this;
		vm.showAssignments = showAssignments;
		vm.submitAssignment = submitAssignment;
		vm.doUploadFile = doUploadFileFunc;
		vm.compare = compare;
		vm.logout = logout;
		vm.getAllCourses = getAllCourses;
		vm.addCourse = addCourse;
		vm.checkUploadMethod = checkUploadMethod;
		
		vm.submitAssgn = false;
		vm.filesubmitAssgn = false;
		vm.gitsubmitAssgn = false;
		vm.submitButton = false;
		vm.assgnSubmitButton = false;

		$scope.subID = {};

		function init() {
			var user_promise = UserService.findUserById(user_id);
			user_promise.then(function (user) {
				if (user != null) {
					$scope.username = user.fName;
				}
				else {
					vm.semError = "Not able to find user";
				}
			},
			function (error) {
				vm.semError = "Not able to find user";
			});

			var promise = CourseService.courses(semID, user_id);
			promise.then(function (SemCourses) {
				if (SemCourses != null) {
					console.log(SemCourses.data);
					$scope.courseList = SemCourses.data;
				}
				else {
					vm.courseerror = "No courses registered for this semester";
				}
			},
			function (error) {
				vm.courseerror = "No courses registered for this semester";
			});
		}

		init();

		function logout() {
			UserService.logOut(vm.uid).then($location.url('/'))
		}

		function showAssignments(item) {
			$scope.courseAssignments = {};
			var details = item.currentTarget.getAttribute('id');

			var courseID = details.substring(0, details.indexOf("-"));
			var courseName = details.substring(details.indexOf("-") + 1, details.length);

			var promise = StudentService.assignment(courseID);
			promise.then(function (assignments) {
				if (assignments != null) {
					$scope.courseAssignments[courseName] = assignments.data;
				}
				else {
					vm.error = "Assignments not found";
				}
			},
			function (error) {
				vm.error = "Assignments not found";
			});
		};

		function getAllCourses() {

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

		function addCourse(course) {
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

		function submitAssignment(item) {
			var details = item.currentTarget.getAttribute('id');

			var assgnID = details.substring(0, details.indexOf("-"));
			$scope.courseID = details.substring(details.indexOf("-") + 1, details.indexOf("_"));
			$scope.assgnName = details.substring(details.indexOf("_") + 1, details.length);
			
			$scope.assgnID = assgnID;
			console.log("AssgnID " + $scope.assgnID);
			console.log("AssgnName " + $scope.assgnName);
			vm.submitAssgn = true;
		};
		
		function checkUploadMethod(){
			if(vm.uploadMethod === "ZIP"){
				vm.filesubmitAssgn = true;
				vm.submitButton = true;
				vm.gitsubmitAssgn = false;
			}
			else if (vm.uploadMethod === "GITHUB"){
				vm.gitsubmitAssgn = true;
				vm.submitButton = true;
				vm.filesubmitAssgn = false;
			}
		}

		function doUploadFileFunc($event) {
			var promise;

			if(vm.uploadMethod === "ZIP") {
				var file = $scope.uploadedFile;
				var url = "/api/user/" + user_id + "/sem/" + semID + "/course/" + $scope.courseID + "/assignment/" + $scope.assgnID + "/uploadFile";

				var data = new FormData();
				data.append('uploadfile', file);

				var config = {
						transformRequest: angular.identity,
						transformResponse: angular.identity,
						headers: {
							'Content-Type': undefined
						}
				};

				promise = StudentService.uploadFile(url, data, config);
				promise.then(function (response) {
					console.log(response.data);
					$scope.subID = response.data.substring(6,response.data.indexOf(","));
					console.log("subid "+$scope.subID);
					$scope.uploadResult = "Successfully uploaded file, now you can submit";
					vm.assgnSubmitButton = true;
				}, function (response) {
					$scope.uploadResult = "Error in uploading file";
				});
			}
			else if (vm.uploadMethod === "GITHUB") {

				var githubURL = vm.githubURL;
				console.log(githubURL);

				promise = StudentService
				.uploadGithubCode(githubURL, user_id, semID, $scope.courseID, $scope.assgnID);
				promise.then(function (response) {
					$scope.subID = response.data.id;
					console.log("subid"+$scope.subID);
					$scope.uploadResult = "Successfully uploaded file, now you can submit";
					vm.assgnSubmitButton = true;
				}, function (response) {
					$scope.uploadResult = "Error in uploading file";
				});
			}

		};

		function compare() {
			var sid = $scope.subID;
			console.log($scope.subID);

			var promise = StudentService.compare(user_id, semID, $scope.courseID,$scope.assgnID,sid);
			promise.then(function (response) {
				$scope.uploadResult = "Submitted successfully";
			}, function (response) {
				$scope.uploadResult = "Error in submission";
			});
		};


	}
})();