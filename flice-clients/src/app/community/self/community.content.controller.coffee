angular.module "web"
  .controller "CommunitySelfContentController", ($scope, $stateParams, CommunitySelfTopicService) ->
        
    $scope.topics = {}

    $scope.init = ->
      console.log 'content controller'
      promiseContent = CommunitySelfTopicService.getById $stateParams.id

      promiseContent.success (data) ->
        $scope.topics = data.topics

    $scope.init()
