angular.module "web"
  .controller "FormController", ($scope, $state, $stateParams, hToast, CommunityService, TagService, FileUploader) ->
    
    $scope.uploader = new FileUploader();

    $scope.attrs =
      tags       : []
      percentage : 20

    $scope.community =
      visibility : true
      images :
        cover : "/assets/images/community_cover.jpg",
        thumb : "/assets/images/community_thumb.jpg"

    $scope.methods = 

      create : ()->
        if $scope.communityCreateForm.$valid

          if $scope.community.id == undefined
            promise = CommunityService.create($scope.community)
          else 
            promise = CommunityService.update($scope.community)

            promise.success (data) ->
              $state.go 'community.list'
            
        else
          hToast.error 'Did you forget any field? :)' 
      
      refreshTags : (arg)->
      
        if arg.length >= 3
          
          promise = TagService.getAutocompleteByName(arg);

          promise.success (data) ->
            $scope.attrs.tags = data
          promise.error (data)->
            alert 'error get tags by autocomplete'

    do ->
      
      if $stateParams.id != undefined
      
        promise = CommunityService.get $stateParams.id
      
        promise.success (data) ->
          $scope.community.id          = data.id
          $scope.community.name        = data.name
          $scope.community.description = data.description




