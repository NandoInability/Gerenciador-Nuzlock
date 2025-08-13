#🏆 Gerenciador de Runs Soulink Nuzlocke#

Um projeto web desenvolvido com Java e Spring Boot para gerenciar e acompanhar o progresso em desafios **"Soulink Nuzlocke"** de Pokémon. A aplicação permite que os jogadores registrem suas runs, os pares de Pokémon capturados e o status de cada um, tudo através de uma interface web amigável e uma API RESTful robusta.

##📖 Sobre o Desafio##

Para entender o propósito deste projeto, é útil conhecer os desafios que o inspiraram:
    Nuzlocke: Um conjunto de regras autoimpostas para aumentar a dificuldade dos jogos Pokémon. As regras principais são:
        Qualquer Pokémon que desmaia ("faints") é considerado morto e deve ser liberado.
        O jogador só pode capturar o primeiro Pokémon que encontrar em cada nova área/rota.
    Soulink: Uma variação multiplayer do Nuzlocke, onde dois jogadores jogam seus respectivos jogos simultaneamente. Os Pokémon capturados por ambos em cada rota formam um "par interligado". Se um dos Pokémon do par morre no jogo de um jogador, seu parceiro no jogo do outro jogador também é considerado morto.
Este gerenciador foi criado para facilitar o acompanhamento complexo que o desafio Soulink exige.

##✨ Funcionalidades Principais##
    
	Gestão de Runs: Crie, visualize e liste todas as suas runs de Soulink em andamento ou concluídas.
    Cadastro de Pares de Pokémon: Registre os pares de Pokémon capturados em cada rota, vinculando-os a uma run específica.
    Controle de Status: Acompanhe o status de cada Pokémon (Vivo, Morto, na Box).
    Interface Web Intuitiva: Um dashboard visual para ver o progresso de suas runs de forma clara e organizada.
    API RESTful: Todos os dados podem ser acessados e manipulados através de uma API bem definida, permitindo futuras integrações (como um app mobile, por exemplo).

##💻 Tecnologias Utilizadas##

Este projeto foi construído utilizando um stack de tecnologias modernas e robustas, focadas no ecossistema Java.
Camada	Tecnologia	Descrição
Backend	Java 17 / 21	Linguagem principal da aplicação.
	Spring Boot	Framework principal para criação rápida e robusta de aplicações.
	Spring Data JPA	Para persistência de dados de forma simplificada e eficiente.
	Hibernate	Implementação do JPA para mapeamento objeto-relacional (ORM).
Frontend	Thymeleaf	Motor de templates para renderizar páginas HTML dinâmicas no lado do servidor.
	HTML5 / CSS3	Estrutura e estilização das páginas web.
	Bootstrap 5	Framework CSS para criar um design responsivo e moderno rapidamente.
Banco de Dados	H2 Database	Banco de dados em memória para ambiente de desenvolvimento. (Pode ser alterado para MySQL/PostgreSQL)
Build & Dependências	Maven	Ferramenta para gerenciamento de dependências e build do projeto.
Testes de API	Postman	Ferramenta utilizada para testar os endpoints da API RESTful durante o desenvolvimento.

##🚀 Como Executar o Projeto Localmente##

Para executar este projeto em sua máquina, siga os passos abaixo.
###Pré-requisitos###
    
	Java Development Kit (JDK) - Versão 17 ou superior.
    Apache Maven - Versão 3.8 ou superior.
    Git para clonar o repositório.
###Passos###
    
	Clone o repositório:
    Bash
git clone https://github.com/NandoInability/Gerenciador-Nuzlock
Navegue até a pasta do projeto:
Bash
cd GerenciadorNuzlocke
Execute a aplicação usando o Maven:
Bash
    mvn spring-boot:run
    Alternativamente, você pode importar o projeto em sua IDE preferida (IntelliJ, Eclipse, VSCode) e executar a classe principal GerenciadorNuzlockeApplication.java.
    Acesse a aplicação:
        Abra seu navegador e acesse http://localhost:8080/. Você deverá ver o dashboard principal da aplicação.
        A API pode ser acessada através de endpoints como http://localhost:8080/api/runs.

##📝 Estrutura do Projeto##

O código-fonte segue a arquitetura em camadas padrão para aplicações Spring:
    src/main/java/
        .../entity: Contém as classes de modelo que são mapeadas para as tabelas do banco de dados (ex: RunsSL, Pokemon).
        .../repository: Contém as interfaces do Spring Data JPA para acesso aos dados (ex: RunsSLRepository).
        .../service: Contém a lógica de negócio da aplicação (ex: RunsSLService).
        .../controller: Contém os Controllers que lidam com as requisições HTTP (tanto para a API REST quanto para as views do Thymeleaf).
    src/main/resources/
        /static: Para arquivos estáticos como CSS, JavaScript e imagens.
        /templates: Onde ficam os arquivos HTML do Thymeleaf.
        application.properties: Arquivo de configuração principal do Spring.
        
##🔮 Próximos Passos e Melhorias Futuras##
    
	[ ] Implementar o formulário de criação de novas runs.
    [ ] Construir a página de detalhes de uma run, listando todos os pares.
    [ ] Adicionar sistema de autenticação e login de usuários.
    [ ] Exibir os sprites (imagens) dos Pokémon nos cards.
    [ ] Implementar a lógica de edição e exclusão de runs e pares.

##👤 Autor
Luiz Fernando Torres da Silva
    GitHub: @NandoInability(https://github.com/NandoInability)
