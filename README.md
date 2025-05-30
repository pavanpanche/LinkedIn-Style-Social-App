# LinkedIn-Style-Social-App


A full-stack LinkedIn-style social media platform where users can manage professional profiles, share posts, and interact with others. Built with **Flutter** (frontend), **Spring Boot** (backend), and **MySQL** (database), the system follows best practices in security, architecture, and user experience.

---

## 💻 Technologies Used

- **Frontend**: Flutter // not implemented 
- **Backend**: Java + Spring Boot
- **Security**: Spring Security with JWT Authentication
- **Database**: MySQL

---

## 🚀 Setup Instructions

**Software installed**

- java sdk latest stable version 
- intelli J IDE / VS code/ Eclipse
- MySql work bench/cmd line
- PostMan endpoint api testing 
- flutter sdk / Visual Studio / Android sdk (Mobile App)

### Backend (Spring Boot)
1. Clone the repository:
   ```bash
   git clone https://github.com/pavanpanche/linkedin-style-social-app.git

2.Configure application.properties with your MySQL credentials:
   - spring.datasource.url=jdbc:mysql://localhost:3306/dbname
   - spring.datasource.username=root
   - spring.datasource.password=yourpassword

3.Run the application:

   - ./mvnw spring-boot:run

4.Frontend (Flutter):

   - cd social-app-frontend

5.Get dependencies:

   - flutter pub get

6.Run the app:
   
   - flutter run

---

## ✅ Complete Feature List


### 1. Authentication & Authorization
   - User registration and login

   - Secure login using Spring Security

   - JWT-based authentication

   - Role-based access control(user,admin)

### 2. Profile Management

####  **Create/edit/view profile with:**

   - Headline

   - About section

   - Skills

  -  education

  -  location

### 3. Post Management
   - Create, update, delete personal posts

   - Partial Update/patch 

   - View all public posts

   - View individual Post details

### 4. Interactions
   - Like/dislike posts (like count, view like summary with pagination)

   - Comment on posts (paginated page for existing comment)

   - View user’s own and liked posts

### 5. Home Feed
   - Display latest posts on home screen

   - Paginated feed for scalability

### 6. Search
   - Search users by name

   - Search posts by keywords in title/content

   - Filter and sort posts by date or popularity


---

## 🌐 API Overview –

| Endpoint                      | Method | Description                           | Auth Required |
| ----------------------------- | ------ | ------------------------------------- | ------------- |
| `/api/auth/register`          | POST   | Register a new user                   | ❌ No          |
| `/api/auth/login`             | POST   | Login and receive JWT token           | ❌ No          |
| `/api/profile`                | POST   | Create profile for the logged-in user | ✅ Yes         |
| `/api/profile`                | PUT    | Fully update the user’s profile       | ✅ Yes         |
| `/api/profile`                | PATCH  | Partially update user profile         | ✅ Yes         |
| `/api/profile`                | GET    | Get the logged-in user's profile      | ✅ Yes         |
| `/api/posts`                  | POST   | Create a new post                     | ✅ Yes         |
| `/api/posts/{postId}`         | PUT    | Update an existing post               | ✅ Yes         |
| `/api/posts/{postId}`         | DELETE | Delete a post by ID                   | ✅ Yes         |
| `/api/posts/{postId}`         | GET    | Get a post by ID                      | ❌ No          |
| `/api/posts`                  | GET    | Get all public posts (non-paginated)  | ❌ No          |
| `/api/posts/feed`             | GET    | Get home feed with pagination         | ❌ No          |
| `/api/posts/search`           | GET    | Search posts by keyword               | ❌ No          |
| `/api/posts/sorted`           | GET    | Get posts sorted by field and order   | ❌ No          |
| `/api/likes/{postId}`         | POST   | Like a post                           | ✅ Yes         |
| `/api/likes/{postId}`         | DELETE | Dislike (remove like) from a post     | ✅ Yes         |
| `/api/likes`                  | GET    | Get liked posts (paginated)           | ✅ Yes         |
| `/api/likes/summary/{postId}` | GET    | Get like summary for a post           | ✅ Yes         |
| `/api/comments/{postId}`      | POST   | Add a comment to a post               | ✅ Yes         |
| `/api/comments/{commentId}`   | PUT    | Update an existing comment            | ✅ Yes         |
| `/api/comments/{commentId}`   | DELETE | Delete a comment                      | ✅ Yes         |


## 📄 License
This project is open-source and available under the MIT License.

## 👨‍💻 Developed By

***Pavan Panche***

Email 📧: pavapanche2@gmail.com
LinkedIn: (https://www.linkedin.com/in/pavanpanche)

