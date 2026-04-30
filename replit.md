# Frases Motivacionais API

API REST em Java + Spring Boot para gerenciar frases motivacionais.

## Stack

- Java 17 (rodando sobre GraalVM 19/22.3.1)
- Spring Boot 3.2.5 (Web, Data JPA, Validation)
- Banco H2 em memória
- Maven 3.8+

## Como rodar

A aplicação é iniciada pelo workflow `Start application` com o comando:

```
mvn -q spring-boot:run
```

Sobe na porta **5000** (configurada em `src/main/resources/application.properties`).

## Endpoints principais

- `GET /frases` — lista todas (suporta `?categoria=`)
- `GET /frases/random` — frase aleatória
- `GET /frases/{id}` — busca por ID
- `POST /frases` — cria nova frase
- `POST /frases/{id}/like` — curte
- `DELETE /frases/{id}` — remove
- `GET /h2-console` — console web do H2

## Arquitetura

Camadas separadas em pacotes dentro de `com.nindo.frases`:

- `controller/` — endpoints REST
- `service/` — regras de negócio
- `repository/` — JPA repositories
- `model/` — entidades
- `config/DataLoader` — popula 10 frases no startup
- `exception/` — `FraseNotFoundException` + `GlobalExceptionHandler`

## Notas

- O usuário pediu que o sistema **não dependa do `.replit`**: o projeto roda
  com `mvn spring-boot:run` em qualquer ambiente Java 17+/Maven 3.8+.
- O usuário pediu que o `LICENSE` fosse mantido intacto.
- Sem comentários no código-fonte (a pedido do usuário).
- A porta padrão do Spring Boot (8080) foi alterada para 5000 em
  `application.properties` para compatibilidade com o ambiente de preview.
