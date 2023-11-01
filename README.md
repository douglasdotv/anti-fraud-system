# Anti Fraud System

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.pt-br.md)

## Introduction

This project is designed to serve as an anti-fraud system capable of detecting and preventing fraudulent financial transactions. It demonstrates Spring Security usage for authentication and authorization and introduces the fundamentals of rule-based systems and fraud detection.

## Features

- **Transaction monitoring**: the service is able to categorize and persist the state and result of transactions.
- **Stolen cards/suspicious IPs tracking**: any stolen cards or suspicious IP addresses are flagged by the system.
- **Role-Based access**: works with different user roles with varying levels of access to the system's functionalities and endpoints.
- **Feedback mechanism**: the system allows for manual review of transactions, dynamically improving and refining the system parameters.

## Tools Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2
- Bean Validation
- Lombok
