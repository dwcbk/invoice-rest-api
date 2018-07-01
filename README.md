# Invoice REST Endpoints
A simple application for searching and adding Invoices to a MySQL database. Templates are written in FreeMarker.

## Requirements
- Java 8
- Maven 3.x
- MySQL 8.0.11 (with user, db, and 'invoices' table pre-configured)

## Building and running
Open project in IntelliJ (or your IDE of choice) and run class [Application](src/main/java/com/dwcbk/Application)

## Accessing REST endpoints via command line tool [HTTPie](https://httpie.org/) :
`http POST "http://localhost:8080/v1/invoice" invoice_number=ABC12345 po_number=X1B23C4D5E due_date=2017-03-15 amount_cents=100000`  
`http GET "http://localhost:8080/v1/invoice?invoice_number=I1001"`  
`http GET "http://localhost:8080/v1/invoice?invoice_number=P1002&limit=2&offset=2"`  

## Accessing REST endpoints via web:
- Homepage: [http://localhost:8080/](http://localhost:8080/)  
- Search example 1: [http://localhost:8080/v1/invoice?invoice_number=I1001](http://localhost:8080/v1/invoice?invoice_number=I1001)  
- Search example 2: [http://localhost:8080/v1/invoice?invoice_number=P1002&limit=2&offset=2](http://localhost:8080/v1/invoice?invoice_number=P1002&limit=2&offset=2)  

