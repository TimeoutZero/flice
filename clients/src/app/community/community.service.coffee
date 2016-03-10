angular.module 'web'
  .service 'CommunityService', ($http, CORE_API) ->

    get : (id) ->
      $http
        url     : CORE_API + "/community/#{id}"
        method  : 'GET'

    getAutocompleteByName : (attr) ->
      $http
        url     : CORE_API + "/community/autocomplete"
        method  : 'GET'
        params    :
          'word' : attr

    list : (page) ->
      $http
        url     : CORE_API + '/community'
        method  : 'GET'
        params   :
          'page' : page
          'size' : 15

    create : (form) ->
      $http
        url    : CORE_API + '/community'
        method : 'POST'
        data   : 
          'name'        : form.name
          'description' : form.description
          'privacy'     : form.privacy
          'tags'        : form.tags
          'image'       : form.image
          'cover'       : form.cover
    
    update : (form) ->
      $http
        url    : CORE_API + "/community/#{form.id}"
        method : 'PUT'
        data   : 
          'name'        : form.name
          'description' : form.description
          'privacy'     : form.privacy
          'tags'        : form.tags
          'image'       : form.image
          'cover'       : form.cover