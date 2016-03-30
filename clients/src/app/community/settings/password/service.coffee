angular.module 'web'
  .service 'PasswordService', ($http, CORE_API) ->

    update : (form) ->

      $http
        feedback : 
          message : 'Password has been updated.'

        url     : CORE_API + '/user/password'
        method  : 'PUT'
        params    :
          'newPassword'             : form.newPassword
          'newPasswordConfirmation' : form.newPasswordConfirmation
          'actualPassword'          : form.actualPassword