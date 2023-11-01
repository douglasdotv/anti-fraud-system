# anti-fraud-system

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.pt-br.md)

This project is designed to serve as an anti-fraud system, capable of detecting and preventing fraudulent financial transactions. It is focused on the use of Spring Security for authentication and authorization and the demonstration of the fundamentals of fraud detection and rule-based systems.

Fraudulent transactions can significantly impact businesses and individuals. The features in this project aim to mitigate such risks by monitoring suspicious activities and transactions.

## Features
- **Transaction monitoring**: the system keeps tabs on, categorizes and persists the state and result of transactions.
- **Stolen cards/suspicious IPs tracking**: any stolen cards or suspicious IP addresses are flagged.
- **Role-Based access**: the system works with different user roles, each with varying levels of access to the system's functionalities and endpoints.
- **Feedback mechanism**: the system allows for manual review of transactions, helping to dynamically improve and refine the system parameters.

## Technologies

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2
- Bean Validation
- Lombok
