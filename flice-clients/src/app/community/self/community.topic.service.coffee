angular.module "web"
  .service "CommunitySelfTopicService", ($http) ->

    getById: (id) ->

      $http
        url    : "https://api.myjson.com/bins/3oq42"
        method : 'GET'
