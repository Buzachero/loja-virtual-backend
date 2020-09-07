# Sistema Web de Loja Virtual - Backend
Sistema backend que gerencia uma loja virtual. Possui funcionalidades como:
- Cadastro de clientes;
- Cadastro de categorias;
- Registro de pedidos;
- Entre outras.

## Tecnologias utilizadas
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [JSON Web Token (JWT)](https://jwt.io/)
- [MySQL](https://www.mysql.com/) / [H2](https://www.h2database.com/html/main.html)
- [Amazon S3](https://aws.amazon.com/pt/s3/)
- [Swagger](https://swagger.io/)

## Requerimentos
- JDK 1.8
- Maven
- Cadastro na [AWS](https://aws.amazon.com/pt/) (para utilizar [Amazon S3](https://aws.amazon.com/pt/s3/))
- Conta de e-mail (para testes de envio de emails)

## Parâmetros do Projeto

A configuracão da aplicação pode ser realizada em quatro arquivos:
1. *application.properties* 
2. *application-dev.properties* 
3. *application-prod.properties* 
4. *application-test.properties*

Na execução com **application-dev.properties** ou **application-test.properties**, o banco de dados já é populado com produtos, categorias, pedidos, clientes e seus dados.

O banco utilizado na execução com **application-test.properties** é o [H2](https://www.h2database.com/html/main.html), já com **application-dev.properties** é o [MySQL](https://www.mysql.com/)

Na execução com **application-prod.properties** não há dados iniciais populados.

Os detalhes de conexão com [Amazon S3](https://aws.amazon.com/pt/s3/), onde são armazenadas as imagens dos produtos, devem ser configurados em **application.properties**, como mostrado abaixo:
```
aws.access_key_id=<Access Key ID>	  
aws.secret_access_key=<Secret Access Key>	  
s3.bucket=<Bucket Name>
s3.region=<Region>
```
A configuração do e-mail para onde serão enviadas as notificações do sistema pode ser feita em **application-dev.properties** ou **application-prod.properties**, na seguinte propriedade:
```
spring.mail.*
```
