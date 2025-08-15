# FórumHub API

### Introdução

Este projeto implementa uma API REST para um fórum, focando especificamente na gestão de tópicos. A API permite que os usuários realizem operações completas de CRUD (Create, Read, Update, Delete) em tópicos e suas respostas, além de gerenciar usuários e cursos.

A arquitetura do projeto foi desenvolvida com base nas melhores práticas do Spring, utilizando Spring Data JPA para persistência de dados em um banco de dados relacional e Spring Security para autenticação e autorização via JWT.

### Funcionalidades

A API oferece os seguintes endpoints para gerenciamento de tópicos e outros recursos:

* **Tópicos (CRUD)**
    * `POST /topicos`: Cria um novo tópico.
    * `GET /topicos`: Lista todos os tópicos existentes com suporte a paginação.
    * `GET /topicos/recentes`: Lista os 10 tópicos mais recentes.
    * `GET /topicos/{id}`: Exibe detalhes de um tópico específico.
    * `PUT /topicos/{id}`: Atualiza um tópico existente.
    * `PATCH /topicos/{id}/status`: Atualiza o status de um tópico.
    * `DELETE /topicos/{id}`: Remove um tópico.

* **Autenticação e Autorização**
    * `POST /login`: Autentica um usuário e retorna um token JWT para acesso protegido.

* **Respostas**
    * `POST /respostas/{topicoId}`: Cria uma nova resposta para um tópico específico.

* **Outros Recursos**
    * `GET /cursos`: Lista todos os cursos.
    * `POST /cursos`: Cadastra um novo curso.
    * `DELETE /cursos/{id}`: Remove um curso.
    * `GET /usuarios`: Lista todos os usuários.
    * `POST /usuarios`: Cadastra um novo usuário.
    * `DELETE /usuarios/{id}`: Remove um usuário.

### Tecnologias Utilizadas

O projeto foi construído com as seguintes tecnologias e frameworks:

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.5.4
* **Persistência:** Spring Data JPA
* **Banco de Dados:** MySQL com `mysql-connector-j`
* **Migrações:** Flyway
* **Segurança:** Spring Security com JWT (JSON Web Token)
* **Validação:** Jakarta Validation com anotações de validação (`@Valid`, `@NotBlank`, etc.)
* **Documentação:** Springdoc OpenAPI (Swagger UI)
* **Outros:** Lombok para simplificação do código

### Requisitos do Sistema

Para executar a aplicação, você precisa ter o seguinte instalado:

* **Java Development Kit (JDK) 21**
* **Apache Maven**
* **Servidor MySQL** (versão 8.0)

### Configuração do Ambiente

1.  **Clone o repositório:**
    `git clone https://github.com/seu-usuario/forumhub-challenge
    cd forumhub`

3.  **Configuração do Banco de Dados:**
    Ajuste as credenciais de acesso ao seu banco de dados no arquivo `src/main/resources/application.properties`. O nome do banco de dados deve ser `forumhub`.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
    spring.datasource.username=${DATASOURCE_USERNAME}
    spring.datasource.password=${DATASOURCE_PASSWORD}
    ```
    O Flyway irá criar as tabelas e o schema do banco de dados automaticamente na primeira execução.

4.  **Configuração da Chave JWT:**
    Defina a sua chave secreta para a geração de tokens JWT no mesmo arquivo:

    ```properties
    api.security.token.secret=${JWT_SECRET:123456}
    ```

### Como Executar a Aplicação

Navegue até o diretório raiz do projeto e execute o comando Maven:

`./mvnw spring-boot:run`

### Documentação da API

A documentação interativa da API está disponível no Swagger UI. Após iniciar a aplicação, acesse a seguinte URL no seu navegador:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
