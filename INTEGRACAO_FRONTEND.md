# 🔗 Integração Frontend - API Livraria Doações

## Fluxo simples (recomendado)

Use estes endpoints para o front enxuto:

### 1) Cadastro

`POST /usuarios`

```json
{
  "nome": "Maria",
  "email": "maria@email.com",
  "telefone": "51999999999",
  "cpf": "12345678900"
}
```

### 2) Login

`POST /usuarios/login`

```json
{
  "email": "maria@email.com",
  "cpf": "12345678900"
}
```

### 3) Doação simples

`POST /doacoes`

```json
{
  "nome": "Maria",
  "telefone": "51999999999",
  "email": "maria@email.com",
  "titulo": "Dom Casmurro"
}
```

### 4) Acervo disponível

`GET /acervo`

Retorna livros com status `DISPONIVEL`.

**URL Base da API (Produção):** `https://doacoes-livraria-back.onrender.com`

**Swagger UI:** `https://doacoes-livraria-back.onrender.com/swagger-ui/index.html`

**OpenAPI JSON:** `https://doacoes-livraria-back.onrender.com/v3/api-docs`

---

## 📋 Configuração Inicial

### 1. Variável de Ambiente
Configure a URL base no seu `.env`:

```env
VITE_API_URL=https://doacoes-livraria-back.onrender.com
```

### 2. Exemplo de Serviço HTTP (JavaScript/TypeScript)

```javascript
// api.js ou api.ts
const API_URL = import.meta.env.VITE_API_URL || 'https://doacoes-livraria-back.onrender.com';

export async function apiRequest(endpoint, options = {}) {
  const url = `${API_URL}${endpoint}`;
  
  try {
    const response = await fetch(url, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    });
    
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.erro || response.statusText);
    }
    
    return await response.json();
  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}
```

---

## 👥 ENDPOINTS - Usuários

### GET /usuarios
Listar todos os usuários

```javascript
const usuarios = await apiRequest('/usuarios');
// Retorna: [{ idUsuario, nome, email, telefone, livros: [] }, ...]
```

**Response (200):**
```json
[
  {
    "idUsuario": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "11999999999",
    "livros": []
  }
]
```

---

### POST /usuarios
Criar novo usuário

```javascript
const novoUsuario = await apiRequest('/usuarios', {
  method: 'POST',
  body: JSON.stringify({
    nome: "Maria Santos",
    email: "maria@example.com",
    telefone: "11988888888"
  })
});
// Retorna: { idUsuario, nome, email, telefone, livros: [] }
```

**Request Body (obrigatório):**
```json
{
  "nome": "string (obrigatório)",
  "email": "string (obrigatório, único)",
  "telefone": "string (obrigatório)"
}
```

**Response (201):**
```json
{
  "idUsuario": 1,
  "nome": "Maria Santos",
  "email": "maria@example.com",
  "telefone": "11988888888",
  "livros": []
}
```

---

### GET /usuarios/{id}
Buscar usuário por ID

```javascript
const usuario = await apiRequest('/usuarios/1');
```

---

### PUT /usuarios/{id}
Atualizar usuário

```javascript
const usuarioAtualizado = await apiRequest('/usuarios/1', {
  method: 'PUT',
  body: JSON.stringify({
    nome: "João Silva Updated",
    email: "joao.updated@example.com",
    telefone: "11987654321"
  })
});
```

---

### DELETE /usuarios/{id}
Deletar usuário

```javascript
await apiRequest('/usuarios/1', { method: 'DELETE' });
// Retorna: 204 No Content
```

---

## 📚 ENDPOINTS - Categorias

### GET /categorias
Listar todas as categorias

```javascript
const categorias = await apiRequest('/categorias');
// Retorna: [{ idCategoria, nome, livros: [] }, ...]
```

**Response (200):**
```json
[
  {
    "idCategoria": 1,
    "nome": "Ficção Científica",
    "livros": []
  }
]
```

---

### POST /categorias
Criar nova categoria

```javascript
const novaCategoria = await apiRequest('/categorias', {
  method: 'POST',
  body: JSON.stringify({
    nome: "Romance"
  })
});
```

**Request Body:**
```json
{
  "nome": "string (obrigatório, único)"
}
```

---

### GET /categorias/{id}
Buscar categoria por ID

```javascript
const categoria = await apiRequest('/categorias/1');
```

---

### PUT /categorias/{id}
Atualizar categoria

```javascript
const categoriaAtualizada = await apiRequest('/categorias/1', {
  method: 'PUT',
  body: JSON.stringify({
    nome: "Ficção Científica Avançada"
  })
});
```

---

### DELETE /categorias/{id}
Deletar categoria

```javascript
await apiRequest('/categorias/1', { method: 'DELETE' });
```

---

## 📖 ENDPOINTS - Livros

### GET /livros
Listar todos os livros

```javascript
const livros = await apiRequest('/livros');
```

**Response (200):**
```json
[
  {
    "idLivro": 1,
    "titulo": "Dune",
    "autor": "Frank Herbert",
    "descricao": "Épica de ficção científica",
    "status": "DISPONIVEL",
    "usuario": {
      "idUsuario": 1,
      "nome": "João Silva",
      "email": "joao@example.com",
      "telefone": "11999999999"
    },
    "categoria": {
      "idCategoria": 1,
      "nome": "Ficção Científica"
    },
    "doacao": null
  }
]
```

---

### POST /livros
Criar novo livro

```javascript
const novoLivro = await apiRequest('/livros', {
  method: 'POST',
  body: JSON.stringify({
    titulo: "Neuromancer",
    autor: "William Gibson",
    descricao: "Clássico da ficção científica cyberpunk",
    status: "DISPONIVEL",
    usuario: { idUsuario: 1 },
    categoria: { idCategoria: 1 }
  })
});
```

**Request Body (obrigatório):**
```json
{
  "titulo": "string (obrigatório)",
  "autor": "string (obrigatório)",
  "descricao": "string (opcional)",
  "status": "DISPONIVEL | DOADO (opcional, default: DISPONIVEL)",
  "usuario": {
    "idUsuario": number (obrigatório - ID do dono)
  },
  "categoria": {
    "idCategoria": number (obrigatório - ID da categoria)
  }
}
```

**Response (201):**
```json
{
  "idLivro": 2,
  "titulo": "Neuromancer",
  "autor": "William Gibson",
  "descricao": "Clássico da ficção científica cyberpunk",
  "status": "DISPONIVEL",
  "usuario": { ... },
  "categoria": { ... },
  "doacao": null
}
```

---

### GET /livros/{id}
Buscar livro por ID

```javascript
const livro = await apiRequest('/livros/1');
```

---

### PUT /livros/{id}
Atualizar livro

```javascript
const livroAtualizado = await apiRequest('/livros/1', {
  method: 'PUT',
  body: JSON.stringify({
    titulo: "Dune - Edição Especial",
    autor: "Frank Herbert",
    descricao: "Descrição atualizada",
    status: "DISPONIVEL",
    usuario: { idUsuario: 1 },
    categoria: { idCategoria: 1 }
  })
});
```

---

### DELETE /livros/{id}
Deletar livro

```javascript
await apiRequest('/livros/1', { method: 'DELETE' });
```

---

### GET /livros/buscar?titulo=Dune
Buscar livros por título (parcial, case-insensitive)

```javascript
const livros = await apiRequest('/livros/buscar?titulo=Dune');
```

---

### GET /livros/status/{status}
Buscar livros por status (DISPONIVEL ou DOADO)

```javascript
const livrosDisponiveis = await apiRequest('/livros/status/DISPONIVEL');
const livrosDoados = await apiRequest('/livros/status/DOADO');
```

---

## 🎁 ENDPOINTS - Doações

### GET /doacoes
Listar todas as doações

```javascript
const doacoes = await apiRequest('/doacoes');
```

**Response (200):**
```json
[
  {
    "idDoacao": 1,
    "dataDoacao": "2026-06-15",
    "nomeRecebedor": "Biblioteca Pública",
    "contatoRecebedor": "biblioteca@email.com",
    "livro": {
      "idLivro": 1,
      "titulo": "Dune",
      "status": "DOADO",
      ...
    }
  }
]
```

---

### POST /doacoes
Criar nova doação

```javascript
const novaDoacao = await apiRequest('/doacoes', {
  method: 'POST',
  body: JSON.stringify({
    dataDoacao: "2026-06-15",
    nomeRecebedor: "Biblioteca Central",
    contatoRecebedor: "biblioteca@email.com",
    livro: { idLivro: 1 }
  })
});
```

**Request Body (obrigatório):**
```json
{
  "dataDoacao": "YYYY-MM-DD (obrigatório)",
  "nomeRecebedor": "string (obrigatório)",
  "contatoRecebedor": "string (obrigatório)",
  "livro": {
    "idLivro": number (obrigatório - ID do livro a ser doado)
  }
}
```

**Response (201):**
```json
{
  "idDoacao": 1,
  "dataDoacao": "2026-06-15",
  "nomeRecebedor": "Biblioteca Central",
  "contatoRecebedor": "biblioteca@email.com",
  "livro": { ... }
}
```

**⚠️ Nota Importante:**
- Ao criar uma doação, o status do livro muda automaticamente para "DOADO"
- Um livro só pode ter UMA doação (não pode ser doado mais de uma vez)

---

### GET /doacoes/{id}
Buscar doação por ID

```javascript
const doacao = await apiRequest('/doacoes/1');
```

---

### PUT /doacoes/{id}
Atualizar doação

```javascript
const doacaoAtualizada = await apiRequest('/doacoes/1', {
  method: 'PUT',
  body: JSON.stringify({
    dataDoacao: "2026-06-20",
    nomeRecebedor: "Biblioteca Atualizada",
    contatoRecebedor: "biblioteca.novo@email.com"
  })
});
```

---

### DELETE /doacoes/{id}
Deletar doação

```javascript
await apiRequest('/doacoes/1', { method: 'DELETE' });
// ⚠️ Nota: Ao deletar, o livro volta ao status DISPONIVEL
```

---

## ⚠️ Tratamento de Erros

Todos os erros retornam neste formato:

```json
{
  "erro": "Mensagem de erro descritiva"
}
```

**Exemplos:**

```javascript
try {
  await apiRequest('/usuarios/999');
} catch (error) {
  console.error(error.message); // "Usuário não encontrado com id 999"
}

try {
  await apiRequest('/livros', {
    method: 'POST',
    body: JSON.stringify({
      titulo: "Livro",
      autor: "Autor",
      usuario: { idUsuario: 999 },
      categoria: { idCategoria: 999 }
    })
  });
} catch (error) {
  console.error(error.message); // "Usuario nao encontrado com id 999"
}
```

**Status HTTP Comuns:**
- `200 OK` - Requisição bem-sucedida
- `201 Created` - Recurso criado
- `204 No Content` - Deletado com sucesso
- `400 Bad Request` - Dados inválidos
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro no servidor

---

## 🔄 CORS (Cross-Origin)

O backend está configurado para aceitar requisições de:
- `https://seu-front.onrender.com` (adicione a URL do seu frontend)
- `http://localhost:3000` (desenvolvimento local)

**Seu frontend precisa estar em uma dessas origens ou você precisa atualizar `APP_CORS_ALLOWED_ORIGINS` no Render.**

---

## 📝 Exemplo Completo - Criar e Listar Usuários

```javascript
// 1. Listar usuários existentes
async function listarUsuarios() {
  try {
    const usuarios = await apiRequest('/usuarios');
    console.log('Usuários:', usuarios);
    return usuarios;
  } catch (error) {
    console.error('Erro ao listar:', error);
  }
}

// 2. Criar novo usuário
async function criarUsuario(dados) {
  try {
    const novoUsuario = await apiRequest('/usuarios', {
      method: 'POST',
      body: JSON.stringify(dados)
    });
    console.log('Usuário criado:', novoUsuario);
    return novoUsuario;
  } catch (error) {
    console.error('Erro ao criar:', error);
  }
}

// 3. Usar
await criarUsuario({
  nome: "Ana Silva",
  email: "ana@example.com",
  telefone: "11987654321"
});

const usuarios = await listarUsuarios();
```

---

## 🎯 Checklist de Integração

- [ ] Configurar URL base da API no `.env`
- [ ] Criar serviço HTTP reutilizável
- [ ] Implementar tela de USUÁRIOS (CRUD completo)
- [ ] Implementar tela de CATEGORIAS (CRUD completo)
- [ ] Implementar tela de LIVROS (CRUD + busca + filtro por status)
- [ ] Implementar tela de DOAÇÕES (CRUD)
- [ ] Testar todas as requisições no Swagger UI
- [ ] Tratamento de erros em todas as telas
- [ ] Validar CORS no navegador
- [ ] Testar integração end-to-end

---

## 📞 Dúvidas?

Use o **Swagger UI** para testar manualmente: `https://doacoes-livraria-back.onrender.com/swagger-ui/index.html`

Todos os endpoints estão documentados lá com possibilidade de testar em tempo real!


