# 🏪 Loja Service

Este microserviço é responsável pelo **catálogo de produtos eletrônicos** e pelo **gerenciamento de usuários**, incluindo **cadastro e login com autenticação JWT**.

Foi desenvolvido com foco em boas práticas de segurança e acesso a dados, usando banco MySQL com Spring Data JPA.

---

## 🎯 Responsabilidades

- Cadastro e login de usuários
- Geração de token JWT com segurança
- Listagem e busca de produtos por nome, categoria ou ID
- Controle de estoque ao efetuar uma compra

---

## 🛠️ Tecnologias e Recursos

- Spring Boot (REST API)
- Spring Security com JWT
- Spring Data JPA + MySQL
- Bean Validation
- HTTPS e headers de segurança
- Eureka Client para registro de serviço

---

## 🌐 Integração

- Outros microserviços (como `carrinho_service` e `compra_service`) consomem os endpoints deste serviço via **Feign Client**
- Retorna os dados de produtos para o carrinho e atualiza o estoque após a compra

---

## 🚀 Endpoints Principais

- `POST /cadastro` → Cadastro de novo usuário
- `POST /login` → Retorna JWT
- `GET /produtos/catalago` → Lista todos os produtos
- `POST /produtos/comprarProduto` → Atualiza estoque
