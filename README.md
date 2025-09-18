# 💊 Pildonitas | Medication Management App
## 📌 Introduction

Pildonitas is a web application developed during the Hackathon F5 – September 2025.
Its purpose is to help users manage their medications by scheduling, tracking, and recording their intake.

The project follows a client-server architecture with a separate frontend and backend, ensuring scalability, maintainability, and clean code practices.

## 🎯 Objectives

- Allow users to register medications with dosage and frequency.
- Schedule intakes and track their status (PENDING, TAKEN, DELAYED).
- Provide a clean API for CRUD operations on medications and intakes.
- Encourage teamwork and agile practices in a fast-paced hackathon environment.

## 🚀 Features
- **User Management**
    - Registration with an initial local currency balance.
    - Authentication with **JWT**.
    - Roles: `USER` and `ADMIN`.
- **Medication**
    - Register new medications with persistence.
    - List medications
- **Intakes**
  - Create scheduled intakes.
  - Mark intakes as TAKEN, PENDING, or DELAYED.
  - List active intakes.
- **Security**
  - Endpoints protected with **Spring Security + JWT**.
  - Identity and role-based validations.

## 🛠️ Tech Stack
- Java + Spring Boot
- REST API with controllers, services, and repositories
- JPA + Hibernate for persistence
- MySQL for relational DB
- JUnit for testing
- Maven for dependency management
- Swagger for API documentation
- Jwt for authentication

## 📡 API Endpoints

### Users
| Method | Endpoint               | Description                  | Access  |
|--------|------------------------|------------------------------|---------|
| GET    | `/api/users/{id}`      | View user profile            | User    |
| PUT    | `/api/users/{id}`      | Edit own profile             | User    |
| GET    | `/api/users`           | List all users               | Admin   |
| DELETE | `/api/users/{id}`      | Delete a user                | Admin   |

### Medications

| Method | Endpoint               | Description                 | Access |
|--------|------------------------|-----------------------------|--------|
| GET    | `/api/medications`      | List medications           | User   |
| POST    | `/api/medications`      | Create a new medication   | User   |
| DELETE | `/api/medications/{id}`  | Delete a medication     | User   |

### Intakes

| Method | Endpoint               | Description                  | Access |
|--------|------------------------|------------------------------|--------|
| GET    | `/api/intakes/{medicationId}`      | Get intakes by medication    | User   |
| POST   | `/api/intakes/{medicationId}`      | Schedule a new intake        | User   |
| PUT    | `/api/intakes/{id}`  | Update intake status         | User   |

## 🧪 Testing

- Unit tests with JUnit & Mockito.
- Target: 75% minimum coverage.

## 🚀 Future improvements
- Register allergies and validate against medications.
- Real-time reminders or notifications.
- Filters for active vs. history and dose calculations.
- Multiple user support with authentication (add Doctor role, for example).
- Add Docker for containerization.

## 👩‍💻 Team

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/brunasonda">
        <img src="https://github.com/brunasonda.png" width="100px;" alt="Brunasonda"/>
        <br />
        <sub><b>Bruna Sonda</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/thaisrqueiroz">
        <img src="https://github.com/thaisrqueiroz.png" width="100px;" alt="thaisrqueiroz"/>
        <br />
        <sub><b>Thais Rocha</b></sub>
      </a>
    </td>
  </tr>
</table>

This project was developed in a student hackathon setting with limited time, focusing on teamwork, learning, and implementing best practices in software development.

✨ Pildonitas: helping people take care of their health, one pill at a time.