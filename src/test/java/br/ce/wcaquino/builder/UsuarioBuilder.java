package br.ce.wcaquino.builder;

import br.ce.wcaquino.entidades.Usuario;

public class UsuarioBuilder {
	
	
	private Usuario usuario;
	
	private UsuarioBuilder() { // É privado para que ninguém possa criar algum builder, externo a esse arquivo
		
		
	}
	
	public static UsuarioBuilder umUsuario(){ // Public e Static = Para poder ser usado sem precisar instaciar.
		UsuarioBuilder builder = new UsuarioBuilder(); // Esse método cria a instância o um objeto
		builder.usuario = new Usuario(); //Inicializando a construção do usuário
		builder.usuario.setNome("Usuário 1"); //Povoando o usuário
		return builder;
		
	}
	
	public Usuario agora() {
		return usuario;
	}
	

}
