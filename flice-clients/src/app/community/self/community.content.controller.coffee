angular.module "web"
  .controller "CommunitySelfContentController", ($scope, $stateParams, CommunitySelfTopicService, CommunitySelfCommentService) ->
        
    $scope.attrs =
      topics           : []
      actualTopicTitle : ''

      comments         : []
      isOnTopicPreview : no

    $scope.methods = 
      
      init : ->
        promiseContent = CommunitySelfTopicService.getById $stateParams.id
        promiseContent.success (data) ->
          $scope.attrs.topics = data.topics


      openTopicPreview: (topic) ->

        $scope.attrs.isOnTopicPreview = yes
        $scope.attrs.actualTopicTitle = topic.title

        promise = CommunitySelfCommentService.getById topic.id, 0, 10

        promise.success (data) ->
          $scope.attrs.comments = data.comments
        $scope.$emit 'hide-menu'

    $scope.methods.init()
