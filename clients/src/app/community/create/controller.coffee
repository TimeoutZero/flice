angular.module "web"
  .controller "CommunityCreateController", ($scope, $state, hToast, CommunityService, FileUploader) ->
    
    $scope.uploader = new FileUploader();

    $scope.attrs =
      percentage : 20

    $scope.community =
      visibility : true
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

          promise = CommunityService.create($scope.community)

          promise.success (data) ->
            $state.go 'community.list'
            
        else
          hToast.error 'Did you forget any field? :)'
