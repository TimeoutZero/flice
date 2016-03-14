INSERT INTO product (id, name, token) VALUES (1, 'flice-core', 'a7a928b6-6f84-4b7c-b91d-91326e0cb2b0');
INSERT INTO `profile` (`id`, `name`, `username`, `description`) VALUES (1, 'Lucas Martins', 'lucas.gmmartins', 'Eu não me importo com o que os outros pensam sobre o que eu faço, mas eu me importo muito com o que eu penso sobre o que eu faço. Isso é caráter.');
INSERT INTO `profile` (`id`, `name`, `username`, `description`) VALUES (2, 'Bruce Wayne', 'bruce.wayne', 'Eu sou do tamanho daquilo que SINTO, que VEJO e que FAÇO, não do tamanho que os outros me enxergam.');
INSERT INTO `user` (`id`, `user_email`, `user_password`, `profile_id`) VALUES (1, 'lucas.gmmartins@gmail.com', '$2a$05$GCE727EgX6Ay7YIVZzQw9.0IPkISp2H4EF76HFaE25j3PHmq1JAai', 1);
INSERT INTO `user` (`id`, `user_email`, `user_password`, `profile_id`) VALUES (2, 'bruce.wayne@gmail.com', '$2a$05$GCE727EgX6Ay7YIVZzQw9.0IPkISp2H4EF76HFaE25j3PHmq1JAai', 2);
