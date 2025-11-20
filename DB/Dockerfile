FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=persons
ENV MYSQL_USER=mahmod
ENV MYSQL_PASSWORD=user123

COPY sqlinit.sql /docker-entrypoint-initdb.d/

EXPOSE 3306