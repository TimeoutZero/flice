angular.module "web"
  .service "CommunitySelfTopicService", ($http) ->

    getById: (id) ->

      $http
        url    : "https://gist.githubusercontent.com/lucasgmartins/ed4ee7601dec13c8fa6e/raw/08dbb41a418335e94be85cd1648af801d0fa5ecb/gistfile1.txt"
        method : 'GET'
