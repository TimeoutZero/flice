angular.module "web"
  .controller "CommunityListController", ($scope, $state, $stateParams, $timeout, CommunityService, hToast) ->

    $scope.isListView = false

    $scope.attrs =
      page              : 0
      autocompleteWord  : ''
      timeout           : null
      maps              : {}


    $scope.methods =

      loadMore : () ->
         if $scope.attrs.communities != undefined && Object.keys($scope.attrs.communities).length  >= 10 
          $scope.attrs.page += 1
          $scope.methods.init($scope.attrs.page)

      search : () =>
        if $scope.attrs.timeout
          $timeout.cancel($scope.attrs.timeout)

        $scope.attrs.timeout = $timeout () -> 

          promise = CommunityService.getAutocompleteByName($scope.attrs.autocompleteWord)

          promise.success (data) ->
            $scope.attrs.communities = data
          promise.error () ->
            hToast.error 'Fail to get communities come later!'
        , 500 

      changeView : ->
        $scope.isListView = !$scope.isListView

      goToCommunity: (id) ->
        $state.go 'community.self', { 'id' : id }

      init : (page) ->
        promise = CommunityService.list page

        promise.success (data) ->

          for community in data
            $scope.attrs.maps[community.id] = community

          $scope.attrs.communities = []
          
          for obj of $scope.attrs.maps 
            $scope.attrs.communities.push $scope.attrs.maps[obj]


    do () ->
      $scope.methods.init(0)
