angular.module "web"
  .service "TopicService", (CORE_API, $http) ->

    getById: (communityId) ->

      $http
        url    : CORE_API + "/community/#{communityId}/topic"
        method : 'GET'
 
    create : (communityId, data) ->

      $http
        feedback : true
        url      : CORE_API + "/community/#{communityId}/topic"
        method   : 'POST'
        data     :
          'name'        : data.name
          'content'     : data.content