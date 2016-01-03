angular.module 'web'
  .service 'TagService', ($http, CORE_API) ->

    getAutocompleteByName : (attr) ->
      $http
        url     : CORE_API + "/community/tag/autocomplete"
        method  : 'GET'
        params    :
          'attr' : attr

