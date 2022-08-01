# Finance Application
## Description
Application is designed to manage personal finances. It is based on microservice architecture and collect using docker compose file.

## Features
- Creating an account with types ```CASH```, ```BANK_ACCOUNT``` and ```BANK_DEPOSIT```.
- Adding operations with custom categories and real currencies for account.
- Scheduled operations for account.
- Report system with diagramms and document in Excel format by ```balance```, ```date``` and ```categories```.
- Scheduled reports.
- Email notifications about creating new report and for verify new user.
- Cloud storage for media files.

## Tech
Finance application uses next technologies:
- Java 11
- Spring Boot
- Hibernate
- PostgreSQL
- Maven
- Docker
- Cloudinary
- Apache POI
- JFreeChart
- Open API

## List of Microservices
The project has next services:
- User service
- Classifier service
- Account service
- Account scheduler service
- Report service
- Mail service
- Mail scheduler service
