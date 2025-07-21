# Beauty Maker
# Manual do Usuário:
<<<<<<< HEAD

=======
>>>>>>> 04a2b84 (Update README.md)
Sistema para Gestão de Salão de Beleza.

# Acesso ao Sistema:
O sistema permite que clientes e funcionários realizem login por meio do nome de usuário ou e-mail, juntamente com a senha. Durante o processo de autenticação:

. O sistema consulta o banco de dados em busca de um usuário com as credenciais fornecidas.

. Se os dados estiverem corretos, o login é validado e a área correspondente (cliente ou funcionário) é aberta.

. Caso não haja correspondência, uma mensagem de erro é exibida, indicando que o cadastro não existe ou os dados estão incorretos.
# Cadastro de Novos Usuários: 
Apenas clientes realizam seu próprio cadastro. Os funcionários são cadastrados previamente pela administração do sistema.

O processo de cadastro de cliente solicita os seguintes dados:

   .Nome de usuário

   .E-mail

   .Senha

   .Data de nascimento

Após o envio, as informações são armazenadas na tabela users do banco de dados e o sistema cria automaticamente o perfil do cliente, permitindo o acesso à sua área exclusiva.

# Área do Funcionário:
Após o login, se o sistema identificar que o usuário é um funcionário, ele será redirecionado para uma área exclusiva com as seguintes funcionalidades:

Visualização dos horários marcados, com informações como:

   .Nome do cliente

   .Serviço solicitado

   .Horário agendado

Cadastro de novos serviços, que serão:

   .Armazenados no banco de dados

Exibidos para os clientes no momento em que forem marcar um horário de atendimento.

# Área do Cliente:
Caso o sistema identifique, no momento do login, que o usuário é um cliente, ele será direcionado para a sua área exclusiva, onde poderá acessar as seguintes funcionalidades:

  # Perfil do Cliente:
  .Visualização de seus próprios dados cadastrados.

  .Possibilidade de edição do perfil (em desenvolvimento, se aplicável).

  # Agendamento de Serviços:
  O cliente poderá realizar agendamentos diretamente pela interface do sistema, escolhendo:

   .O profissional desejado

   .O dia e horário disponíveis

   .O serviço a ser realizado

  Todas essas informações são armazenadas no banco de dados e automaticamente vinculadas ao profissional escolhido, sendo exibidas na área de trabalho do funcionário correspondente.

  # Consulta de Serviços e Profissionais:
  Além do agendamento, o cliente pode:

  .Visualizar a lista completa de serviços cadastrados pelos profissionais no sistema.

  .Acessar a relação de profissionais disponíveis, com nome e função, facilitando a escolha no momento do agendamento.
<<<<<<< HEAD
=======
  
>>>>>>> 04a2b84 (Update README.md)
