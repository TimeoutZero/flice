angular.module "web"
  .controller "CommunityListController", ($scope, $state, CommunityService) ->

    $scope.isListView = false

    $scope.methods =
      changeView : ->
        $scope.isListView = !$scope.isListView

      goToCommunity: (id) ->
        $state.go 'community.self', { 'id' : id }

    do () ->

      promise = CommunityService.list()
      promise.success (data) ->
        $scope.communities = data