# Guia Completo: Deploy no Render + Supabase

Este guia mostra exatamente como colocar a aplicação em produção.

## Pré-requisitos

- [ ] Repositório GitHub
- [ ] Conta no [Render](https://render.com)
- [ ] Projeto criado no [Supabase](https://supabase.com)

## Passo 1: Coletar informações do Supabase

No painel do Supabase, vá para **Project Settings > Database**.

Você vai precisar de:

1. **Connection string** (formato: `postgresql://user:password@host:port/database`)
2. **Host**: `db.<seu-project-ref>.supabase.co`
3. **Port**: `5432`
4. **Username**: `postgres` (padrão)
5. **Password**: A senha que você criou ao inicializar o projeto

### Converter para JDBC

A URL JDBC do Supabase segue este padrão:

```
jdbc:postgresql://db.seu-project-ref.supabase.co:5432/postgres?sslmode=require
```

**Exemplo real**:

```
jdbc:postgresql://db.abc123def456.supabase.co:5432/postgres?sslmode=require
```

## Passo 2: Criar Web Service no Render

1. Acesse [dashboard.render.com](https://dashboard.render.com)
2. Clique em **New +** > **Web Service**
3. Escolha **Build and deploy from a Git repository**
4. Conecte seu repositório GitHub
5. Escolha a branch (`main` ou `develop`)

## Passo 3: Configurar Build e Start

No formulário de criação do Web Service:

### Build Command
```bash
./mvnw package
```

**Ou deixe em branco** — o Render detecta o `Dockerfile` automaticamente.

### Start Command
```bash
java -jar target/livraria-doacoes-0.0.1-SNAPSHOT.jar
```

**Ou deixe em branco** — o Dockerfile já tem o comando padrão.

## Passo 4: Configurar Variáveis de Ambiente

No formulário, expanda **Environment** e adicione:

| Variável | Valor | Exemplo |
|----------|-------|---------|
| `SPRING_DATASOURCE_URL` | URL JDBC do Supabase | `jdbc:postgresql://db.abc123def456.supabase.co:5432/postgres?sslmode=require` |
| `SPRING_DATASOURCE_USERNAME` | Usuário | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Senha | `sua-senha-super-secreta` |
| `APP_CORS_ALLOWED_ORIGINS` | URLs permitidas (separadas por vírgula, sem espaços) | `https://seu-front.onrender.com,http://localhost:3000` |

### Exemplo completo

```
SPRING_DATASOURCE_URL=jdbc:postgresql://db.abc123def456.supabase.co:5432/postgres?sslmode=require
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=minha-senha-123
APP_CORS_ALLOWED_ORIGINS=https://meu-front.onrender.com,http://localhost:3000
```

## Passo 5: Deploy

1. Clique em **Create Web Service**
2. Espere o Render fazer:
   - Clone do repositório
   - Build da imagem Docker
   - Deploy na nuvem
3. Você vai receber uma URL pública, algo como:

```
https://livraria-api.onrender.com
```

## Passo 6: Testar a API

Acesse no navegador ou use `curl`:

```bash
curl https://livraria-api.onrender.com/usuarios
```

Você deve receber uma resposta JSON com a lista de usuários (possivelmente vazia se o banco está novo).

## Passo 7: Integrar com o Frontend

Configure o frontend para apontar para a URL pública do backend:

```javascript
const API_URL = "https://livraria-api.onrender.com";

// Exemplo de requisição
fetch(`${API_URL}/livros`)
  .then(res => res.json())
  .then(data => console.log(data));
```

**Atenção**: O CORS está configurado para aceitar requisições apenas de `APP_CORS_ALLOWED_ORIGINS`.  
Se o frontend está em outro domínio, adicione ele lá.

## Troubleshooting

### Erro: "Cannot connect to database"

- Verifique a URL JDBC
- Confirme que `sslmode=require` está na URL
- Teste se o Supabase aceita conexões de fora (IP allowlist)

### Erro: "CORS policy blocked"

- Confirme que `APP_CORS_ALLOWED_ORIGINS` inclui a URL do seu frontend
- Não use espaços após as vírgulas

### Erro: "Build failed"

- Verifique se o `pom.xml` está correto
- Confirme que Java 21 está no `pom.xml` (`<java.version>21</java.version>`)

### O banco está vazio

- Isso é normal na primeira vez
- Crie usuários e categorias via API antes de criar livros

## Próximos passos

- [ ] Configurar HTTPS (já incluído no Render)
- [ ] Adicionar logs estruturados
- [ ] Implementar autenticação (JWT ou OAuth)
- [ ] Configurar backup automático do Supabase
- [ ] Monitorar performance no painel do Render

---

**Dúvidas?** Confira o [README.md](README.md) para detalhes técnicos.

