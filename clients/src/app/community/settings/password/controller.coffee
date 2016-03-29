angular.module 'web'
  .controller 'PasswordController', ($scope, PasswordService) ->

    $scope.attrs =
      passwordForm : {}

    $scope.methods =

      update : () ->
        
        if $scope.form.$valid
          if $scope.attrs.passwordForm.newPassword == $scope.attrs.passwordForm.newPasswordConfirmation
            
            promise = PasswordService.update($scope.attrs.passwordForm)

            promise.success (data) ->
              console.log 'success'
            promise.error () ->
              console.log 'error'

          else
            $scope.form.newPasswordConfirmation.$setValidity('passwordDifferentOfConfirmation', false)