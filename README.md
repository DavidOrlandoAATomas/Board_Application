# 🗂️ Board - Kanban Management System

## 📌 Descrição
O **Board** é uma aplicação desenvolvida em **Java Spring Boot** para a gestão de quadros no estilo **Kanban**.  
Permite criar e organizar **quadros**, **colunas** e **cartões**, ajudando a gerir tarefas de forma eficiente.

O projeto segue uma arquitetura baseada em **camadas**:
- **DTOs** para transferência de dados.
- **Entities & DAOs** para persistência.
- **Services** para lógica de negócio.
- **UI** para interação do utilizador.
- **Liquibase** para migração e versionamento da base de dados.

---

## 🚀 Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Liquibase** (migração de base de dados)
- **Gradle** (gestão de dependências)

---

## 📂 Estrutura do Projeto
Board/
┣ src/main/java/org/david
┃ ┣ dto/ # Objetos de Transferência de Dados (DTOs)
┃ ┣ exception/ # Exceções customizadas
┃ ┣ persistance/ # Configuração, DAO, Entities e migrações
┃ ┣ service/ # Lógica de negócio
┃ ┣ UI/ # Menus e interação do utilizador
┃ ┗ Main.java # Classe principal da aplicação
┣ build.gradle # Configuração do Gradle
┣ settings.gradle.kts # Definições do projeto
┗ liquibase.log # Log de migrações do Liquibase
