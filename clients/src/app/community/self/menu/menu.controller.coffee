angular.module "web"
  .controller "MenuController", ($scope, $state, $stateParams, CommunitySelfService) ->

    $scope.methods =

      init : () ->
        promise = CommunitySelfService.getById $stateParams.id

        promise.success (data) ->
          community             = {}
          community.name        = data.name
          community.image       = data.image
          community.cover       = data.cover
          community.members     = data.members
          community.description = data.description
          community.tags        = data.tags
          community.created     = data.created
          community.privacity   = data.privacity

          $scope.community = community

        promise.error (data, status) ->
          console.log 'fail'

      changeToCreateTopicView : () ->
        $state.go 'community.self.post'
    
    do ->
      $scope.methods.init()
