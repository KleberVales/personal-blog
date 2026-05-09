# personal-blog

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

- Personal Blog

    - Users can register, create posts, and comment.

    - Search, filtering, and sorting functions.

A blog project developed with **Java**, **Spring Boot**, **Spring Security**, **JPA/Hibernate**, and **MySQL/PostgreSQL**, allowing user, post, and comment management with permission control.

---

## 🛠 Technologies

- **Back-end:** Java 21, Spring Boot 3  
- **Security:** Spring Security + roles (POSTER / COMMENTER)  
- **Database:** PostgreSQL  
- **Build:** Gradle  
- **API testing:** Postman  

---

## 🚀 Features

- User registration with roles:
  - `POSTER`: can create posts and comments
  - `COMMENTER`: can only create comments
- Create, read, update, and delete posts
- Create and read comments
- Basic authentication (HTTP Basic Auth)
- Role-based authorization control
- Table structure:
  - `users` (id, username, email, password)
  - `roles` (id, name)
  - `user_roles` (user ↔ role relationship)
  - `posts` (id, title, text, user_id)
  - `comments` (id, text, post_id, user_id)

---

## 📁 Project Structure

```text
src/main/java/com/example/blog
├── config → Spring Security configurations
├── controller → REST endpoints
├── domain → Entities (User, Role, Post, Comment)
├── repository → JPA repositories
├── service → Business logic
└── security → UserDetailsService integration

```

---

## ✉️ Contact

Connect with me:  

[GitHub](https://github.com/KleberVales)

**Kleber Vales**  
*Java & Spring Software Engineer*

Cloud | DevOps | Docker | Kubernetes | Git/GitHub | Scrum | Generative AI

🏆 **Oracle Certified Associate – Java SE 7 Programmer**  
🏆 **Microsoft Technology Associate – Software Development Fundamentals**  
🏆 **Scrum Fundamentals Certified (SFC™)**  
🏆 **Oracle Cloud Infrastructure 2025 – DevOps Professional**  
🏆 **Oracle Cloud Infrastructure 2025 – Generative AI Professional**

🎓 **Bachelor's Degree in Computer Science**  
🎓 **MBA in Web Software Development**











