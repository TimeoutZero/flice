angular.module "web"
  .service "CommunitySelfCommentService", (CORE_API, $http) ->

    getById : (id, start, end) ->

      $http
        url    : CORE_API + "/topic/#{id}/comment/"
        method : "GET"