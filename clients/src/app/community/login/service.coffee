angular.module 'web'
  .service 'LoginService', ($http, CORE_API) ->

    register : (form) ->
      $http
        url    : CORE_API + "/account/user"
        method : 'POST'
        data   :
          'email'    : form.email.$modelValue
          'password' : form.password.$modelValue

    createToken : (form) ->
      $http
        url    : CORE_API + "/account/token"
        method : 'POST'
        data   :
          'email'    : form.email.$modelValue
          'password' : form.password.$modelValue
      
