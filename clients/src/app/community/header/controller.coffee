angular.module "web"
  .controller 'HeaderController', ($rootScope, $scope, $timeout) ->

    $scope.attrs =
        feedback :
          enable  : no
          fadein  : no
          fadeout : no
          title	  : ''
          message : ''

    $scope.methods =

      disableFeedback : ()->
        $scope.attrs.feedback.fadeout = yes
        $scope.attrs.feedback.enable  = no

      isLogged : ()->
        return $rootScope.isLogged

    $scope.$on 'feedback-content', (event, data) ->

      $scope.attrs.feedback.enable = true
      $scope.attrs.feedback.fadein = true

      if data.success
        $scope.attrs.feedback.title   = 'Success!'
        $scope.attrs.feedback.message = data.message
      else
        $scope.attrs.feedback.title   = 'Error!'
        $scope.attrs.feedback.message = data.message

      $timeout($scope.methods.disableFeedback, 3000)
