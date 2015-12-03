
angular.module 'hToast.vendors', [
  'ngToast'
  'ngAnimate'
  'ngTouch'
  'ngSanitize'
  'ngResource'
]

# =============================================
# Modules
# =============================================
angular.module 'hToast.controllers' , ['hToast.vendors']
angular.module 'hToast.filters'     , ['hToast.vendors']
angular.module 'hToast.factories'   , ['hToast.vendors']
angular.module 'hToast.services'    , ['hToast.vendors']
angular.module 'hToast.constants'   , ['hToast.vendors']
angular.module 'hToast.directives'  , ['hToast.vendors']
angular.module 'hToast.mocks'       , ['hToast.vendors']
angular.module 'hToast.providers'   , ['hToast.vendors']
angular.module 'hToast.templates'   , ['hToast.vendors']
angular.module 'hToast.configs'     , ['hToast.vendors']

# =============================================
# Scripts Module
# =============================================
angular.module 'hToast.scripts'     , [
  'hToast.controllers'
  'hToast.constants'
  'hToast.filters'
  'hToast.factories'
  'hToast.services'
  'hToast.directives'
  'hToast.mocks'
  'hToast.providers'
  'hToast.templates'
  'hToast.configs'
]

# =============================================
# Main Module
# =============================================
angular.module 'hToast', [
  'hToast.vendors'
  'hToast.scripts'
]