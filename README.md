*** Args to debug at eclipse

Account: --spring.profiles.active=DEV --server.port=8081 --spring.datasource.url=jdbc:mysql://docker:3307/flice-account
Core: --spring.datasource.url=jdbc:mysql://docker:3307/flice-core --account.url=http://localhost:8081/account/api