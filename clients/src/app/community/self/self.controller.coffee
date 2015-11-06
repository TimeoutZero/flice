angular.module "web"
  .controller "CommunitySelfController", ($scope, $stateParams, CommunitySelfService) ->

    $scope.community = {}
    $scope.isOnContent = yes

    $scope.methods =
      
      changeToCreateTopicView : ()->
        $scope.isOnContent = no

      init : ()->
        console.log 'ae'
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

        promise.error (data, status) ->
          console.log 'fail'

    $scope.$on 'changeToContentView', (event)->
      $scope.isOnContent = yes
      $scope.methods.init()
    
    do ->
      $scope.methods.init()
