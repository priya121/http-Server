#HTTP Server

1. Clone the repo : `git@github.com:priya121/http-Server.git`
2. Build the project using the command : `mvn package`
3. Navigate to the _target_ folder and start up the server with : 

`java -cp target/http-server-1.0-SNAPSHOT.jar main.MainApp -p port -d directory`


* -p /port. Enter a chosen port number
* -d  /directory.  Enter a path to the public folder where your public cob spec folder lives

The default port is `5000`

The default directory is the `public` folder within the project



Run the tests using the command: `mvn test`

