package br.ce.wcaquino.servicos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.runners.ParallelRunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

//@RunWith(ParallelRunner.class)
public class CalculadoraServiceTest {
	
	static StringBuffer ordem = new StringBuffer();
	
	//TDD
	
	private CalculadoraService calculadora;
	
	@Before
	public void setup() {
		calculadora = new CalculadoraService();
		System.out.println("iniciando...");
		ordem.append("1");
	}
	
	@After
	public void teearDown() {
		System.out.println("finalizando...");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println(ordem.toString());
	}

	
	@Test
	public void deveSomarDoisValores() {
		
		//Cen�rio
		
		int a = 5;
		int b = 4;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//A��o
		
		int resultado = calculadora.somar(a,b);
		
		//Verifica��o
		Assert.assertEquals(9, resultado); 
		// Usou a vari�vel resultado, pois ela trar� o valor real.
		
		
	}
	

	@Test
	public void deveSubtrairDoisValores() {
		
		//Cen�rio
		
		int a = 5;
		int b = 4;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//A��o
		
		int resultado = calculadora.subtrair(a,b);
		
		
		
		//Verifica��o
		Assert.assertEquals(1, resultado); 
		// Usou a vari�vel resultado, pois ela trar� o valor real.
		
		
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		
		//Cen�rio
		
		int a = 4;
		int b = 2;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//A��o
		
		int resultado = calculadora.dividir(a,b);
		
		
		
		//Verifica��o
		Assert.assertEquals(2, resultado); 
		// Usou a vari�vel resultado, pois ela � que trar� o valor real.
	}
	
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		
		//Cen�rio
		
		int a = 10;
		int b = 0;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//A��o
		
		calculadora.dividir(a,b);
		
		//Verifica��o
		
	}
	
	

}
