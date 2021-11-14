# surface1989-store
*Application made with Spring Boot
-Jdk 1.8
-Spring Boot
-Spring Security
-Thymeleaf
-Mysql 8


*Create database struct and insert data
1. Clone sql script from link https://github.com/surface1999/sql
2. Open my sql: File-> Open Sql Script-> Find to script just clone -> excute script.

*Run Source code
1. Clone source code in to a new workspace:
  git clone https://github.com/surface1999/surface1989-store.git
2. In eclipse:
  File -> Import -> Import Existing Maven Project
  At Root Directory, choose source code folder just cloned -> Finish 
3. Edit username and password in application.properties file.
4. Run as Spring Boot Application
5. In browser: input url http://localhost:1989/login to run web application

*Deltail: The web have functions:
-Register
-Login
-Logout
- CRUD product, validation
-Paging products
- Search product follow name, manufacture and paging result.
-Display list user
