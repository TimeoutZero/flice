angular.module 'web'
  .controller 'TopicController', ($scope, $state, $stateParams, TopicService) ->
 
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

      sendTo : (topic) ->
        $state.go 'community.self.content', { 'id' : $stateParams.id, 'topicId' : topic.id, 'page' : 1}

    do ->

      promise = TopicService.getById $stateParams.id
      promise.success (data) ->

        for topic in data 
          topic.qtyPages = Math.ceil topic.answers / $scope.attrs.pageSize

        $scope.attrs.topics = data