angular.module "web"
  .service "CommunitySelfTopicService", (CORE_API, $http) ->

    getById: (communityId) ->

      $http
        url    : CORE_API + "/community/#{communityId}/topic"
        method : 'GET'

    create : (communityId, data) ->

      $http
        url    : CORE_API + "/community/#{communityId}/topic"
        method : 'POST'
        data   :
          'name'        : data.name
          'content'     : data.content

