angular.module "web"
  .controller 'HeaderController', ($rootScope, $scope, $timeout) ->

    $scope.methods =
      isLogged : ()->
        return $rootScope.isLogged