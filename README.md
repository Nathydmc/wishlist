# Projeto: Wishlist
## 1. Sobre
Projeto criado para gerenciar a Wishlist de clientes de um ecommerce.

## 2. Funcionalidades
* Adicionar produto a wishlist de um cliente
* Remover produto da wishlist de um cliente
* Verificar se um produto está na wishlist de um cliente
* Listar os produtos da wishlist de um cliente

## 3. Dependências (local)
1. Java 11
3. Git
2. Docker

## 4. Como rodar (local)
1. Clone o projeto através da URL: https://github.com/Nathydmc/wishlist.git
2. Rode o comando: `docker run -v ~/docker --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=developer -e MONGO_INITDB_ROOT_PASSWORD=developer mongo`
3. Acesse a pasta onde encontra-se o projeto através do comando: `cd wishlist`
4. Rode o comando: `gradle clean build`
5. Inicie a aplicação através do comando: `gradle bootRun`

## 5. Rodar testes
* Rode o comando: `gradle test`

## 6. Endpoints
Ao executar o projeto, os endpoints e seus detalhes podem ser acessados através da URL: http://localhost:8080/swagger-ui.html#/
