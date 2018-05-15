(function () {
    angular
        .module("CodeSniffer")
        .config(configuration);

    function configuration($routeProvider) {
        $routeProvider
            .when("/login", {
                templateUrl: 'views/user/templates/login.view.client.html',
                controller: 'LoginController',
                controllerAs: 'model'
            })
            .when("/", {
                redirectTo: "/login"
            })
            .when("/register", {
                templateUrl: "views/user/templates/register.view.client.html",
                controller: "RegisterController",
                controllerAs: "model"
            })
            .when('/user/:uid/admin', {
                templateUrl: "views/admin/templates/admin.view.html",
                controller: "AdminController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin,
                    adminUser: isAdmin
                }
            })
            .when('/user/:uid/admin/users', {
                templateUrl: "views/admin/templates/admin.users.view.html",
                controller: "AdminUsersController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin,
                    adminUser: isAdmin
                }
            })
            .when('/user/:uid/admin/semesters', {
                templateUrl: "views/admin/templates/admin.semesters.view.html",
                controller: "AdminSemestersController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin,
                    adminUser: isAdmin
                }
            })
            .when('/user/:uid/admin/courses', {
                templateUrl: "views/admin/templates/admin.courses.view.html",
                controller: "AdminCoursesController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin,
                    adminUser: isAdmin
                }
            })
            .when("/user/:uid/semester", {
                // show all the semesters
                templateUrl: "views/dashboard/templates/dashboard.view.client.html",
                controller: "DashboardController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin
                }
            })
            .when("/user/:uid/semester/:semid/student/course", {
                // show all the courses in that semester
                templateUrl: "views/studentdashboard/templates/student.courses.view.client.html",
                controller: "StudentCourseController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin
                }
            })
            .when("/user/:uid/semester/:semid/faculty/course", {
                // show all the courses in that semester
                templateUrl: "views/faculty/templates/courses.view.client.html",
                controller: "CourseController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin,
                    isFaculty: isFaculty
                }
            })
            .when("/user/:uid/semester/:semid/faculty/course/:cid/assignment/:aid/reports", {
                // for faculty, see reports for one assignment
                templateUrl: "views/faculty/templates/report.view.client.html",
                controller: "ReportController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin,
                    isFaculty: isFaculty
                }
            })
            .when("/user/:uid/semester/:semid/faculty/course/:cid/assignment/:aid/reports/:reportlink", {
                // for student, upload a assignment
                templateUrl: "views/faculty/templates/detailedreport.view.client.html",
                controller: "DetailedReportController",
                controllerAs: "model",
                resolve: {
                    loggedIn: checkLogin,
                    isFaculty: isFaculty
                }
            })
            .otherwise({
                redirectTo: "/login"
            });
    }

    function isAdmin($q, UserService, $location, $route) {
        var deffered = $q.defer();

        UserService
            .isAdmin($route.current.params.uid)
            .then(function (user) {
                if (user == '0') {
                    deffered.reject();
                    $location.url('/admin/' + user.id);
                } else {
                    deffered.resolve(user);
                }
            });
        return deffered.promise;
    }

    function isFaculty($q, UserService, $location, $route) {
        var deffered = $q.defer();

        UserService
            .isFaculty($route.current.params.uid)
            .then(function (user) {
                if (user == '0') {
                    deffered.reject();
                    $location.url('/user/' + user.id + '/semester');
                } else {
                    deffered.resolve(user);
                }
            });
        return deffered.promise;
    }

    function checkLogin($q, UserService, $location, $route) {
        var deffered = $q.defer();
        UserService
            .loggedIn($route.current.params.uid)
            .then(function (user) {
                if (user == '0') {
                    deffered.reject();
                    $location.url('/login')
                } else {
                    deffered.resolve(user);
                }
            });
        return deffered.promise;
    }
})();