angular.module 'web'
  .service 'UserService', ($http, CORE_API) ->

    get : (id) ->
      $http
        url     : CORE_API + '/user/me'
        method  : 'GET'

    update : (form) -> 
      $http
        url     : CORE_API + '/user/' + form.id
        method  : 'PUT'
        params    :
          'name'        : form.name,
          'username'    : form.username
          'description' : form.description
          'photo'       : form.photo