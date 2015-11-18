angular.module "web"
  .service "CommentService", (CORE_API, $http) ->

    getById : (id, start, end) ->

      $http
        url    : CORE_API + "/topic/#{id}/comment"
        method : "GET"

    create : (id, data) ->
      $http
        url     : CORE_API + "/topic/#{id}/comment"
        method  : "POST"
        data    :
          'content' : data