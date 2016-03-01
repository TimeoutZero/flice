angular.module 'web'
  .controller 'InviteModalController', ($scope, hToast, InviteService) ->

    $scope.attrs = 
      email : ''

    $scope.methods =

      send : ()->

        promise = InviteService.send($scope.attrs.email)

        callback = () ->
          $scope.modalInstance.close();


        promise.success (data) ->
          hToast.success('The invite was sent.');
          callback();

        promise.error (error) ->
          hToast.error('Error to send your invite, try later.')
          callback();