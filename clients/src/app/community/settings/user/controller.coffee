angular.module "web"
  .controller "UserController", ($scope, UserService) ->

    $scope.attrs =
      settings  : {}
      timeout   : null

    $scope.methods =

      verifyUsername : () ->
        if $scope.attrs.timeout
          $timeout.cancel($scope.attrs.timeout)

        $scope.attrs.timeout = $timeout () -> 

          promise = CommunityService.getAutocompleteByName($scope.attrs.autocompleteWord)

          promise.success (data) ->
            $scope.attrs.communities = data
          promise.error () ->
            hToast.error 'Fail to get communities come later!'
        , 500         

      update : () ->
        if $scope.settingsForm.$valid
          console.log $scope.attrs.settings
          promise = UserService.update($scope.attrs.settings)
          promise.success (data) ->
            $scope.attrs.settings.name        = data.profile.name
            $scope.attrs.settings.username    = data.profile.username
            $scope.attrs.settings.description = data.profile.description
            $scope.attrs.settings.photo       = data.profile.photo

    do ->

      promise = UserService.get()
      promise.success (data) ->
        $scope.attrs.settings.id          = data.id
        $scope.attrs.settings.email       = data.email
        $scope.attrs.settings.name        = data.profile.name
        $scope.attrs.settings.username    = data.profile.username
        $scope.attrs.settings.description = data.profile.description
        $scope.attrs.settings.photo       = data.profile.photo

