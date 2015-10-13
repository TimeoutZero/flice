INSERT INTO `user` (`user_id`, `user_account_id`, `user_email`) VALUES (1, 1 , 'lucas.gmmartins@gmail.com');
INSERT INTO `user_roles` (`user_id`, `roles`) VALUES (1, 'ROLE_USER');

INSERT INTO `community` (`community_id`, `community_active`, `community_created`, `community_description`, `community_image`, `community_name`, `user_id`)
VALUES
  (1, 00000001, NULL, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio ph', 'http://s2.glbimg.com/rYHPUCUursE3coAA6D0eUhKuYmw=/s.glbimg.com/jo/g1/f/original/2014/05/22/cartaz-batman-v-superman.jpg', 'Batman V Superman: Dawn of Justice', NULL),
  (2, 00000001, NULL, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio ph', 'http://br.web.img2.acsta.net/medias/nmedia/18/75/09/69/19692840.jpg', 'Game of Thrones', NULL),
  (3, 00000001, NULL, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio ph', 'https://pbs.twimg.com/profile_images/378800000124779041/fbbb494a7eef5f9278c6967b6072ca3e_400x400.png', 'Docker', NULL),
  (4, 00000001, NULL, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio ph', 'http://static.tvgcdn.net/mediabin/showcards/tvshows/500000-599999/thumbs/589709_true-detective-season-2_300x400.png', 'True Detective', NULL),
  (5, 00000001, NULL, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio ph', 'https://goo.gl/8qiMmd', 'Angular', NULL),
  (6, 00000001, NULL, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur aliquam, elit nec dignissim pulvinar, tortor purus molestie sem, quis aliquam lectus justo quis purus. Donec purus augue, scelerisque a dui eget, semper dictum augue. Cras vehicula odio ph', 'http://images.kinoman.az/thumb/2015/04/sorvigolova-daredevil.jpg', 'Daredevil', NULL);

INSERT INTO `community_tags` (`comunity_id`, `tag_id`)
VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(2, 4),
	(2, 2),
	(2, 5),
	(3, 6),
	(3, 7),
	(3, 8),
	(3, 9),
	(4, 4),
	(4, 10),
	(4, 11),
	(4, 12),
	(5, 6),
	(5, 8),
	(5, 13),
	(5, 14),
	(6, 4),
	(6, 15),
	(6, 16);
  
INSERT INTO `user_communities` (`user_id`, `community_id`)
VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5),
  (1, 6);

