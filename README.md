#  Alerta Cidadão — Sistema de Monitoramento e Alerta de Enchentes

## 1. Sobre o projeto

O **Alerta Cidadão** é uma solução de monitoramento de condições relacionadas a enchentes, desenvolvida com Java e Spring Boot.

O objetivo é reunir informações de monitoramento, organizar leituras de sensores e estações, identificar situações de risco e disponibilizar informações por meio de uma API REST.

A solução também incorpora recursos de inteligência artificial para fornecer orientações relacionadas aos alertas e utiliza processamento assíncrono para trabalhar com eventos do sistema.

### Problema

Enchentes e alagamentos podem causar danos materiais, interromper serviços e colocar vidas em risco. A falta de centralização de dados e de comunicação rápida dificulta o acompanhamento dessas ocorrências.

### Nossa proposta

Desenvolver uma plataforma backend capaz de:

- Centralizar informações de pontos de monitoramento.
- Registrar e consultar leituras relacionadas a enchentes.
- Integrar dados externos do CEMADEN.
- Gerenciar alertas e suas classificações de risco.
- Disponibilizar notificações.
- Registrar auditoria das operações relacionadas aos alertas.
- Oferecer orientações com inteligência artificial.
- Controlar o acesso por autenticação e autorização.
- Processar eventos de forma assíncrona utilizando Apache Kafka.

### Solução desenvolvida

O projeto utiliza uma arquitetura organizada em camadas, separando responsabilidades entre controllers, services, repositories, models, DTOs e componentes de integração.

A aplicação funciona como um backend centralizado, disponibilizando os recursos por endpoints HTTP e documentando as operações por meio do Swagger/OpenAPI.

---

## 2. Tecnologias utilizadas

| Tecnologia | Responsabilidade no projeto |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot | Estrutura e inicialização da aplicação |
| Spring Web | Criação da API REST |
| Spring Data JPA | Persistência e comunicação com o banco |
| Hibernate | Mapeamento objeto-relacional |
| Oracle Database | Armazenamento dos dados |
| Maven | Gerenciamento de dependências e build |
| Spring Security | Autenticação e autorização |
| JWT | Autenticação baseada em tokens |
| BCrypt | Hash das senhas |
| Spring AI / Google Gemini | Geração de orientações com IA |
| Apache Kafka | Mensageria e processamento assíncrono |
| Docker Compose | Inicialização do ambiente Kafka |
| Swagger / OpenAPI | Documentação e testes da API |
| Bean Validation | Validação dos dados recebidos |

### Java 21

Java é a linguagem utilizada para desenvolver toda a lógica de negócio do sistema.

O Java permite estruturar o projeto utilizando orientação a objetos, encapsulamento, classes, interfaces, serviços e componentes.

### Spring Boot

O Spring Boot é responsável por inicializar e organizar a aplicação.

Ele facilita a configuração dos componentes, a criação dos endpoints REST, a integração com o banco de dados, a segurança e a comunicação entre os serviços.

### Spring Web

É utilizado para disponibilizar os endpoints HTTP.

Os controllers recebem as requisições dos clientes, encaminham as operações para os serviços e retornam as respostas.

### Spring Data JPA e Hibernate

O Spring Data JPA facilita as operações de persistência.

O Hibernate implementa o mapeamento objeto-relacional (ORM), permitindo representar tabelas do banco por meio de classes Java.

Por exemplo, a entidade `Alerta` representa os dados de um alerta armazenado no banco.

Os repositories utilizam interfaces do Spring Data JPA para executar operações como:

- Salvar registros.
- Consultar registros.
- Atualizar informações.
- Excluir registros.
- Consultar dados por critérios específicos.

### Oracle Database

O Oracle é o banco de dados relacional utilizado para armazenar as informações do sistema.

Entre os dados persistidos estão:

- Usuários.
- Alertas.
- Leituras de enchentes.
- Pontos de monitoramento.
- Notificações.
- Auditoria dos alertas.

A conexão é realizada por JDBC, utilizando o driver Oracle.

### Maven

O Maven gerencia as dependências e a compilação do projeto.

O arquivo `pom.xml` contém as bibliotecas necessárias para executar a aplicação.

O projeto também possui o Maven Wrapper (`mvnw` e `mvnw.cmd`), permitindo executar comandos Maven sem depender de uma instalação global do Maven.

---

## 3. Arquitetura e organização do projeto

A aplicação segue uma arquitetura em camadas.

Cada pacote possui uma responsabilidade específica.

```text
alerta-cidadao/
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/alertacidadao/alerta_cidadao/
│   │   │       │
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── event/
│   │   │       ├── exception/
│   │   │       ├── integration/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       │
│   │   │       └── AlertaCidadaoApplication.java
│   │   │
│   │   └── resources/
│   │       └── application-example.properties
│   │
│   └── test/
│       └── java/
│
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

### 3.1. Pacote config

Contém configurações de componentes e integrações da aplicação.

**KafkaConfig.java**

Responsável pelas configurações relacionadas à comunicação com o Kafka.

**PasswordConfig.java**

Disponibiliza o componente `PasswordEncoder`, utilizando BCrypt para gerar hashes seguros das senhas.

**SwaggerConfig.java**

Configura a documentação OpenAPI e a autenticação Bearer JWT no Swagger.

### 3.2. Pacote controller

É a camada responsável por receber as requisições HTTP.

Os controllers disponíveis são:

| Controller | Responsabilidade |
|---|---|
| AlertaController | Operações relacionadas aos alertas |
| AssistenteIAController | Orientações geradas por inteligência artificial |
| AuditoriaAlertaController | Consulta de registros de auditoria |
| AuthController | Cadastro e autenticação |
| LeituraEnchenteController | Operações com leituras de enchentes |
| NotificacaoController | Gerenciamento de notificações |
| PontoMonitoramentoController | Gerenciamento dos pontos de monitoramento |
| UsuarioController | Operações relacionadas aos usuários |

Os controllers não devem concentrar as regras de negócio. Essas responsabilidades ficam principalmente nos services.

### 3.3. Pacote dto

DTO significa Data Transfer Object.

São objetos utilizados para transportar informações entre a API e seus consumidores.

Os DTOs permitem controlar quais informações entram e saem da aplicação, sem expor diretamente todas as propriedades das entidades do banco.

Exemplos:

- `CadastroRequestDTO`: dados necessários para cadastrar um usuário.
- `LoginRequestDTO`: credenciais utilizadas na autenticação.
- `AuthResponseDTO`: resposta da autenticação, incluindo o token.
- `AlertaRequestDTO`: dados recebidos para operações com alertas.
- `AlertaResponseDTO`: informações devolvidas sobre alertas.
- `LeituraEnchenteRequestDTO`: dados recebidos para registrar leituras.
- `LeituraEnchenteResponseDTO`: informações retornadas sobre leituras.

Também existem DTOs específicos para notificações, usuários, pontos de monitoramento, auditoria e orientações de IA.

### 3.4. Pacote event

Contém os objetos que representam eventos utilizados na comunicação assíncrona.

**AlertaCriadoEvent.java**

Representa um evento relacionado à criação de um alerta.

Esse objeto transporta informações do evento para que outros componentes possam processá-las.

### 3.5. Pacote exception

Centraliza o tratamento de erros.

**GlobalExceptionHandler.java**

Utiliza o mecanismo de tratamento global de exceções do Spring para padronizar respostas de erro.

**RecursoNaoEncontradoException.java**

Representa situações em que um recurso solicitado não foi encontrado.

**RegraNegocioException.java**

Representa erros relacionados às regras de negócio.

Essa organização evita espalhar tratamentos de exceções diferentes por todos os controllers.

### 3.6. Pacote integration

Concentra a integração com serviços externos.

**CemadenClient.java**

Responsável pela comunicação com a API externa do CEMADEN.

**CemadenController.java**

Disponibiliza as operações de integração por meio da API REST do projeto.

**CemadenService.java**

Concentra a lógica relacionada à consulta e utilização dos dados externos.

**CemadenSincronizacaoService.java**

Responsável pelo processo de sincronização das informações externas com os dados persistidos na aplicação.

### 3.7. Pacote model

Contém as entidades JPA que representam os dados persistidos no Oracle.

| Entidade | Responsabilidade |
|---|---|
| Alerta | Representa os alertas gerados pelo sistema |
| AuditoriaAlerta | Registra informações de auditoria dos alertas |
| LeituraEnchente | Representa as leituras relacionadas ao monitoramento |
| Notificacao | Representa as notificações |
| PontoMonitoramento | Representa os pontos e estações monitoradas |
| Usuario | Representa os usuários cadastrados |

As classes utilizam anotações JPA, como `@Entity`, `@Table`, `@Id` e `@Column`, para realizar o mapeamento entre Java e Oracle.

### 3.8. Pacote repository

É a camada responsável pelo acesso aos dados.

Os repositories utilizam `JpaRepository` para disponibilizar operações de persistência.

Existem repositories para:

- Alertas.
- Auditoria.
- Leituras de enchentes.
- Notificações.
- Pontos de monitoramento.
- Usuários.

Essa separação evita que os controllers precisem executar consultas diretamente no banco.

### 3.9. Pacote security

Contém os componentes de autenticação e autorização.

**JwtService.java**

Responsável pela geração dos tokens JWT.

**SecurityConfig.java**

Define as regras de segurança da aplicação, a configuração do JWT e as permissões de acesso aos endpoints.

### 3.10. Pacote service

É a camada que concentra as regras de negócio.

| Classe | Responsabilidade |
|---|---|
| AlertaService | Regras de negócio dos alertas |
| AssistenteIAService | Integração com o modelo de inteligência artificial |
| AuditoriaAlertaService | Registro e consulta de auditoria |
| AuthService | Cadastro e autenticação de usuários |
| LeituraEnchenteService | Operações de negócio das leituras |
| NotificacaoService | Operações relacionadas às notificações |
| PontoMonitoramentoService | Gerenciamento dos pontos de monitoramento |
| UsuarioService | Regras de negócio dos usuários |
| AlertaProducer | Publicação de eventos no Kafka |
| AlertaConsumer | Consumo e processamento dos eventos Kafka |

### 3.11. Classe principal

`AlertaCidadaoApplication.java`

É o ponto de entrada da aplicação Spring Boot.

A anotação `@SpringBootApplication` habilita a configuração automática e inicializa o contexto da aplicação.

---

## 4. Banco de dados Oracle

O Oracle Database é utilizado como banco relacional principal.

### Configuração da conexão

A aplicação utiliza uma URL JDBC no seguinte formato:

```properties
spring.datasource.url=jdbc:oracle:thin:@//oracle.fiap.com.br:1521/orcl
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

Os valores de usuário e senha devem ser fornecidos pelo responsável pelo banco.

### Configurações JPA

```properties
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
```

**O que cada propriedade faz?**

- `show-sql`: permite visualizar as consultas SQL executadas.
- `ddl-auto=update`: solicita ao Hibernate que atualize o esquema conforme o mapeamento das entidades.
- `database-platform`: informa o dialeto utilizado para o Oracle.

> Em ambientes de produção, recomenda-se utilizar migrations controladas e evitar alterações automáticas de estrutura do banco.

### Como configurar o banco

1. Obtenha acesso ao Oracle utilizado pela equipe.
2. Tenha em mãos o endereço JDBC, usuário e senha.
3. Configure as variáveis de ambiente correspondentes.
4. Confira se o computador consegue alcançar o servidor Oracle.
5. Inicie a aplicação e verifique os logs de conexão.

O projeto utiliza o banco Oracle externo. O Docker Compose disponibilizado neste repositório não cria um banco Oracle: ele inicializa o Kafka.

---

## 5. Integração com o CEMADEN

O projeto utiliza dados externos do CEMADEN para ampliar as informações disponíveis sobre monitoramento.

CEMADEN significa Centro Nacional de Monitoramento e Alertas de Desastres Naturais.

Site institucional:

https://www.gov.br/cemaden/pt-br

A integração foi organizada em componentes próprios para separar a comunicação externa das regras de negócio.

### Como funciona

1. A aplicação solicita informações à API configurada do CEMADEN.
2. O cliente de integração recebe os dados externos.
3. Os serviços tratam as informações recebidas.
4. A sincronização identifica os pontos e as leituras correspondentes.
5. Os registros válidos são persistidos no Oracle.
6. As informações ficam disponíveis para consulta e utilização pelo sistema.

A integração trabalha com informações de estações e acumulados de precipitação, incluindo períodos como:

- 1 hora.
- 3 horas.
- 6 horas.
- 24 horas.

A disponibilidade dos campos depende dos dados retornados pela fonte externa.

### Configuração

As propriedades utilizadas são:

```properties
cemaden.api.base-url=${CEMADEN_API_URL}
cemaden.api.token=${CEMADEN_API_TOKEN}
cemaden.api.codibge=${CEMADEN_CODIBGE}
cemaden.api.uf=${CEMADEN_UF}
cemaden.api.rede=${CEMADEN_REDE}
```

O endereço base e os parâmetros devem corresponder ao serviço e à configuração de acesso disponibilizados para a integração.

**Importante:** a URL completa do endpoint externo deve ser conferida na configuração efetivamente utilizada em `CemadenClient.java` e no ambiente da equipe. Não se deve substituir o endereço por uma URL presumida.

### Sincronização

A sincronização é disponibilizada pela aplicação por meio do controller de integração.

Endpoint utilizado durante os testes:

```http
POST /integracao/cemaden/sincronizar-leituras
```

A operação permite executar o processo de atualização das leituras externas.

---

## 6. Inteligência artificial — Google Gemini

O projeto utiliza Spring AI com o modelo Gemini, da Google, para fornecer orientações relacionadas aos alertas.

A inteligência artificial funciona como um recurso complementar ao sistema de monitoramento.

### Objetivo

A IA pode utilizar o contexto de um alerta para produzir uma orientação textual que ajude a interpretar a situação e compreender possíveis cuidados.

### Componentes

**AssistenteIAController.java**

Disponibiliza o endpoint responsável por solicitar uma orientação.

**AssistenteIAService.java**

Concentra a comunicação com o modelo de IA e a lógica de geração da resposta.

**OrientacaoIAResponseDTO.java**

Transporta a resposta produzida pelo assistente.

### Como funciona

1. O cliente solicita uma orientação para determinado alerta.
2. O controller recebe a requisição.
3. O serviço recupera as informações necessárias do alerta.
4. O contexto é encaminhado ao modelo Gemini por meio do Spring AI.
5. A resposta é processada e devolvida pela API.

### Configuração

A propriedade utilizada para a chave é:

```properties
spring.ai.google.genai.api-key=${GOOGLE_API_KEY}
```

O modelo pode ser configurado por:

```properties
spring.ai.google.genai.chat.model=gemini-2.5-flash
```

A chave de acesso deve ser obtida no Google AI Studio:

https://aistudio.google.com/

Documentação do Spring AI:

https://docs.spring.io/spring-ai/reference/

Documentação da API Gemini:

https://ai.google.dev/gemini-api/docs

### Endpoint do assistente

```http
GET /assistente/alertas/{id}/orientacao
```

Substitua `{id}` pelo identificador do alerta.

> A IA é um recurso de apoio. Suas respostas não substituem comunicados oficiais, orientações da Defesa Civil ou decisões das autoridades responsáveis.

---

## 7. Apache Kafka e processamento assíncrono

O Apache Kafka é utilizado para comunicação baseada em eventos.

Sua função no projeto é permitir que determinadas operações sejam publicadas como eventos e processadas por consumidores, sem exigir que todo o processamento aconteça dentro da mesma requisição HTTP.

### O que é mensageria?

Mensageria é um modelo de comunicação em que um componente publica uma mensagem e outro componente pode recebê-la e processá-la.

Isso permite reduzir o acoplamento entre partes do sistema.

### Como o Kafka funciona no projeto

O fluxo principal é:

1. Uma operação do sistema gera um evento relacionado a um alerta.
2. O `AlertaProducer` publica o evento no Kafka.
3. O Kafka armazena a mensagem no tópico correspondente.
4. O `AlertaConsumer` recebe a mensagem.
5. O consumidor executa o processamento associado ao evento.

### Componentes

**AlertaCriadoEvent.java**

Representa os dados do evento.

**AlertaProducer.java**

Publica os eventos no Kafka.

**AlertaConsumer.java**

Recebe e processa os eventos publicados.

**KafkaConfig.java**

Centraliza configurações da integração.

### Docker e Docker Compose

O Docker permite executar aplicações isoladas em containers.

O Docker Compose permite configurar e inicializar vários serviços por meio de um único arquivo YAML.

Neste projeto, o arquivo `docker-compose.yml` configura o Kafka.

O serviço utiliza a imagem:

```yaml
image: apache/kafka:3.9.1
```

A configuração utiliza o modo KRaft, permitindo executar o Kafka sem depender de um servidor ZooKeeper separado.

A porta utilizada é:

```yaml
ports:
  - "9092:9092"
```

O endereço utilizado pela aplicação é:

```properties
spring.kafka.bootstrap-servers=localhost:9092
```

### Como iniciar o Kafka

É necessário ter Docker Desktop instalado e em execução.

Na raiz do projeto, execute:

```powershell
docker compose up -d
```

Para conferir os containers:

```powershell
docker compose ps
```

Para visualizar os logs:

```powershell
docker compose logs -f kafka
```

Para parar o serviço:

```powershell
docker compose down
```

O comando `docker compose down` encerra e remove o container e a rede criada pelo Compose. Ele não substitui uma estratégia de persistência de dados do Kafka.

---

## 8. Segurança da aplicação

A segurança é uma parte importante do Alerta Cidadão, pois o sistema possui operações que não devem estar disponíveis para qualquer usuário.

O projeto utiliza Spring Security e autenticação baseada em JWT.

### 8.1. Cadastro

O cadastro é disponibilizado por:

```http
POST /auth/cadastro
```

O usuário informa os dados necessários para criação da conta.

O sistema realiza o processamento da senha antes de persistir o usuário.

### 8.2. Hash de senhas com BCrypt

O projeto utiliza `BCryptPasswordEncoder`.

Uma senha não deve ser armazenada diretamente no banco de dados.

Em vez disso, o BCrypt gera um hash da senha original.

Exemplo conceitual:

```text
Senha informada:
MinhaSenhaSegura

Valor armazenado:
$2a$10$...
```

O valor armazenado é um hash, não a senha original.

Durante o login, o sistema compara a senha informada com o hash armazenado utilizando o `PasswordEncoder`.

Não se deve descriptografar ou recuperar a senha original.

### 8.3. Login

O endpoint de autenticação é:

```http
POST /auth/login
```

O usuário envia email e senha.

Quando as credenciais são válidas, o sistema gera um token JWT.

A resposta de autenticação contém informações como:

- Token.
- Tipo de autenticação.
- Nome do usuário.
- Papel do usuário.

### 8.4. JWT

JWT significa JSON Web Token.

É um formato de token utilizado para transportar informações de autenticação.

No projeto, o token é assinado com o algoritmo HS256.

O token possui validade de duas horas.

Entre as informações utilizadas estão:

- Emissor.
- Data de emissão.
- Data de expiração.
- Email do usuário.
- Nome.
- Papel.
- Autoridades.

O `JwtService.java` é responsável pela geração do token.

### 8.5. Como utilizar o token

Depois de realizar o login:

1. Copie o token retornado.
2. Abra o Swagger.
3. Clique em `Authorize`.
4. Informe o token no campo de autenticação Bearer.
5. Execute as operações protegidas.

Nas requisições HTTP, o formato utilizado é:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

### 8.6. Papéis e permissões

O sistema utiliza os seguintes papéis:

| Papel | Descrição |
|---|---|
| ROLE_USER | Usuário comum |
| ROLE_ADMIN | Administrador |

As permissões são controladas pelo Spring Security.

A configuração atual restringe operações de criação, atualização e exclusão de alertas aos administradores.

Exemplo:

```java
.requestMatchers(HttpMethod.POST, "/alertas/**").hasRole("ADMIN")
.requestMatchers(HttpMethod.PUT, "/alertas/**").hasRole("ADMIN")
.requestMatchers(HttpMethod.DELETE, "/alertas/**").hasRole("ADMIN")
```

O Spring Security utiliza `hasRole("ADMIN")` para verificar a autoridade `ROLE_ADMIN`.

As rotas de autenticação são públicas:

```java
.requestMatchers("/auth/**").permitAll()
```

A documentação Swagger também pode ser acessada sem autenticação:

```java
.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
```

As demais rotas exigem autenticação, conforme as regras configuradas.

### 8.7. Stateless

A aplicação utiliza:

```java
SessionCreationPolicy.STATELESS
```

Isso significa que o servidor não mantém uma sessão HTTP tradicional para cada usuário autenticado.

A autenticação é realizada por meio do token enviado nas requisições.

### 8.8. CSRF

A configuração atual desabilita CSRF:

```java
.csrf(csrf -> csrf.disable())
```

Essa escolha está associada ao modelo de API stateless autenticada por Bearer JWT.

Caso a aplicação passe a utilizar autenticação baseada em cookies, a estratégia de proteção CSRF deverá ser reavaliada.

### 8.9. Proteção de segredos

As credenciais não devem ser publicadas no GitHub.

O arquivo local:

```text
src/main/resources/application.properties
```

está protegido pelo `.gitignore`.

O repositório contém o arquivo de exemplo:

```text
src/main/resources/application-example.properties
```

As chaves e senhas reais devem ser fornecidas por variáveis de ambiente.

---

## 9. Configuração do ambiente

### Pré-requisitos

Antes de executar o projeto, instale:

- Java JDK 21.
- Docker Desktop.
- Git.
- Acesso ao Oracle Database.
- Credenciais do serviço CEMADEN utilizado pela integração.
- Chave de API do Google Gemini.

O projeto utiliza o Maven Wrapper, portanto não é necessário instalar Maven globalmente.

### 9.1. Clonar o repositório

```powershell
git clone https://github.com/GS-CitizenTech/GS-Java.git
```

Entre na pasta do projeto:

```powershell
cd GS-Java
```

> Se o projeto estiver dentro de uma subpasta do repositório, entre nela antes de executar os comandos Maven.

### 9.2. Verificar o Java

```powershell
java -version
```

O ambiente deve utilizar Java 21.

### 9.3. Configurar o arquivo de propriedades

O projeto possui um modelo de configuração:

```text
src/main/resources/application-example.properties
```

Para criar sua configuração local:

```powershell
Copy-Item src/main/resources/application-example.properties src/main/resources/application.properties
```

Depois, abra `application.properties` e informe as configurações reais.

**Nunca publique esse arquivo com credenciais no GitHub.**

### 9.4. Variáveis de ambiente

As propriedades sensíveis devem ser configuradas no ambiente de execução.

Exemplo no PowerShell:

```powershell
$env:DB_URL="jdbc:oracle:thin:@//oracle.fiap.com.br:1521/orcl"
$env:DB_USERNAME="SEU_USUARIO_ORACLE"
$env:DB_PASSWORD="SUA_SENHA_ORACLE"

$env:CEMADEN_API_URL="URL_BASE_CONFIGURADA"
$env:CEMADEN_API_TOKEN="SEU_TOKEN_CEMADEN"
$env:CEMADEN_CODIBGE="CODIGO_IBGE"
$env:CEMADEN_UF="SP"
$env:CEMADEN_REDE="REDE_CONFIGURADA"

$env:GOOGLE_API_KEY="SUA_CHAVE_GOOGLE"
$env:JWT_SECRET="SUA_CHAVE_SECRETA_FORTE"
```

Essas variáveis são exemplos para a sessão atual do PowerShell.

Os valores devem corresponder às credenciais e aos parâmetros reais disponibilizados para o ambiente.

A chave JWT deve ser forte, aleatória e mantida em segredo.

### 9.5. Inicializar o Kafka

Na raiz do projeto:

```powershell
docker compose up -d
```

Confira:

```powershell
docker compose ps
```

### 9.6. Compilar o projeto

```powershell
.\mvnw.cmd clean compile
```

### 9.7. Executar a aplicação

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

Para interromper a aplicação, pressione:

```text
CTRL + C
```

---

## 10. Documentação e testes da API

O projeto utiliza Swagger/OpenAPI para documentar e testar os endpoints.

Com a aplicação em execução, acesse:

http://localhost:8080/swagger-ui/index.html

O Swagger permite:

- Visualizar os endpoints.
- Consultar os métodos HTTP.
- Conferir parâmetros e corpos das requisições.
- Executar operações.
- Visualizar códigos de resposta.
- Autorizar requisições utilizando JWT.

### Fluxo básico de autenticação

**1. Cadastrar usuário**

```http
POST /auth/cadastro
```

Exemplo de corpo:

```json
{
  "nome": "Usuario Teste",
  "email": "usuario@exemplo.com",
  "senha": "SenhaSegura123"
}
```

**2. Fazer login**

```http
POST /auth/login
```

Exemplo:

```json
{
  "email": "usuario@exemplo.com",
  "senha": "SenhaSegura123"
}
```

**3. Utilizar o JWT**

Copie o token recebido e informe no Swagger por meio do botão `Authorize`.

**4. Testar as rotas protegidas**

Execute as operações disponíveis para o papel do usuário autenticado.

### Testes de segurança realizados

Durante os testes de autenticação, foram observados:

| Operação | Resultado |
|---|---|
| Cadastro de usuário | HTTP 201 |
| Login com credenciais válidas | HTTP 200 |
| Usuário ROLE_USER tentando criar alerta | HTTP 403 |

O HTTP 403 no último teste é esperado: significa que o usuário foi autenticado, mas não possui autorização para executar aquela operação.

Outros testes, como login com senha incorreta, cadastro duplicado e consultas autenticadas, devem ser conferidos no ambiente antes de serem considerados aprovados.

---

## 11. Endpoints principais

Os endpoints abaixo foram utilizados ou identificados durante o desenvolvimento:

| Método | Endpoint | Finalidade |
|---|---|---|
| POST | `/auth/cadastro` | Cadastro de usuário |
| POST | `/auth/login` | Autenticação |
| POST | `/alertas` | Criação de alerta |
| GET | `/assistente/alertas/{id}/orientacao` | Orientação de IA |
| POST | `/integracao/cemaden/sincronizar-leituras` | Sincronização do CEMADEN |

A API também possui controllers específicos para:

- Alertas.
- Auditoria.
- Leituras de enchentes.
- Notificações.
- Pontos de monitoramento.
- Usuários.

Para visualizar a lista completa, incluindo os métodos, parâmetros, respostas e códigos HTTP efetivamente implementados, consulte o Swagger.

**Swagger:**

http://localhost:8080/swagger-ui/index.html

---

## 12. Fluxo geral da aplicação

O fluxo simplificado do sistema pode ser representado assim:

```text
                CLIENTE / SWAGGER
                       |
                       v
                API REST SPRING
                       |
                       v
                   CONTROLLERS
                       |
                       v
                    SERVICES
                  /    |     \
                 /     |      \
                v      v       v
           REPOSITORY  IA    KAFKA
                |      |       |
                v      v       v
             ORACLE  GEMINI  CONSUMER
                |
                v
          DADOS PERSISTIDOS
```

### Exemplo: fluxo de monitoramento

1. O sistema solicita informações à integração externa.
2. O CEMADEN fornece dados de monitoramento.
3. A camada de integração trata as informações.
4. Os serviços realizam o processamento.
5. As leituras são persistidas no Oracle.
6. Os dados podem ser utilizados nas operações relacionadas aos alertas.
7. Quando ocorre um evento de alerta, ele pode ser publicado no Kafka.
8. O consumidor recebe e processa o evento.
9. O assistente de IA pode gerar uma orientação contextualizada para um alerta.

---

## 13. Tratamento de erros e validações

O projeto possui componentes para validar dados e tratar exceções.

### Bean Validation

Permite validar os dados recebidos nos DTOs.

Exemplos de validações possíveis:

- Campos obrigatórios.
- Formato de email.
- Tamanho mínimo de senha.
- Limites de caracteres.

### GlobalExceptionHandler

Centraliza o tratamento de exceções e permite devolver respostas HTTP mais organizadas.

Isso facilita o entendimento dos erros pelos clientes da API.

### Exceções de negócio

O projeto possui exceções específicas para:

- Recursos não encontrados.
- Violações de regras de negócio.

---

## 14. Limitações e escopo da entrega

O escopo implementado concentra-se no backend.

As funcionalidades de API, persistência, autenticação, integração externa, IA e mensageria foram desenvolvidas no projeto.

**Integração mobile:** não foi implementada nesta entrega. A API foi estruturada para disponibilizar os recursos necessários a futuros consumidores, incluindo aplicações móveis.

**Banco de dados:** a aplicação depende do acesso ao Oracle e das credenciais correspondentes. O Docker Compose deste repositório inicializa o Kafka, não o Oracle.

**Serviços externos:** a integração com CEMADEN e Gemini depende da disponibilidade dos serviços, das configurações e das credenciais válidas.

**Inteligência artificial:** as orientações geradas são auxiliares e não devem ser consideradas substitutas de informações oficiais de emergência.

---

## 15. Estrutura de configuração e arquivos importantes

| Arquivo | Função |
|---|---|
| `pom.xml` | Dependências e configuração Maven |
| `docker-compose.yml` | Configuração do Kafka |
| `.gitignore` | Arquivos que não devem ser enviados ao Git |
| `.gitattributes` | Configurações de atributos dos arquivos |
| `mvnw` | Maven Wrapper para Linux/macOS |
| `mvnw.cmd` | Maven Wrapper para Windows |
| `application-example.properties` | Modelo de configuração |
| `application.properties` | Configuração local, não versionada |
| `AlertaCidadaoApplication.java` | Inicialização do Spring Boot |

---

## 16. Comandos úteis

### Compilar

```powershell
.\mvnw.cmd clean compile
```

### Executar testes automatizados

```powershell
.\mvnw.cmd test
```

### Executar a aplicação

```powershell
.\mvnw.cmd spring-boot:run
```

### Iniciar Kafka

```powershell
docker compose up -d
```

### Verificar containers

```powershell
docker compose ps
```

### Visualizar logs do Kafka

```powershell
docker compose logs -f kafka
```

### Parar Kafka

```powershell
docker compose down
```

### Verificar o estado do Git

```powershell
git status
```

---

## 17. Considerações finais

O Alerta Cidadão foi desenvolvido com o objetivo de demonstrar como diferentes tecnologias podem trabalhar juntas para construir uma solução de monitoramento e apoio à prevenção de riscos relacionados a enchentes.

A aplicação combina:

- Backend Java com Spring Boot.
- Persistência relacional com Oracle.
- Integração com dados externos do CEMADEN.
- Autenticação e autorização com Spring Security e JWT.
- Proteção de senhas com BCrypt.
- Inteligência artificial com Google Gemini e Spring AI.
- Processamento assíncrono com Apache Kafka.
- Ambiente de mensageria executado com Docker.
- Documentação de API com Swagger.

A separação em camadas permite organizar as responsabilidades, facilitar a manutenção e ampliar a solução futuramente.

**Repositório do projeto:**

https://github.com/GS-CitizenTech/GS-Java

**Documentação da API em ambiente local:**

http://localhost:8080/swagger-ui/index.html
