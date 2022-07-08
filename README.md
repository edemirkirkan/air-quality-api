# Air Quality Restful Api

**Retrieve the historical air pollution data by specified city and dates.**

# Documentations for the api can be found at
- https://edemirkirkan.github.io/air-quality-api/
- http://localhost:8080/swagger-ui/index.html

***Note: You can try endpoints from SwaggerUI either by using localhost or Docker***

# To run application with Docker
    1. install Docker, and setup any Docker Postgres image you want
    2. update the name of the Postgres image in 'docker-compose.yml' file
    3. update the application.properties file
           3.1 comment localhost:5432 datasource credentials  
           3.2 uncomment postgredb:5432 with its credentials
    4. finally, run following commands at the project folder CLI, respectively
           4.1. docker build . -t air-quality-api:1.0
           4.2. docker-compose up
           
# SwaggerUI

### App Routes
![Alt text](./src/main/resources/static/images/full_page.png?raw=true "full_page")

### Register
![Alt text](./src/main/resources/static/images/register.png?raw=true "register")

### Login
![Alt text](./src/main/resources/static/images/login_1.png?raw=true "login_1")
![Alt text](./src/main/resources/static/images/login_2.png?raw=true "login_2")

### Change Password
![Alt text](./src/main/resources/static/images/password.png?raw=true "password")

### Retrieve Pollutant Data
![Alt text](./src/main/resources/static/images/pollution_1.png?raw=true "pollution_1")

![Alt text](./src/main/resources/static/images/pollution_2.png?raw=true "pollution_2")
 
![Alt text](./src/main/resources/static/images/pollution_3.png?raw=true "pollution_3")

### Delete Pollutant Data
![Alt text](./src/main/resources/static/images/delete.png?raw=true "delete")
