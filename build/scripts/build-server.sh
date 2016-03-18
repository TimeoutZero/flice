
cd ../../

mvn clean install -DskipTests -f projects/account/client/pom.xml
mvn test -DskipTests -f projects/account/server/pom.xml
mvn test -DskipTests -f projects/core/server/pom.xml