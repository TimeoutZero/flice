angular.module "web"
  .controller "CommunityCreateController", ($scope, $state, CommunityService) ->
    
    $scope.community = 
      images :
        cover : "/assets/images/community_cover.jpg",
        thumb : "/assets/images/community_thumb.jpg"

    $scope.values = [
        { id: "1", name: "teste1" },
        { id: "2", name: "teste2" }
      ]

    $scope.methods = 

      create : ()->
        if $scope.communityCreateForm.$valid
          alert 'certo'

          promise = CommunityService.create($scope.communityCreateForm)

          promise.success (data) ->
            alert 'cadastrou krl'
          promise.error(data, status) ->
            alert 'deu merda ' + status
            
        else
          alert 'errado'