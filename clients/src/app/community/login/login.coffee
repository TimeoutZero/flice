angular.module "web"
  .controller 'LoginController', ($scope, $state, $timeout, LoginService) ->

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

            $scope.$emit 'feedback', { success: true, message: 'Welcome' }
            $state.go 'community.list'
          
          promise.error (data, status) ->
            $scope.$emit 'feedback', { success: false, message: 'Houston, we have a problem! Try later :(' }


        else
          $scope.$emit 'feedback', { success: false, message :'E-mail or password invalid'}

      register : ()->
        
        promise = LoginService.register($scope.signupForm)

        promise.success (data) ->
          $scope.$emit 'feedback', { success: true, message :'Welcome at flice community'}
          $scope.methods.changePages()

        promise.error (data, status) ->
          $scope.$emit 'feedback', { success: false, message: 'Houston, we have a problem! Try later :(' }