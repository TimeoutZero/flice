angular.module "web"
  .controller 'LoginController', ($scope, $state, $timeout, LoginService) ->

    $scope.attrs =
      isSignIn : yes
      isSignUp : no

      feedback :
        isSuccess  : no
        isError    : no
        message    : ''

    $scope.methods =

      changePages : () ->
        $scope.attrs.isSignIn = !$scope.attrs.isSignIn
        $scope.attrs.isSignUp = !$scope.attrs.isSignUp

      login : () ->

        if $scope.userForm.$valid
         
          $scope.$emit 'disable-login-background'
          $state.go 'community.list'
          
        else 
          $scope.attrs.feedback.isError  = yes
          $scope.methods.doFeedback "E-mail or password invalid"

      register : ()->
        
        promise = LoginService.register($scope.signupForm)

        promise.success (data) ->
          $scope.attrs.feedback.isSuccess = yes
          $scope.methods.doFeedback "Welcome and enjoy Flice ;) "
          $scope.methods.changePages()

        promise.error (data, status) ->
          $scope.attrs.feedback.isError  = yes
          $scope.methods.doFeedback "Houston, we have a problem! Try later :("

      doFeedback : (message) ->
        $scope.attrs.feedback.message = message
        $timeout($scope.methods.disableFeedback, 2000)

      disableFeedback : () ->
        $scope.attrs.feedback.isSuccess = no
        $scope.attrs.feedback.isError   = no

