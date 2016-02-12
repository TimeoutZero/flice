angular.module "web"
  .controller 'HeaderController', ($rootScope, $scope, $timeout, $uibModal) ->

    $scope.methods =

      isLogged : () ->
        return $rootScope.isLogged

      openInviteModal : () ->
        modalInstance = $uibModal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'app/community/header/invite/modal.html',
          controller: 'InviteModalController',
          size: null,
        })

