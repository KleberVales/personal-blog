# personal-blog

- Personal Blog

    - Users can register, create posts, and comment.

    - Search, filtering, and sorting functions.
 
Um projeto de blog simples desenvolvido com **Java**, **Spring Boot**, **Spring Security**, **JPA/Hibernate** e **MySQL/PostgreSQL**, permitindo gerenciamento de usuários, posts e comentários, com controle de permissões.

---

## 🛠 Tecnologias

- **Back-end:** Java 21, Spring Boot 3
- **Segurança:** Spring Security + roles (POSTER / COMMENTER)
- **Banco de dados:** PostgreSQL
- **Build:** Gradle
- **API testing:** Postman

---

## 🚀 Funcionalidades

- Cadastro de usuários com roles:
  - `POSTER`: pode criar posts e comentários
  - `COMMENTER`: pode criar apenas comentários
- Criação, leitura, atualização e exclusão de posts
- Criação e leitura de comentários
- Autenticação básica (HTTP Basic Auth)
- Controle de autorização baseado em roles

