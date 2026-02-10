# Ktor Sample - Sistema de Biblioteca

Sistema completo de gerenciamento de biblioteca desenvolvido com Ktor, incluindo API REST documentada e interface web.

## 🚀 Tecnologias

- **Ktor 3.4.0** - Framework web assíncrono para Kotlin
- **Koin 3.5.6** - Injeção de dependências leve (IoC)
- **Exposed 0.57.0** - ORM para Kotlin
- **H2 Database** - Banco de dados em memória com migrations automáticas
- **Kotlinx Serialization** - Serialização JSON
- **Swagger/OpenAPI** - Documentação interativa da API
- **Kotlin HTML DSL + Tailwind CSS** - Interface web responsiva

## 📁 Estrutura do Projeto

```
src/main/kotlin/com/library/
├── config/
│   ├── DatabaseFactory.kt   # Configuração do banco de dados
│   └── KoinModule.kt        # Módulo de injeção de dependências
├── dto/
│   ├── AutorDto.kt          # DTOs de Autor (Request/Response)
│   └── LivroDto.kt          # DTOs de Livro (Request/Response)
├── model/
│   └── Models.kt            # Entidades Exposed (Autores e Livros)
├── repository/
│   ├── AutorRepository.kt   # Acesso a dados de Autores
│   └── LivroRepository.kt   # Acesso a dados de Livros
├── routes/
│   ├── AutorRoutes.kt       # Endpoints REST de Autores
│   ├── LivroRoutes.kt       # Endpoints REST de Livros
│   └── WebRoutes.kt         # Rotas da interface web
├── service/
│   ├── AutorService.kt      # Lógica de negócio de Autores
│   └── LivroService.kt      # Lógica de negócio de Livros
├── plugins/
│   ├── Koin.kt              # Plugin Koin
│   ├── Serialization.kt     # Plugin de serialização
│   ├── StatusPages.kt       # Tratamento de erros
│   └── Swagger.kt           # Configuração Swagger
├── Application.kt           # Ponto de entrada
└── Routing.kt              # Configuração de rotas
```

## 🏃 Como Executar

### Compilar o projeto
```bash
./gradlew clean build
```

### Executar a aplicação
```bash
./gradlew run
# ou
java -jar build/libs/ktor-sample-all.jar
```

A aplicação estará disponível em: **http://localhost:8080**

## 🌐 Interfaces Disponíveis

### Interface Web
- **Home**: http://localhost:8080/
- **Gerenciar Autores**: http://localhost:8080/autores
- **Gerenciar Livros**: http://localhost:8080/livros

### Documentação da API
- **Swagger UI**: http://localhost:8080/swagger
- **OpenAPI Spec**: http://localhost:8080/openapi

## 📚 API REST

### Autores

#### Listar todos os autores
```bash
GET /api/autores
```

#### Buscar autor por ID
```bash
GET /api/autores/{id}
```

#### Criar autor
```bash
POST /api/autores
Content-Type: application/json

{
  "nome": "Machado de Assis",
  "nacionalidade": "Brasileiro"
}
```

#### Atualizar autor
```bash
PUT /api/autores/{id}
Content-Type: application/json

{
  "nome": "Machado de Assis",
  "nacionalidade": "Brasileiro"
}
```

#### Excluir autor
```bash
DELETE /api/autores/{id}
```

### Livros

#### Listar todos os livros
```bash
GET /api/livros
```

#### Buscar livro por ID
```bash
GET /api/livros/{id}
```

#### Criar livro
```bash
POST /api/livros
Content-Type: application/json

{
  "titulo": "Dom Casmurro",
  "isbn": "978-8535908770",
  "anoPublicacao": 1899,
  "autorId": 1
}
```

#### Atualizar livro
```bash
PUT /api/livros/{id}
Content-Type: application/json

{
  "titulo": "Dom Casmurro",
  "isbn": "978-8535908770",
  "anoPublicacao": 1899,
  "autorId": 1
}
```

#### Excluir livro
```bash
DELETE /api/livros/{id}
```

## 🧪 Exemplos de Uso

### Criar um autor
```bash
curl -X POST http://localhost:8080/api/autores \
  -H "Content-Type: application/json" \
  -d '{"nome": "Machado de Assis", "nacionalidade": "Brasileiro"}'
```

### Criar um livro
```bash
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Dom Casmurro",
    "isbn": "978-8535908770",
    "anoPublicacao": 1899,
    "autorId": 1
  }'
```

### Listar livros
```bash
curl http://localhost:8080/api/livros
```

## 🔧 Banco de Dados

O projeto utiliza H2 Database em memória com as seguintes características:

- **Migrations automáticas** via Exposed
- **Modo PostgreSQL** para compatibilidade
- Dados são resetados a cada reinicialização (desenvolvimento)

### Tabelas

**Autores**
- id (BIGINT, PK)
- nome (VARCHAR)
- nacionalidade (VARCHAR, nullable)

**Livros**
- id (BIGINT, PK)
- titulo (VARCHAR)
- isbn (VARCHAR, unique)
- ano_publicacao (INT)
- autor_id (BIGINT, FK -> Autores)

## 🎨 Interface Web

A interface web foi construída com:
- **Kotlin HTML DSL** - Geração de HTML type-safe
- **Tailwind CSS** - Framework CSS utilitário
- **Vanilla JavaScript** - Interação com a API REST

Recursos da interface:
- ✅ Listagem de autores e livros
- ✅ Criação de novos registros
- ✅ Exclusão de registros
- ✅ Design responsivo
- ✅ Feedback visual de operações

## 🔌 Injeção de Dependências (Koin)

O projeto usa Koin como container de IoC, uma alternativa leve ao Spring:

```kotlin
val appModule = module {
    // Repositories
    single { AutorRepository() }
    single { LivroRepository() }
    
    // Services
    single { AutorService(get()) }
    single { LivroService(get()) }
}
```

## 📝 DTOs

O projeto segue o padrão de DTOs separados para Request e Response:

- **AutorRequest** - Dados para criar/atualizar autor
- **AutorResponse** - Dados retornados pela API
- **LivroRequest** - Dados para criar/atualizar livro
- **LivroResponse** - Dados retornados pela API (inclui AutorResponse)

## 🚧 Próximas Melhorias

- [ ] Adicionar autenticação/autorização
- [ ] Implementar paginação
- [ ] Adicionar testes unitários e de integração
- [ ] Migrar para banco de dados persistente (PostgreSQL/MySQL)
- [ ] Adicionar validações mais robustas
- [ ] Implementar funcionalidade de edição na interface web
- [ ] Adicionar busca e filtros

## 📄 Licença

Este projeto é um exemplo educacional.
# ktor-library-poc
