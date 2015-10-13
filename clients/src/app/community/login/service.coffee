angular.module 'web'
  .service 'LoginService', ($http, CORE_API) ->

    register : (form) ->
      $http
        url    : CORE_API + "/user"
        method : 'POST'
        data   :
          'email'    : form.email.$modelValue
          'password' : form.password.$modelValue

    createToken : (form) ->
      $http
        url    : CORE_API + "/user/token"
        method : 'POST'
        data   :
          'email'    : form.email.$modelValue
          'password' : form.password.$modelValue
      
