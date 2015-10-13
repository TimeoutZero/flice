angular.module 'web'
  .service 'CommunityService', ($http, CORE_API) ->

    list : () ->
      $http
        url     : CORE_API + '/community'
        method  : 'GET'

    create : (form) ->
      $http
        url    : CORE_API + '/community'
        method : 'POST'
        data   : 
          'name'        : form.name
          'description' : form.description
          'visibility'  : form.visibility