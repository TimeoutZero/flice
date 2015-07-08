
angular.module "web"
  .config ($stateProvider, $urlRouterProvider) ->
    $stateProvider

      .state "home",
        url: "/"
        templateUrl : "app/community/community.html"
        #controller  : "CommunityController"
        #abstract    : yes

      .state "community",
        url: "/community"
        templateUrl : "app/community/community.html"
        controller  : "CommunityController"
        abstract    : yes

      .state('community.self'
        url : '/x'
        views :
          '' :
            controller  : 'CommunitySelfController'
            templateUrl : 'app/community/self.html'
          'community-menu@community.self' :
            controller  : 'CommunitySelfMenuController'
            templateUrl : 'app/community/subview/menu.html'
          'community-content@community.self' :
            controller  : 'CommunitySelfContentController'
            templateUrl : 'app/community/subview/content.html'
      )

    $urlRouterProvider.otherwise '/'