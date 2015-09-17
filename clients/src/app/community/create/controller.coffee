angular.module "web"
  .controller "CommunityCreateController", ($scope, $state) ->
    $scope.community = 
      images :
        cover : "/assets/images/community_cover.jpg",
        thumb : "/assets/images/community_thumb.jpg"

    $scope.values = [
        { id: "1", name: "teste1" },
        { id: "2", name: "teste2" }
      ]

    $scope.uploadImage : () ->
      console.log 'CHANGE PHOTO'

    $scope.methods = 

      uploadThumb : () ->
        console.log 'CHANGE PHOTO'
    # $scope.queryOptions = 
    #   query : (query)->
    #     query.callback({results : $scope.values })