angular.module "web"
  .controller "MenuController", ($scope, $state) ->

    $scope.methods.changeToCreateTopicView = () ->
      $state.go 'community.self.post'