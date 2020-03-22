package br.ce.wcaquino.builder;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import buildermaster.BuilderMaster;

public class FilmeBuilder {
	
private Filme filme;
	
	
//Método de entrada
//	Cenário
private FilmeBuilder() { // É privado para que ninguém possa criar algum builder, externo a esse arquivo
		
	}
	
	public static FilmeBuilder umFilme(){ // Public e Static = Para poder ser usado sem precisar instaciar.
		FilmeBuilder builder = new FilmeBuilder(); // Esse método cria a instância o um objeto
		builder.filme = new Filme(); //Inicializando a construção do usuário
		builder.filme.setNome("Usuário 1"); //Povoando
		builder.filme.setEstoque(2); //Povoando
		builder.filme.setPrecoLocacao(5.0); //Povoando
		return builder;

	}
	
	//Cenário
	// Um método para filme sem estoque já
	private FilmeBuilder umFilmeSemEstoque() {
		FilmeBuilder builder = new FilmeBuilder(); // Esse método cria a instância o um objeto
		builder.filme = new Filme(); //Inicializando a construção do usuário
		builder.filme.setNome("Usuário 1"); //Povoando
		builder.filme.setEstoque(0); //Povoando
		builder.filme.setPrecoLocacao(5.0); //Povoando
		return builder;

		
	}
	
	public FilmeBuilder semEstoque() {
		filme.setEstoque(0);

		return this;
	}
	
	public FilmeBuilder comValor(Double valor) {
		filme.setPrecoLocacao(valor);
		return this;
	}// Esse método só será usado se eu precisa alterar o valor
	//Posso criar outros métodos para alterar outros dados do método principal.
	
	public Filme agora() {
		return filme;
	}
	
	
	//Esse método de encadeamento dos métodos é chamado de changeBuilder e termino o encadeamento com
	// a função agora.
	
	
	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}
	
	
	

}
