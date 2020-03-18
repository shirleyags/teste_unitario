package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService  {
	
	


	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException{
		
		
		for(Filme filme:filmes) { // A cada um filme da lista filmes, fa�a o que pede abaixo:
		if(filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException();
		}}
		
		if(usuario == null) {
			throw new LocadoraException("Usu�rio n�o existente");
		}
		
		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme n�o existente");
		}
		
		
		
		
		
		
		
		
		
		
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		// Que c�digo maravilhoso!
		//Crie uma vari�vel para receber total de filmes
		//Fa�a um "for" pela lista de filmes e assim conseguir� fazer o get de cada um.
		Double valorTotal = 0d; 
		for(Filme filme:filmes) {
			valorTotal +=filme.getPrecoLocacao();
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		
		
		return locacao;
	}

	
}