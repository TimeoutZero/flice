angular.module "web"
  .controller "ContentController", ($scope, $state, $stateParams, TopicService, CommentService) ->
        
    $scope.attrs =

      #CONSTANT
      pageSize         : 10

      #ATTRIBUTES

      comment  :
        start  : 0
        end    : 0

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

        $scope.attrs.comment.start = $scope.attrs.page + 1;
        $scope.attrs.comment.end = if $scope.attrs.topic.qtyPages > 5 then 5 else $scope.attrs.topic.qtyPages; 

        $scope.attrs.pages = (result for result in [$scope.attrs.comment.start..$scope.attrs.comment.end])

        $scope.$emit 'hide-menu'

      isActive : (id) ->
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
        
        console.log 'a ' + $scope.attrs.page
        console.log 'a ' + $scope.attrs.comment.start

        if $scope.attrs.page >= 1
          $scope.attrs.page -= 1

          for page, index in $scope.attrs.pages 

            newIndice = page -= 1

            if newIndice > 0
              if index is 0
                $scope.attrs.comment.start = newIndice
              if index is $scope.attrs.pages.length - 1
                $scope.attrs.comment.end = newIndice

              $scope.attrs.pages[index] = newIndice

         $scope.methods.getComments()

      getPage : (pageNumber) ->
        $scope.attrs.page = pageNumber;
        $scope.methods.getComments()

      getComments : () ->
        promise = CommentService.getById $scope.attrs.topic.id, $scope.attrs.page, $scope.attrs.pageSize

        promise.success (data) ->
          $scope.attrs.comments = data

      setCommentActive : (active) ->
        $scope.attrs.commentActive = active
 
    do ->

      promise = TopicService.getById $stateParams.id
      promise.success (data) ->

        for topic in data 
          topic.qtyPages = Math.ceil topic.answers / $scope.attrs.pageSize

        $scope.attrs.topics = data
