angular.module 'web'
  .controller 'TopicController', ($scope, $state, $stateParams, TopicService) ->

    $scope.attrs =
      page          : 0
      topics  : {}
      maps    : {}
 
    $scope.methods =
      create : () ->
        promise = TopicService.create $stateParams.id, $scope.topic
        promise.success (data) ->
          $state.go 'community.self.content', { 'id' : $stateParams.id, 'topicId' : data.id, 'page' : 1}
        promise.error (data) ->
          alert 'error'

      update : () ->
        promise = TopicService.update $stateParams.id, $scope.topic
        promise.success (data) ->
          $state.go 'community.self.content', { 'id' : $stateParams.id, 'topicId' : data.id, 'page' : 1}
        promise.error (data) ->
          alert 'error'

      delete : () ->
        promise = TopicService.delete $scope.topic.id
        promise.success (data)->
          $state.go 'community.self.content', { 'id' : $stateParams.id, 'topicId' : data.id, 'page' : 1}
        promise.error (data)->
          alert 'error'

      loadMore : () ->

        if $scope.attrs.topics != undefined && Object.keys($scope.attrs.topics).length  >= 10 
          $scope.attrs.page += 1
          $scope.methods.init($scope.attrs.page)

      sendTo : (topic) ->
        $state.go 'community.self.content', { 'id' : $stateParams.id, 'topicId' : topic.id, 'page' : 1}

      init : (page) ->

        promise = TopicService.getById $stateParams.id, page

        promise.success (data) ->

          for topic in data
            
            topic.qtyPages = Math.ceil topic.answers / $scope.attrs.pageSize
            $scope.attrs.maps[topic.id] = topic
         
          $scope.attrs.topics = []
          
          for obj of $scope.attrs.maps 
            $scope.attrs.topics.push $scope.attrs.maps[obj]

   
    do ->
      $scope.methods.init(0)
