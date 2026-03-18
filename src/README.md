# 🏦 API para Gestão de Pagamentos

<div align="center">

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

**API REST completa para gerenciamento de contas bancarias e transacoes financeiras**

[Funcionalidades](#-funcionalidades) •
[Tecnologias](#-tecnologias) •
[Arquitetura](#-arquitetura) •
[Como Rodar](#-como-rodar) •
[Endpoints](#-endpoints) •
[Documentacao](#-documentacao-swagger)

</div>

---

## 📋 Sobre o Projeto

O **Fintech Payments** e uma plataforma de pagamentos digitais desenvolvida com **Java 17** e **Spring Boot 3**, implementando operacoes financeiras como PIX, TED e DOC entre contas bancarias. O projeto aplica boas praticas de desenvolvimento como arquitetura em camadas, autenticacao stateless com JWT, validacao de dados e documentacao interativa com Swagger.

Desenvolvido como projeto de portfolio para demonstrar dominio em desenvolvimento backend enterprise com Java moderno.

---

## Funcionalidades

- Autenticacao segura com JWT (registro e login de usuarios)
- Gerenciamento de contas bancarias com numero gerado automaticamente
- Transferencias financeiras — PIX, TED, DOC, Deposito e Saque
- Controle de saldo em tempo real com debito e credito automatico
- Bloqueio e encerramento de contas
- Extrato de transacoes por conta
- Validacao de dados com Bean Validation
- Tratamento global de excecoes com respostas padronizadas
- Documentacao interativa via Swagger UI

---

## Tecnologias

| Camada | Tecnologia | Versao |
|---|---|---|
| Linguagem | Java | 17 |
| Framework | Spring Boot | 3.5 |
| Seguranca | Spring Security + JWT (jjwt) | 0.11.5 |
| Banco de Dados | PostgreSQL | 16 |
| ORM | Spring Data JPA + Hibernate | - |
| Documentacao | SpringDoc OpenAPI (Swagger) | 2.3.0 |
| Gerenciador de Build | Maven | 3.9 |
| Validacao | Jakarta Bean Validation | - |
| Utilitarios | Lombok | - |

---

## Arquitetura

O projeto segue o padrao de **arquitetura em camadas** (Layered Architecture):

```
src/main/java/com/fintech/fintech_payments/
│
├── controller/          ← Endpoints REST (recebe requisicoes HTTP)
│   ├── AuthController      ← /api/auth/**
│   ├── ContaController     ← /api/contas/**
│   └── TransacaoController ← /api/transacoes/**
│
├── service/             ← Regras de negocio
│   ├── UsuarioService
│   ├── ContaService
│   └── TransacaoService
│
├── repository/          ← Acesso ao banco de dados (Spring Data JPA)
│   ├── UsuarioRepository
│   ├── ContaRepository
│   └── TransacaoRepository
│
├── model/               ← Entidades JPA (tabelas do banco)
│   ├── Usuario
│   ├── Conta
│   └── Transacao
│
├── dto/                 ← Objetos de transferencia de dados
│   ├── ContaDTO
│   └── TransacaoDTO
│
├── security/            ← Autenticacao JWT
│   ├── JwtService
│   └── JwtFilter
│
├── config/              ← Configuracoes gerais
│   ├── SecurityConfig
│   └── SwaggerConfig
│
└── exception/           ← Tratamento de erros
    ├── BusinessException
    └── GlobalExceptionHandler
```

---

## Como Rodar

### Pre-requisitos

- [Java 17+](https://adoptium.net)
- [Maven 3.9+](https://maven.apache.org/download.cgi)
- [PostgreSQL 16](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/)

### 1 — Clonar o repositorio

```bash
git clone https://github.com/DykstraBruno/Plataforma-de-pagamentos-digitais.git
cd Plataforma-de-pagamentos-digitais
```

### 2 — Criar o banco de dados

```sql
CREATE DATABASE fintech;
```

### 3 — Configurar variaveis de ambiente

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fintech
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
jwt.secret=sua-chave-secreta-aqui
```

### 4 — Rodar a aplicacao

```bash
./mvnw spring-boot:run
```

A aplicacao estara disponivel em: `http://localhost:8080`

---

## Endpoints

### Autenticacao

| Metodo | Endpoint | Descricao | Auth |
|---|---|---|---|
| `POST` | `/api/auth/registrar` | Registrar novo usuario | Nao |
| `POST` | `/api/auth/login` | Login e obter token JWT | Nao |

**Exemplo — Registrar:**
```json
POST /api/auth/registrar
{
  "nome": "Joao Silva",
  "email": "joao@email.com",
  "senha": "123456"
}
```

**Exemplo — Login:**
```json
POST /api/auth/login
{
  "email": "joao@email.com",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "nome": "Joao Silva",
  "email": "joao@email.com"
}
```

---

### Contas

Todas as rotas abaixo requerem o header: `Authorization: Bearer {token}`

| Metodo | Endpoint | Descricao |
|---|---|---|
| `POST` | `/api/contas` | Criar nova conta bancaria |
| `GET` | `/api/contas` | Listar todas as contas |
| `GET` | `/api/contas/{numeroConta}` | Buscar conta pelo numero |
| `PATCH` | `/api/contas/{numeroConta}/bloquear` | Bloquear conta |
| `PATCH` | `/api/contas/{numeroConta}/encerrar` | Encerrar conta |

**Exemplo — Criar conta:**
```json
POST /api/contas
{
  "titular": "Joao Silva",
  "cpf": "12345678901",
  "email": "joao@email.com",
  "saldoInicial": 1000.00
}
```

**Resposta:**
```json
{
  "id": 1,
  "numeroConta": "75701286-8",
  "titular": "Joao Silva",
  "cpf": "12345678901",
  "saldo": 1000.00,
  "status": "ATIVA",
  "criadoEm": "2026-03-12T13:04:26"
}
```

---

### Transacoes

| Metodo | Endpoint | Descricao |
|---|---|---|
| `POST` | `/api/transacoes` | Realizar transacao (PIX/TED/DOC) |
| `GET` | `/api/transacoes` | Listar todas as transacoes |
| `GET` | `/api/transacoes/extrato/{contaId}` | Extrato da conta |

**Tipos de transacao disponíveis:** `PIX` `TED` `DOC` `BOLETO` `DEPOSITO` `SAQUE`

**Exemplo — PIX:**
```json
POST /api/transacoes
{
  "numeroContaOrigem": "75701286-8",
  "numeroContaDestino": "82081624-0",
  "valor": 200.00,
  "tipo": "PIX",
  "descricao": "Pagamento teste"
}
```

**Resposta:**
```json
{
  "id": 1,
  "codigoTransacao": "307a0e76-879a-41e9-8a52-26c92f797af9",
  "valor": 200.00,
  "tipo": "PIX",
  "status": "CONCLUIDA",
  "processadoEm": "2026-03-12T14:33:08"
}
```

---

## Documentacao Swagger

Com a aplicacao rodando, acesse a documentacao interativa:

```
http://localhost:8080/swagger-ui/index.html
```

Na interface do Swagger voce pode:
- Visualizar todos os endpoints documentados
- Autenticar com o token JWT
- Testar as requisicoes diretamente pelo navegador

---

## Modelo de Dados

```
usuarios          contas              transacoes
─────────         ──────────          ──────────────
id                id                  id
nome              numeroConta         codigoTransacao
email             titular             contaOrigem (FK)
senha             cpf                 contaDestino (FK)
perfil            email               valor
ativo             saldo               tipo
criadoEm         status              status
                  criadoEm           descricao
                                      processadoEm
```

---

## Proximas Funcionalidades

- [ ] Relatorios financeiros em PDF
- [ ] Agendamento de transferencias
- [ ] Notificacoes por e-mail
- [ ] Integracao com Kafka para processamento assincrono
- [ ] Deploy com Docker e Kubernetes
- [ ] Testes automatizados com JUnit 5 e Mockito
- [ ] Deteccao de fraudes

---

## Autor

**Bruno Dykstra**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/seulinkedin)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/DykstraBruno)

---

## Licenca

Este projeto esta sob a licenca MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">
  Desenvolvido com cafe e muito Java
</div>
