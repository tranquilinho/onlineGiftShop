onlineGiftShop - template for a distributed online shop, using Java stack (JSP,  JavaBeans, Java web services) under Apache Tomcat. Data stored in MySql.

Author: Jesus Cuenca (jesus.cuenca@gmail.com)

Deployment
---------

- Customize database-related variables:
  - db-host in build.xml
  - dbURL in local/WEB-INF/web.xml (URL of the local database)
  - dbURL in central/WEB-INF/web.xml (URL of the central database)
  - dbUrl in VisaImpl.java and ArticuloImpl.java (URL of the databases for those 2 services)

- On every server, run "ant todo" from the code root
- In the central server, set up the URLs of the other sites

You can change the deployment URL in the "manager" property of the build.xml files

See build.xml help for deployment details

NOTES

javac must be available (set up $PATH appropriately). Setup CLASSPATH environment variable so it includes the location of Tomcat's servlet-api.jar and catalina-ant.jar ($TOMCAT_HOME/common/lib/servlet-api.jar ,$TOMCAT_HOME/server/lib).

The application requires a JDBC library to access the database (for example, mysql-connector-java, at http://dev.mysql.com/downloads/connector/j). Copy the jar file to the lib directory.

Test
----

Go to...
	
// Central server

http://localhost:8080/servidor_central/

// Local sites

http://localhost:8080/servidor_local/

Default users:

Login: empleado. Clave: empleado.
Login: cliente. Clave: cliente.	

Structure
---------

- central: Central server code
- local: Local sites code
- VISA: simulated bank payment web service code
- BD: sql files to create and populate the databases (use ant)