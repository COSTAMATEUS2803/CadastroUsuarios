	package Aplicacao;
	
	public class Pessoa {
	
		private int IdPessoa;
		private String NomePessoa;
		private String EmailPessoa;
		private static int proximoId = 1;
		
		public int getIdPessoa() {
			return IdPessoa;
		}
		public void setIdPessoa(int idPessoa) {
			IdPessoa = idPessoa;
		}
		public String getNomePessoa() {
			return NomePessoa;
		}
		public void setNomePessoa(String nomePessoa) {
			NomePessoa = nomePessoa;
		}
		public String getEmailPessoa() {
			return EmailPessoa;
		}
		public void setEmailPessoa(String emailPessoa) {
			EmailPessoa = emailPessoa;
		}
		
		public Pessoa(String nomePessoa, String emailPessoa) {
			this.IdPessoa = proximoId++;
			this.NomePessoa = nomePessoa;
			this.EmailPessoa = emailPessoa;
		}
		@Override
		public String toString() {
			return "Pessoa [IdPessoa=" + IdPessoa + ", NomePessoa=" + NomePessoa + ", EmailPessoa=" + EmailPessoa + "]";
		}
	}
