# Diagrama de Classes - Beauty Maker

Este documento apresenta os diagramas de classes para os principais pacotes da aplicação Beauty Maker, organizados por responsabilidades e arquitetura.

## Pacote Model

### Hierarquia de Usuários

```mermaid
classDiagram
    class User {
        <<abstract>>
        -int id
        -String name
        -String email
        -LocalDate birthDate
        +User(int, String, String, LocalDate)
        +getId() int
        +getName() String
        +getEmail() String
        +equals(Object) boolean
    }
    
    class Client {
        +Client(int, String, String, LocalDate)
    }
    
    class Employee {
        -LocalDate hireDate
        +Employee(int, String, String, LocalDate, LocalDate)
    }
    
    class StaffData {
        +String name
        +float rating
        +int ratingCount
        +LocalDate experience
        +StaffData(String, float, int, LocalDate)
        +StaffData()
    }
    
    class NotificationType {
        <<enumeration>>
        ERROR
        WARNING
    }
    
    User <|-- Client
    User <|-- Employee
    Employee --> StaffData : contém dados de staff
```

### Relacionamentos do Model

- **User**: Classe abstrata base para todos os tipos de usuário
- **Client**: Herda de User, representa clientes do sistema
- **Employee**: Herda de User, possui data de contratação adicional
- **StaffData**: DTO para dados de funcionário com avaliações
- **NotificationType**: Enum para tipos de notificação do sistema

## Pacote Service

### Gerenciamento de Eventos

```mermaid
classDiagram
    class EventManager {
        <<singleton>>
        -EventManager instance
        -Map~Class, List~EventListener~~ listeners
        -EventManager()
        +getInstance() EventManager
        +subscribe(Class~T~, EventListener~T~) void
        +unsubscribe(Class~T~, EventListener~T~) void
        +publish(T) void
    }
    
    class EventListener~T~ {
        <<interface>>
        +onEvent(T) void
    }
    
    class RequestLoginEvent {
        +String username
        +char[] password
        +RequestLoginEvent(String, char[])
    }
    
    class RequestSignupEvent {
        +String username
        +String email
        +String confirmEmail
        +char[] password
        +char[] confirmPassword
        +LocalDate birthDate
        +RequestSignupEvent(...)
    }
    
    class RequestLogoutEvent {
        +RequestLogoutEvent()
    }
    
    class SuccessfulLoginEvent {
        +SuccessfulLoginEvent()
    }
    
    class UserLoggedOutEvent {
        +UserLoggedOutEvent()
    }
    
    class NotificationEvent {
        +NotificationType type
        +String title
        +String message
        +NotificationEvent(NotificationType, String, String)
    }
    
    class MenuItemSelectedEvent {
        +String menuItem
        +MenuItemSelectedEvent(String)
    }
    
    class ShowSignupViewEvent {
        +ShowSignupViewEvent()
    }
    
    EventManager --> EventListener : gerencia
    EventManager --> RequestLoginEvent : publica/escuta
    EventManager --> RequestSignupEvent : publica/escuta
    EventManager --> RequestLogoutEvent : publica/escuta
    EventManager --> SuccessfulLoginEvent : publica/escuta
    EventManager --> UserLoggedOutEvent : publica/escuta
    EventManager --> NotificationEvent : publica/escuta
    EventManager --> MenuItemSelectedEvent : publica/escuta
    EventManager --> ShowSignupViewEvent : publica/escuta
```

### Gerenciamento de Dados

```mermaid
classDiagram
    class DatabaseManager {
        <<singleton>>
        +String DB_SCHEMA
        -DatabaseManager instance
        -DatabaseManager()
        +getInstance() DatabaseManager
        +dbIsAvailable() boolean
        +getUser(String, char[]) User
        +register(String, String, char[], LocalDate) User
        +getUserType(long) String
        +getStaffs() ArrayList~StaffData~
    }
    
    class AuthService {
        +login(String, char[]) User
        +signup(String, String, char[], LocalDate) User
    }
    
    class ConnectionService {
        +getConnection() Connection
    }
    
    class ContentProvider {
        <<singleton>>
        -ContentProvider instance
        -List~DataChangeListener~ listeners
        -ContentProvider()
        +getInstance() ContentProvider
        +subscribe(DataChangeListener) void
        +unsubscribe(DataChangeListener) void
        +notifyDataChanged(AppData) void
        +getStaffData() ArrayList~StaffData~
    }
    
    class DataChangeListener {
        <<interface>>
        +onDataChanged(AppData) void
    }
    
    class AppData {
        +ArrayList~StaffData~ staffs
        +AppData(ArrayList~StaffData~)
    }
    
    DatabaseManager --> AuthService : utiliza
    DatabaseManager --> ConnectionService : utiliza
    DatabaseManager --> StaffData : retorna
    ContentProvider --> DataChangeListener : gerencia
    ContentProvider --> AppData : fornece
    ContentProvider --> DatabaseManager : consulta
```

### Utilitários do Service

```mermaid
classDiagram
    class PasswordUtils {
        +hashPassword(char[]) String
        +verifyPassword(char[], String) boolean
    }
    
    class Validator {
        +isValidUsername(String) boolean
        +isValidEmail(String) boolean
        +isValidPassword(char[]) boolean
    }
```

## Pacote Session

### Gerenciamento de Sessão

```mermaid
classDiagram
    class SessionManager {
        <<singleton>>
        -SessionManager instance
        -EventManager eventManager
        -User user
        -SessionManager()
        +getInstance() SessionManager
        +getUser() User
        +userIsLoggedIn() boolean
        -initialize() void
        -initializeEvents() void
        -onLoginRequest(RequestLoginEvent) void
        -onSignupRequest(RequestSignupEvent) void
        -onLogoutRequest(RequestLogoutEvent) void
        -validateSignupRequest(RequestSignupEvent) void
    }
    
    SessionManager --> EventManager : utiliza
    SessionManager --> User : gerencia
    SessionManager --> DatabaseManager : utiliza
    SessionManager --> Validator : utiliza
    SessionManager --> RequestLoginEvent : escuta
    SessionManager --> RequestSignupEvent : escuta
    SessionManager --> RequestLogoutEvent : escuta
    SessionManager --> SuccessfulLoginEvent : publica
    SessionManager --> UserLoggedOutEvent : publica
    SessionManager --> NotificationEvent : publica
```

## Arquitetura Geral do Sistema

### Visão de Alto Nível

```mermaid
classDiagram
    class UIComponents {
        <<package>>
        Views, Panels, Forms
    }
    
    class SessionLayer {
        <<package>>
        SessionManager
    }
    
    class ServiceLayer {
        <<package>>
        EventManager
        DatabaseManager
        ContentProvider
    }
    
    class ModelLayer {
        <<package>>
        User, Client, Employee
        StaffData, NotificationType
    }
    
    UIComponents --> SessionLayer : interage via eventos
    SessionLayer --> ServiceLayer : utiliza serviços
    ServiceLayer --> ModelLayer : manipula dados
    ServiceLayer -.-> UIComponents : notifica via eventos
```

## Padrões de Design Implementados

### Singleton Pattern
- **EventManager**: Garante único ponto de comunicação entre componentes
- **SessionManager**: Controla estado único de autenticação
- **DatabaseManager**: Gerencia conexões e operações de banco
- **ContentProvider**: Fornece dados de forma centralizada

### Observer Pattern
- **EventManager**: Implementa publisher-subscriber para comunicação
- **ContentProvider**: Notifica mudanças de dados para observadores

### Strategy Pattern
- **EventListener**: Permite diferentes estratégias de tratamento de eventos

### DTO Pattern
- **StaffData**: Transfere dados de funcionário com campos públicos

## Fluxo de Comunicação

```mermaid
sequenceDiagram
    participant UI as UI Component
    participant SM as SessionManager
    participant EM as EventManager
    participant DM as DatabaseManager
    participant Model as User/Client/Employee
    
    UI->>EM: publish(RequestLoginEvent)
    EM->>SM: onLoginRequest()
    SM->>DM: getUser(email, password)
    DM->>Model: create User instance
    Model-->>DM: return User
    DM-->>SM: return User
    SM->>EM: publish(SuccessfulLoginEvent)
    EM->>UI: notify subscribers
```

## Considerações de Arquitetura

### Separação de Responsabilidades
- **Model**: Entidades de domínio e dados
- **Service**: Lógica de negócio e infraestrutura
- **Session**: Gerenciamento de estado de usuário
- **UI**: Interface e interação (não incluída neste diagrama)

### Comunicação Desacoplada
- Eventos permitem comunicação assíncrona
- Componentes não dependem diretamente uns dos outros
- Facilita manutenção e extensibilidade

### Persistência e Dados
- DatabaseManager centraliza acesso ao banco
- ContentProvider oferece cache e notificações reativas
- StaffData serve como DTO para transferência eficiente