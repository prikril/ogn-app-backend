# ogn-app-backend
Backend for OGN Viewer App

The backend gathers aircraft positions from OGN.

The backend offers a REST API to get the flight path for an aircraft.

Current version: 0.4.0


## Dependencies
The backend uses two repositories:

https://github.com/prikril/ogn-commons-java

and

https://github.com/prikril/ogn-client-java

You need Eclipse and Maven to build the jar files.

Use "maven install" to build them.

## Building
Use IntelliJ to open the project and run "buildOnly".

You will find a new .jar file in /build/libs that can be run from command line.

## Running in background (on unix systems)
At command line type:

``nohup java -jar ogn-app-backend.jar &``

If you want you can save logging output to file with:

``nohup java -jar ogn-app-backend.jar --logging.file.name=logfile.log > /dev/null 2>&1 &``

