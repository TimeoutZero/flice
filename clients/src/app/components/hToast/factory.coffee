'use strict'

angular.module('hToast.factories')
  .factory 'hToast', ['ngToast', '$sce', (ngToast, $sce) ->

    return {
      alert   : (options = {}) ->
        ngToast.create(options)

      success : (message, options) ->
        @generic('success', message, options)

      info    : (message, options) ->
        @generic('info', message, options)

      warning : (message, options) ->
        @generic('warning', message, options)

      error   : (message, options) ->
        @generic('danger', message, options)

      generic : (className, message, options = {}) ->
        options.content          = $sce.trustAsHtml(message)
        options.className        = className
        options.compileContent   = true
        options.timeout          or= @calculateTimeout(message)
        @alert(options)

      calculateTimeout: (message = "") ->
        aditionalTimeout = 0
        minTime          = ngToast.settings.timeout
        totalTime        = 0

        unless minTime is 0
          # Get the html for jQuery objects
          if message.html
            message          = message.html()
            aditionalTimeout = ( message.indexOf("<a>") > -1 ? 2000 : 0 )

          # Average 150 words per minute
          totalTime = ( message.split(" ").length * 600 ) + aditionalTimeout
          unless totalTime > minTime then totalTime = minTime

        return totalTime
    }
  ]
