(function () {
    angular
        .module("CodeSniffer")
        .factory('UserService',userService);
    function userService($http) {

        var api={
            "createUser": createUser,
            "findUserById": findUserById,
            "findUserByUsername": findUserByUsername,
            "findUserByCredentials": findUserByCredentials,
            "findUserByEmailId": findUserByEmailId,
            "findAllUsers": findAllUsers,
            "updateUser": updateUser,
            "deleteUser": deleteUser,
            "isAdmin": isAdmin,
            "loggedIn": loggedIn,
            "logIn": logIn,
            "logOut": logOut,
            "isFaculty" : isFaculty
        };
        return api;


        function findUserByCredentials(username, password) {
            // return $http.get("/login?emailId="+username+"&password="+password);
            console.log("in client service");
            console.log(username);
            console.log(password);
            return $http.get("/api/user/findUserByCredentials?username="+username+"&password="+password);
        }

        function createUser(newUser) {
            console.log(JSON.stringify(newUser));
            return $http.post("/api/user/createUser", newUser);
        }

        function updateUser(user) {
            console.log("update user:");
            console.log(JSON.stringify(user));
            return $http.put("/api/user/updateUser", user);
        }

        function deleteUser(userId) {
            console.log("delete user:");
            console.log(userId);
            return $http.delete("/api/user/deleteUser/" + userId);
        }

        function findUserByEmailId(emailId) {
            return $http.get("/api/user/findUserByEmailId?emailId=" + emailId);
        }

        function findUserByUsername(username) {
            return $http.get("/api/user/findUserByUsername?username=" + username);
        }

        function findUserById(userId) {
        	console.log("in user service client: ", userId);
            return $http.get("/api/user/findUserById?userId=" + userId);
        }
        
        function findAllUsers() {
            console.log("User Service, find all user");
            return $http.get("/api/user/all");
        }

        function isAdmin(uid) {
            return $http.get('/api/auth/isAdmin/' + uid);
        }

        function isFaculty(uid) {
            return $http.get('/api/auth/isFaculty/' + uid);
        }
        
        function loggedIn(uid) {
            return $http.get('/api/auth/loggedIn/' + uid);
        }

        function logIn(user) {
            return $http.post('/api/auth/logIn/', user);
        }

        function logOut(uid) {
            return $http.get('/api/auth/logOut/' + uid);
        }
    }
})();