angular.module "web"
  .service "CommunitySelfService", ($http) ->

    getById: (id) ->

      $http
        url    : "https://api.myjson.com/bins/4gdhe"
        method : 'GET'
