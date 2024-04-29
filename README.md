# Hexagonal Architecture PoC - Reservation-Inventory

[![hexagonal-architecture](https://alistair.cockburn.us/wp-content/uploads/2017/03/logo285x146-half.png)](https://alistair.cockburn.us/hexagonal-architecture/)

This is an example of a very simplified book inventory system made in an architecture based on ports and adapters using:

* _Java 17_
* _Maven 3.6.3_
* Spring Boot
* Spring Email
* Spring Data JPA
* Spring GraphQL
* Lombok
* RabbitMQ
* H2 Database

**Comprehensive guide describing the architecture:**

* [Hexagonal architecture](https://alistair.cockburn.us/hexagonal-architecture/)

Other articles around Hexagonal architecture that could be interesting:

* [O que é uma Arquitetura Hexagonal?](https://engsoftmoderna.info/artigos/arquitetura-hexagonal.html)
* [Arquitetura Ports and Adapters: Desmistificando a arquitetura hexagonal.](https://medium.com/@kenneth-de-oliveira/introdu%C3%A7%C3%A3o-ee89aa856f33)
* [O que é DDD? Um Guia de Bolso Para Desenvolvedores](https://medium.com/@kenneth-de-oliveira/o-que-%C3%A9-ddd-um-guia-de-bolso-para-desenvolvedores-86be4818fb28)
* [Design x Arquitetura de Software](https://medium.com/@kenneth-de-oliveira/design-x-arquitetura-de-software-383f2a5d6320)

## Business Case

This project is a “fictitious” digital book inventory project proposal developed as a form of training for Minsait "Ports & Adapters Architecture" training, whose main objective is to offer main services, such as: reserving books, consult reserved books and book cruds and students.

## Architecture overview

<p align="center">
    <img alt="NET Microservices Architecture" src="https://i.ibb.co/72YKnzx/Desenho.jpg" />
</p>

# Instructions

First, clone the repository at the address:

`https://github.com/kenneth-de-oliveira/ReservationInventory.git` 

After the project is cloned, open the terminal in the cloned directory **ReservationInventory**

```bash
cd ReservationInventory
mvn install
```

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
