angular.module "web"
  .controller "CommunityController", ($scope, $timeout, webDevTec, toastr) ->

    $scope.attrs =
      isHideMenu : no
  
    $scope.$on 'hide-menu', (event)->
      $scope.attrs.isHideMenu = yes

    $scope.$on 'feedback', (event, data) ->
      $scope.$broadcast 'feedback-content', data