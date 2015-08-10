angular.module "web"
  .controller "MainController", ($scope) ->

    $scope.attrs =
      body : "{ 'background-color' : '#000' }"
      login : false;

    $scope.methods =
      init : ()->
        console.log $scope.attrs.body
        console.log 'mainCtrl'

    $scope.$on 'disable-login-background', (event)->
      console.log 'chegou'
      $scope.attrs.login = false
    $scope.methods.init()
