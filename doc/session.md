# Documentação do Pacote Session

Este documento descreve o gerenciamento de sessões da aplicação BeautyMaker, localizado no pacote `com.doo.finalActv.beautymaker.session`.

## Visão Geral

O pacote session é responsável por gerenciar o estado de autenticação dos usuários, controlando login, logout e cadastro através de um sistema baseado em eventos. Implementa o padrão Singleton para garantir uma única instância de sessão ativa no sistema.

## Classe Principal

### SessionManager

**Localização**: `session/SessionManager.java`

Classe central que gerencia o estado de autenticação do usuário e coordena operações de login/logout.

#### Estrutura da Classe

**Padrão Singleton:**
- `instance` - Instância única da classe
- `getInstance()` - Método para obter a instância singleton

**Dependências:**
- `EventManager` - Para comunicação via eventos
- `DatabaseManager` - Para operações de banco de dados
- `Validator` - Para validação de dados de entrada

**Estado da Sessão:**
- `User user` - Usuário atualmente logado (null se não logado)

#### Métodos Públicos

**Verificação de Estado:**
```java
public User getUser()           // Retorna o usuário atual
public boolean userIsLoggedIn() // Verifica se há usuário logado
```

**Funcionalidade:**
- `getUser()`: Retorna a instância do usuário atualmente autenticado
- `userIsLoggedIn()`: Verifica se existe um usuário ativo na sessão

#### Sistema de Eventos

O SessionManager funciona como um hub central que escuta e responde a eventos de autenticação:

**Eventos Escutados:**
- `RequestLoginEvent` - Solicitação de login
- `RequestSignupEvent` - Solicitação de cadastro
- `RequestLogoutEvent` - Solicitação de logout

**Eventos Publicados:**
- `SuccessfulLoginEvent` - Login realizado com sucesso
- `UserLoggedOutEvent` - Usuário deslogado
- `NotificationEvent` - Notificações de erro/aviso

#### Fluxo de Autenticação

### 1. Processo de Login

**Trigger**: `RequestLoginEvent`

**Validações:**
1. Verifica se já existe usuário logado
2. Se sim, publica notificação de aviso

**Processo:**
```java
private void onLoginRequest(RequestLoginEvent event) {
    if(userIsLoggedIn()) {
        // Notifica que usuário já está logado
        return;
    }
    
    try {
        // Autentica através do DatabaseManager
        this.user = DatabaseManager.getUser(event.username, event.password);
        // Publica evento de sucesso
        eventManager.publish(new SuccessfulLoginEvent());
    } catch (Exception e) {
        // Publica notificação de erro
        eventManager.publish(new NotificationEvent(
            NotificationType.ERROR, "Login failed", e.getMessage()));
    }
}
```

**Resultado:**
- Sucesso: Define usuário na sessão e publica `SuccessfulLoginEvent`
- Falha: Publica `NotificationEvent` com detalhes do erro

### 2. Processo de Cadastro

**Trigger**: `RequestSignupEvent`

**Validações Múltiplas:**
1. Verifica se usuário já está logado
2. Valida confirmação de email
3. Valida confirmação de senha
4. Valida formato do username
5. Valida formato do email
6. Valida complexidade da senha

**Processo de Validação:**
```java
private void validateSignupRequest(RequestSignupEvent event) throws IllegalSignupRequest {
    // Confirmação de email
    if(!(event.email.equals(event.confirmEmail))) {
        throw new IllegalSignupRequest("Emails do not match.");
    }
    
    // Confirmação de senha
    if(!(Arrays.equals(event.password, event.confirmPassword))) {
        throw new IllegalSignupRequest("Passwords do not match.");
    }
    
    // Validações de formato
    if(!Validator.isValidUsername(event.username)) {
        throw new IllegalSignupRequest("Username is not valid.");
    }
    
    // ... outras validações
}
```

**Processo Completo:**
1. Validação de estado (usuário não logado)
2. Validação dos dados de entrada
3. Tentativa de registro no banco
4. Login automático após registro bem-sucedido

### 3. Processo de Logout

**Trigger**: `RequestLogoutEvent`

**Validação:**
- Verifica se existe usuário logado

**Processo:**
```java
private void onLogoutRequest(RequestLogoutEvent event) {
    if(!userIsLoggedIn()) {
        // Notifica que não há usuário para deslogar
        return;
    }
    
    this.user = null;  // Remove usuário da sessão
    eventManager.publish(new UserLoggedOutEvent());
}
```

## Integração com Outros Componentes

### DatabaseManager
- Utilizado para autenticação (`getUser`) e registro (`register`)
- Abstrai a complexidade de acesso aos dados

### EventManager
- Canal de comunicação com outros componentes
- Permite arquitetura desacoplada

### Validator (Utils)
- Centraliza regras de validação
- Garante consistência na validação de dados

### UI Components
- Recebem notificações através de eventos
- Atualizam interface baseado no estado da sessão

## Tratamento de Exceções

**Exceções Específicas:**
- `IllegalSignupRequest` - Dados de cadastro inválidos
- `UserNotFoundException` - Usuário não encontrado no login
- `InvalidPasswordException` - Senha incorreta
- `IllegalSignupException` - Erro no processo de cadastro

**Estratégia de Tratamento:**
1. Captura exceções específicas
2. Converte em eventos de notificação
3. Permite que UI trate adequadamente

## Segurança

**Proteção de Senha:**
- Utiliza `char[]` ao invés de `String` para senhas
- Reduz risco de senhas permaneceram em memória

**Validação Rigorosa:**
- Múltiplas camadas de validação no cadastro
- Regras específicas para username, email e senha

**Estado Controlado:**
- Apenas uma sessão ativa por aplicação
- Verificações de estado antes de operações críticas

## Padrões de Design

### 1. Singleton
- Garante única instância de sessão
- Acesso global controlado ao estado de autenticação

### 2. Observer/Event-Driven
- Comunicação através de eventos
- Baixo acoplamento com outros componentes

### 3. Command Pattern (implícito)
- Eventos funcionam como comandos
- Encapsulam operações de autenticação

## Exemplo de Uso Completo

```java
// Obter instância do SessionManager
SessionManager session = SessionManager.getInstance();

// Verificar estado atual
if (session.userIsLoggedIn()) {
    User currentUser = session.getUser();
    // ... usar dados do usuário
}

// Login via evento (normalmente disparado pela UI)
EventManager.getInstance().publish(
    new RequestLoginEvent("usuario@email.com", "senha".toCharArray())
);

// Logout via evento
EventManager.getInstance().publish(new RequestLogoutEvent());
```

## Considerações de Desenvolvimento

### Thread Safety
- Implementação atual não é thread-safe
- Considerar sincronização para aplicações multi-threaded

### Persistência de Sessão
- Sessão é perdida ao fechar aplicação
- Pode implementar "lembrar-me" para persistência

### Timeout de Sessão
- Não implementa timeout automático
- Pode adicionar expiração de sessão por inatividade

### Múltiplas Sessões
- Atual permite apenas uma sessão
- Pode expandir para suportar múltiplos usuários simultâneos

## Extensibilidade

O design atual permite facilmente:
- Adicionar novos tipos de validação
- Implementar diferentes estratégias de autenticação
- Adicionar auditoria de sessões
- Integrar com sistemas externos de autenticação
- Implementar autorização baseada em roles