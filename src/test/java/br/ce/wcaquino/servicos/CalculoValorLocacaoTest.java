package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.daos.LocacaoDAOFake;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;



//vídeo16 - MUITO IMPORTANTE
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService service;
	private SPCService spc;
	private LocacaoDAO dao;

	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)//Valor 1, pois a lista de filmes (acima) começa em zero
	public Double valorLocacao;

	@Parameter(value=2)
	public String cenario;
	@Before
	public void setup() {
		service = new LocacaoService();
		dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		spc = Mockito.mock(SPCService.class);
		service.setSPCService(spc);
	}
	
	
	private static Filme filme1 = FilmeBuilder.umFilme().agora();	
	private static Filme filme2 = FilmeBuilder.umFilme().agora();	
	private static Filme filme3 = FilmeBuilder.umFilme().agora();	
	private static Filme filme4 = FilmeBuilder.umFilme().agora();	
	private static Filme filme5 = FilmeBuilder.umFilme().agora();	
	private static Filme filme6 = FilmeBuilder.umFilme().agora();	
	
	
//	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
//	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
//	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
//	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
//	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
//	private static Filme filme6 = new Filme("Filme 6", 2, 4.0);
//	
	@Parameters   (name="{2}")//(name="Teste{index}= {0}-{1}")
	public static Collection<Object[]> getPametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2, filme3), 11.0,"3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0,"4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"}
		});
	}

	@Test
	public void deveCalcularValorLocacaoComDesconto() throws FilmeSemEstoqueException, LocadoraException {

		Usuario usuario = new Usuario("Usuario 1");

		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificação

		assertThat(resultado.getValor(), is(valorLocacao));

	}
	
	@Test
	public void print() {
		System.out.println(valorLocacao);
	}

}
