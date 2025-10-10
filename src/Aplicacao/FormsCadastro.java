package Aplicacao;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormsCadastro extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtFieldEmail;
	private JTextField txtFieldNome;
	private JButton btnCancelar;
	private JButton btnSalvar;
	private FormsPrincipal principal;
	private Pessoa pessoaAtual; 
	private boolean isEditMode; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormsCadastro frame = new FormsCadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public FormsCadastro(FormsPrincipal principal) {
		this(principal, null, true); 
	}
	
	public FormsCadastro(FormsPrincipal principal, Pessoa pessoa, boolean isEditMode) {
		super(principal, pessoa == null ? "Incluir Nova Pessoa" : (isEditMode ? "Alterar Pessoa" : "Consultar Pessoa"), true);
		
		this.principal = principal;
		this.pessoaAtual = pessoa;
		this.isEditMode = isEditMode;
	
		InitializeComponents();
		ConfigurarEventos();
		
		aplicarModo();
		
		setLocationRelativeTo(principal);
	}
	
	public FormsCadastro() {
		this(null, null, true);
	}
	
	private void aplicarModo() {
		if (pessoaAtual != null) {
			
			txtFieldNome.setText(pessoaAtual.getNomePessoa());
			txtFieldEmail.setText(pessoaAtual.getEmailPessoa());
			
			if (!isEditMode) {
				txtFieldNome.setEditable(false);
				txtFieldEmail.setEditable(false);
				btnSalvar.setText("FECHAR");
				btnCancelar.setVisible(false); 
			}
		}
	}
	
	public void InitializeComponents() {
		setResizable(false);
		setTitle("Forms Cadastro");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtFieldEmail = new JTextField();
		txtFieldEmail.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFieldEmail.setBounds(148, 156, 482, 32);
		contentPane.add(txtFieldEmail);
		txtFieldEmail.setColumns(10);
		
		txtFieldNome = new JTextField();
		txtFieldNome.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtFieldNome.setColumns(10);
		txtFieldNome.setBounds(148, 113, 482, 32);
		contentPane.add(txtFieldNome);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Verdana", Font.PLAIN, 16));
		lblNome.setBounds(92, 120, 57, 14);
		contentPane.add(lblNome);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Verdana", Font.PLAIN, 16));
		lblEmail.setBounds(92, 163, 57, 14);
		contentPane.add(lblEmail);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCancelar.setBounds(362, 219, 129, 47);
		contentPane.add(btnCancelar);
		
		btnSalvar = new JButton("SALVAR");
		btnSalvar.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnSalvar.setBounds(501, 219, 129, 47);
		contentPane.add(btnSalvar);
		
		JLabel lblTituloCadastro = new JLabel("CADASTRO DE USUÁRIOS");
		lblTituloCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloCadastro.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblTituloCadastro.setBounds(28, 55, 602, 38);
		contentPane.add(lblTituloCadastro);

	}
	
	public void ConfigurarEventos() {
		FecharCadastro();
		SalvarCadastro();
	}
	
	public void FecharCadastro() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void SalvarCadastro() {
		btnSalvar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(!isEditMode) {
				dispose();
				return;
			}
			
			String nome = txtFieldNome.getText().trim();
			String email = txtFieldEmail.getText().trim();
			
			if(nome.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(FormsCadastro.this, "Preencha o Nome e/ou Email corretamenta para salvar!", 
						"Erro ao salvar cadastro", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(pessoaAtual == null) {
				Pessoa novaPessoa = new Pessoa(nome, email);
				principal.incluirPessoa(novaPessoa);
			} else {
				pessoaAtual.setNomePessoa(nome);
				pessoaAtual.setEmailPessoa(email);
				principal.atualizarPessoa(pessoaAtual);
			}
			dispose();
		}
	});
	}
}
