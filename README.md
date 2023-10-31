Projeto criado para abrigar o projeto de Tech Challenge do curso de Arquitetura de Software da Fiap. 

Esse projeto consiste num sistema de gerenciamento de pedidos de uma lanchoete fast food com auto atendimento. 
## Requisitos

O sistema usa
* SpringBoot
* Gradle
* Postgres

## Dependências
Esse projeto possui dependências de terceiros, para checar quais
e a versão correta de cada uma, por favor confira no arquivo build.gradle

**Note**
Lombok e dependências de framework não estão sendo usados na camada de domínio da aplicação!

## Padrões utilizados
* Arquitetura hexagonal
* DDD

**Warning**
O projeto ainda está em desenvolvimento, existem comentários de to-dos principalmente nas classes de entidade e valueobjects da camada de domínio. 
Portanto, mesmo que algumas classes ainda possam parecer anêmicas, a estrutura já foi criada para que regras de negócio que serão implementadas mudem o cenário. 
## Rodando o projeto com docker-compose
#Você vai precisar:

* Docker e docker-compose instalados na sua máquina

Clone o projeto e execute o comando

	$ docker-compose up

