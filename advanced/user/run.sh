run=$1

if [ -z $run ];then
	run=pr
fi

if [ $run == "pr"];
then
cd ../agent
mvn clean package

mv target/agent*.jar ../user/
cd ../user
mvn clean package
fi

java -cp target/advanced-1.0-SNAPSHOT.jar -javaagent:*.jar  javat.agent.transformer.Main
#java -cp target/advanced-1.0-SNAPSHOT.jar  javat.agent.transformer.Main



独立负责CRedis 3.0项目，保证项目顺利演练且在生产环境正式使用
修复CRedis Java和.NET客户端bug；优化Redis主从切换性能，将多Redis主从切换时间从分钟级降低到秒级
