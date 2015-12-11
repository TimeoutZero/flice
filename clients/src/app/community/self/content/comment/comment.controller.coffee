angular.module "web"
  .controller "ContentController", ($scope, $state, $stateParams, $sanitize, TopicService, CommentService) ->
        
    $scope.attrs =

      #CONSTANT
      pageSize         : 10

      #ATTRIBUTES

      comment  :
        start  : 0
        end    : 0
        content : ""

      commentActive    : no
      topicEditActive  : no

      topics           : []
      topic            : {}
      page             : 0
      comments         : []

    $scope.methods =

      setTopicEditActive : (boolean) ->
        $scope.attrs.topicEditActive = boolean

      updateTopic : () ->
        
        promise = TopicService.update $stateParams.id, $scope.attrs.topic
        
        promise.success (data) ->
          $scope.methods.setTopicEditActive(false)
          $state.go 'community.self.content', { 'id' : $stateParams.id, 'topicId' : data.id, 'page' : 1}
        promise.error (data) ->
          alert 'error'

      sendComment : (id) ->
        
        if id == undefined
          promise = CommentService.create $scope.attrs.topic.id, $scope.attrs.comment.content
        else 
          promise = CommentService.update $scope.attrs.topic.id, $scope.attrs.comment.id, $scope.attrs.comment.content   

        promise.success (data)->
          
          $scope.attrs.comment.id      = 0
          $scope.attrs.comment.content = ""
          $scope.attrs.commentActive   = no
          $scope.methods.getComments()

        promise.error (data)->
          alert 'fail to create comment'

      activeTopic : (topic) ->

        $state.go '.', { 'id' : $stateParams.id, 'topicId' : topic.id, 'page': 1 }, { notify : false }

        if topic.id == $scope.attrs.topic.id
          $state.go 'community.self.topic', { 'topicId' : topic.id }
          $scope.attrs.topic            = {}
          return

        $scope.attrs.topic = topic
        $scope.methods.getComments()

        $scope.attrs.comment.start = $scope.attrs.page + 1;
        $scope.attrs.comment.end   = if $scope.attrs.topic.qtyPages > 5 then 5 else $scope.attrs.topic.qtyPages; 

        $scope.attrs.pages = (result for result in [$scope.attrs.comment.start..$scope.attrs.comment.end])

      isTopicActive : (id) ->
        console.log "id: #{id} boolean : #{$scope.attrs.topic.id == id}"
        $scope.attrs.topic.id == id

      isActivePage : (pageNumber) ->
        $scope.attrs.page + 1 == pageNumber

      nextPage : () ->

        limit = $scope.attrs.comment.start + 4
        
        if limit < $scope.attrs.topic.qtyPages 
          
          $scope.attrs.page += 1
          
          if $scope.attrs.page >= limit
            for page, index in $scope.attrs.pages 

              newIndice = page += 1

              if index is 0
                $scope.attrs.comment.start = newIndice
              if index is $scope.attrs.pages.length - 1
                $scope.attrs.comment.end = newIndice
  
              $scope.attrs.pages[index] = newIndice
  
          $scope.methods.getComments()
  

      previousPage : () ->

        if $scope.attrs.page > 0

          $scope.attrs.page -= 1

          start = $scope.attrs.pages[0]

          if $scope.attrs.page <= start - 1 && $scope.attrs.page != 0
            for page, index in $scope.attrs.pages 

              newIndice = page - 1

              if newIndice > 0
                if index is 0
                  $scope.attrs.comment.start = newIndice
                if index is $scope.attrs.pages.length - 1
                  $scope.attrs.comment.end = newIndice

                $scope.attrs.pages[index] = newIndice

        $scope.methods.getComments()

      getPage : (pageNumber) ->
        $scope.attrs.page = pageNumber - 1;
        $scope.methods.getComments()

      delete : (comment) ->
        promise = CommentService.delete comment.topicId, comment.id
     
        promise.success (data) ->
          $scope.methods.getComments()
        promise.error ()->
          alert 'error'

      getComments : () ->
        promise = CommentService.getById $scope.attrs.topic.id, $scope.attrs.page, $scope.attrs.pageSize

        promise.success (data) ->
          $scope.attrs.comments = data

      setCommentActive : (active) ->
        $scope.attrs.commentActive = active
 
      setCommentEditActive : (comment) ->
        $scope.attrs.comment.id      = comment.id
        $scope.attrs.comment.content = comment.content

      isCommentEditActive : (commentId) ->
        $scope.attrs.comment.id == commentId
 
    do ->

      promise = TopicService.getById $stateParams.id
      promise.success (data) ->

        for topic in data 
          topic.qtyPages = Math.ceil topic.answers / $scope.attrs.pageSize
          if topic.id == parseInt($stateParams.topicId)
            $scope.methods.activeTopic topic

        $scope.attrs.topics = data

