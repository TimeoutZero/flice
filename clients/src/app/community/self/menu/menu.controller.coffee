angular.module "web"
  .controller "MenuController", ($scope, $rootScope, $state, $stateParams, CommunitySelfService) ->

    $scope.attrs = 
      member : false
      owner  : false

    $scope.methods =

      join : () ->

        if $scope.attrs.member is not true
          promise = CommunitySelfService.join $stateParams.id
          promise.success (data) ->
            $scope.attrs.member = true

          $rootScope.$emit 'joinedCommunity'

          promise.error (data, status) ->
            alert 'not joined'

      edit : ()->
        $state.go 'community.form', { 'id' : $stateParams.id }

      changeToCreateTopicView : () ->
        if $scope.attrs.member
          $state.go 'community.self.post'

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

          $scope.community = community
          $scope.methods.initMemberInfo()
         
        promise.error (data, status) ->
          alert 'fail get community'

      initMemberInfo : () ->

        promise = CommunitySelfService.getMemberInfo($stateParams.id)
        
        promise.success (data) ->
          $scope.attrs.member = data.member 
          $scope.attrs.owner  = data.owner

        promise.error (data) ->
          alert 'error member request'
    do ->
      $scope.methods.init()
