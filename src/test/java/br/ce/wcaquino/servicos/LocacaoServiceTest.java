package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable.BinaryOp.UMulHigh;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.builder.LocacaoBuilder;
import br.ce.wcaquino.builder.UsuarioBuilder;
import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	
	@InjectMocks
	private LocacaoService service; //Para o before e o after funcionarem para todos os testes dessa classe
	
	
	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAO dao;
	@Mock
	private EmailService email;

	
	
	
	//Definição do contador
	//private static int contador =0; // Quando coloco que é static passa para o escopo da classe, não do método/teste
	
	@Rule
	public ErrorCollector error = new ErrorCollector(); // Para que todos os erros apareçam no console, não um de cada vez.

	@Rule
	public ExpectedException exception = ExpectedException.none(); // 4)Da forma nova

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
//		service = new LocacaoService(); //Só o nome da classe que está declarada em cima.
//		dao = Mockito.mock(LocacaoDAO.class);
//		service.setLocacaoDAO(dao);
//		spc = Mockito.mock(SPCService.class);
//		service.setSPCService(spc);
//		email = Mockito.mock(EmailService.class);
//		service.setEmail(email);

		
	
//			contador++;
//			System.out.println(contador);

		
		
		//Incremento
		//Impressão do contador

	}
//	@After
//	public void tearDown() {
//		System.out.println("After");
//	}
	
	//Essa opção é para antes da classe e depois dela ser instaciada.
	//Não vai se aplicar antes e depois de cada método de teste.
	
//	@BeforeClass
//	public static void setupClass() { //Lembre do static
//		System.out.println("BeforeClass");
//	}
//	
//	@AfterClass
//	public static void tearDownClass() { //Lembre do static
//		System.out.println("AfterClass");
//	}
	
	
	
	// ----------------- |TDD vídeo 13
	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// Instancie a classe que vc quer testar
		//Usuario usuario = new Usuario("Usuario 1");
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());		

		//List <Filme>filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// Ação

		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificação
		error.checkThat(locacao.getValor(), is(equalTo(4.0))); // Fazer o import estático//Botão direito - sourse -
																// "add import"
	
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(),MatchersProprios.ehHoje());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1)); // Fazer o import estático//Botão direito - sourse -
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void deveLancarExcecaoDeFilmeSemEstoque() throws Exception {

		// Cenário - onde as variáveis serão inicializadas
		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
	// usuario, Filme filme)

		// Instancie a classe que vc quer testar
		
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		//Usuario usuario = new Usuario("Usuario 1");
		//List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 4.0));
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());		


		// Ação

		service.alugarFilme(usuario, filmes);

	}
	
	
	@Test()
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {

		// Cenário - onde as variáveis serão inicializadas
		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
		// usuario, Filme filme)

		// Instancie a classe que vc quer testar
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());		
		//Através do Array.asList tudo que for passado por parâmetro
		//vai ser transfoprmado em um item de uma lista 
		//System.out.println("Teste!");


		// Ação
			try {
				service.alugarFilme(null,filmes);
				Assert.fail();
			} catch (LocadoraException e) {
				Assert.assertThat(e.getMessage(),is("Usuário não existente"));
			}
	}
	
	
	//Forma 4
	@Test()
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {

		// Cenário 
		Usuario usuario = UsuarioBuilder.umUsuario().agora();	
		exception.expect(LocadoraException.class);	
		exception.expectMessage("Filme não existente");

		// Ação

		service.alugarFilme(usuario,null);	

	}
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
			// TESTES PARA VERIFICAR DESCONTOS DE ACORDO COM A QUANTIDADE DE PROTUDOS COMPRADOS	- Vídeo 14
			//EXISTE UMA FORMA SIMPLIFICADA NO ARQUIVO "CalculoValorLocacaoTest"
	
//	@Test
//	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
//		//cenário
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),new Filme("Filme 2", 2, 4.0),new Filme("Filme 3", 2, 4.0));
//
//		//ação
//		//Não precisa instaciar o objeto, pois já está no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verificação
//		
//		assertThat(resultado.getValor(), is(11.00));
//
//		
//	}
//	
//	@Test
//	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
//		//cenário
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),new Filme("Filme 2", 2, 4.0),new Filme("Filme 3", 2, 4.0),new Filme("Filme 1", 2, 4.0));
//
//		//ação
//		//Não precisa instaciar o objeto, pois já está no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verificação
//		
//		assertThat(resultado.getValor(), is(13.00));
//
//	}
//	
//	@Test
//	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
//		//cenário
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), 
//				new Filme("Filme 1", 2, 4.0),new Filme("Filme 2", 2, 4.0),
//				new Filme("Filme 3", 2, 4.0),new Filme("Filme 1", 2, 4.0));
//
//		//ação
//		//Não precisa instaciar o objeto, pois já está no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verificação
//		
//		assertThat(resultado.getValor(), is(14.00));
//
//	}
//	
//	@Test
//	public void devePagar100PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
//		//cenário
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), 
//				new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0),
//				new Filme("Filme 4", 2, 4.0),new Filme("Filme 5", 2, 4.0));
//			
//		//ação
//		//Não precisa instaciar o objeto, pois já está no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verificação
//		
//		assertThat(resultado.getValor(), is(14.00));
//
//	}
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	
	@Test
	// @Ignore - Para pular um teste
	public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));//Esse código só roda no sábado.
		
		//cenário
		//Preciso de um usuário e um filme ao menos pra isso.
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());		
		
		//ação
		Locacao retorno = service.alugarFilme(usuario, filmes);	
		
		//verificação
		
		//Vídeo 17
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
		//assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
//		assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
//		
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		
		//Cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		//Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuário2").agora();

		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());		
		
		
		Mockito.when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		//DESSA FORMA DE CIMA, INDEPENDENTE DE QUAL USUÁRIO FOR PASSADO O TESTE VAI ACONTECER
		
		//Ação
		try {
			service.alugarFilme(usuario, filmes);
			//Verificacao
			Assert.fail();	
		}catch(LocadoraException e){
			
			Assert.assertThat(e.getMessage(),is("Usuário negativado"));
		}
		
		//Verificação
		
		Mockito.verify(spc).possuiNegativacao(usuario);
	}
	
	@Test // Muito difícil de entender - Vídeo 5 de Mocks
	public void deveEnviarEmailLocacoesAtrasadas() {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuário do dia").agora();
		

		//Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario2").agora();

		//Cenário
		List<Locacao> locacoes = Arrays.asList(LocacaoBuilder.umLocacao()
				.atrasado()
				.comUsuario(usuario)
				.agora(),
				/*LocacaoBuilder.umLocacao()
				.atrasado()
				.comUsuario(usuario)
				.agora(),*/
				
				LocacaoBuilder.umLocacao().comUsuario(usuario2).agora());
		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		//Ação
		service.notificarAtrasos();
		
		
		//Verficação
		
		Mockito.verify(email).notificarAtraso(usuario);
		//Mockito.verify(email, Mockito.times(2)).notificarAtraso(usuario);
		//Mockito.verify(email, Mockito.atLeast(2)).notificarAtraso(usuario); - Deve ser enviado pelo menos dois emails.
		Mockito.verify(email, Mockito.never()).notificarAtraso(usuario2); //Para verificar se nunca ocorreu o envio para o usuario2
		Mockito.verifyNoMoreInteractions(email); // Mostra se nenhum outro email foi enviado que não seja
		//para o "usuário"
		Mockito.verifyZeroInteractions(spc);
				
	}
	
	
	
	@Test
	public void deveTratarErronoSPC() throws Exception {
		
		//Cenário
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		
		Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha Catastrófica"));
		
		//Verificação

		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com o SPC, tente novamente.");

		
		
		//Ação
		
		service.alugarFilme(usuario, filmes);
		
		
	}
	
	
	
	
	}
	
	

	
	
	

	
	
	
	
	
	//Teste com lista
//	@Test()
//	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
//
//		// Cenário - onde as variáveis serão inicializadas
//		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
//		// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0)); 
//		//Através do Array.asList tudo que for passado por parâmetro
//		//vai ser transfoprmado em um item de uma lista 
//		//System.out.println("Teste!");
//
//
//
//		// Ação
//			try {
//				service.alugarFilme(null,filmes);
//				Assert.fail();
//			} catch (LocadoraException e) {
//				Assert.assertThat(e.getMessage(),is("Usuário não existente"));
//			}
//	}

	//
//	@Test
//	public void testeDeLocacao() throws Exception {
//		// Cenário - onde as variáveis serão inicializadas
//		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
//		// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 2, 5.0);
//
//		// Ação
//
//		Locacao locacao = service.alugarFilme(usuario, filme);
//
//		// Verificação
//		error.checkThat(locacao.getValor(), is(equalTo(5.0))); // Fazer o importe estático//Botão direito - sourse -
//																// "add import"
//		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
//		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//
//	}

//	//OPÇÃO 4 FORMA NOVA-
//
//	@Test()
//	public void testLocacao_filmeSemEstoque_3() throws Exception {
//
//		// Cenário - onde as variáveis serão inicializadas
//		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
//	// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar	
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 5, 5.0);
//		
//		
//		exception.expect(Exception.class);		
//		exception.expectMessage("Filme sem estoque");
//
//		// Ação
//
//		service.alugarFilme(usuario, filme);	
//
//	}

//	//OPÇÃO 3 - FORMA ROBUSTA DE TRATAR EXCEÇÕES
//
//	@Test()
//	public void testLocacao_filmeSemEstoque_2(){
//
//		// Cenário - onde as variáveis serão inicializadas
//		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
//		// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0, 5.0);
//					
//		// Ação
//			try {
//			 service.alugarFilme(usuario, filme);
//			 Assert.fail("Deveria ter lançado uma exceção");
//			 
//			 } catch(Exception e){	
//				 Assert.assertThat(e.getMessage(),is("Filme sem estoque."));	
//			 }
//					
//		}

//	//OPÇÃO 2 - CHAMADA  DE FORMA ELEGANTE DE TRATAR ERROS
//
//	@Test(expected=Exception.class)
//	public void testLocacao_filmeSemEstoque() throws Exception {
//
//		// Cenário - onde as variáveis serão inicializadas
//		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
//	// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 5, 5.0);
//
//		// Ação
//
//		service.alugarFilme(usuario, filme);
//
//	}

	// OPÇÃO 1) Uma forma de fazer erros ou fallhas aparecerem
//		Locacao locacao;
//		try {
//			locacao = service.alugarFilme(usuario, filme);
//			//Verificação
//			error.checkThat(locacao.getValor(),is(equalTo(5.0))); //Fazer o importe estático//Botão direito - sourse - "add import"
//			error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),is(true));		
//			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//			
//			
//		}catch (Exception e){
//			e.printStackTrace();
//			Assert.fail("Não deveria lançar exceção");
//			
//	     }

	// Verificação
	// Assert.assertEquals(5.0, locacao.getValor(), 0.01);
//		assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
//		assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

//		assertThat(locacao.getValor(),is(equalTo(5.0))); //Fazer o importe estático//Botão direito - sourse - "add import"
//		assertThat(locacao.getValor(),is(not(6.0))); //Verifique que o valor da locação não é 6
//		
//		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),is(true));		
//		assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//		
	// Precisa usar error.checkThat, no lugar de assertThat
	// Junto, lá em cima, @Rule public ErrorCollector error = new ErrorCollector();
	// error.checkThat(locacao.getValor(),is(not(6.0))); //Verifique que o valor da
	// locação não é 6
	
	
	
	
	
	
	
	
	
	
