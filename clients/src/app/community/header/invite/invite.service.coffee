angular.module 'web'
  .service 'InviteService', ($http, CORE_API) ->

    send : (email) ->
      $http
        feedback : true
        url      : CORE_API + "/user/invite"
        method   : 'POST'
        params   :
          'email' : email