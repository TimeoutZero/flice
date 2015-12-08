angular.module 'web'
.directive 'ngThumb', ($window)->
  helper =
    support: !!($window.FileReader and $window.CanvasRenderingContext2D)
    isFile: (item)->
      angular.isObject(item) and item instanceof $window.File
    isImage: (file)->
      type = "|#{file.type.slice(file.type.lastIndexOf('/') + 1)}|"
      return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) isnt -1

  return directive = {
    restrict: 'A'
    template: '<canvas/>'
    link: (scope, el, attrs)->
      return if !helper.support

      params = scope.$eval attrs.ngThumb

      return if !helper.isFile(params.file) or !helper.isImage(params.file)

      canvas = el.find('canvas')
      reader = new FileReader()

      onLoad =
        file: (event)->
          img = new Image()
          img.onload = onLoad.image
          img.src = event.target.result

        image: ()->
          width = params.width or (@width / @height) * params.height
          height = params.height or (@height / @width) * params.width

          canvas.attr {width, height}
          canvas[0].getContext('2d').drawImage(@, 0, 0, width, height)

      reader.onload = onLoad.file
      reader.readAsDataURL(params.file)

      return

  }