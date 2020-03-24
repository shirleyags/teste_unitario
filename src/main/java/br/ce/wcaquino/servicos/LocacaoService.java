package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.mockito.Mock;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {

	
	private LocacaoDAO dao;
	
	private SPCService spcService;
	
	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usu�rio n�o existente");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme n�o existente");
		}

		for (Filme filme : filmes) { // A cada um filme da lista filmes, fa�a o que pede abaixo:
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}

		if (spcService.possuiNegativacao(usuario)) {
			throw new LocadoraException("Usu�rio negativado");
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		// Crie uma vari�vel para receber total de filmes
		Double valorTotal = 0d; // Forma para c�lculo do desconto:
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			switch (i) {
			case 2:
				valorFilme = valorFilme * 0.75;
				break;
			case 3:
				valorFilme = valorFilme * 0.50;
				break;
			case 4:
				valorFilme = valorFilme * 0.25;
				break;
			case 5:
				valorFilme = valorFilme * 0d;
				break;
			}
//			if(i == 2) {
//				valorFilme = valorFilme * 0.75;
//			}
//			if(i == 3) {
//				valorFilme = valorFilme * 0.50;
//			}
//			if(i == 4) {
//				valorFilme = valorFilme * 0.25;
//			}
//			if(i == 5) {
//				valorFilme = valorFilme * 0d;
//			}
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);

		dao.salvar(locacao);

		return locacao;
	}

	public void notificarAtrasos() {
		List<Locacao> locacoes = dao.obterLocacoesPendentes();
		for (Locacao locacao : locacoes) {
			if(locacao.getDataRetorno().before(new Date()))
				emailService.notificarAtraso(locacao.getUsuario());
		}
	}

	//	public void setLocacaoDAO(LocacaoDAO dao) {
	//		this.dao = dao;
	//
	//	}	
	//
	//	public void setEmail(EmailService email) {
	//		
	//		emailService = email;
	//
	//	}
	//
	//	public void setSPCService(SPCService spc) { // Est� injetado
	//		spcService = spc;
	//	}

}