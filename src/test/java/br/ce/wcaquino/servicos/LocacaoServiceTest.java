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

	
	
	
	//Defini��o do contador
	//private static int contador =0; // Quando coloco que � static passa para o escopo da classe, n�o do m�todo/teste
	
	@Rule
	public ErrorCollector error = new ErrorCollector(); // Para que todos os erros apare�am no console, n�o um de cada vez.

	@Rule
	public ExpectedException exception = ExpectedException.none(); // 4)Da forma nova

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
//		service = new LocacaoService(); //S� o nome da classe que est� declarada em cima.
//		dao = Mockito.mock(LocacaoDAO.class);
//		service.setLocacaoDAO(dao);
//		spc = Mockito.mock(SPCService.class);
//		service.setSPCService(spc);
//		email = Mockito.mock(EmailService.class);
//		service.setEmail(email);

		
	
//			contador++;
//			System.out.println(contador);

		
		
		//Incremento
		//Impress�o do contador

	}
//	@After
//	public void tearDown() {
//		System.out.println("After");
//	}
	
	//Essa op��o � para antes da classe e depois dela ser instaciada.
	//N�o vai se aplicar antes e depois de cada m�todo de teste.
	
//	@BeforeClass
//	public static void setupClass() { //Lembre do static
//		System.out.println("BeforeClass");
//	}
//	
//	@AfterClass
//	public static void tearDownClass() { //Lembre do static
//		System.out.println("AfterClass");
//	}
	
	
	
	// ----------------- |TDD v�deo 13
	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// Instancie a classe que vc quer testar
		//Usuario usuario = new Usuario("Usuario 1");
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());		

		//List <Filme>filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// A��o

		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verifica��o
		error.checkThat(locacao.getValor(), is(equalTo(4.0))); // Fazer o import est�tico//Bot�o direito - sourse -
																// "add import"
	
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(),MatchersProprios.ehHoje());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1)); // Fazer o import est�tico//Bot�o direito - sourse -
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void deveLancarExcecaoDeFilmeSemEstoque() throws Exception {

		// Cen�rio - onde as vari�veis ser�o inicializadas
		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
	// usuario, Filme filme)

		// Instancie a classe que vc quer testar
		
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		//Usuario usuario = new Usuario("Usuario 1");
		//List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 4.0));
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());		


		// A��o

		service.alugarFilme(usuario, filmes);

	}
	
	
	@Test()
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {

		// Cen�rio - onde as vari�veis ser�o inicializadas
		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
		// usuario, Filme filme)

		// Instancie a classe que vc quer testar
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());		
		//Atrav�s do Array.asList tudo que for passado por par�metro
		//vai ser transfoprmado em um item de uma lista 
		//System.out.println("Teste!");


		// A��o
			try {
				service.alugarFilme(null,filmes);
				Assert.fail();
			} catch (LocadoraException e) {
				Assert.assertThat(e.getMessage(),is("Usu�rio n�o existente"));
			}
	}
	
	
	//Forma 4
	@Test()
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {

		// Cen�rio 
		Usuario usuario = UsuarioBuilder.umUsuario().agora();	
		exception.expect(LocadoraException.class);	
		exception.expectMessage("Filme n�o existente");

		// A��o

		service.alugarFilme(usuario,null);	

	}
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
			// TESTES PARA VERIFICAR DESCONTOS DE ACORDO COM A QUANTIDADE DE PROTUDOS COMPRADOS	- V�deo 14
			//EXISTE UMA FORMA SIMPLIFICADA NO ARQUIVO "CalculoValorLocacaoTest"
	
//	@Test
//	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
//		//cen�rio
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),new Filme("Filme 2", 2, 4.0),new Filme("Filme 3", 2, 4.0));
//
//		//a��o
//		//N�o precisa instaciar o objeto, pois j� est� no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verifica��o
//		
//		assertThat(resultado.getValor(), is(11.00));
//
//		
//	}
//	
//	@Test
//	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
//		//cen�rio
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),new Filme("Filme 2", 2, 4.0),new Filme("Filme 3", 2, 4.0),new Filme("Filme 1", 2, 4.0));
//
//		//a��o
//		//N�o precisa instaciar o objeto, pois j� est� no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verifica��o
//		
//		assertThat(resultado.getValor(), is(13.00));
//
//	}
//	
//	@Test
//	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
//		//cen�rio
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), 
//				new Filme("Filme 1", 2, 4.0),new Filme("Filme 2", 2, 4.0),
//				new Filme("Filme 3", 2, 4.0),new Filme("Filme 1", 2, 4.0));
//
//		//a��o
//		//N�o precisa instaciar o objeto, pois j� est� no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verifica��o
//		
//		assertThat(resultado.getValor(), is(14.00));
//
//	}
//	
//	@Test
//	public void devePagar100PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
//		//cen�rio
//		Usuario usuario = new Usuario("Usuario 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), 
//				new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0),
//				new Filme("Filme 4", 2, 4.0),new Filme("Filme 5", 2, 4.0));
//			
//		//a��o
//		//N�o precisa instaciar o objeto, pois j� est� no "Before"
//		
//		
//		Locacao resultado = service.alugarFilme(usuario, filmes);	
//		
//		//verifica��o
//		
//		assertThat(resultado.getValor(), is(14.00));
//
//	}
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	
	@Test
	// @Ignore - Para pular um teste
	public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));//Esse c�digo s� roda no s�bado.
		
		//cen�rio
		//Preciso de um usu�rio e um filme ao menos pra isso.
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());		
		
		//a��o
		Locacao retorno = service.alugarFilme(usuario, filmes);	
		
		//verifica��o
		
		//V�deo 17
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
		//assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
//		assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
//		
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		
		//Cen�rio
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		//Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usu�rio2").agora();

		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());		
		
		
		Mockito.when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		//DESSA FORMA DE CIMA, INDEPENDENTE DE QUAL USU�RIO FOR PASSADO O TESTE VAI ACONTECER
		
		//A��o
		try {
			service.alugarFilme(usuario, filmes);
			//Verificacao
			Assert.fail();	
		}catch(LocadoraException e){
			
			Assert.assertThat(e.getMessage(),is("Usu�rio negativado"));
		}
		
		//Verifica��o
		
		Mockito.verify(spc).possuiNegativacao(usuario);
	}
	
	@Test // Muito dif�cil de entender - V�deo 5 de Mocks
	public void deveEnviarEmailLocacoesAtrasadas() {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usu�rio do dia").agora();
		

		//Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario2").agora();

		//Cen�rio
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
		//A��o
		service.notificarAtrasos();
		
		
		//Verfica��o
		
		Mockito.verify(email).notificarAtraso(usuario);
		//Mockito.verify(email, Mockito.times(2)).notificarAtraso(usuario);
		//Mockito.verify(email, Mockito.atLeast(2)).notificarAtraso(usuario); - Deve ser enviado pelo menos dois emails.
		Mockito.verify(email, Mockito.never()).notificarAtraso(usuario2); //Para verificar se nunca ocorreu o envio para o usuario2
		Mockito.verifyNoMoreInteractions(email); // Mostra se nenhum outro email foi enviado que n�o seja
		//para o "usu�rio"
		Mockito.verifyZeroInteractions(spc);
				
	}
	
	
	
	@Test
	public void deveTratarErronoSPC() throws Exception {
		
		//Cen�rio
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		
		Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha Catastr�fica"));
		
		//Verifica��o

		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com o SPC, tente novamente.");

		
		
		//A��o
		
		service.alugarFilme(usuario, filmes);
		
		
	}
	
	
	
	
	}
	
	

	
	
	

	
	
	
	
	
	//Teste com lista
//	@Test()
//	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
//
//		// Cen�rio - onde as vari�veis ser�o inicializadas
//		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
//		// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0)); 
//		//Atrav�s do Array.asList tudo que for passado por par�metro
//		//vai ser transfoprmado em um item de uma lista 
//		//System.out.println("Teste!");
//
//
//
//		// A��o
//			try {
//				service.alugarFilme(null,filmes);
//				Assert.fail();
//			} catch (LocadoraException e) {
//				Assert.assertThat(e.getMessage(),is("Usu�rio n�o existente"));
//			}
//	}

	//
//	@Test
//	public void testeDeLocacao() throws Exception {
//		// Cen�rio - onde as vari�veis ser�o inicializadas
//		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
//		// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 2, 5.0);
//
//		// A��o
//
//		Locacao locacao = service.alugarFilme(usuario, filme);
//
//		// Verifica��o
//		error.checkThat(locacao.getValor(), is(equalTo(5.0))); // Fazer o importe est�tico//Bot�o direito - sourse -
//																// "add import"
//		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
//		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//
//	}

//	//OP��O 4 FORMA NOVA-
//
//	@Test()
//	public void testLocacao_filmeSemEstoque_3() throws Exception {
//
//		// Cen�rio - onde as vari�veis ser�o inicializadas
//		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
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
//		// A��o
//
//		service.alugarFilme(usuario, filme);	
//
//	}

//	//OP��O 3 - FORMA ROBUSTA DE TRATAR EXCE��ES
//
//	@Test()
//	public void testLocacao_filmeSemEstoque_2(){
//
//		// Cen�rio - onde as vari�veis ser�o inicializadas
//		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
//		// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0, 5.0);
//					
//		// A��o
//			try {
//			 service.alugarFilme(usuario, filme);
//			 Assert.fail("Deveria ter lan�ado uma exce��o");
//			 
//			 } catch(Exception e){	
//				 Assert.assertThat(e.getMessage(),is("Filme sem estoque."));	
//			 }
//					
//		}

//	//OP��O 2 - CHAMADA  DE FORMA ELEGANTE DE TRATAR ERROS
//
//	@Test(expected=Exception.class)
//	public void testLocacao_filmeSemEstoque() throws Exception {
//
//		// Cen�rio - onde as vari�veis ser�o inicializadas
//		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
//	// usuario, Filme filme)
//
//		// Instancie a classe que vc quer testar
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 5, 5.0);
//
//		// A��o
//
//		service.alugarFilme(usuario, filme);
//
//	}

	// OP��O 1) Uma forma de fazer erros ou fallhas aparecerem
//		Locacao locacao;
//		try {
//			locacao = service.alugarFilme(usuario, filme);
//			//Verifica��o
//			error.checkThat(locacao.getValor(),is(equalTo(5.0))); //Fazer o importe est�tico//Bot�o direito - sourse - "add import"
//			error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),is(true));		
//			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//			
//			
//		}catch (Exception e){
//			e.printStackTrace();
//			Assert.fail("N�o deveria lan�ar exce��o");
//			
//	     }

	// Verifica��o
	// Assert.assertEquals(5.0, locacao.getValor(), 0.01);
//		assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
//		assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

//		assertThat(locacao.getValor(),is(equalTo(5.0))); //Fazer o importe est�tico//Bot�o direito - sourse - "add import"
//		assertThat(locacao.getValor(),is(not(6.0))); //Verifique que o valor da loca��o n�o � 6
//		
//		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),is(true));		
//		assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//		
	// Precisa usar error.checkThat, no lugar de assertThat
	// Junto, l� em cima, @Rule public ErrorCollector error = new ErrorCollector();
	// error.checkThat(locacao.getValor(),is(not(6.0))); //Verifique que o valor da
	// loca��o n�o � 6
	
	
	
	
	
	
	
	
	
	
