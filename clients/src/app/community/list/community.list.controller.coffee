angular.module "web"
  .controller "CommunityListController", ($scope, $state) ->

    $scope.isListView = false

    $scope.methods =
      changeView : ->
        $scope.isListView = !$scope.isListView

      goToCommunity: (id) ->
        $state.go 'community.self', {id: id}

    $scope.communities = [
      {
        id    : 1
        name  : "Batman V Superman: Dawn of Justice"
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio pharetra enim efficitur, eget dapibus erat luctus. Vestibulum scelerisque odio pulvinar, viverra lacus eget, viverra massa. "
        image : "http://s2.glbimg.com/rYHPUCUursE3coAA6D0eUhKuYmw=/s.glbimg.com/jo/g1/f/original/2014/05/22/cartaz-batman-v-superman.jpg",
        tags  : ["Movie", "Entertainment", "Trailer" ]
      },
      {
        id    : 2
        name  : "Game of Thrones"
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio pharetra enim efficitur, eget dapibus erat luctus. Vestibulum scelerisque odio pulvinar, viverra lacus eget, viverra massa. "
        image : "http://br.web.img2.acsta.net/medias/nmedia/18/75/09/69/19692840.jpg"
        tags  : [ "Series", "Entertainment", "Books & Theorys "]
      },
      {
        id    : 3
        name  : "Docker"
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio pharetra enim efficitur, eget dapibus erat luctus. Vestibulum scelerisque odio pulvinar, viverra lacus eget, viverra massa. "
        image : "https://pbs.twimg.com/profile_images/378800000124779041/fbbb494a7eef5f9278c6967b6072ca3e_400x400.png",
        tags  : [ "Development", "DevOps", "Articles", "Books" ]
      },
      {
        id    : 4
        name  : "True Detective"
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio pharetra enim efficitur, eget dapibus erat luctus. Vestibulum scelerisque odio pulvinar, viverra lacus eget, viverra massa. "        
        image : "http://static.tvgcdn.net/mediabin/showcards/tvshows/500000-599999/thumbs/589709_true-detective-season-2_300x400.png"
        tags  : ["Series", "Suspense", "HBO", "Top Series" ]
      },
      {
        id    : 5
        name  : "Angular",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio pharetra enim efficitur, eget dapibus erat luctus. Vestibulum scelerisque odio pulvinar, viverra lacus eget, viverra massa. "        
        image : "https://goo.gl/8qiMmd"
        tags  : ["Development", "Articles", "Videos & Books", "Front-End" ]
      },
      {
        id    : 6
        name  : "Daredevil",
        description : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio pharetra enim efficitur, eget dapibus erat luctus. Vestibulum scelerisque odio pulvinar, viverra lacus eget, viverra massa. "        
        image : "http://images.kinoman.az/thumb/2015/04/sorvigolova-daredevil.jpg"
        tags  : ["Series", "Action", "Netflix"]
      }
  ]
