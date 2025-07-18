# SmartEd
SmartEd is a full-stack school management system built with Spring Boot and Thymeleaf. It features role-based login for Students, Teachers, and Admins, with modules for leaves , course updates, notices, and secure JWT authentication. Designed for efficient school operations.
# SmartEd – Student & Teacher Portal 🏫
---

## ✨ Features

- 👨‍🎓 Student & 👨‍👩‍👧 Teachers login support
- 🔐 Secure Authentication (Spring Security)
- 🧾 Fees Management (CRUD)
- 📄 Profile Update Functionality
- 🧩 Modular Microservice Design
- 🌐 RESTful API Integration

---

## 🧰 Tech Stack

| Layer           | Technologies                        |
|----------------|-------------------------------------|
| Backend         | Java, Spring Boot, Spring Data JPA |
| Frontend        | HTML, CSS, Thymeleaf (if used)     |
| Database        | MySQL / PostgreSQL                 |
| Build Tool      | Maven                              |
| Deployment      | AWS (EC2/S3/EBS)                   |
| Version Control | Git + GitHub                       |

---

## 🚀 Getting Started

```bash
# Clone the repo
git clone https://github.com/DrLaksh/SmartEd.git

# Navigate into the project
cd SmartEd

# Build the project
./mvnw clean install

# Run the app
./mvnw spring-boot:run

```
Important :- Do the COnfig CHanges in The APplication.properties file as per the changes

-------
🏗️ Project Structure

SmartEd/
│
├── src/
│   ├── main/
│   │   ├── java/com/smarted/          # All controllers, services, repositories
│   │   └── resources/                 # application.properties, templates
│
├── target/                           # [Ignored] Build output
├── .gitignore                        # Git ignore rules
├── pom.xml                           # Maven build config
├── README.md                         # You're reading it!
└── LICENSE                           # Optional: MIT / Apache / GPL

---
Screenshots :- 
<img width="1899" height="1012" alt="image" src="https://github.com/user-attachments/assets/82f12d70-2ab8-464e-a787-b56162e46098" />
<img width="1899" height="919" alt="image" src="https://github.com/user-attachments/assets/c6ca3946-874a-4a81-98b1-1e7003a9bd17" />
<img width="1900" height="920" alt="image" src="https://github.com/user-attachments/assets/f9daa8d2-9ddf-49dc-bff9-a868df6a5527" />
<img width="1898" height="917" alt="image" src="https://github.com/user-attachments/assets/96ae276c-2667-4f20-88ca-d135014c4655" />
<img width="1900" height="920" alt="image" src="https://github.com/user-attachments/assets/b95581da-2730-46f5-adcb-99c4c20befc7" />


Upcoming CHnages :- 
Implementation of  AI Chatbot Support for Question Solving
