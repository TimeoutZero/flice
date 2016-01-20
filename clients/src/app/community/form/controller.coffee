angular.module "web"
  .controller "FormController", ($scope, $state, $stateParams, hToast, CommunityService, TagService) ->
    
    $scope.cropper =
      source  :
        image : null
        cover : null
      cropped :
        image : null
        cover : null

    $scope.bounds = 
      left   : 0
      right  : 0
      top    : 0
      bottom : 0

    $scope.attrs =
      tags       : []
      percentage : 20

    $scope.community =
      privacy : 'PUBLIC'
      images :
        cover : "/assets/images/community_cover.jpg",
        thumb : "/assets/images/community_thumb.jpg"

    $scope.methods = 

      create : ()->
        if $scope.communityCreateForm.$valid

          if $scope.cropper.cropped.image != null
            $scope.community.image = $scope.cropper.cropped.image
         
          if $scope.cropper.cropped.cover != null
            $scope.community.cover = $scope.cropper.cropped.cover

          if $scope.community.id == undefined
            promise = CommunityService.create($scope.community)

            promise.success (data) ->
              $state.go 'community.list'
            promise.error (data) ->
              alert 'error create community'
              
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

            if data.length == 0
              $scope.attrs.tags.push { id : null , name : arg } 
          
          promise.error (data)->
            alert 'error get tags by autocomplete'

    do ->
      
      if $stateParams.id != undefined && $stateParams.id.length > 0
        
        promise = CommunityService.get $stateParams.id
      
        promise.success (data) ->
          $scope.community.id          = data.id
          $scope.community.name        = data.name
          $scope.community.description = data.description
          $scope.community.tags        = data.tags
          $scope.community.privacy   = data.privacy





