angular.module 'web'
  .controller 'CommunityTopicController', ($scope, $state, $stateParams, CommunitySelfTopicService) ->

    $scope.methods =
      create : () ->
        promise = CommunitySelfTopicService.create($stateParams.id, $scope.topic)
        promise.success (data) ->
          alert 'certo'
          $state.go 'community.self.content', { "id" : $stateParams.id }
        promise.error
          alert 'error'