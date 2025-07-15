# Documentação do Pacote UI

Este documento descreve os componentes de interface do usuário da aplicação BeautyMaker, localizados no pacote `com.doo.finalActv.beautymaker.ui`.

## Visão Geral

O pacote UI implementa a interface gráfica da aplicação utilizando Java Swing. A arquitetura segue o padrão MVC com componentes especializados para diferentes funcionalidades: autenticação, navegação principal e views específicas para clientes e funcionários.

## Estrutura da Interface

### Componente Principal

#### MainWindow

**Localização**: `ui/MainWindow.java`

Janela principal da aplicação que gerencia todas as outras views através de JInternalFrames.

**Responsabilidades:**
- Inicialização da aplicação
- Gerenciamento de views (LOGIN, SIGNUP, HOME)
- Coordenação entre componentes de UI
- Integração com sistema de eventos

**Estrutura:**
```java
public enum AppView {
    LOGIN,    // Tela de login
    SIGNUP,   // Tela de cadastro  
    HOME      // Tela inicial após login
}
```

**Dependências:**
- `EventManager` - Para comunicação via eventos
- `SessionManager` - Para verificar estado de autenticação
- `NotificationPanel` - Para exibir notificações

**Funcionalidade:**
- Controla qual view está ativa
- Responde a eventos de mudança de estado
- Integra sistema de notificações

### Views de Autenticação

#### LoginView

**Localização**: `ui/LoginView.java`

Tela de autenticação do usuário.

**Herança**: Estende `InternalView`

**Componentes:**
- Campo de email/username
- Campo de senha
- Botão de login
- Botão para ir para cadastro

**Funcionalidade:**
- Coleta credenciais do usuário
- Dispara eventos de login
- Integra com validação de dados

**Integração:**
- Publica `RequestLoginEvent` com credenciais
- Escuta eventos de resposta para feedback

#### SignupView

**Localização**: `ui/SignupView.java`

Tela de cadastro de novos usuários.

**Herança**: Estende `InternalView`

**Componentes:**
- Campos para dados pessoais (nome, email, data nascimento)
- Campos de senha e confirmação
- Validação em tempo real
- Botões de cadastro e cancelamento

**Funcionalidade:**
- Coleta dados completos para registro
- Valida confirmação de campos
- Dispara eventos de cadastro

**Validações:**
- Confirmação de email
- Confirmação de senha
- Formato de dados

### Views Principais

#### BaseHomeView

**Localização**: `ui/BaseHomeView.java`

Classe base para as telas principais da aplicação.

**Herança**: Estende `InternalView`

**Funcionalidade:**
- Define estrutura comum para home views
- Implementa navegação base
- Gerencia componentes compartilhados

#### ClientHomeView

**Localização**: `ui/ClientHomeView.java`

Tela principal para usuários do tipo Cliente.

**Herança**: Estende `BaseHomeView`

**Funcionalidades Específicas:**
- Agendamento de serviços
- Visualização de histórico
- Avaliação de serviços
- Gerenciamento de perfil

#### EmployeeHomeView

**Localização**: `ui/EmployeeHomeView.java`

Tela principal para usuários do tipo Funcionário.

**Herança**: Estende `BaseHomeView`

**Funcionalidades Específicas:**
- Visualização de agenda
- Gerenciamento de clientes
- Relatórios de performance
- Configurações de serviços

### Componentes Customizados

#### InternalView (Classe Base)

**Localização**: `ui/customComponents/InternalView.java`

Classe base para todos os painéis internos da aplicação.

**Herança**: Estende `JInternalFrame`

**Configurações:**
- Tamanho padrão: 800x600 pixels
- Remove decorações padrão do frame
- Torna componente transparente
- Desabilita redimensionamento

**Métodos:**
- `setupFrameAppearance()` - Configura aparência personalizada
- Construtores com dimensões customizáveis

**Funcionalidade:**
Fornece base consistente para todas as views internas, eliminando elementos visuais desnecessários e criando aparência uniforme.

#### NotificationPanel

**Localização**: `ui/customComponents/NotificationPanel.java`

Componente para exibição de notificações temporárias.

**Características:**
- Tamanho fixo: 300x100 pixels
- Tempo de exibição configurável (padrão: 5 segundos)
- Suporte a tipos de notificação (ERROR, WARNING)
- Auto-destruição após tempo configurado

**Funcionalidade:**
```java
// Criação de notificação
NotificationPanel notification = new NotificationPanel(
    "Título", 
    "Mensagem\nMultilinha", 
    containerPanel, 
    5000, 
    NotificationType.ERROR
);
```

**Recursos:**
- Timer automático para remoção
- Suporte a HTML para formatação
- Quebra de linha automática
- Integração com container principal

#### Outros Componentes Customizados

**LogoHome**: Componente de logo para tela inicial

**MenuCardPanel**: Painéis de menu em formato de card

### Views Específicas do Cliente

**Localização**: `ui/client/`

#### AppointmentsView
- Gerenciamento de agendamentos
- Visualização de horários disponíveis
- Histórico de serviços

#### ProfileView
- Edição de dados pessoais
- Preferências de contato
- Configurações de conta

#### ServicesView
- Catálogo de serviços disponíveis
- Preços e descrições
- Agendamento direto

#### StaffView
- Visualização de profissionais
- Avaliações e comentários
- Seleção de profissional preferido

#### Cards (`client/cards/`)
Componentes de card para exibição de informações estruturadas.

## Arquitetura de Eventos na UI

### Integração com EventManager

As views seguem arquitetura orientada a eventos:

**Eventos Publicados:**
- `RequestLoginEvent` - Login de usuário
- `RequestSignupEvent` - Cadastro de usuário
- `RequestLogoutEvent` - Logout
- `ShowSignupViewEvent` - Mudança para tela de cadastro

**Eventos Escutados:**
- `SuccessfulLoginEvent` - Login bem-sucedido
- `UserLoggedOutEvent` - Logout realizado
- `NotificationEvent` - Exibição de notificações

### Fluxo de Comunicação

```
UI Component → Event → EventManager → Service Layer → Database
                ↓
         NotificationEvent ← EventManager ← Service Response
                ↓
           NotificationPanel (UI Feedback)
```

## Padrões de Design Implementados

### 1. Template Method
- `InternalView` define estrutura base
- Views específicas implementam comportamentos únicos

### 2. Observer Pattern
- Components escutam eventos relevantes
- Atualizações automáticas baseadas em mudanças de estado

### 3. Factory Pattern (implícito)
- MainWindow cria views conforme necessário
- Centraliza criação de componentes

### 4. Strategy Pattern
- Diferentes views para diferentes tipos de usuário
- Comportamentos específicos por contexto

## Características Técnicas

### Responsividade
- Layouts flexíveis com BorderLayout e BoxLayout
- Componentes se adaptam a redimensionamentos
- Tamanhos mínimos e máximos definidos

### Acessibilidade
- Componentes seguem padrões Swing
- Suporte a navegação por teclado
- Labels descritivos para campos

### Performance
- Views criadas sob demanda
- Reutilização de componentes quando possível
- Timer eficiente para notificações

### Customização Visual
- Aparência personalizada através de InternalView
- Temas consistentes entre componentes
- Suporte a elementos gráficos customizados

## Integração com Dados

### Binding com Session
- Views refletem estado atual de sessão
- Atualização automática em mudanças de usuário
- Proteção de acesso baseada em tipo de usuário

### Validação de Formulários
- Validação em tempo real em campos críticos
- Feedback visual para erros
- Integração com sistema de validação backend

### Persistence de Estado
- Views mantêm estado durante navegação
- Dados temporários preservados
- Recuperação de formulários em progresso

## Considerações de UX/UI

### Design Consistente
- Padrões visuais uniformes
- Navegação intuitiva
- Feedback apropriado para ações

### Error Handling
- Notificações claras para erros
- Orientação para correção de problemas
- Graceful degradation em falhas

### Performance Percebida
- Feedback imediato para ações
- Loading states quando necessário
- Transições suaves entre views

## Oportunidades de Melhoria

### 1. Responsividade Avançada
- Layouts que se adaptam a diferentes tamanhos de tela
- Suporte a diferentes resoluções

### 2. Temas e Personalização
- Sistema de temas configurável
- Cores e fontes customizáveis
- Modo escuro/claro

### 3. Componentização Avançada
- Biblioteca de componentes reutilizáveis
- Sistema de design consistente
- Componentes com estado próprio

### 4. Acessibilidade Melhorada
- Suporte completo a screen readers
- Navegação avançada por teclado
- Contraste e tamanhos de fonte ajustáveis

### 5. Internacionalização
- Suporte a múltiplos idiomas
- Formatação regional de datas/números
- Direção de texto configurável