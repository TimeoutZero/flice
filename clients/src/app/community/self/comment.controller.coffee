angular.module "web"
  .controller "CommunitySelfContentController", ($scope, $stateParams, CommunitySelfTopicService, CommunitySelfCommentService) ->
        
    $scope.attrs =
      isOnTopicPreview : no

      topics           : []
      topic            : {}
      page             : 0
      comments         : []

    $scope.methods =
     
      openCreateTopic : ()->
        $scope.attrs.isOnTopicPreview = no

      openTopicPreview : (topic) ->
        if topic.id == $scope.attrs.topic.id
          $scope.attrs.isOnTopicPreview = no
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
          console.log data
          $scope.attrs.comments = data
 
    do ->

      promise = CommunitySelfTopicService.getById $stateParams.id
      promise.success (data) ->
        $scope.attrs.topics = data
