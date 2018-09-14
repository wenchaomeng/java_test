mvn clean package
java -cp target/advanced-1.0-SNAPSHOT.jar -javaagent:target/advanced-1.0-SNAPSHOT.jar  javat.agent.transformer.Main
#java -cp target/advanced-1.0-SNAPSHOT.jar  javat.agent.transformer.Main
