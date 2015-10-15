angular.module "web"
  .controller "CommunityCreateController", ($scope, $state, CommunityService, FileUploader) ->
    
    $scope.uploader = new FileUploader();

    $scope.community =
      visibility : true
      images :
        cover : "/assets/images/community_cover.jpg",
        thumb : "/assets/images/community_thumb.jpg"

    $scope.values = [
        { id: "1", name: "teste1" },
        { id: "2", name: "teste2" }
      ]


    # $scope.communityCreateForm =

    $scope.methods = 

      create : ()->
        if $scope.communityCreateForm.$valid

          promise = CommunityService.create($scope.community)

          promise.success (data) ->
           
            $scope.$emit 'feedback', { success: true, message: 'Community created!' }
            $state.go 'community.list'

          promise.error (data, status) ->

            $scope.$emit 'feedback', { success: false, message: 'Fail to create community!' }
            
        else
          $scope.$emit 'feedback', { success: false, message: 'Did you forget any field? :)' }
