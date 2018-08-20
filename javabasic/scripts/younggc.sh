CORE=`cat /proc/cpuinfo | egrep processor | wc -l`
threads=100
stacks=100
JAVA_OPTS="-Dsun.misc.URLClassPath.disableJarChecking=true -Xms13g -Xmx13g -Xmn10g -XX:+AlwaysPreTouch -XX:MaxDirectMemorySize=2g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -XX:+UseParNewGC -XX:MaxTenuringThreshold=2 -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:+ScavengeBeforeFullGC -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:CMSFullGCsBeforeCompaction=9 -XX:CMSInitiatingOccupancyFraction=60 -XX:+CMSClassUnloadingEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:-ReduceInitialCardMarks -XX:+CMSPermGenSweepingEnabled -XX:CMSInitiatingPermOccupancyFraction=70 -XX:+ExplicitGCInvokesConcurrent -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationConcurrentTime -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -Duser.timezone=Asia/Shanghai -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8"

function kill_tasks(){
	for pid in `ps -ef | grep "javabasi[c]" | awk '{print $2}'`
	do
		echo killing $pid
		kill -9 $pid
	done
}


for threads in "100"
do
	echo threads $threads
	kill_tasks
	/usr/java/latest/bin/java $JAVA_OPTS -Xloggc:heap_trace_${CORE}_${threads}_${stacks}.txt  -Dthreads=$threads -Dstacks=$stacks -cp javabasic-1.0-SNAPSHOT.jar:./jar/* javabasic.gc.YoungGc &
done
#sleep 3
#/usr/java/latest/bin/jstat -gc `ps -ef | grep javabasi[c] | awk '{print $2}'` 5000

