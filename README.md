# Frases Motivacionais API

> API REST em **Java + Spring Boot** para gerenciar uma coleção de frases motivacionais com sistema de likes, filtro por categoria e banco de dados em memória.

[![Java](https://img.shields.io/badge/Java-17+-007396?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![H2](https://img.shields.io/badge/H2%20Database-in--memory-1F4B7E)](https://h2database.com/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Modelo de Dados](#-modelo-de-dados)
- [Endpoints da API](#-endpoints-da-api)
- [Como Executar](#-como-executar)
- [Como Testar (Postman / curl)](#-como-testar-postman--curl)
- [Console do H2](#-console-do-h2)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Estrutura de Pastas](#-estrutura-de-pastas)

---

## Sobre o Projeto

Esta é uma API REST simples, organizada em camadas (Controller → Service → Repository → Model), criada para servir e gerenciar frases motivacionais. A API permite:

- Listar todas as frases
- Obter uma frase aleatória
- Filtrar frases por categoria
- Cadastrar novas frases
- Curtir uma frase (sistema de likes)
- Remover uma frase pelo ID

O projeto utiliza um banco H2 em memória, que é populado automaticamente com 10 frases na inicialização.

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 (compatível com 17+) |
| Spring Boot | 3.2.5 |
| Spring Web | starter |
| Spring Data JPA | starter |
| Spring Validation | starter |
| H2 Database | runtime |
| Maven | 3.8+ |

---

## Arquitetura

O projeto segue o padrão clássico em camadas do Spring:

```
┌─────────────────────────────────────────┐
│          Controller (REST)              │  → recebe requisições HTTP
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│              Service                    │  → regras de negócio
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│            Repository (JPA)             │  → acesso a dados
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│          Model / Entity (H2)            │  → tabela "frases"
└─────────────────────────────────────────┘
```

Camadas adicionais:

- **`config/DataLoader`** — carrega frases iniciais no startup
- **`exception/GlobalExceptionHandler`** — captura e formata erros (404, 400, 500)

---

## Modelo de Dados

### `Frase`

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `Long` | Identificador único (auto-gerado) |
| `texto` | `String` | Conteúdo da frase (obrigatório) |
| `autor` | `String` | Autor da frase (obrigatório) |
| `categoria` | `String` | Categoria/tema (obrigatório) |
| `likes` | `int` | Contador de curtidas (inicia em 0) |

---

## Endpoints da API

Base URL: `http://localhost:5000`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/frases` | Lista todas as frases |
| `GET` | `/frases?categoria={nome}` | Filtra frases por categoria |
| `GET` | `/frases/random` | Retorna uma frase aleatória |
| `GET` | `/frases/{id}` | Busca uma frase pelo ID |
| `POST` | `/frases` | Cadastra uma nova frase |
| `POST` | `/frases/{id}/like` | Curte uma frase (incrementa `likes`) |
| `DELETE` | `/frases/{id}` | Remove uma frase pelo ID |

### Exemplo de payload (`POST /frases`)

```json
{
  "texto": "Faça o que ama e nunca trabalhará um dia na vida.",
  "autor": "Confúcio",
  "categoria": "trabalho"
}
```

### Exemplo de resposta

```json
{
  "id": 11,
  "texto": "Faça o que ama e nunca trabalhará um dia na vida.",
  "autor": "Confúcio",
  "categoria": "trabalho",
  "likes": 0
}
```

---

## Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior

### Passos

```bash
# 1. Clone o repositório
git clone <url-do-repo>
cd frases-api

# 2. Compile o projeto
mvn clean package

# 3. Execute a aplicação
mvn spring-boot:run
```

A API estará disponível em `http://localhost:5000`.

> **Observação sobre porta:** O Spring Boot por padrão sobe na porta `8080`.
> Este projeto usa a porta `5000` (definida em `application.properties`) para
> compatibilidade com o ambiente de execução. Se quiser usar `8080`, basta
> alterar a propriedade `server.port` em `src/main/resources/application.properties`.

---

## Como Testar (Postman / curl)

### Usando Postman

1. **Abra o Postman** e crie uma nova *Collection* chamada `Frases API`.
2. Adicione as requisições abaixo:

#### 📋 Listar todas as frases
- **Método:** `GET`
- **URL:** `http://localhost:5000/frases`
- Clique em **Send** e veja a lista completa.

#### 🔀 Frase aleatória
- **Método:** `GET`
- **URL:** `http://localhost:5000/frases/random`

#### 🏷️ Filtrar por categoria
- **Método:** `GET`
- **URL:** `http://localhost:5000/frases?categoria=motivacao`

#### ➕ Criar nova frase
- **Método:** `POST`
- **URL:** `http://localhost:5000/frases`
- **Headers:** `Content-Type: application/json`
- **Body** (raw → JSON):
  ```json
  {
    "texto": "A jornada de mil milhas começa com um único passo.",
    "autor": "Lao Tsé",
    "categoria": "sabedoria"
  }
  ```

#### ❤️ Curtir uma frase
- **Método:** `POST`
- **URL:** `http://localhost:5000/frases/1/like`

#### 🗑️ Deletar uma frase
- **Método:** `DELETE`
- **URL:** `http://localhost:5000/frases/1`
- Resposta esperada: `204 No Content`

### Usando curl

```bash
# Listar todas
curl http://localhost:5000/frases

# Aleatória
curl http://localhost:5000/frases/random

# Filtrar por categoria
curl "http://localhost:5000/frases?categoria=motivacao"

# Criar nova frase
curl -X POST http://localhost:5000/frases \
  -H "Content-Type: application/json" \
  -d '{"texto":"Teste","autor":"Eu","categoria":"teste"}'

# Curtir
curl -X POST http://localhost:5000/frases/1/like

# Deletar
curl -X DELETE http://localhost:5000/frases/1
```

---

## Console do H2

O console web do H2 está habilitado para inspecionar o banco em memória:

- **URL:** `http://localhost:5000/h2-console`
- **JDBC URL:** `jdbc:h2:mem:frasesdb`
- **User:** `sa`
- **Password:** *(vazio)*

---

## Tratamento de Erros

A API retorna respostas padronizadas em caso de erro:

### `404 Not Found` — frase inexistente
```json
{
  "timestamp": "2026-04-30T00:00:00",
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Frase com id 9999 nao encontrada."
}
```

### `400 Bad Request` — payload inválido
```json
{
  "timestamp": "2026-04-30T00:00:00",
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "texto: must not be blank; autor: must not be blank"
}
```

---

## Estrutura de Pastas

```
frases-api/
├── pom.xml
├── README.md
├── LICENSE
└── src/
    └── main/
        ├── java/
        │   └── com/nindo/frases/
        │       ├── FrasesApplication.java          ← classe principal
        │       ├── controller/
        │       │   └── FraseController.java        ← endpoints REST
        │       ├── service/
        │       │   └── FraseService.java           ← regras de negócio
        │       ├── repository/
        │       │   └── FraseRepository.java        ← acesso a dados (JPA)
        │       ├── model/
        │       │   └── Frase.java                  ← entidade JPA
        │       ├── config/
        │       │   └── DataLoader.java             ← seed inicial
        │       └── exception/
        │           ├── FraseNotFoundException.java
        │           └── GlobalExceptionHandler.java
        └── resources/
            └── application.properties              ← configurações
```

---

## Licença

Distribuído sob a licença MIT. Veja [`LICENSE`](LICENSE) para mais informações.
