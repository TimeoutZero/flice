
angular.module "web"
  .config ($stateProvider, $urlRouterProvider) ->
    $stateProvider
     
      .state "community",
        url: "/community"
        abstract    : yes
        views :
          '' :
            templateUrl : 'app/community/main.html'
            controller  : "CommunityController"
          'community-header@community':
            templateUrl : 'app/community/header/main.html'
            controller  : 'HeaderController'

      .state('community.login'
        url   : '/login'
        views :
          '' :
            templateUrl : 'app/community/login/main.html'
            controller  : 'LoginController'
      )      
      .state('community.create'
        url   : '/create'
        views :
          '' :
            templateUrl : 'app/community/create/main.html'
            controller  : 'CommunityCreateController'
      )
      .state('community.self'
        url : '/self/:id'
        views :
          '' :
            controller  : 'CommunitySelfController'
            templateUrl : 'app/community/self/main.html'
          'community-self-menu@community.self' :
            controller  : 'CommunitySelfMenuController'
            templateUrl : 'app/community/self/subview/menu.html'
          'community-self-content@community.self' :
            controller  : 'CommunitySelfContentController'
            templateUrl : 'app/community/self/subview/content.html'
          'community-self-post@community.self' :
            controller  : 'CommunityTopicController'
            templateUrl : 'app/community/self/subview/post.html'
      )
 
      .state('community.list'
        url: "/list"
        controller  : 'CommunityListController'
        templateUrl : "app/community/list/main.html"
      )
  
    $urlRouterProvider.otherwise '/community/login'