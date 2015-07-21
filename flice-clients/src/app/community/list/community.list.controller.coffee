angular.module "web"
  .controller "CommunityListController", ($scope) ->

    $scope.communities = [
      {
        id    : 1
        name  : "Batman V Superman: Dawn of Justice"
        image : "http://s2.glbimg.com/rYHPUCUursE3coAA6D0eUhKuYmw=/s.glbimg.com/jo/g1/f/original/2014/05/22/cartaz-batman-v-superman.jpg",
        tags  : ["Movie", "Entertainment", "Trailer" ]
      },
      {
        id    : 2
        name  : "Game of Thrones"
        image : "http://br.web.img2.acsta.net/medias/nmedia/18/75/09/69/19692840.jpg"
        tags  : [ "Series", "Entertainment", "Books & Theorys "]
      },
      {
        id    : 3
        name  : "Docker"
        image : "https://pbs.twimg.com/profile_images/378800000124779041/fbbb494a7eef5f9278c6967b6072ca3e_400x400.png",
        tags  : [ "Development", "DevOps", "Articles", "Books" ]
      },
      {
        id    : 4
        name  : "True Detective"
        image : "http://static.tvgcdn.net/mediabin/showcards/tvshows/500000-599999/thumbs/589709_true-detective-season-2_300x400.png"
        tags  : ["Series", "Suspense", "HBO", "Top Series" ]
      },
      {
        id    : 5
        name  : "Angular",
        image : "https://goo.gl/8qiMmd"
        tags  : ["Development", "Articles", "Videos & Books", "Front-End" ]
      },
      {
        id    : 6
        name  : "Daredevil",
        image : "http://images.kinoman.az/thumb/2015/04/sorvigolova-daredevil.jpg"
        tags  : ["Series", "Action", "Netflix"]
      }
  ]
