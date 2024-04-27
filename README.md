**Reservation-Inventory**
========================================================================
This project is a “fictitious” digital book inventory project proposal developed as a form of training for Minsait "Ports & Adapters Architecture" training, whose main objective is to offer main services, such as: reserving books, consult reserved books and book cruds and students.

**Some of the resources used**

- Ports & Adapters Architecture
- Clean Architecture
- Spring Boot Email
- Clean Code
- Spring Boot
- RabbitMQ
- Java 17
- Maven 3.6.3
- RestFull
- H2 Database
- JPA - Hibernate
- Spring Data JPA

# **Instructions**

	First, clone the repository at the address:
	
	https://github.com/kenneth-de-oliveira/ReservationInventory.git
	
	After the project is cloned, open the terminal in the cloned directory **ReservationInventory** 

And use the following commands:

	cd ReservationInventory
	mvn install

**It is very important to wait for the execution of the above mentioned commands.**

Project configuration
========================================================================
- Java 17
- Maven 3.6.3

Example of execution
========================================================================

Create a new book **POST**: localhost:8080/api/v1/books
```json
{
  "title": "Clean Code",
  "authorName": "Uncle Bob",
  "text": "bla bla bla",
  "isbn": "9783127323207",
  "category": {
    "name": "Informática",
    "description": "bla, bla, bla"
  }
}
```

Create a new student **POST**: localhost:8080/api/v1/students
```json
{
  "document": "51436427606",
  "name": "Kenneth de Oliveira Soares",
  "email": "kennetholiveira2015@gmail.com",
  "address": {
    "postalCode": "58051020"
  }
}
```

Process reservation **POST**:  ``` localhost:8080/api/v1/reservation-inventory?isbn=9783127323207&document=51436427606```

Find reservation **GET**:  ``` localhost:8080/api/v1/reservation-inventory/reservations?document=51436427606```

Find book by isbn **GET**: ```localhost:8080/api/v1/books/9783127323207```
