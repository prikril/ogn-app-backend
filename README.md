# ogn-app-backend
Backend for OGN Viewer App

The backend gathers aircraft positions from OGN.

The backend offers a REST API to get flightpaths from an aircraft.


## Dependencies
The backend uses two repositories from glidernet:

https://github.com/glidernet/ogn-commons-java

and

https://github.com/glidernet/ogn-client-java

You need Eclipse and Maven to build the jar files.

Use "maven install" to build them.

## Building
Use IntelliJ to open the project and run "buildOnly".

You will find a new .jar file in /build/libs that can be run from command line.

## Running in background
At command line type
``nohup java -jar ogn-backend.jar &``