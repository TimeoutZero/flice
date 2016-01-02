angular.module "web"
  .service "CommunitySelfService", (CORE_API, $http) ->

    getById : (id) ->

      $http
        url    : CORE_API + "/community/#{id}"
        method : 'GET'

    getMemberInfo : (id) ->
    
      $http
        url     : CORE_API + "/community/#{id}/member/info" 
        method  : 'GET'

    join : (id) ->

      $http
        feedback : true
        url      : CORE_API + "/community/#{id}/join"
        method   : 'PUT'
