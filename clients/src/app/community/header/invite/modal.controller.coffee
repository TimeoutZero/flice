angular.module 'web'
  .controller 'InviteModalController', ($scope, hToast, InviteService) ->

    $scope.attrs = 
      email : ''

    $scope.methods =

      send : ()->
        
        promise = InviteService.send($scope.attrs.email)

        promise.success (data) ->
          hToast.success('The invite was sent.');
        promise.error (error) ->
          hToast.error('Error to send your invite, try later.')