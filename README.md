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

## Executar

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Deploy no Render

Passos mínimos para subir no Render (Web Service):

1. Conecte o repositório ao Render e escolha "Web Service".
2. Build command: `./mvnw.cmd -q package` (ou `./mvnw.cmd package`).
3. Start command: `java -jar target/livraria-doacoes-0.0.1-SNAPSHOT.jar` (ajuste a versão se necessário).
4. Variáveis de ambiente recomendadas:
   - `PORT` — Render provê automaticamente; a aplicação lê `server.port=${PORT:8080}`.
   - `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` — credenciais do banco (Postgres).
   - `APP_CORS_ALLOWED_ORIGINS` — origens permitidas para o front (ex: `https://meu-front.onrender.com` ou `http://localhost:3000`).

Observações:
- Não armazene segredos no `application.properties`; use variáveis de ambiente no painel do Render.
- Se usar um banco hospedado (Supabase, RDS, etc.), assegure que o serviço do Render pode alcançar a instância de Postgres.
- Para integrar com o frontend, configure `APP_CORS_ALLOWED_ORIGINS` com a URL do frontend ou use `*` em dev.

