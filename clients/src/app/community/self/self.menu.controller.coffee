angular.module "web"
  .controller "CommunitySelfMenuController", ($scope, $state) ->

    $scope.methods.changeToCreateTopicView = () ->
      $state.go 'community.self.post'