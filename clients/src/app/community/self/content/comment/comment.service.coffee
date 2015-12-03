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
        feedback : true
        url      : CORE_API + "/topic/#{id}/comment"
        method   : "POST"
        data     :
          'content' : data

    update : (topicId, commentId, data) ->
      $http
        feedback : true
        url      : CORE_API + "/topic/#{topicId}/comment/#{commentId}"
        method   : "PUT"
        data     :
          'content' : data

    delete : (topicId, commentId) ->
      $http
        feedback : true
        url      : CORE_API + "/topic/#{topicId}/comment/#{commentId}"
        method   : "DELETE"