echo "Compilando mais coisa."
echo @javac -cp C:\Users\Gabriel\Downloads\GWT\gwt-user.jar;C:\Users\Gabriel\workspace\WapFramework\jars\commons-fileupload-1.2.1.jar -d ../www/WEB-INF/classes nunes/rabello/server/servlets/*.java nunes/rabello/server/mysql/*.java
copy C:\Users\Gabriel\workspace\Revistas\src\nunes\rabello\web.xml C:\Users\Gabriel\workspace\Revistas\www\WEB-INF
xcopy C:\Users\Gabriel\workspace\Revistas\bin\nunes\rabello\server C:\Users\Gabriel\workspace\Revistas\www\WEB-INF\classes\nunes\rabello\server /E/Y
xcopy C:\Users\Gabriel\workspace\Revistas\bin\nunes\rabello\client C:\Users\Gabriel\workspace\Revistas\www\WEB-INF\classes\nunes\rabello\client /E/Y
mkdir C:\Users\Gabriel\workspace\Revistas\www\nunes.rabello.Revistas\WEB-INF
xcopy C:\Users\Gabriel\workspace\Revistas\www\WEB-INF C:\Users\Gabriel\workspace\Revistas\www\nunes.rabello.Revistas\WEB-INF /E/Y
xcopy C:\Users\Gabriel\workspace\Revistas\www\nunes.rabello.Revistas "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\nunes.rabello.Revistas" /E/Y
xcopy C:\Users\Gabriel\workspace\Revistas\src\nunes\rabello\public\images\capas "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\nunes.rabello.Revistas\images\capas" /E/Y

cd "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\bin"
tomcat5w.exe
cd "C:\Users\Gabriel\workspace\Revistas"