angular.module 'web'
  .service 'UserService', ($http, CORE_API) ->

    get : (id) ->
      $http
        url     : CORE_API + '/user/me'
        method  : 'GET'