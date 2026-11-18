# IIPS Skill Development & Certification Tracking System

A simple web application (minor college lab project) where a student can keep all skill-development
courses, certifications and skills in one place.

**Modules:** NPTEL · SWAYAM · Coursera · Certifications · Student Skill Portfolio

**Tech stack:** Java 21 · Spring Boot 3.3.5 · Spring MVC · Spring Data JPA · Hibernate · Thymeleaf · HTML/CSS/JS · MySQL 8 · Maven

---

## 1. Required software

| Software | Version |
|----------|---------|
| JDK | **21** |
| MySQL Server | **8.x** |
| IntelliJ IDEA | Community Edition (any recent version) |
| Maven | Not needed separately – IntelliJ has Maven built in |
| Internet | Needed only the first time, to download Maven dependencies |

## 2. JDK 21 requirement

The project is configured for Java 21 (`<java.version>21</java.version>` in `pom.xml`).

In IntelliJ: **File → Project Structure → Project → SDK** → choose JDK 21 (use *Add SDK → Download JDK* and pick
version 21 if you do not have it). Set **Language level** to *21*.

## 3. MySQL requirement

MySQL 8.x must be installed and running on `localhost:3306`. You need the password of your MySQL `root` user.

## 4. Create the database

Option A – run the script `database_setup.sql` in MySQL Workbench or the MySQL command line:

```sql
CREATE DATABASE IF NOT EXISTS iips_skill_tracker;
USE iips_skill_tracker;
```

Option B – simply create the database named `iips_skill_tracker` yourself.
(The connection URL also contains `createDatabaseIfNotExist=true`, so the app creates it automatically if you forget.)

**Do NOT create tables.** Hibernate creates all tables automatically on start-up.

## 5. Configure MySQL username / password

Open `src/main/resources/application.properties` and change **only** this line:

```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Replace `YOUR_MYSQL_PASSWORD` with your own MySQL root password. If your MySQL user is not `root`, also change
`spring.datasource.username`.

## 6. Open the project in IntelliJ IDEA

1. Extract `iips-skill-tracker.zip`.
2. Start IntelliJ IDEA → **File → Open**.
3. Select the extracted **iips-skill-tracker** folder (the one containing `pom.xml`) and click OK.
4. If asked, choose **Trust Project**.

## 7. Reload Maven

IntelliJ normally imports Maven automatically. If it does not (class names shown in red):

- Open the **Maven** tool window (right side) and click the **Reload All Maven Projects** button (circular arrows), or
- Right-click `pom.xml` → **Maven → Reload project**.

Wait until the progress bar at the bottom finishes downloading dependencies.

## 8. Run the application

1. Open `src/main/java/com/iips/skilltracker/IipsSkillTrackerApplication.java`.
2. Click the green ▶ icon next to `main` → **Run 'IipsSkillTrackerApplication'**.
3. Wait for the console message: `Started IipsSkillTrackerApplication`.

(Alternative from a terminal inside the project folder, if Maven is installed: `mvn spring-boot:run`.)

## 9. Open in the browser

**http://localhost:8080**

## 10. Demo login

| Field | Value |
|-------|-------|
| Email | `student@iips.edu` |
| Password | `1234` |

The demo student and sample data are created automatically on the first start-up and are **not duplicated** on restart.

## 11. Project modules

| Module | URL | What you can do |
|--------|-----|-----------------|
| Login / Logout | `/login`, `/logout` | Session-based login (no Spring Security) |
| Dashboard | `/dashboard` | Summary counts from the database + navigation |
| NPTEL | `/nptel` | Add, view, edit, delete NPTEL courses |
| SWAYAM | `/swayam` | Add, view, edit, delete SWAYAM courses |
| Coursera | `/coursera` | Add, view, edit, delete Coursera courses |
| Certifications | `/certifications` | Add, view, edit, delete certifications |
| My Skill Portfolio | `/skills` | Add, view, edit, delete skills |

Every student only sees and edits **their own** records.

## 12. Database tables (created automatically)

| Table | Purpose |
|-------|---------|
| `students` | id, name, roll_number, email (unique), password, course, semester |
| `nptel_courses` | course_name, course_id, duration, completion_date, score, grade, certificate_available, student_id |
| `swayam_courses` | course_name, course_id, duration, completion_date, score, certificate_available, student_id |
| `coursera_courses` | course_name, provider, certificate_id, completion_date, skills_acquired, student_id |
| `certifications` | certification_name, issuing_organization, certificate_id, issue_date, expiry_date, status, student_id |
| `skills` | skill_name, category, skill_level, student_id |

Each module table has a many-to-one relation (`student_id`) to `students`.

## Project structure

```
iips-skill-tracker/
├── pom.xml
├── README.md
├── database_setup.sql
├── .gitignore
└── src/main/
    ├── java/com/iips/skilltracker/
    │   ├── IipsSkillTrackerApplication.java
    │   ├── controller/   (Login, Dashboard, Nptel, Swayam, Coursera, Certification, Skill)
    │   ├── entity/       (Student, NptelCourse, SwayamCourse, CourseraCourse, Certification, Skill)
    │   ├── repository/   (Spring Data JPA repositories)
    │   ├── service/      (business logic)
    │   └── config/       (DataInitializer, LoginInterceptor, WebConfig)
    └── resources/
        ├── application.properties
        ├── templates/    (Thymeleaf pages)
        └── static/       (css/style.css, js/script.js)
```

## 13. Common errors and solutions

| Error | Cause | Solution |
|-------|-------|----------|
| `Access denied for user 'root'@'localhost'` | Wrong MySQL password | Fix `spring.datasource.password` in `application.properties` |
| `Communications link failure` / `Connection refused` | MySQL is not running | Start the MySQL service (Windows: `services.msc` → MySQL80 → Start) |
| `Unknown database 'iips_skill_tracker'` | Database missing | Run `database_setup.sql` or create the database manually |
| `Port 8080 was already in use` | Another program uses 8080 | Stop it, or set `server.port=8081` and open `http://localhost:8081` |
| `release version 21 not supported` / `invalid source release: 21` | IntelliJ uses an older JDK | **File → Project Structure → SDK = 21**, and **Settings → Build → Compiler → Java Compiler → Target bytecode = 21** |
| Red imports / `package org.springframework does not exist` | Maven dependencies not loaded | Reload Maven (section 7); check your internet connection |
| `Public Key Retrieval is not allowed` | MySQL 8 authentication | Already handled by `allowPublicKeyRetrieval=true` in the URL; do not remove it |
| Login says "Invalid email or password" | Wrong credentials or data not created | Use `student@iips.edu` / `1234`; check the console for start-up errors |
| Page shows "Something went wrong" | Unexpected server error | Check the IntelliJ Run console for the real error message |

## Note

Passwords are stored as plain text because this is a simple lab project and Spring Security is intentionally not
used. A real application must hash passwords (for example with BCrypt).
