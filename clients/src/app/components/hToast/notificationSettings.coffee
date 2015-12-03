# =============================================
# Modules
# =============================================
angular.module('hToast')

  # =============================================
  # ngToastProvider Config
  # =============================================
  .config ['ngToastProvider', (ngToastProvider) ->

    ngToastProvider.configure
      additionalClasses  : 'text-left animated fast flipInY'
      verticalPosition   : 'top'
      horizontalPosition : 'right'
      maxNumber          : 2
      dismissButton      : true
      timeout            : 4000
      dismissButtonHtml  : """
        <span class="icon icon-adm-close"></span>
      """

  ]