(function () {
	angular
	.module("CodeSniffer")
	.factory('CourseService',GetCoursesService);
	function GetCoursesService($http) {

		var api={
				"courses": getCourses,
				"getAllCourses" : getAllCourses,
				"addCourses" : addCourses,
				"showSubmissions" : showSubmissions,
				"addUserToAllCourses" : addUserToAllCourses,
				"updateCourse" : updateCourse,
				"deleteCourse" : deleteCourse,
				"findAllCourses" : findAllCourses,
				"getAllUnregisteredCourses" : getAllUnregisteredCourses
		};
		return api;


		function getCourses(semesterId, userId) {
			return $http.get("/api/user/"+userId+"/semester/"+semesterId+"/findAllEnrolledCourses");
		}

		function getAllCourses(userId,semesterId) {
			return $http.get("/api/user/"+userId+"/semester/"+semesterId+"/findAllUnregisteredCoursesForASemester");
		}

		function addCourses(userId,semID, courseId) {
			return $http.put("/api/user/"+userId+"/semester/"+semID+"/course/"+courseId);
		}

		function addUserToAllCourses(userId) {
			return $http.put("/api/user/"+userId+"/course/addUserToAllCourses");
		}

		function showSubmissions(assgnName) {
			return $http.get("/api/course/addSubmissionsByAssgnName?assgnName="+assgnName);
		}
		
		
		function getAllUnregisteredCourses(userID,semID) {
			return $http.get("/api/user/"+userID+"/semester/"+semID+"/findAllUnregisteredCoursesForASemester");
		}

		function updateCourse(course) {
			return $http.put("/api/course", course);
		}

		function deleteCourse(courseId) {
			return $http.delete("/api/course/" + courseId);
		}

		function findAllCourses() {
			console.log("find all courses in course service client");
			return $http.get("/api/course/all");
		}
	}
})();