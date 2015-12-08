angular.module "web"
  .controller "MenuController", ($scope, $state, $stateParams, CommunitySelfService) ->

    $scope.methods =

      join : () ->

        if $scope.community.member is not true
          promise = CommunitySelfService.join $stateParams.id
          promise.success (data) ->
            $scope.methods.init()
          promise.error (data, status) ->
            alert 'not joined'

      edit : ()->
        $state.go 'community.form', { 'id' : $stateParams.id }

      init : () ->
        promise = CommunitySelfService.getById $stateParams.id

        promise.success (data) ->
          community             = {}
          community.name        = data.name
          community.image       = data.image
          community.cover       = data.cover
          community.members     = data.members
          community.description = data.description
          community.tags        = data.tags
          community.created     = data.created
          community.privacity   = data.privacity
          community.member      = data.member

          $scope.community = community

        promise.error (data, status) ->
          alert 'fail'

      changeToCreateTopicView : () ->
        if $scope.community.member
          $state.go 'community.self.post'
    
    do ->
      $scope.methods.init()
