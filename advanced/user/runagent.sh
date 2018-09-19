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

for pid in `ps -ef | grep "transformer.Mai[n]" | awk '{print $2}'`
do
	echo killing pid:$pid
	kill -9 $pid
done

echo run main class
nohup java -cp target/*-1.0-SNAPSHOT.jar javat.agent.transformer.Main  &

ps -ef | grep java

sleep 1
pid=`ps -ef | grep "transformer.Mai[n]" | awk '{print $2}'`
echo pid:$pid
java -cp  agent-1.0-SNAPSHOT.jar:${JAVA_HOME}/lib/tools.jar javat.agent.transformer.AgentMainRunAttach $pid  agent-1.0-SNAPSHOT.jar

sleep 60
