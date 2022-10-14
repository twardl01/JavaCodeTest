# williamsleacodetest

This is a project that I undertook as a technical excercise for Williams Lea.

The specification is as follows:

"Write a java & spring boot application which has one endpoint “upload”.
The endpoint should take a .txt file as input (see attached text file for format).
The text file should be split into records and stored in a database. 
The record should contain:
•       Company Name (taken directly from the text file, i.e. ASH RAIL LTD)
•       Company Number (“”, i.e. 11467106)
•       EventType (The code should be mapped to the index at the beginning of the file. I.e. C2 should be mapped to “NOTIFICATION OF ANY CHANGE AMONG THE COMPANY'S DIRECTORS.”)
•       EventDate (directly from the code). 
•       UniqueIdentifier (some way to uniquely identify the record).
 
Note: 
The same company name can straddle multiple lines. 
Assume in practice this text file will be very large.
Write this code as though it needs to be production ready."

Application was tested through Postman & monitoring the H2 console.
Unit tests were used to test individual components.
