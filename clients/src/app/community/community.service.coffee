angular.module 'web'
  .service 'CommunityService', ($http, CORE_API) ->

    get : (id) ->
      $http
        url     : CORE_API + "/community/#{id}"
        method  : 'GET'

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
    
    update : (form) ->
      $http
        url    : CORE_API + "/community/#{form.id}"
        method : 'PUT'
        data   : 
          'name'        : form.name
          'description' : form.description
          'visibility'  : form.visibility