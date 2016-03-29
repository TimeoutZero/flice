
mvn clean install -DskipTests -f projects/account/client/pom.xml
mvn clean package -DskipTests -f projects/account/server/pom.xml
mvn clean package -DskipTests -f projects/core/server/pom.xml