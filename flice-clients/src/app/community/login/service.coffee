angular.module 'web'
  .service 'LoginService', ($http, ACCOUNT_API) ->

    register : (form)->
      $http
        url    : ACCOUNT_API + "/account/user"
        method : 'POST'
        data   : 
          'email'    : form.email.$modelValue
          'password' : form.password.$modelValue
      
