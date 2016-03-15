angular.module "web"
  .controller "UserController", ($scope, $timeout, hToast, UserService) ->

    $scope.attrs =
      settings  : {}
      timeout   : null

    $scope.methods =

      checkUsername : () ->
        
        if $scope.attrs.timeout
          $timeout.cancel($scope.attrs.timeout)

        $scope.attrs.timeout = $timeout () -> 
          
          if $scope.attrs.settings.username
            
            promise = UserService.checkUsername($scope.attrs.settings.username)

            promise.success (data) ->
              if data.exist == true
                $scope.settingsForm.username.$setValidity('valid', false);
              
            promise.error () ->
              hToast.error 'Fail to get communities come later!'
        , 500         

      updatePhoto : () ->
        $scope.attrs.updatePhoto = yes

      update : () ->

        if $scope.settingsForm.$valid
         if $scope.attrs.cropper?.cropped.image
            $scope.attrs.settings.photo = $scope.attrs.cropper.cropped.image

          promise = UserService.update($scope.attrs.settings)
          promise.success (data) ->
            $scope.attrs.updatePhoto          = no
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

        if data.profile.photo
          $scope.attrs.settings.photo = data.profile.photo
        else
          $scope.attrs.settings.photo = "https://www.win.tue.nl/ieeetfpm/lib/exe/fetch.php?cache=&media=shared:unknown.jpg"

