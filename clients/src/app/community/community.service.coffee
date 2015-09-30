angular.module 'web'
  .service 'CommunityService', ($http, CORE_API) ->

    create : (form) ->
      $http
        url    : CORE_API + '/community'
        method : 'POST'
        withCredentials : true
        data   : 
          'name'        : form.name.$modelValue
          'description' : form.description.$modelValue