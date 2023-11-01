# Anti Fraud System

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.pt-br.md)

## Introdução

Este projeto serve como um sistema capaz de detectar e evitar transações financeiras suspeitas, combatendo fraudes. Ele demonstra a utilização de Spring Security para autenticação e autorização e introduz fundamentos de detecção de fraudes e rule-based systems.

## Funcionalidades

- **Monitoramento de transações**: o sistema monitora, categoriza e persiste o estado e o resultado das transações.
- **Rastreamento de cartões roubados e IPs suspeitos**: qualquer IP ou cartão roubado suspeito é marcado pelo sistema.
- **Acesso baseado em _roles_**: o sistema trabalha com diferentes tipos de _roles_, cada um com diferentes níveis de acesso às funcionalidades e endpoints do sistema.
- **Mecanismo de _feedback_**: o sistema permite a revisão manual das transações, contribuindo para o aprimoramento dinâmico dos parâmetros do sistema.

## Ferramentas Utilizadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2
- Bean Validation
- Lombok
