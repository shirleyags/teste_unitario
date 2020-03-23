package br.ce.wcaquino.builder;

import br.ce.wcaquino.entidades.Usuario;

public class UsuarioBuilder {
	
	
	private Usuario usuario;
	
	private UsuarioBuilder() { // � privado para que ningu�m possa criar algum builder, externo a esse arquivo
		
		
	}
	
	public static UsuarioBuilder umUsuario(){ // Public e Static = Para poder ser usado sem precisar instaciar.
		UsuarioBuilder builder = new UsuarioBuilder(); // Esse m�todo cria a inst�ncia o um objeto
		builder.usuario = new Usuario(); //Inicializando a constru��o do usu�rio
		builder.usuario.setNome("Usu�rio 1"); //Povoando o usu�rio
		return builder;
		
	}
	
	public UsuarioBuilder comNome(String nome) { //V�deo 4/Mockito - Cadastrar outro usu�rio no arquivo LocacaoServiceTest 
		usuario.setNome(nome);
		return this;
	}
	
	public Usuario agora() {
		return usuario;
	}
	

}
