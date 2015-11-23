angular.module "web"
  .controller "ContentController", ($scope, $state, $stateParams, TopicService, CommentService) ->
        
    $scope.attrs =

      #CONSTANT
      pageSize         : 10
      #ATTRIBUTES

      commentActive    : no
      isOnTopicPreview : no

      topics           : []
      topic            : {}
      page             : 0
      comments         : []


    $scope.methods =

      createComment : () ->
        
        promise = CommentService.create $scope.attrs.topic.id, $scope.attrs.newcoment

        promise.success (data)->
          
          $scope.attrs.newcoment = ""
          $scope.attrs.commentActive = no
          $scope.methods.getComments()
         
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


      isActivePage : (pageNumber) ->
        $scope.attrs.page + 1 == pageNumber

      nextPage : () ->
        $scope.attrs.page += 1
        $scope.methods.getComments()

      previousPage : () ->
        $scope.attrs.page -= $scope.attrs.page
        $scope.methods.getComments()

      getPage : (pageNumber) ->
        $scope.attrs.page = pageNumber;
        $scope.methods.getComments()

      getComments : () ->
        promise = CommentService.getById $scope.attrs.topic.id, $scope.attrs.page, $scope.attrs.pageSize

        promise.success (data) ->
          $scope.attrs.comments = data

          actualPage = $scope.attrs.page + 1;
          $scope.attrs.pages = (result for result in [actualPage..$scope.attrs.topic.qtyPages])

      setCommentActive : (active) ->
        $scope.attrs.commentActive = active
 
    do ->

      promise = TopicService.getById $stateParams.id
      promise.success (data) ->

        for topic in data 
          topic.qtyPages = Math.ceil topic.answers / $scope.attrs.pageSize

        $scope.attrs.topics = data
