angular.module "web"
  .service "CommentService", (CORE_API, $http) ->

    getById : (id, page, pageSize) ->

      $http
        url    : CORE_API + "/topic/#{id}/comment"
        method : "GET"
        params   :
          'page' : page
          'size' : pageSize

    create : (id, data) ->
      $http
        url     : CORE_API + "/topic/#{id}/comment"
        method  : "POST"
        data    :
          'content' : data