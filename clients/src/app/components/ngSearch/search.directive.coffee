angular.module "web"
  .directive 'ngTeste', ->

    directive = 
      restrict    :  'AE',
      templateUrl : 'app/components/ngSearch/search.html'
      link        : (scope, iElement, iAttrs, ngModel) ->
 
        scope.search = ()->

          console.log scope
          console.log scope.content
          if scope.content && scope.content.length > 0
            scope.showOptions = true
          else 
            scope.showOptions = false