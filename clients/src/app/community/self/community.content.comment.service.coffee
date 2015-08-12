angular.module "web"
  .service "CommunitySelfCommentService", ($http) ->

    getById : (id, start, end) ->

      $http
        url    : "https://api.myjson.com/bins/2mtwy"
        method : "GET"