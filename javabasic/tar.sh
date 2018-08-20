dir=test-tar

if [ -f test.tar ];then
	rm test.tar
fi

mvn clean package -DskipTests > mvn.log
mv target/javabasic*.jar  $dir
cp scripts/*.sh $dir

tar -cvf test.tar $dir
