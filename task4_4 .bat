javac -encoding "utf-8" -sourcepath src -d classes src/part_four/task_one/*.java
jar -cfm task4_4.jar manifest.txt -C classes/ part_four/task_one/ database.properties selectdialog.properties
java -cp "task4_4.jar;lib/mysql-connector-java-8.0.21.jar" part_four.task_one.Task01
pause