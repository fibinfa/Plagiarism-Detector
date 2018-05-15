(function(){
	angular
	.module("CodeSniffer")
	.controller("ReportController", ReportController)
	.controller("DetailedReportController", DetailedReportController);

	function ReportController($location,$rootScope,$http,$scope,$routeParams,FacultyService,UserService) {
		var assgnID = $routeParams.aid;
		var uid = $routeParams.uid;
		var semId = $routeParams.semid;
		var cid = $routeParams.cid;

		var vm = this;
		vm.viewDetailedReport = viewDetailedReport;
		vm.shareReport = shareReport;
		vm.getSubmissions = getSubmissions;
		vm.logout = logout;

		function init() {
			var promise = FacultyService.getReports(assgnID);
			promise.then(function (reports) {
				if(reports !=null){
					console.log(reports.data);
					$scope.reportsList = reports.data;
				}
				else{
					vm.error="No reports found";
				}
			},
			function (error) {
				vm.error="No reports found";
			});

//			var sub_promise = FacultyService.getSubmissions(assgnID);
//			sub_promise.then(function (res) {
//			console.log(res.data);
//			$scope.submissionList = res.data;
//			},
//			function (error) {
//			vm.share_response="Not able to fetch submissions";
//			});
		}
		init();

		function shareReport(report){
			var promise = FacultyService.shareReport(assgnID, report.id);
			promise.then(function (res) {

				vm.share_response = "Report shared Successfully";
			},
			function (error) {
				vm.share_response="Not able to share report";
			});
		}

		function getSubmissions(){
//			var promise = FacultyService.getSubmissions(assgnID);
//			promise.then(function (res) {
//			console.log(res.data);
//			},
//			function (error) {
//			vm.share_response="Not able to fetch submissions";
//			});
		}

		function viewDetailedReport(report){
			var rplink = report.reportLink;

			$location.url("/user/"+uid+"/semester/"+semId+"/faculty/course/"+cid+"/assignment/"+assgnID+"/reports/"+rplink);
		};

		function logout() {
			UserService.logOut(vm.uid).then($location.url('/'))
		}

	}

	function DetailedReportController($location,$scope,$sce,$routeParams,UserService) {
		var vm = this;

		vm.logout = logout;
		
		function init() {
			var rplink = $routeParams.reportlink;
			console.log(rplink);
			var first = getPosition(rplink, 'user', 1)+4;
			var second = getPosition(rplink, 'user', 2)+4;
			
			var user1 = rplink.substring(first,first+1);
			var user2 = rplink.substring(second,second+1);
			
			var user1_promise = UserService.findUserById(user1);
			user1_promise.then(function (user) {
				if(user !=null){
					$scope.username1 = user.data.fName;
				}
				else{
					console.log("Not able to find user");
				}
			});
			
			var user2_promise = UserService.findUserById(user2);
			user2_promise.then(function (user) {
				if(user !=null){
					$scope.username2 = user.data.fName;
				}
				else{
					console.log("Not able to find user");
				}
			});
		}
		init();

		function getPosition(string, subString, index) {
			return string.split(subString, index).join(subString).length;
		}

		function logout() {
			UserService.logOut(vm.uid).then($location.url('/'))
		}

		var rplink = $routeParams.reportlink;

		$scope.path = "https://s3.amazonaws.com/codesniffer-reports/"+rplink+"/match0.html";

		$scope.trustSrc = function(src) {
			return $sce.trustAsResourceUrl(src);
		}

	}

})();