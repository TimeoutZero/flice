angular.module "web"
  .service "TopicService", (CORE_API, $http) ->

    getById: (communityId, page) ->

      $http
        url    : CORE_API + "/community/#{communityId}/topic"
        method : 'GET'
        params   :
          'page' : page
          'size' : 10
 
    create : (communityId, data) ->

      $http
        feedback : true
        url      : CORE_API + "/community/#{communityId}/topic"
        method   : 'POST'
        data     :
          'name'        : data.name
          'content'     : data.content

    update : (communityId, topic) ->

      $http
        feedback : true
        url      : CORE_API + "/community/#{communityId}/topic/#{topic.id}"
        method   : 'PUT'
        data     :
          'name'        : data.name

    delete : (communityId, topicId) ->

      $http
        # feedback : 
        #   message = 'Topic deleted!'
        feedback : true
        url    : CORE_API + "/community/#{communityId}/topic/#{topicId}"
        method : 'DELETE'

