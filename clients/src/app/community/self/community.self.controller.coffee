angular.module "web"
  .controller "CommunitySelfController", ($scope, $stateParams, CommunitySelfService) ->

    $scope.community = {}

    $scope.init = ->

      promiseMenu    = CommunitySelfService.getById $stateParams.id

      promiseMenu.success (data) ->
        community             = {}
        community.name        = data.name
        community.image       = data.image
        community.members     = data.members
        community.description = data.description
        community.tags        = data.tags
        community.created     = data.created
        community.privacity   = data.privacity

        $scope.community = community

      promiseMenu.error (data, status) ->
        console.log 'fail'

    $scope.init();
  
