# Documentação do Pacote Model

Este documento descreve as classes do modelo de dados da aplicação BeautyMaker, localizadas no pacote `com.doo.finalActv.beautymaker.model`.

## Visão Geral

O pacote model contém as classes fundamentais que representam as entidades principais do sistema de gerenciamento de salão de beleza. Estas classes definem a estrutura de dados para usuários, funcionários, clientes e tipos de notificação.

## Classes Principais

### User (Classe Abstrata)

**Localização**: `model/User.java`

Classe abstrata base que representa um usuário genérico do sistema.

**Atributos:**
- `int id` - Identificador único do usuário
- `String name` - Nome do usuário
- `String email` - Email do usuário
- `LocalDate birthDate` - Data de nascimento

**Métodos Principais:**
- `getId()` - Retorna o ID do usuário
- `getName()` - Retorna o nome do usuário
- `getEmail()` - Retorna o email do usuário
- `equals(Object o)` - Compara usuários baseado no nome, email e data de nascimento

**Funcionalidade:**
Serve como classe base para todos os tipos de usuários no sistema, definindo os atributos comuns e comportamentos básicos.

### Client

**Localização**: `model/Client.java`

Classe que representa um cliente do salão de beleza, herda de User.

**Herança**: Estende `User`

**Funcionalidade:**
Representa clientes que utilizam os serviços do salão. Atualmente utiliza apenas os atributos herdados da classe User, mas pode ser expandida para incluir informações específicas de clientes como histórico de serviços, preferências, etc.

**Exemplo de Uso:**
```java
Client client = new Client(1, "Maria Silva", "maria@email.com", LocalDate.of(1990, 5, 15));
```

### Employee

**Localização**: `model/Employee.java`

Classe que representa um funcionário do salão, herda de User.

**Herança**: Estende `User`

**Atributos Adicionais:**
- `LocalDate hireDate` - Data de contratação do funcionário

**Funcionalidade:**
Representa funcionários do salão, incluindo informações específicas como data de contratação. Pode incluir cabeleireiros, esteticistas, recepcionistas, etc.

**Exemplo de Uso:**
```java
Employee employee = new Employee(2, "João Santos", "joao@email.com", 
    LocalDate.of(1985, 3, 20), LocalDate.of(2020, 1, 15));
```

### StaffData

**Localização**: `model/StaffData.java`

Classe que armazena dados de avaliação e experiência de funcionários.

**Atributos:**
- `String name` - Nome do funcionário
- `float rating` - Avaliação média (nota)
- `int ratingCount` - Quantidade de avaliações recebidas
- `LocalDate experience` - Data relacionada à experiência

**Construtores:**
- `StaffData(String name, float rating, int ratingCount, LocalDate experience)` - Construtor completo
- `StaffData()` - Construtor padrão com valores iniciais

**Funcionalidade:**
Armazena informações de desempenho e avaliação dos funcionários, permitindo acompanhar a qualidade dos serviços prestados.

### NotificationType (Enum)

**Localização**: `model/NotificationType.java`

Enumeração que define os tipos de notificação disponíveis no sistema.

**Valores:**
- `ERROR` - Notificações de erro
- `WARNING` - Notificações de aviso

**Funcionalidade:**
Define os tipos de notificação que podem ser exibidas ao usuário, utilizada pelo sistema de eventos para categorizar mensagens.

## Subpacote UI

**Localização**: `model/ui/`

Contém componentes de interface relacionados ao modelo de dados.

## Relações Entre Classes

```
User (abstrata)
├── Client
└── Employee

StaffData (independente)
NotificationType (enum)
```

## Padrões de Design Utilizados

1. **Herança**: Client e Employee herdam de User, promovendo reutilização de código
2. **Encapsulamento**: Atributos privados com métodos de acesso apropriados
3. **Enumeração**: NotificationType fornece constantes type-safe para tipos de notificação

## Considerações de Desenvolvimento

- As classes User, Client e Employee seguem princípios de POO com herança bem definida
- StaffData utiliza campos públicos intencionalmente por servir como DTO (Data Transfer Object)
- O sistema está preparado para expansão com novos tipos de usuários
- A validação de dados é implementada parcialmente no método equals() da classe User

## Extensibilidade

O modelo atual permite fácil extensão para:
- Novos tipos de usuário (ex: Manager, Admin)
- Atributos específicos para Client e Employee
- Novos tipos de notificação
- Relacionamentos entre entidades (agendamentos, serviços, etc.)