# CadastroUsuarios
Aplicação dos conceitos da disciplina de Programação Orientada a Objetos (POO) com Interface Gráfica aplicada a um contexto de um sistema de cadastramento genérico.

#Estruturação do projeto:
1. Arquitetura do Projeto
  A aplicação será dividida em três componentes principais, seguindo uma variação do padrão Model-View-Controller (MVC):
  O Modelo (Model): Uma classe simples (POJO - Plain Old Java Object) que representa a entidade a ser cadastrada. Por exemplo, uma classe Pessoa com atributos como id, nome e email.
  O Repositório de Dados (Data Layer): Uma classe que centralizará o acesso aos dados. Como não usaremos um banco de dados, esta classe terá uma ArrayList<Pessoa> estática. O uso de static garante que a mesma lista de dados seja acessível por todas as janelas da aplicação.

  As Telas (View):
  FormPrincipal.java: A janela principal que contém a JTable para listar os registros e os botões de ação (Incluir, Alterar, Excluir, Consultar, Fechar).
  FormCadastro.java: Uma janela de diálogo (JDialog) para cada ação que será aberta para as operações de inclusão, alteração e consulta respectivamente.
  O fluxo de interação entre as janelas pode ser visualizado da seguinte forma:
  A[FormPrincipal] -- Clique "Incluir" --> B(Abre FormCadastro em modo 'Novo');
  A -- Seleciona linha + Clique "Alterar" --> C(Abre FormCadastro em modo 'Edição' com dados preenchidos);
  A -- Seleciona linha + Clique "Consultar" --> D(Abre FormCadastro em modo 'Visualização' com campos bloqueados);
  A -- Seleciona linha + Clique "Excluir" --> E{Confirmação de Exclusão};
  Janela de Cadastro
        B -- Salvar --> F[Adiciona dados na ArrayList];
        C -- Salvar --> G[Atualiza dados na ArrayList];
         D -- Fechar --> A;
  end
  F --> H[Atualiza JTable no FormPrincipal];
  G --> H;
  E -- Sim --> I[Remove dados da ArrayList];
  I --> H;

2. A Tela Principal (FormPrincipal)
Esta é a porta de entrada da aplicação.

Componentes Essenciais:
  JTable dentro de um JScrollPane: Para exibir os dados. O JScrollPane é crucial para que os cabeçalhos da tabela sejam exibidos e para permitir a rolagem caso haja muitos registros.
  DefaultTableModel: O "cérebro" da JTable. É este modelo que será conectado à nossa ArrayList de dados. Qualquer alteração na lista deve ser refletida aqui para que a tabela se atualize.
  JButton "Incluir":
    Ação: Abre uma nova instância da janela FormCadastro em modo de criação (sem dados preenchidos).
    A janela principal deve aguardar o fechamento da FormCadastro para então atualizar a JTable com o novo registro.
  JButton "Alterar":
    Ação: Primeiro, verifica se uma linha na JTable foi selecionada. Se não, exibe uma mensagem de aviso.
    Se uma linha estiver selecionada, obtém o objeto Pessoa correspondente da ArrayList.
    Abre a FormCadastro passando este objeto para que os campos já venham preenchidos.
    Após o fechamento, atualiza a JTable.
  JButton "Excluir":
    Ação: Verifica se uma linha foi selecionada.
    Exibe uma caixa de diálogo de confirmação (JOptionPane.showConfirmDialog) para evitar exclusões acidentais.
    Se o usuário confirmar, remove o objeto da ArrayList e atualiza a JTable.
  JButton "Consultar":
    Ação: Funciona de forma similar ao "Alterar", mas abre a FormCadastro em um modo "somente leitura". Os campos virão preenchidos, mas estarão desabilitados para edição (setEditable(false)), e o botão "Salvar" estará oculto ou desabilitado.
  JButton "Fechar":
    Ação: Fecha a aplicação (System.exit(0)).

3. A Janela de Cadastro (FormCadastro)
Esta janela é um JDialog modal. Ser "modal" significa que ela bloqueia a interação com a janela principal enquanto estiver aberta, forçando o usuário a concluir a ação (salvar ou cancelar).
  Design Reutilizável: Esta única janela pode ser configurada para três finalidades diferentes:
  Modo Inclusão: Título "Incluir Novo Registro". Campos vazios.
  Modo Alteração: Título "Alterar Registro". Campos preenchidos com os dados do objeto selecionado.
  Modo Consulta: Título "Consultar Registro". Campos preenchidos e bloqueados. O botão "Salvar" pode ter seu texto alterado para "Fechar" ou ser simplesmente removido.
Componentes Essenciais:
  JTextFields, JFormattedTextFields, etc.: Campos para a entrada de dados do usuário.
  JButton "Salvar" (ou "Confirmar"):
    Ação: Realiza a validação dos campos (ex: verifica se não estão vazios).
    Se a validação passar, executa a operação correspondente (adicionar um novo objeto à ArrayList ou atualizar os atributos do objeto existente).
    Fecha a janela de diálogo (dispose()).
  JButton "Cancelar":
    Ação: Simplesmente fecha a janela de diálogo (dispose()) sem salvar nenhuma alteração.
   
