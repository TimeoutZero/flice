angular.module "web"
  .controller "CommunitySelfContentController", ($scope, $stateParams, CommunitySelfTopicService, CommunitySelfCommentService) ->
        
    $scope.attrs =
      isOnTopicPreview : no

      topics           : []
      topic            : {}
      page             : 0
      comments         : []

    $scope.methods =
      
      init : ->
        promiseContent = CommunitySelfTopicService.getById $stateParams.id
        promiseContent.success (data) ->
          $scope.attrs.topics = data.topics

      openTopicPreview: (topic) ->

        if topic.id == $scope.attrs.topic.id
          $scope.attrs.isOnTopicPreview = false
          $scope.attrs.topic            = {}
          return

        $scope.attrs.isOnTopicPreview = yes
        $scope.attrs.topic            = topic

        $scope.methods.getComments()
        $scope.$emit 'hide-menu'

      isActive : (id) ->
        $scope.attrs.topic.id == id

      nextPage : () ->
        $scope.attrs.page += $scope.attrs.page
        $scope.methods.getComments()

      previousPage : () ->
        $scope.attrs.page -= $scope.attrs.page
        $scope.methods.getComments()

      firstPage : () ->
        $scope.attrs.page = 0
        $scope.methods.getComments()

      getComments : () ->
        promise = CommunitySelfCommentService.getById $scope.attrs.topic.id, $scope.attrs.page, 10

        promise.success (data) ->
          $scope.attrs.comments = data.comments

    $scope.methods.init()
