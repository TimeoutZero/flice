angular.module 'web'
  .controller 'CommunityTopicController', ($scope, $state, $stateParams, CommunitySelfTopicService) ->

    $scope.methods =

      create : ()->
        
        promise = CommunitySelfTopicService.create $stateParams.id, $scope.topic
        promise.success (data) ->
          alert 'certo'
          $scope.$emit 'changeToContentView'

        promise.error (data, status)->
          alert "errou status: #{status} | #{data}"
