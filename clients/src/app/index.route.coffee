
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
      .state('community.settings'
        url   : '/user/settings'
        redirectTo : 'community.settings.profile'
        views :
          ''  :
            templateUrl : 'app/community/settings/main.html'
      )
      .state('community.settings.profile'
        url   : '/profile'
        views :
          'container@community.settings'  :
            templateUrl : 'app/community/settings/profile/main.html'
            controller  : 'UserController'
      )
      .state('community.settings.password'
        url   : '/password'
        views :
          'container@community.settings'  :
            templateUrl : 'app/community/settings/password/main.html'
            controller  : 'PasswordController'
      )
      
      .state('community.form'
        url   : '/form/:id'
        views :
          '' :
            templateUrl : 'app/community/form/main.html'
            controller  : 'FormController'
      )
      .state('community.list'
        url: "/list"
        controller  : 'CommunityListController'
        templateUrl : "app/community/list/main.html"
      )
      .state('community.self'
        url        : '/:id'
        redirectTo : 'community.self.topic'
        views :
          '' :
            templateUrl : 'app/community/self/content/comment/main.html'
          'community-self-menu@community.self' :
            controller  : 'MenuController'
            templateUrl : 'app/community/self/menu/view/main.html'
      )
      .state('community.self.topic'
        url   : '/topic/'
        views :
          'container@community.self' :
            controller  : 'TopicController'
            templateUrl : 'app/community/self/content/topic/view/main.html'
          'community-self-menu@community.self' :
            controller  : 'MenuController'
            templateUrl : 'app/community/self/menu/view/main.html'
      )
      .state('community.self.content'
        url : '/topic/:topicId/page/:page'
        views :
          'container@community.self' :
            controller  : 'ContentController'
            templateUrl : 'app/community/self/content/comment/view/main.html'
          'community-self-menu@community.self' :
            controller  : 'MenuController'
            templateUrl : 'app/community/self/menu/view/main.html'
      )
      .state('community.self.post'
        url : '/post'
        views :
          'container@community.self' :
            controller  : 'TopicController'
            templateUrl : 'app/community/self/post/view/main.html'
      )

    $urlRouterProvider.otherwise '/community/login'