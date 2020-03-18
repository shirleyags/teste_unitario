package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	
	
	//Definição do contador
	//private static int contador =0; // Quando coloco que é static
	//Passa para o escopo da classe, não do método/teste
	
	@Rule
	public ErrorCollector error = new ErrorCollector(); // Para que todos os erros apareçam no console, não um de cada

									// vez.

//	@Rule
//	public ExpectedException exception = ExpectedException.none(); // 4)Da forma nova

	// OPÇÃO 3 - FORMA ROBUSTA DE TRATAR EXCEÇÕES

	
	@Before
	public void setup() {
		service = new LocacaoService(); //Só o nome da classe que está declarada em cima.
	
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
	
	
	
	@Test()
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {

		// Cenário - onde as variáveis serão inicializadas
		// Inicializou abaixo essas duas variáveis public: Locacao alugarFilme(Usuario
		// usuario, Filme filme)

		// Instancie a classe que vc quer testar
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0)); 
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

}
