(function () {
    angular
        .module("CodeSniffer")
        .controller('AdminController', adminController)
        .controller('AdminUsersController', adminUsersController)
        .controller('AdminSemestersController', adminSemestersController)
        .controller('AdminCoursesController', adminCoursesController);

    function adminController($routeParams, $location, UserService) {
        var vm = this;
        vm.uid = $routeParams.uid;
        vm.logout = logout;
        vm.manageUsers = manageUsers;
        vm.manageSemesters = manageSemesters;
        vm.manageCourses = manageCourses;

        function init() {
        }

        init();

        function logout() {
            UserService.logOut(vm.uid).then($location.url('/'))
        }

        function manageUsers() {
            $location.url('/user/' + vm.uid + '/admin/' + 'users');
        }

        function manageSemesters() {
            $location.url('/user/' + vm.uid + '/admin/' + 'semesters');
        }

        function manageCourses() {
            $location.url('/user/' + vm.uid + '/admin/' + 'courses');
        }
    }

    //--------------------------------------------------------------------------------------------
    function adminSemestersController($routeParams, $location, $route, SemesterService, UserService) {
        var vm = this;

        vm.uid = $routeParams.uid;
        vm.createSemester = createSemester;
        vm.deleteSemester = deleteSemester;
        vm.updateSemester = updateSemester;
        vm.logout = logout;

        function init() {
            findAllSemesters();
        }

        init();

        function logout() {
            UserService.logOut(vm.uid).then($location.url('/'))
        }

        function findAllSemesters() {
            SemesterService.findAllSemesters()
                .then(function (semesters) {
                    vm.semesters = semesters["data"];
                }, function (err) {
                    vm.error = err;
                });
        }

        function createSemester(newSem) {
            console.log(JSON.stringify(newSem));
            if (!newSem) {
                vm.error = "missing required field";
                return;
            }
            if (!newSem.name) {
                vm.error = "missing required field";
                return;
            }

            SemesterService.createSemester(newSem)
                .success(function (data) {
                    $route.reload();
                })
                .error(function (data, status) {
                    vm.error = data;
                });
        }

        function deleteSemester(semester) {
            console.log("admin semester controller");
            console.log(JSON.stringify(semester));
            SemesterService
                .deleteSemester(semester.id)
                .success(function (data) {
                    vm.info = "Semester deleted";
                })
                .error(function (data, status) {
                    vm.error = data;
                });

            findAllSemesters();
        }

        function updateSemester(sem) {
            SemesterService
                .updateSemester(sem)
                .success(function (data) {
                    vm.info = "Semester updated";
                })
                .error(function (data, status) {
                    vm.error = data;
                });

            $route.reload();
            // findAllSemesters();
        }
    }

    //--------------------------------------------------------------------------------------------
    function adminCoursesController($routeParams, $route, $location, UserService, CourseService) {
        var vm = this;
        vm.logout = logout;
        vm.uid = $routeParams.uid;
        vm.createCourse = createCourse;
        vm.updateCourse = updateCourse;
        vm.deleteCourse = deleteCourse;

        function init() {
            findAllCourses();
        }
        init();

        function logout() {
            UserService.logOut(vm.uid).then($location.url('/'))
        }

        function findAllCourses() {
            console.log("find all courses in admin controller");
            CourseService.findAllCourses()
                .success(function (data) {
                    vm.courses = data;
                })
                .error(function (data, status) {
                    vm.error = data;
                });
        }

        function createCourse(newSem) {
            console.log(JSON.stringify(newSem));
            if (!newSem) {
                vm.error = "missing required field";
                return;
            }
            if (!newSem.name) {
                vm.error = "missing required field";
                return;
            }

            CourseService.createCourse(newSem)
                .success(function (data) {
                    $route.reload();
                })
                .error(function (data, status) {
                    vm.error = data;
                });
        }

        function deleteCourse(course) {
            console.log("admin course controller");
            console.log(JSON.stringify(course));
            CourseService
                .deleteCourse(course.id)
                .success(function (data) {
                    vm.info = "Course deleted";
                })
                .error(function (data, status) {
                    vm.error = data;
                });

            findAllCourses();
        }

        function updateCourse(sem) {
            CourseService
                .updateCourse(sem)
                .success(function (data) {
                    vm.info = "Course updated";
                })
                .error(function (data, status) {
                    vm.error = data;
                });

            $route.reload();
            // findAllCourses();
        }
    }

    //--------------------------------------------------------------------------------------------
    function adminUsersController($routeParams, $location, UserService) {
        var vm = this;

        vm.uid = $routeParams.uid;
        vm.createUser = createUser;
        vm.deleteUser = deleteUser;
        vm.updateUser = updateUser;
        vm.logout = logout;

        function init() {
            findAllUsers();
        }

        init();

        function logout() {
            UserService.logOut(vm.uid).then($location.url('/'))
        }

        function updateUser(user) {
            UserService
                .updateUser(user)
                .then(findAllUsers);
        }

        function deleteUser(user) {
            UserService
                .deleteUser(user)
                .then(findAllUsers);
        }

        function createUser(nUser) {
            if (!nUser) {
                vm.error = "missing user";
                return;
            }

            if (!nUser.username || !nUser.email || !nUser.userType || !nUser.password) {
                vm.error = "missing required field";
                return;
            }

            UserService
                .createUser(nUser)
                .success(function (data) {
                    findAllUsers;
                })
                .error(function (data, status) {
                    vm.error = data;
                });
        }

        function findAllUsers() {
            UserService
                .findAllUsers()
                .then(function (users) {
                    vm.users = users["data"];
                }, function (err) {
                    vm.error = err;
                });
        }
    }
})();