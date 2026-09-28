# API Controle Financeiro 

---

[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)](#)
[![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)](#)

Desenvolvi uma API REST simples de controle financeiro que te permite registrar uma despesa com Data, Titulo, Valor e opcionalmente uma descrição.

O código foi desenvolvido utilizando:
- Java 25 
- PostgreSQL 
- Spring Boot

Esta é meu primeiro contato com o framework Spring e criação de APIs. Feedbacks, críticas e recomendações são muito bem vindos.

## Índice
- [API](#api)
- [Sistema](#sistema)

---

## API

O arquivo "application.properties" possui a linha responsável pela URL do seu banco de dados e inserção de user e senha. Assim como a linha para criação automática das tabelas (desde que a conexão esteja estabelecida). 

No mesmo arquivo, configure as variáveis de ambiente DATABASE_USERNAME e DATABASE_PASSWORD com as credenciais do seu PostgreSQL local. Caso não saiba como, apenas apague:
```
${DATABASE_USERNAME} e ${DATABASE_PASSWORD}
```
E coloque seu user e senha no lugar.

Caso deseje testar o código, por favor, verifique a conexão com o banco. 

Para testar essa API utilize o Postman, clone o repositório e inicie o código, ele ira rodar pelo seguinte endereço:
```
localhost:8080
```

### Endpoints

A API possui os seguintes endpoints:

```
GET /despesas  -> Busca todas as despesas que estiverem no banco

GET /despesas/{id}  -> Busca uma despesas especifica pelo ID

POST /despesas  -> Registra uma nova despesa no banco

DELETE /despesas/{id}  -> Deleta uma despesa do banco pelo ID

PUT /despesas/{id}  -> Atualiza uma despesa (verbo PUT, então é preciso repassar todo o valor no JSON)

GET /despesas/dia?data=dd/MM/aaaa  -> Busca uma despesa no banco por uma data específica 

GET /despesas/periodo?inicio=dd/MM/aaaa&fim=dd/MM/aaaa  -> Busca despesas no banco dentro de um período específico 
```

Para registrar uma despesa, siga o seguinte modelo JSON:

```
    {
        "dataDespesa": "27/09/2026",
        "titulo": "Café da manhã",
        "valor": 12.00,
        "descricao": "Cafézinho de lanchonete"
    }
```

A descrição é um campo opcional então não tem problema não adicionar nada, mas será preciso remover ela do corpo.

---

## Sistema 

O sistema segue uma arquitetura em camadas (Controller -> Service -> Repository), com DTOs fazendo a comunicação entre elas.

### Controller

No Controller esta toda a parte do código que recebe as requisições HTTP, cada método está marcado com seu respectivo verbo HTTP.

### Domain 

No domain temos nossa "entidade original", é nela que estão os atributos a serem seguidos, assim como as suas anotações para persistência de dados.

### DTO

No pacote DTO temos as classes "DespesaCreateDto" e "DespesaResponseDto", importantes para transportar dados entres as diferentes camadas do sistema, e seguem o modelo da "entidade original".

O **DespesaCreateDto** é responsável pela criação do objeto, quando fazemos um POST é ele que recebe os dados e transforma em um objeto Despesa.

Possui anotações como:
* @JsonFormat(pattern = "dd/MM/yyyy") - para indicar a forma que a data deve ser inserida no JSON.
* NotNull/NotBlank - serve como uma segunda camada de proteção, caso algum dado esteja vazio, ele quebra e da erro antes de percorrer todo o caminho até o banco de dados.

Já o **DespesaResponseDto** é a entidade que retornamos para o usuário.

O DespesaResponse possui as anotações:
* @JsonInclude(JsonInclude.Include.NON_NULL) - Para não exibir o campo "descrição" no corpo do JSON caso o campo esteja vazio, já que descrição não é um campo obrigatório.
* @JsonFormat(pattern = "dd/MM/yyyy") - Apenas para exibir a data no mesmo formato da inserção.

### Exception e ExceptionHandler

No pacote "exception" temos 2 exceções personalizadas e um ErrorResponse que funciona basicamente como um ResponseDto. O trabalho do response é tornar o erro retornado pelo JSON em algo mais "amigável" e legível. 


Já no pacote "handler" temos o **GlobalExceptionHandler** que é o responsável por lidar com essa "transformação" da mensagem de erro. 

Tentei mapear o maior numero de erros possíveis, e deixei anotado em cima de cada método em qual ocasião aquela exceção seria lançada. Recomendo uma rápida checagem para saber os tipos de exceções que estão sendo tratadas.

### Repository

Aqui temos a interface IDespesaRepository que estende "JpaRepository", usando da especificação JPA e do Hibernate para aplicar a persistência de dados. Isso nos livra de ter que ficar escrevendo comando SQL manual, a interface já possui métodos prontos e semânticos para cada operação.

Utilizei dos Query Methods para fazer 2 métodos de consultas relacionadas a uma data ou período específico:

```
 List<Despesa> findDespesaByDataDespesa(LocalDate dataDespesa);

 List<Despesa> findByDataDespesaBetween(LocalDate dataInicial, LocalDate dataFinal);
```

### Service

Seguindo a arquitetura, temos a service para toda lógica de negócio. Pela primeira vez utilizei funções Lambdas para simplificar o código e Stream junto de builders para transformação e criação dos objetos Entidade e EntidadeResponse.


**O lançamento das exceções próprias do sistema:**

A maioria das exceções tratadas na classe GlobalExceptionHandler, são exceções disparadas pelo próprio Spring, não temos controle de onde e quando vamos lançá-las, mas aqui, as nossas 2 exceções personalizadas entram em ação:

```
 DadoInvalidoException("")
 
 NaoEncontradoException("")
```

Elas são aplicadas em situações em que o JSON não identifica o erro com clareza (ou sequer identifica como erro), ou não sabe uma lógica sobre o dado inserido, por exemplo:
* O JSON não sabe que o valor de uma despesa não pode ser negativo.
* Em uma busca por coleção, uma lista vazia é um resultado válido. Mas em uma busca por um recurso específico, um retorno vazio se torna um erro.
* O filtro da URL não sabe que na busca por período, a data de início precisa ser anterior a data final.

Nesses cenários as 2 exceções personalizadas são úteis, apontando exatamente o que, e onde falhou. 

### ControleDeGastoApplication

Por fim, a classe que é responsável por fazer a varredura de beans e identificar seus componentes, assim iniciando o sistema corretamente.
