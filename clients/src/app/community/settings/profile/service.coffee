angular.module 'web'
  .service 'UserService', ($http, CORE_API) ->

    get : (id) ->
      $http
        url     : CORE_API + '/user/me'
        method  : 'GET'

    checkUsername : (username) ->
      $http
        url     : CORE_API + '/user/check/username'
        method  : 'GET'
        params  :
          'username' : username

    update : (form) -> 
      $http
        feedback : 
          message : 'User has been updated.'

        url     : CORE_API + '/user/' + form.id
        method  : 'PUT'
        data    :
          'name'        : form.name,
          'username'    : form.username
          'description' : form.description
          'photo'       : form.photo