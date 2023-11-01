# anti-fraud-system

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/anti-fraud-system/blob/master/README.pt-br.md)

Este projeto foi desenvolvido para atuar como um sistema antifraude com o objetivo de detectar e evitar transações financeiras suspeitas. O foco do projeto é a utilização de Spring Security para autenticação e autorização e a demonstração de fundamentos de detecção de fraudes e de sistemas baseados em regras.

Transações fraudulentas podem ter um impacto significativo tanto em empresas como em indivíduos. As funcionalidades deste projeto visam mitigar tais riscos, monitorando atividades e transações suspeitas.

## Funcionalidades
- **Monitoramento de transações**: o sistema monitora, categoriza e persiste o estado e o resultado das transações.
- **Rastreamento de cartões roubados e IPs suspeitos**: qualquer IP ou cartão roubado suspeito é marcado pelo sistema.
- **Acesso baseado em _roles_**: o sistema trabalha com diferentes tipos de _roles_, cada um com diferentes níveis de acesso às funcionalidades e endpoints do sistema.
- **Mecanismo de _feedback_**: o sistema permite a revisão manual das transações, contribuindo para o aprimoramento dinâmico dos parâmetros do sistema.

## Tecnologias

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2
- Bean Validation
- Lombok
