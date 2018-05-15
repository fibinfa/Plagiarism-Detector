(function () {
    angular
        .module("CodeSniffer")
        .factory('SemesterService', SemesterService);

    function SemesterService($http) {
        var api = {
            "findAllSemesters" : findAllSemesters,
            "findSemestersByUserId": findSemestersByUserId,
            "addUserToAllSemesters": addUserToAllSemesters,
            "createSemester" : createSemester,
            "updateSemester" : updateSemester,
            "deleteSemester" : deleteSemester
        };
        return api;


        function findSemestersByUserId(user_id) {
            return $http.get("/api/user/" + user_id + "/semester/findSemestersByUserId");
        }

        function addUserToAllSemesters(user_id) {
            return $http.put("/api/user/" + user_id + "/semester/addUserToAllSemesters");
        }

        function findAllSemesters() {
            return $http.get(("/api/semester/all"));
        }

        function createSemester(newSemester) {
            return $http.post("/api/semester", newSemester);
        }

        function updateSemester(semester) {
            return $http.put("/api/semester", semester);
        }

        function deleteSemester(semesterId) {
            console.log("delete semester: " + semesterId);
            return $http.delete("/api/semester/" + semesterId);
        }
    }

})();