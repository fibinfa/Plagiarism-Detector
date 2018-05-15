(function () {
	angular
	.module("CodeSniffer")
	.factory('StudentService',StudentService);
	function StudentService($http) {

		var api={
				"assignment": assignment,
				"reportPage" : reportPage,
				"uploadFile" : uploadFile,
				"uploadGithubCode" : uploadGithubCode,
				"compare" : compare
		};
		return api;


		function assignment(courseId) {
			return $http.get("/api/course/"+courseId+"/findAllAsgmtsByCourse");
		}
		
		function reportPage() {
			return $http.get("/api/report/findReportFromJplag");
		}
		
		function uploadFile(url, data, config) {
			return $http.post(url, data, config);
		}
		
		function compare(user_id, semID, courseID, assgnID,sid) {
			return $http.post("/api/user/"+user_id+"/sem/"+semID+"/course/"+courseID+"/assignment/"+assgnID+"/submission/"+sid+"/compare");
		}


		function uploadGithubCode(gitRepo, user_id, sem_id, course_id, assign_id) {
			// TODO: here
			var reqURL = "/api/user/" + user_id +
                "/sem/" + sem_id +
                "/course/" + course_id +
                "/assignment/" + assign_id +
                "/gitclone?gitRepo=" + gitRepo;

			console.log("request URL: ", reqURL);
            return $http.post(reqURL, gitRepo);
        }
	}
})();