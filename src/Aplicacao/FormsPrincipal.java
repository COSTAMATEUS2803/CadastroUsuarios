package Aplicacao;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class FormsPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnFechar;
	private JButton btnConsultar;
	private JButton btnExcluir;
	private JButton btnAlterar;
	private JButton btnIncluir;
	private JTable tabelaCadastrados;
	/**
	 * Launch the application.
	 */
	
	private DataLayer dataLayer = new DataLayer();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormsPrincipal frame = new FormsPrincipal();
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
	public FormsPrincipal() {
		InitializeComponents();		
		ConfigurarEventos();
		carregarTabela();
		setLocationRelativeTo(null);
	}
	
	public void InitializeComponents() {
		setResizable(false);
		setTitle("Forms Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnFechar = new JButton("FECHAR");
		btnFechar.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnFechar.setBounds(722, 487, 129, 47);
		contentPane.add(btnFechar);
		
		btnConsultar = new JButton("CONSULTAR");
		btnConsultar.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnConsultar.setBounds(583, 487, 129, 47);
		contentPane.add(btnConsultar);
		
		btnExcluir = new JButton("EXCLUIR");
		btnExcluir.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnExcluir.setBounds(444, 487, 129, 47);
		contentPane.add(btnExcluir);
		
		btnAlterar = new JButton("ALTERAR");
		btnAlterar.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnAlterar.setBounds(305, 487, 129, 47);
		contentPane.add(btnAlterar);
		
		btnIncluir = new JButton("INCLUIR");
		btnIncluir.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnIncluir.setBounds(166, 487, 129, 47);
		contentPane.add(btnIncluir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 92, 816, 384);
		contentPane.add(scrollPane);
		
		tabelaCadastrados = new JTable();
		
		tabelaCadastrados.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nome Cadastrado", "Email Cadastrado"
			}
		));
		tabelaCadastrados.setFont(new Font("Verdana", Font.PLAIN, 14));
		tabelaCadastrados.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tabelaCadastrados);
		
		JLabel lblTituloFormsPrincipal = new JLabel("USUÁRIOS CADASTRADOS NO SISTEMA");
		lblTituloFormsPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloFormsPrincipal.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblTituloFormsPrincipal.setBounds(35, 34, 816, 47);
		contentPane.add(lblTituloFormsPrincipal);
	}
	
	public void ConfigurarEventos() {
		fecharAplicacao();
		abrirCadastro();
		alterarCadastro();
		excluirCadastro();
		consultarCadastro();
	}
	
	public void incluirPessoa(Pessoa novaPessoa) {
		dataLayer.adicionarPessoa(novaPessoa);
		carregarTabela();
		JOptionPane.showMessageDialog(this, "Pessoa incluída com sucesso!", 
				"Inclusão", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void atualizarPessoa(Pessoa pessoaEditada) {
		dataLayer.atualizarPessoa(pessoaEditada);
		carregarTabela();
		JOptionPane.showMessageDialog(this, "Pessoa alterada com sucesso!", 
				"Alteração", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void carregarTabela() {
		DefaultTableModel model = (DefaultTableModel) tabelaCadastrados.getModel();
		model.setRowCount(0);
		
		ArrayList<Pessoa> lista = (ArrayList<Pessoa>) dataLayer.listarPessoa();
		
		for(Pessoa p : lista) {
			model.addRow(new Object[]{
				p.getIdPessoa(), p.getNomePessoa(), p.getEmailPessoa()
			});
		}
	}
	
	public void fecharAplicacao() {
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void abrirCadastro() {
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormsCadastro telaCadastro = new FormsCadastro(FormsPrincipal.this);
				telaCadastro.setVisible(true);
			}
		});
	}
	
	public void alterarCadastro() {
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verificarLinhaSelecionada("alterar")) {
					int linha = tabelaCadastrados.getSelectedRow();
					int idSelecionado = (int) tabelaCadastrados.getValueAt(linha, 0); 
					Pessoa pessoaParaEditar = dataLayer.buscarPessoaPorId(idSelecionado);
					
					if(pessoaParaEditar != null) {
						FormsCadastro telaCadastro = new FormsCadastro(FormsPrincipal.this, pessoaParaEditar, true);
						telaCadastro.setVisible(true);
					}
				}
			}
		});
	}
	
	public void excluirCadastro() {
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verificarLinhaSelecionada("excluir")) {
					int linha = tabelaCadastrados.getSelectedRow();

					int idSelecionado = (int) tabelaCadastrados.getValueAt(linha, 0);
					String nomeSelecionado = (String) tabelaCadastrados.getValueAt(linha, 1);
					
					int confirmacao = JOptionPane.showConfirmDialog(FormsPrincipal.this, 
						"Tem certeza que deseja excluir " + nomeSelecionado + " (Cód: " + idSelecionado + ")?", 
						"Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
					
					if (confirmacao == JOptionPane.YES_OPTION) {
						if (dataLayer.removerPessoa(idSelecionado)) {
							carregarTabela();
							JOptionPane.showMessageDialog(FormsPrincipal.this, "Pessoa excluída com sucesso!", 
														   "Exclusão", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(FormsPrincipal.this, "Erro ao excluir pessoa.", 
														   "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
	}
	
	public void consultarCadastro() {
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verificarLinhaSelecionada("consultar")) {
					int linha = tabelaCadastrados.getSelectedRow();
					int idSelecionado = (int) tabelaCadastrados.getValueAt(linha, 0);
					Pessoa pessoaParaConsulta = dataLayer.buscarPessoaPorId(idSelecionado);
	
					if (pessoaParaConsulta != null) {
						FormsCadastro telaCadastro = new FormsCadastro(FormsPrincipal.this, pessoaParaConsulta, false);
						telaCadastro.setVisible(true);
					}
				}
			}
		});
	}
	
	public boolean verificarLinhaSelecionada(String operacao) {
		int linhaSelecionada = tabelaCadastrados.getSelectedRow();
		if(linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(this, "Selecione uma linha para " + operacao + "!", 
													"Erro ao encontrar cadastro", JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}
}
