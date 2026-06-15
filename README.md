# Livraria Doacoes API

API REST simples para cadastro de usuarios, categorias, livros e doacoes.

## Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven

## Endpoints

- `GET/POST/PUT/DELETE /usuarios`
- `GET/POST/PUT/DELETE /categorias`
- `GET/POST/PUT/DELETE /livros`
- `GET /livros/buscar?titulo=`
- `GET /livros/status/{status}`
- `GET/POST/PUT/DELETE /doacoes`

## Swagger / OpenAPI

Depois de subir a aplicação, a documentação interativa fica disponível em:

- `/swagger-ui/index.html`
- `/v3/api-docs`

No Render, basta acessar a URL pública da API e adicionar um desses caminhos.

## Executar

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Docker (Local e Render)

### Build local com Docker

```bash
docker build -t livraria-doacoes .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  -e APP_CORS_ALLOWED_ORIGINS=http://localhost:3000 \
  livraria-doacoes
```

---

## Deploy no Render

### Render + Supabase

Este projeto usa **PostgreSQL do Supabase como banco externo** e roda no Render como **Web Service**.

#### Passos de configuração no Render

1. Conecte o repositório ao Render e escolha **Web Service**.
2. O Render detecta o `Dockerfile` automaticamente.
3. Se preferir build manual (sem Docker), configure:
   - **Build Command**: `./mvnw package`
   - **Start Command**: `java -jar target/livraria-doacoes-0.0.1-SNAPSHOT.jar`

4. Configure as variáveis de ambiente no Render:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `APP_CORS_ALLOWED_ORIGINS`

#### Exemplo de configuração do Supabase

No painel do Supabase, pegue os dados em **Project Settings > Database**. A URL JDBC costuma seguir este formato:

```text
jdbc:postgresql://db.<seu-project-ref>.supabase.co:5432/postgres?sslmode=require
```

Valores comuns:

- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=<senha do banco do Supabase>`

> Observação: a URL exata pode mudar conforme o projeto do Supabase, então prefira copiar a connection string do próprio painel e adaptar para JDBC, sempre com `sslmode=require`.

#### CORS para o front

Se o frontend estiver em outro domínio, defina `APP_CORS_ALLOWED_ORIGINS` com as origens permitidas, separadas por vírgula e **sem espaços**:

```text
https://meu-front.onrender.com,http://localhost:3000
```

#### Observações importantes

- A aplicação já lê a porta do Render com `server.port=${PORT:8080}`.
- Não armazene segredos no `application.properties`; use variáveis de ambiente no painel do Render.
- Para desenvolvimento local, você pode manter os valores padrão do arquivo de configuração.
- O banco do Supabase é só o PostgreSQL; não é necessário adicionar Supabase Auth para esta API básica.

### Integração com o frontend

Para o front consumir a API, ele só precisa conhecer a URL pública do backend no Render. Exemplo:

```text
https://sua-api.onrender.com
```

Payloads importantes:

- `POST /livros` espera um `usuario.idUsuario` e uma `categoria.idCategoria`.
- `POST /doacoes` espera um `livro.idLivro`.
- Erros vêm no formato `{ "erro": "mensagem" }`.

