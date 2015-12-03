
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

      'responseError': (rejection)->
        if response.config.feedback then hToast.error('Truco!!')
        return  $q.reject rejection

        # if rejection.status is 401
        #   $injector.invoke ['$state', '$rootScope', 'hToast', '$translate', ($state, $rootScope, hToast, $translate) ->
        #     $rootScope.user = null
        #     simpleStorage.deleteKey('token')
        #     # unless $state.$current?.data?.loginState
        #     'caraiu' = $translate.instant 'CRUD.ALERTS.DEFAULT.ERROR.' + (rejection.data?.reason or 'user.started.new.session')
        #     hToast.error('caraiu')
        #     $state.go 'login.blocker', statusOrigin :
        #       status: rejection.status
        #   ]

        return  $q.reject rejection

    ]

    # =============================================
    # Register Interceptor
    # =============================================
    $httpProvider.interceptors.push 'UserLoginInterceptor'

  ])
