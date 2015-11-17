angular.module "web"
  .run ($rootScope, $state, $window, $http, CORE_API) ->

    $rootScope.$on '$stateChangeStart', (evt, to, params)->

      if to.redirectTo
        evt.preventDefault();
        $state.go(to.redirectTo, params)

      
      promise = $http (url : CORE_API + '/user/me', method : 'GET')

      promise.success (data) ->
        $rootScope.isLogged = yes

    $window.fbAsyncInit = ()->
      FB.init(
        appId   : '571707226301425',
        status  : true, 
        cookie  : true, 
        xfbml   : true,
        version : 'v2.3'
      )


    do (d = document, s = 'script', id = 'facebook-jssdk') ->
      fjs = d.getElementsByTagName(s)[0]
      if d.getElementById(id) then return
      js    = d.createElement(s)
      js.id = id
      js.src = "//connect.facebook.net/en_US/sdk.js"
      fjs.parentNode.insertBefore(js, fjs)


