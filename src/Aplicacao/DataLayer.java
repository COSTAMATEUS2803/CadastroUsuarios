package Aplicacao;

import java.util.ArrayList;

public class DataLayer {
	
	private static ArrayList<Pessoa> PessoasCadastradas = new ArrayList<>();
	
	public ArrayList<Pessoa> listarPessoa(){
		return PessoasCadastradas;
	}
	
	public void adicionarPessoa(Pessoa p) {
		PessoasCadastradas.add(p);
	}
	
	public boolean atualizarPessoa(Pessoa pessoaAtualizada) {
		for(int i = 0; i < PessoasCadastradas.size(); i++) {
			Pessoa p = PessoasCadastradas.get(i);
			
			if(p.getIdPessoa() == pessoaAtualizada.getIdPessoa()) {
				p.setNomePessoa(pessoaAtualizada.getNomePessoa());
				p.setEmailPessoa(pessoaAtualizada.getEmailPessoa());
				return true;
			}
		}
		return false;
	}
	
	public boolean removerPessoa(int id) {
		return PessoasCadastradas.removeIf(p -> p.getIdPessoa() == id);
	}
	
	public Pessoa buscarPessoaPorId(int id) {
		for(Pessoa p : PessoasCadastradas) {
			if(p.getIdPessoa() == id) {
				return p;
			} 
		}
		return null;
	}
}
