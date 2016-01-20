
# =============================================
# Module
# =============================================
angular.module 'web'


  # =============================================
  # Config
  # =============================================
  .config( ['$httpProvider', '$provide', ($httpProvider, $provide) ->

    # =============================================
    # UserLoginInterceptor
    # =============================================
    $provide.factory 'UserLoginInterceptor', [ '$q', '$injector', '$rootScope', 'hToast', ($q, $injector, $rootScope, hToast) ->

      'request': (config) ->
        # console.log 'ae'
        # hToast.success('caraiu')
        return config

      'requestError': (rejection)->
        hToast.success('caraiu')
        return $q.reject rejection

      'response': (response)->
        if response.config.feedback then hToast.success('Truco!!')
        return response or $q.when response

      'responseError': (response)->
        if response.status is 401
          $injector.invoke ['$state', '$rootScope', 'hToast', ($state, $rootScope, hToast) ->
            hToast.error('You are no longer logged in!')
            $state.go 'community.login'
          ]
        return  $q.reject response

    ]

    # =============================================
    # Register Interceptor
    # =============================================
    $httpProvider.interceptors.push 'UserLoginInterceptor'

  ])
