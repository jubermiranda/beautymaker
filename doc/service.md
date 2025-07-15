# Documentação do Pacote Service

Este documento descreve as classes e serviços da camada de serviço da aplicação BeautyMaker, localizadas no pacote `com.doo.finalActv.beautymaker.serivce`.

## Visão Geral

O pacote service implementa a lógica de negócio da aplicação, organizando os serviços em subpacotes especializados: database (db), eventos (event), dados da aplicação (appdata) e utilitários (utils). Este pacote segue o padrão arquitetural de separação de responsabilidades.

## Subpacotes e Estrutura

### Database Services (`db/`)

Responsável pela gestão de dados e conexões com o banco de dados PostgreSQL.

#### DatabaseManager

**Localização**: `serivce/db/DatabaseManager.java`

Classe principal para gerenciamento do banco de dados, implementando o padrão Singleton.

**Atributos:**
- `DB_SCHEMA = "beautymaker"` - Nome do schema do banco
- `instance` - Instância singleton

**Métodos Principais:**
- `getInstance()` - Retorna a instância singleton
- `dbIsAvailable()` - Verifica disponibilidade da conexão
- `getUser(String email, char[] password)` - Autentica usuário via AuthService
- `register(String username, String email, char[] password, LocalDate birthDate)` - Registra novo usuário via AuthService

**Funcionalidade:**
Centraliza o acesso aos dados e coordena operações de banco através de outros serviços especializados.

#### AuthService

**Localização**: `serivce/db/AuthService.java`

Serviço especializado em autenticação e autorização de usuários.

**Métodos Principais:**
- `login(String email, char[] password)` - Autentica usuário
- `signup(String username, String email, char[] password, LocalDate birthDate)` - Registra novo usuário

**Funcionalidade:**
Implementa a lógica de autenticação, validação de credenciais e criação de novos usuários.

#### ConnectionService

**Localização**: `serivce/db/ConnectionService.java`

Gerencia conexões com o banco de dados PostgreSQL.

**Funcionalidade:**
Responsável por estabelecer e gerenciar conexões com o banco, implementando pooling e configurações de conexão.

### Event Services (`event/`)

Sistema de eventos baseado no padrão Observer/Publisher-Subscriber.

#### EventManager

**Localização**: `serivce/event/EventManager.java`

Gerenciador central do sistema de eventos, implementando o padrão Singleton.

**Atributos:**
- `listeners` - Map de listeners por tipo de evento (ConcurrentHashMap)

**Métodos Principais:**
- `getInstance()` - Retorna instância singleton
- `subscribe(Class<T> eventType, EventListener<T> listener)` - Registra listener para tipo de evento
- `unsubscribe(Class<T> eventType, EventListener<T> listener)` - Remove listener
- `publish(T event)` - Publica evento para todos os listeners registrados

**Funcionalidade:**
Implementa comunicação desacoplada entre componentes através de eventos, permitindo arquitetura reativa.

**Exemplo de Uso:**
```java
EventManager em = EventManager.getInstance();
em.subscribe(NotificationEvent.class, this::handleNotification);
em.publish(new NotificationEvent(NotificationType.ERROR, "Erro", "Mensagem"));
```

#### EventListener (Interface)

**Localização**: `serivce/event/EventListener.java`

Interface funcional para manipulação de eventos.

**Método:**
- `onEvent(T event)` - Método callback executado quando evento é publicado

**Funcionalidade:**
Define contrato para componentes que desejam receber notificações de eventos específicos.

### Event Models (`event/model/`)

Classes que representam diferentes tipos de eventos no sistema.

#### NotificationEvent

**Localização**: `serivce/event/model/NotificationEvent.java`

Evento para exibição de notificações ao usuário.

**Atributos:**
- `String title` - Título da notificação
- `String message` - Mensagem da notificação  
- `NotificationType type` - Tipo da notificação (ERROR, WARNING)

**Funcionalidade:**
Transporta informações de notificações através do sistema de eventos.

#### Eventos de Autenticação

**RequestLoginEvent**: Evento de solicitação de login
- `String username` - Nome de usuário
- `char[] password` - Senha do usuário

**RequestSignupEvent**: Evento de solicitação de cadastro
- Inclui dados completos para registro de novo usuário

**SuccessfulLoginEvent**: Evento de login bem-sucedido

**UserLoggedOutEvent**: Evento de logout do usuário

**RequestLogoutEvent**: Evento de solicitação de logout

#### Outros Eventos

**ShowSignupViewEvent**: Evento para exibir tela de cadastro

**MenuItemSelectedEvent**: Evento de seleção de item de menu

## Outros Subpacotes

### Application Data (`appdata/`)

Contém serviços relacionados aos dados da aplicação e configurações.

### Utils (`utils/`)

Contém classes utilitárias e helpers, incluindo validadores e formatadores.

## Padrões de Design Implementados

### 1. Singleton
- `EventManager` e `DatabaseManager` garantem instância única
- Acesso global controlado aos serviços principais

### 2. Observer/Publisher-Subscriber
- Sistema de eventos permite comunicação desacoplada
- Componentes podem reagir a mudanças sem conhecimento direto

### 3. Strategy (implícito)
- Diferentes tipos de eventos processados de forma específica
- EventListener permite implementações variadas

### 4. Service Layer
- Separação clara entre lógica de negócio e outras camadas
- Serviços especializados por responsabilidade

## Fluxo de Dados Típico

1. **Requisição de Login:**
   ```
   UI → RequestLoginEvent → SessionManager → DatabaseManager → AuthService → Database
   ```

2. **Notificação de Erro:**
   ```
   Service → NotificationEvent → EventManager → UI Components
   ```

3. **Registro de Usuário:**
   ```
   UI → RequestSignupEvent → SessionManager → DatabaseManager → AuthService → Database
   ```

## Características Arquiteturais

### Thread Safety
- `EventManager` utiliza `ConcurrentHashMap` para thread safety
- Suporte a operações concorrentes no sistema de eventos

### Reatividade
- Sistema baseado em eventos permite arquitetura reativa
- Componentes respondem automaticamente a mudanças de estado

### Extensibilidade
- Fácil adição de novos tipos de eventos
- Novos serviços podem ser integrados sem modificar código existente

### Desacoplamento
- Componentes comunicam através de eventos
- Reduz dependências diretas entre módulos

## Considerações de Desenvolvimento

1. **Tratamento de Exceções**: Serviços propagam exceções específicas para tratamento adequado
2. **Validação**: Validações são centralizadas nos serviços apropriados
3. **Configuração**: Conexões e configurações são gerenciadas pelos serviços de infraestrutura
4. **Performance**: Padrão Singleton evita criação desnecessária de instâncias

## Oportunidades de Melhoria

1. **Pool de Conexões**: Implementar pooling avançado no ConnectionService
2. **Async Processing**: Considerar processamento assíncrono de eventos
3. **Caching**: Implementar cache para operações frequentes
4. **Logging**: Adicionar logging estruturado aos serviços
5. **Configuration**: Externalizar configurações para arquivos de propriedades