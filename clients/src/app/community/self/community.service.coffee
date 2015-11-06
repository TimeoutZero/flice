angular.module "web"
  .service "CommunitySelfService", (CORE_API, $http) ->

    getById: (id) ->

      $http
        url    : CORE_API + "/community/#{id}"
        method : 'GET'
