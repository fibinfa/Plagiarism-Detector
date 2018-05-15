(function () {
	angular
	.module("CodeSniffer")
	.factory('FacultyService',FacultyService);
	
	function FacultyService($http) {

		var api={
				"assignment": assignment,
				"addAssignment" : addAssignment,
				"updateThreshold" : updateThreshold,
				"getReports" : getReports,
				"createCourse" : createCourse,
				"shareReport" : shareReport,
				"getSubmissions" : getSubmissions
		};
		return api;


		function assignment(courseId) {
			return $http.get("/api/course/"+courseId+"/findAllAsgmtsByCourse");
		}
		
		function addAssignment(courseId, assgn) {
			return $http.post("/api/course/"+courseId+"/createAsgmt", assgn);
		}
		
		function createCourse(userID, semID, newCourse) {
			return $http.post("/api/user/"+userID+"/semester/"+semID+"/addcourse", newCourse);
		}
		
		function updateThreshold(assgn) {
			return $http.put("/api/assignment/updateThreshold", assgn);
		}
		
		function getReports(assgnID) {
			return $http.get("/api/assignment/"+assgnID+"/findReportsByAsgmtId");
		}
		
		function getSubmissions(assgnID) {
			return $http.get("/api/assignment/"+assgnID+"/findSubmissionsByAsgmtId");
		}

		function shareReport(assgnID, reportID) {
			console.log(assgnID +" "+ reportID);
            return $http.post("/api/assignment/"+assgnID+"/report/"+ reportID +"/share");
        }

	}
})();