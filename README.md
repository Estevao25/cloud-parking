# cloud-parking
Desafio de projeto Spring Boot para controlar um estacionamento de veículos.


## Run database
docker run --name parking-db -p 5432:5432 -e POSTGRES_DB=parking -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123 -d postgres:10-alpine
