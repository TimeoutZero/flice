
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
        url        : '/self/:id'
        redirectTo : 'community.self.content'
        views :
          '' :
            controller  : 'CommunitySelfController'
            templateUrl : 'app/community/self/main.html'
          'community-self-menu@community.self' :
            controller  : 'MenuController'
            templateUrl : 'app/community/self/menu/view/main.html'
      )
      .state('community.self.content'
        url : ''
        views :
          'container@community.self' :
            controller  : 'ContentController'
            templateUrl : 'app/community/self/content/view/main.html'
      )
      .state('community.self.post'
        url : '/post'
        views :
          'container@community.self' :
            controller  : 'TopicController'
            templateUrl : 'app/community/self/post/view/main.html'
      )
      .state('community.list'
        url: "/list"
        controller  : 'CommunityListController'
        templateUrl : "app/community/list/main.html"
      )
  
    $urlRouterProvider.otherwise '/community/login'