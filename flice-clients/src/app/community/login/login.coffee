angular.module "web"
  .controller 'LoginController', ($scope, $state) ->

    $scope.methods =

      doLogin : () ->
        $scope.$emit 'disable-login-background'
        $state.go 'community.list'
