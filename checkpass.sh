find . \( -name \*.java -or -name \*.properties \)  -exec egrep -A 1 "jdbc|password|getPassword" {} \;
