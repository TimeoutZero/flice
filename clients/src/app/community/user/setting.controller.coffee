angular.module 'web'
  .controller 'SettingController', ($scope) ->

    $scope.cropper =
      source  :
        image : null
        cover : null
      cropped :
        image : null
        cover : null

    $scope.bounds = 
      left   : 0
      right  : 0
      top    : 0
      bottom : 0

  

    $scope.methods =

      update : () ->
        console.log $scope.settingsForm
        if $scope.attrs.settings.$valid
          console.log($scope.attrs.settings);