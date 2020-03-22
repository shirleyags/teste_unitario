package br.ce.wcaquino.builder;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import buildermaster.BuilderMaster;

public class FilmeBuilder {
	
private Filme filme;
	
	
//M�todo de entrada
//	Cen�rio
private FilmeBuilder() { // � privado para que ningu�m possa criar algum builder, externo a esse arquivo
		
	}
	
	public static FilmeBuilder umFilme(){ // Public e Static = Para poder ser usado sem precisar instaciar.
		FilmeBuilder builder = new FilmeBuilder(); // Esse m�todo cria a inst�ncia o um objeto
		builder.filme = new Filme(); //Inicializando a constru��o do usu�rio
		builder.filme.setNome("Usu�rio 1"); //Povoando
		builder.filme.setEstoque(2); //Povoando
		builder.filme.setPrecoLocacao(5.0); //Povoando
		return builder;

	}
	
	//Cen�rio
	// Um m�todo para filme sem estoque j�
	private FilmeBuilder umFilmeSemEstoque() {
		FilmeBuilder builder = new FilmeBuilder(); // Esse m�todo cria a inst�ncia o um objeto
		builder.filme = new Filme(); //Inicializando a constru��o do usu�rio
		builder.filme.setNome("Usu�rio 1"); //Povoando
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
	}// Esse m�todo s� ser� usado se eu precisa alterar o valor
	//Posso criar outros m�todos para alterar outros dados do m�todo principal.
	
	public Filme agora() {
		return filme;
	}
	
	
	//Esse m�todo de encadeamento dos m�todos � chamado de changeBuilder e termino o encadeamento com
	// a fun��o agora.
	
	
	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}
	
	
	

}
