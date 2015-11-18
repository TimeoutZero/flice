angular.module "web"
  .controller "ContentController", ($scope, $state, $stateParams, TopicService, CommentService) ->
        
    $scope.attrs =
      isOnTopicPreview : no

      topics           : []
      topic            : {}
      page             : 0
      comments         : []

      #create
      newcoment        : {}


    $scope.methods =

      createComment : () ->
        
        console.log 
        promise = CommentService.create $scope.attrs.topic.id, $scope.newcoment

        promise.success (data)->
          alert 'comment created'
        promise.error (data)->
          alert 'fail to create comment'

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
        promise = CommentService.getById $scope.attrs.topic.id, $scope.attrs.page, 10

        promise.success (data) ->
          $scope.attrs.comments = data
 
    do ->

      promise = TopicService.getById $stateParams.id
      promise.success (data) ->
        $scope.attrs.topics = data
