SET CP=..\JADE-all-3_1_.5\JADE-bin-3.5\jade\lib\Base64.jar;..\JADE-all-3_1_.5\JADE-bin-3.5\jade\lib\http.jar;..\JADE-all-3_1_.5\JADE-bin-3.5\jade\lib\iiop.jar;..\JADE-all-3_1_.5\JADE-bin-3.5\jade\lib\jade.jar;..\JADE-bin-3.5\jade\lib\jadeTools.jar
javac -d bin -cp %CP%  @sourcefiles.TXT
SET CP=%CP%;bin\;
java -cp %CP% jade.Boot session:com.u1s1f1.collabsys.session.SessionAgent floor:com.u1s1f1.collabsys.floor.FloorAgent user:com.u1s1f1.collabsys.user.UserAgent