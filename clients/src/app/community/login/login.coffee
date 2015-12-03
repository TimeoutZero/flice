angular.module "web"
  .controller 'LoginController', ($rootScope, $scope, $state, $timeout, LoginService, hToast) ->

    $scope.attrs =
      isSignIn : yes
      isSignUp : no

    $scope.methods =

      changePages : () ->
        $scope.attrs.isSignIn = !$scope.attrs.isSignIn
        $scope.attrs.isSignUp = !$scope.attrs.isSignUp

      login : () ->

        if $scope.userForm.$valid
         
          promise = LoginService.createToken($scope.userForm)

          promise.success (data, headers) ->
            $rootScope.isLogged = yes
            $state.go 'community.list'
          
        else
          hToast.error 'E-mail or password invalid'

      register : ()->
        
        promise = LoginService.register($scope.signupForm)

        promise.success (data) ->
          $scope.methods.changePages()
