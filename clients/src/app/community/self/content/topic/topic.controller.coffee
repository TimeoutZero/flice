angular.module 'web'
  .controller 'TopicController', ($scope, $state, $stateParams, TopicService) ->
 
    $scope.methods =
      create : () ->
        promise = TopicService.create $stateParams.id, $scope.topic
        promise.success (data) ->
          alert 'certo'
          $state.go 'community.self.content', { "id" : $stateParams.id }
        promise.error (data) ->
          alert 'error'

      update : () ->
        promise = TopicService.update $stateParams.id, $scope.topic
        promise.success (data) ->
          alert 'atualizado'
          $state.go 'community.self.content', { "id" : $stateParams.id }
        promise.error (data) ->
          alert 'error'

      delete : () ->
        promise = TopicService.delete $scope.topic.id
        promise.success (data)->
          alert 'certo'
        promise.error (data)->
          alert 'error'