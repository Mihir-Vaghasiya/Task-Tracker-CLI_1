# Task-Tracker-CLI_1

# 📝 Task Tracker CLI (Spring Boot)

A simple **Command Line Interface (CLI)** application to track and manage your daily tasks.  
Built using **Java + Spring Boot**, with **JSON file-based persistence** and no database.

This project is inspired by the roadmap.sh Task Tracker project.

---

## 🚀 Features

- Add new tasks
- Update existing tasks
- Delete tasks
- Mark tasks as **IN_PROGRESS** or **DONE**
- List all tasks
- Filter tasks by status:
    - TODO
    - IN_PROGRESS
    - DONE
- Stores data in a local `tasks.json` file
- Graceful handling of invalid commands and inputs

---

## 🛠️ Tech Stack

- Java 11
- Spring Boot 2.7.18
- Maven
- Jackson (for JSON serialization)
- File System (No Database)

---

## 📂 Project Structure
src/main/java/com/example/task_tracker/
│
├── TaskTrackerApplication.java # CLI entry point
├── model/
│ ├── Task.java
│ └── TaskStatus.java
├── storage/
│ └── TaskStorage.java


---

## ▶️ How to Run

### 1️⃣ Build the project

- mvn clean package

### 2️⃣ Run the CLI app
- java -jar target/task_tracker-0.0.1-SNAPSHOT.jar

### 💻 CLI Commands
- Add a task
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar add "Learn Spring Boot"

- List all tasks
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar list

- List tasks by status
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar list todo
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar list progress
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar list done

- Update a task
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar update 1 "Learn Spring Boot CLI"

- Mark task as in progress
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar progress 1

- Mark task as done
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar done 1

- Delete a task
  - java -jar target/task_tracker-0.0.1-SNAPSHOT.jar delete 1

---

## 📄 Data Storage

- Tasks are stored in a local file named tasks.json

- File is automatically created if it does not exist

Example:

- [
  {
    "id": 1,
    "description": "Learn Spring Boot",
    "status": "DONE",
    "createdAt": "2024-04-05T11:30:10",
    "updatedAt": "2024-04-05T11:45:22"
  }
]
---
## 🧠 Learning Outcomes

- Building a non-web Spring Boot application

- Using CommandLineRunner

- File handling and JSON persistence

- Java version compatibility (Java 11)

- Clean CLI command parsing and validation

