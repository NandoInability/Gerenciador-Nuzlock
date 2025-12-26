# Etapa 1: Build da aplicação com Maven
# Usamos uma imagem que já vem com Maven e JDK para compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia primeiro os arquivos de dependências para aproveitar o cache do Docker
COPY pom.xml .
COPY .mvn/ .mvn/

# Baixa as dependências do projeto
RUN mvn dependency:go-offline

# Copia o resto do código-fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar, pulando os testes
RUN mvn package -DskipTests


# Etapa 2: Imagem final de execução (Runtime)
# Usamos uma imagem leve, apenas com o Java Runtime Environment (JRE)
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o arquivo .jar gerado na etapa de build para a imagem final
# e o renomeia para app.jar para facilitar a execução
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a sua aplicação usa
EXPOSE 8080

# Comando para iniciar a sua aplicação quando o container for executado
ENTRYPOINT ["java", "-jar", "app.jar"]