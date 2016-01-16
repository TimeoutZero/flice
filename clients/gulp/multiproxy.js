 /*jshint unused:false */

var httpProxies = {};
var httpProxy = null;

/**
 * Contains all middlewares
 * @type {Array}
 */
var middlewares = [];

module.exports = function(options) {

  'use strict';
  var moduleOptions = options.modulesData['proxy'];
  var _             = options.plugins.lodash;
  var chalk         = options.plugins.chalk;
  httpProxy         = require('../node_modules/basebuild-angular/node_modules/http-proxy')

  /*
   * Location of your backend server
   */
  var proxies = {
    core: {
      // from : 'http://localhost/api/core'
      // to   : 'http://192.168.99.100:8080',

      target: 'http://192.168.99.100:8080',
      
      // Não é pra fazer proxy quando a url tiver não tiver /api/
      next: function(req, res, next){
        var isAPI = req.url.indexOf('/api/') === -1;
        return isAPI;
      }
    },
    s3: {
      target: 'http://192.168.99.100:4569',

      // Não é pra fazer proxy quando a url tiver não tiver http://flice.s3.amazon.com
      next: function(req, res, next){
        return req.url.indexOf('http://flice.s3.amazon.com') === -1
      }
    }
  };

  /**
   * Executed only if the module is enabled
   */
  if(moduleOptions.isEnabled){
    
    for(var proxyName in proxies){
      var proxySettings = proxies[proxyName];

      if(!httpProxies[proxyName]){

        httpProxies[proxyName] = httpProxy.createProxyServer({
          target: proxySettings.target
        });

        /**
         * Used to test the context of request
         * @type {Regex, Function}
         */
        var nextOption = proxySettings.next || moduleOptions.next;

     
        (function(proxySettings, proxy, nextOption){
          /**
           * By pass to proxy
           * @type {[type]}
           */
          var nextTest   = null;

          /**
           * Logs every request
           * @param  {Object} proxyReq Proxy request
           * @param  {Object} req      Request
           * @param  {Object} res      Response
           */
          httpProxies[proxyName].on('proxyReq', function(proxyReq, req, res) {
            proxyReq.setHeader('Access-Control-Allow-Origin', proxySettings.target);

            console.log(chalk.green('[Proxy]'), 'Request made to:', proxySettings.target + req.url);
          });


          /**
           * Logs errors
           * @param  {Object} proxyReq Proxy request
           * @param  {Object} req      Request
           * @param  {Object} res      Response
           */
          httpProxies[proxyName].on('error', function(error, req, res) {
            res.writeHead(500, {
              'Content-Type': 'text/plain'
            });

            console.error(chalk.red('[Proxy]'), error);
          });

          var validateNextFunction = function(req, res, next){
            return nextOption(req, res, next)
          }


          var validateRegexNext = function(req, res, next){
            return nextOption.test(req.url)
          }

          /**
           * Checks the type of modulesData.proxy.next
           */
          if(_.isRegExp(nextOption)){
            nextTest = validateRegexNext;
          } else if(_.isFunction(nextOption)){
            nextTest = validateNextFunction;
          } else {
            console.error(chalk.red('[Proxy]'), 'Iligal type for property \"next\"');
          }

          /*
           * The proxy middleware is an Express middleware added to BrowserSync to
           * handle backend request and proxy them to your backend.
           */
          var proxyMiddleware = function(req, res, next) {
            /*
             * This test is the switch of each request to determine if the request is
             * for a static file to be handled by BrowserSync or a backend request to proxy.
             *
             * The existing test is a standard check on the files extensions but it may fail
             * for your needs. If you can, you could also check on a context in the url which
             * may be more reliable but can't be generic.
             */

            if (nextTest(req, res, next))  {
              next();
            } else {
              proxy.web(req, res);
            }
          }

          middlewares.push(proxyMiddleware);
        })(proxySettings, httpProxies[proxyName], nextOption);


      }
    }
  }

  return middlewares;
};