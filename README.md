
# Gestão de Votos
Desafio desenvolvido para integrar o time de desenvolvimento da [NTConsult](https://ntconsult.com.br/). Descrição do desafio e requisitos no arquivo **[DESAFIO.md](https://github.com/dayvidsonveiga/gestao-votos-ms/blob/main/DESAFIO.md)**

# Tecnologias utilizadas

- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)** 
- **[Hibernate](https://hibernate.org/orm/)**
- **[Lombok](https://projectlombok.org/)**
- **[Docker](https://www.docker.com/)**
- **[MySQL](https://www.mysql.com/)**
- **[RabbitMQ](https://www.rabbitmq.com/)**

# Requisitos para executar o projeto
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [JDK 11+](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)

# Como executar o projeto
- Clone o projeto.
```bash
  git clone https://github.com/dayvidsonveiga/gestao-votos-ms.git
```
- Abra um terminal na raiz do projeto e execute o comando abaixo para iniciar o banco de dados Mysql e o RabbitMQ no docker.
```bash
  cd docker && docker-compose up -d
```
- Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

- Execute o arquivo com a classe main GestaoVotosMsApplication.java

- Acesse a interface dos recursos do backend através do swagger usando o endereço local http://localhost:8080


# Rotas
## Associado Controller
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /v1/associado | Cria um novo associado |


## Pauta Controller
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /v1/pauta | Cria uma nova pauta |
| POST  |  /v1/pauta/votar | Votar em uma pauta |
| POST  |  /v1/pauta/abrir | Abrir sessão para votação da pauta |
| POST  |  /v1/pauta/consultar-resultado | Consultar resultado da votação da pauta |