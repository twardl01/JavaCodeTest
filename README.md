# JavaCodeTest

This is a project that I undertook as a technical excercise.

IDE Used: Visual Studio Code

The specification for this program was:

Write a java & spring boot application which has one endpoint “upload”. 
The endpoint should take a .txt file as input (see SampleDataFile.txt for format). 
The text file should be split into records and stored in a database. 
The record should contain: 
- Company Name (taken directly from the text file, i.e. ASH RAIL LTD)
- Company Number (“”, i.e. 11467106)
- EventType (The code should be mapped to the index at the beginning of the file. I.e. C2 should be mapped to “NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.”)
- EventDate (directly from the code). 
- UniqueIdentifier (some way to uniquely identify the record).
 
Note: 
The same company name can straddle multiple lines. 
Assume in practice this text file will be very large. 
Write this code as though it needs to be production ready. 

Application was tested through Postman & monitoring the H2 database through the console 
 
Unit tests were used to test individual components.

TO RUN:
- Install Maven & Spring Boot, along with their dependencies
- Install H2 Database Engine (https://www.h2database.com/html/main.html)
- Run JavaCodeTestApplication.java in src/main/java/com/tomaswardle/williamsleacodetest/ (ensure localhost:8080 is not in use)
 - If port 8080 is in use, add a server.port definition in application.properties
- POST a txt file (the sample, or another file with the same format of the file) to http://localhost:8080/upload/, specifying the file with the file parameter.
- Log in to http://localhost:8080/h2-console/ with username "sa" and no password to access the database
- Select the "RECORD" table and use the query "SELECT * FROM RECORD" to see all of the records.
