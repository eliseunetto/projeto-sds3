# Semana Spring React

![Animação](https://user-images.githubusercontent.com/15930456/117609183-e21f0980-b135-11eb-8ebc-b448de3d25c2.gif)

## Sumário

- [Objetivo](#Objetivo)
- [Tecnologias e ferramentas](#Tecnologias-e-ferramentas)
- [Implantação e Deploy](#Implantação-e-Deploy)
- [Guia do BACKEND](#Guia-do-Backend)
  - [Passo 1: configuração de segurança](#Passo-1:-configuração-de-segurança)
  - [Passo 2: criar as entidades e o seed do banco](#Passo-2:-criar-as-entidades-e-o-seed-do-banco)
  - [Passo 3: Estruturar o projeto em camadas](#Passo-3:-Estruturar-o-projeto-em-camadas)
  - [Passo 4: Busca paginada de vendas](#Passo-4:-Busca-paginada-de-vendas)
  - [Passo 5: Buscas agrupadas (GROUP BY)](<#Passo-5:-Buscas-agrupadas-(GROUP-BY)>)
  - [Passo 6: Validação no Postgres local](#Passo-6:-Validação-no-Postgres-local)
  - [Passo 7: Implantação no Heroku](#Passo-7:-Implantação-no-Heroku)
- [Guia do FRONTEND](#Guia-do-Frontend)

## Objetivo

O projeto consiste em uma aplicação web para exibir dados de vendas e desempenho dos vendedores:

- Utilizando as Bibliotecas REact Router DOM e Axios.
- Criando Rotas das páginas.
- Navegação entre as páginas.
- Criando as requisições para o banco de dados.
- Realizando as integrações para os gráficos dinâmicos e tabela.
- Implantação no Heroku
- Finalizando com o deploy no Netlify.

&#128070; [Voltar ao Sumário](#Sumário)

## Tecnologias e ferramentas

As principais tecnologias e ferramentas utilizadas foram:

- Backend:

  - Java
  - Spring Boot

- Frontend:

  - React JS
  - TypeScript
  - Bootstrap
  - ApexCharts

- Ferramentas:
  - JDK 11
  - STS
  - Postman
  - Postgresql 12 e pgAdmin
  - Heroku CLI
  - NPM
  - VS Code
  - Git

&#128070; [Voltar ao Sumário](#Sumário)

## Implantação e Deploy

- Backend:
  - Heroku
- Frontend:
  - Netlify

&#128070; [Voltar ao Sumário](#Sumário)

# Guia do Backend

### Objetivos do projeto para este tópico

- Implementar o back end
  - Modelo de domínio
  - Estruturar o back end no padrão camadas
  - Consulta paginada de vendas
  - Consultas agrupadas para gráficos
  - Implantação na nuvem

# Checklist

## Passo 1: configuração de segurança

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().anyRequest().permitAll();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
```

&#128070; [Voltar ao Sumário](#Sumário)

## Passo 2: criar as entidades e o seed do banco

### Modelo conceitual

<img src="https://github.com/devsuperior/bds-assets/raw/main/sds/sds3-mc.png" alt="Image" title="Modelo conceitual" style="max-width:100%;">

### application.properties

```java
spring.jpa.open-in-view=false

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Seed do banco de dados

Ex:

```java
INSERT INTO tb_sellers(name) VALUES ('Logan');
INSERT INTO tb_sellers(name) VALUES ('Anakin');
INSERT INTO tb_sellers(name) VALUES ('BarryAllen');
INSERT INTO tb_sellers(name) VALUES ('Kal-El');
INSERT INTO tb_sellers(name) VALUES ('Padme');

INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (5,83,66,5501.0,'2021-04-01');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (5,113,78,8290.0,'2021-03-31');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (1,36,12,6096.0,'2021-03-30');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (1,42,22,3223.0,'2021-03-27');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (2,38,12,15017.0,'2021-03-26');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (5,88,52,20899.0,'2021-03-21');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (3,95,66,12383.0,'2021-03-17');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (3,117,78,10748.0,'2021-03-17');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (5,114,71,22274.0,'2021-03-15');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (3,127,96,19284.0,'2021-03-14');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (1,44,13,6871.0,'2021-03-09');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (3,49,25,9034.0,'2021-03-05');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (5,105,84,8114.0,'2021-03-04');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (3,94,65,21628.0,'2021-03-03');
INSERT INTO tb_sales(seller_id,visited,deals,amount,date) VALUES (2,97,46,21707.0,'2021-02-28');
```

&#128070; [Voltar ao Sumário](#Sumário)

## Passo 3: Estruturar o projeto em camadas

### Padrão de camadas adotado

<img src="https://github.com/devsuperior/bds-assets/raw/main/sds/camadas.png" alt="Image" title="Padrão camadas" style="max-width:100%;">

- Criar repositories
- Criar DTO's
- Criar service
- Criar controller

&#128070; [Voltar ao Sumário](#Sumário)

## Passo 4: Busca paginada de vendas

- Pageable
- page, size, sort
- Evitando interações repetidas ao banco de dados

&#128070; [Voltar ao Sumário](#Sumário)

## Passo 5: Buscas agrupadas (GROUP BY)

- Total de vendas por vendedor
- Taxa de sucesso por vendedor

&#128070; [Voltar ao Sumário](#Sumário)

## Passo 6: Validação no Postgres local

Criar três perfis de projeto: test, dev, prod
Gerar script SQL no perfil dev
Testar projeto no banco Postgres local

#### application.properties

```properties
spring.profiles.active=test

spring.jpa.open-in-view=false
```

#### application-dev.properties

```properties
#spring.jpa.properties.javax.persistence.schema-generation.create-source=metadata
#spring.jpa.properties.javax.persistence.schema-generation.scripts.action=create
#spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=create.sql
#spring.jpa.properties.hibernate.hbm2ddl.delimiter=;

spring.datasource.url=jdbc:postgresql://localhost:5432/nomeDoBanco
spring.datasource.username=postgres
spring.datasource.password=

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

#### application-prod.properties

```properties
spring.datasource.url=${DATABASE_URL}
```

#### application.properties

```properties
spring.profiles.active=${APP_PROFILE:test}
spring.jpa.open-in-view=false
```

#### system.properties

```properties
java.runtime.version=11
```

&#128070; [Voltar ao Sumário](#Sumário)

## Passo 7: Implantação no Heroku

- Criar app no Heroku
- Provisionar banco Postgres
- Definir variável APP_PROFILE=prod
- Conectar ao banco via pgAdmin
- Criar seed do banco

```
heroku -v
heroku login
heroku git:remote -a <nome-do-app>
git remote -v
git subtree push --prefix backend heroku main
```

&#128070; [Voltar ao Sumário](#Sumário)

# Guia do Frontend

### Objetivos do projeto para este tópico

- Criar projetos backend e frontend
- Salvar os projeto no Github em monorepo
- Montar o visual estático do front end
- Publicar o front end no Netlify

# Checklist

## Passo 1: criar projetos

### Conferir Yarn

```
yarn -v
npm install --global yarn
```

### Criar projeto ReactJS com `create-react-app`:

```
npx create-react-app frontend --template typescript
```

- Lembrete: excluir repositório Git do projeto ReactJS

- Criar projeto Spring Boot no `Spring Initializr` com as seguintes dependências:

  - Web
  - JPA
  - H2
  - Postgres
  - Security

&#128070; [Voltar ao Sumário](#Sumário)
Continua...
