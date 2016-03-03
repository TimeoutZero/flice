angular.module 'web'
  .controller 'SettingController', ($scope) ->


    $scope.attrs =
      timeout : null

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
          console.log('ae');




