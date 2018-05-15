(function () {
    angular
        .module("CodeSniffer")
        .controller("LoginController", loginController)
        .controller("LogoutController", logoutController)
        .controller("RegisterController", registerController);

    function loginController(UserService, $location, $rootScope) {
        var vm = this;
        vm.login = login;

        function login(user) {
            console.log("in client controller");
            console.log(user.username);
            console.log(user.password);

            var promise = UserService.findUserByCredentials(user.username, user.password);

            promise.success(function (res) {
                console.log("login successful");
                console.log(res.fName);
                console.log(res.id);
                var role = res.userType;

                if (role === "ADMIN") {
                    $location.url("/user/"+res.id+"/admin");
                }
                else{
                    $location.url("/user/"+res.id+"/semester");
                }
            });

            promise.error(function (res, status) {
                console.log("login failed");
                console.log(status);

                vm.error = "Login Failed";
            });
        }
    }

    function registerController($location, UserService, SemesterService, CourseService) {
        var vm = this;

        // event handlers
        vm.register = register;

        function init() {
        }

        init();

        function register(user) {
            if (!user) {
                vm.error = "Missing required fields";
            }
            else if ((!user.username) || (!user.email) || (!user.userType) || (!user.password)) {
                vm.error = "Missing required fields";
            }
            else if (user.password !== vm.vpassword) {
                vm.error = "Passwords don't match";
            }
            else {
                var promise = UserService.findUserByUsername(user.username);
                promise.success(function (statusCode) {
                    console.log("register returned");

                    var createUserPromise = UserService.createUser(user);

                    // create user successful, redirect to the new user page
                    createUserPromise.success(function (user) {

                        var addToSemestersPromise = SemesterService.addUserToAllSemesters(user.id);
                        // adding user to every semester
                        addToSemestersPromise.then(function (value) {

                            // adding student to every course
                            if (user.userType === "STUDENT") {
                                var addToCoursesPromise = CourseService.addUserToAllCourses(user.id);
                                addToCoursesPromise.then(function (value1) {
                                    $location.url("/login");
                                });
                            }
                            else {
                                $location.url("/login");
                            }
                        });
                    });

                    // Some other error happened while creating the user at server side
                    createUserPromise.error(function (createUserRes, createUserStatus) {
                        vm.error = createUserRes;
                    });
                });

                promise.error(function (res, status) {
                    console.log("register failed");
                    console.log(res);
                    console.log(status);

                    vm.error = res;
                });
            }
        }
    }

    function logoutController(UserService, $location) {
        var vm = this;
        vm.logout = logout;

        function logout(username) {
            var loggedInPromise = UserService.loggedIn();

            loggedInPromise.success(function (res) {
                // the user is indeed logged in
                var logOutPromise = UserService.logOut(username);
                logOutPromise.then(function (res) {
                    console.log("logout successful");
                }, function (reason) {
                    console.error(reason);
                });
            });
            $location.url("/login");
        }
    }
})();