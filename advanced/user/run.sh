run=$1

if [ -z $run ];then
	run=pr
fi

if [ $run == "pr" ]
then
	cd ../agent
	mvn -q clean package

	mv target/agent*.jar ../user/
	cd ../user
	mvn -q clean package
fi

#java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -cp target/*-1.0-SNAPSHOT.jar -javaagent:agent-1.0-SNAPSHOT.jar  javat.agent.transformer.Main
java -cp target/*-1.0-SNAPSHOT.jar -javaagent:agent-1.0-SNAPSHOT.jar  javat.agent.transformer.Main
