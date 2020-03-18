package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	
	private LocacaoService service; //Para o before e o after funcionar para todos os testes dessa classe
	
	
	//Defini��o do contador
	//private static int contador =0; // Quando coloco que � static
	//Passa para o escopo da classe, n�o do m�todo/teste
	
	@Rule
	public ErrorCollector error = new ErrorCollector(); // Para que todos os erros apare�am no console, n�o um de cada

									// vez.

//	@Rule
//	public ExpectedException exception = ExpectedException.none(); // 4)Da forma nova

	// OP��O 3 - FORMA ROBUSTA DE TRATAR EXCE��ES

	
	@Before
	public void setup() {
		service = new LocacaoService(); //S� o nome da classe que est� declarada em cima.
	
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
	
	
	
	@Test()
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {

		// Cen�rio - onde as vari�veis ser�o inicializadas
		// Inicializou abaixo essas duas vari�veis public: Locacao alugarFilme(Usuario
		// usuario, Filme filme)

		// Instancie a classe que vc quer testar
		Filme filme = new Filme("Filme 1", 2, 5.0);
		//System.out.println("Teste!");



		// A��o
			try {
				service.alugarFilme(null, filme);
				Assert.fail();
			} catch (LocadoraException e) {
				Assert.assertThat(e.getMessage(),is("Usu�rio n�o existente"));
			}
	}

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

}