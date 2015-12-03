angular.module 'web'
  .service 'LoginService', ($http, CORE_API) ->

    register : (form) ->
      $http
        feedback : true
        url      : CORE_API + "/user"
        method   : 'POST'
        data     :
          'email'    : form.email.$modelValue
          'password' : form.password.$modelValue

    createToken : (form) ->
      $http
        feedback : true
        url      : CORE_API + "/user/token"
        method   : 'POST'
        data     :
          'email'    : form.email.$modelValue
          'password' : form.password.$modelValue
      
