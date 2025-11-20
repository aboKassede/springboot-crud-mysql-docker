Database Setup Instructions
========================

1. Build the MySQL Docker image:
   docker build -t mysql-db .

2. Run the container:
   docker run -d --name mysql-container -p 3306:3306 mysql-db

3. Connect to database:
   Host: localhost
   Port: 3306
   Database: persons
   Username: mahmod
   Password: user123

4. The database will be initialized with sample person data automatically.