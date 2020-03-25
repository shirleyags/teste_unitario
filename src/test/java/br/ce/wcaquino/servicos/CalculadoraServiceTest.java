package br.ce.wcaquino.servicos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.runners.ParallelRunner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

@RunWith(ParallelRunner.class)
public class CalculadoraServiceTest {
	
	//TDD
	
	private CalculadoraService calculadora;
	
	@Before
	public void setup() {
		calculadora = new CalculadoraService();
		System.out.println("iniciando...");
	}
	
	@After
	public void teearDown() {
		System.out.println("finalizando...");
	}
	
	@Test
	public void deveSomarDoisValores() {
		
		//Cenário
		
		int a = 5;
		int b = 4;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//Ação
		
		int resultado = calculadora.somar(a,b);
		
		//Verificação
		Assert.assertEquals(9, resultado); 
		// Usou a variável resultado, pois ela trará o valor real.
		
		
	}
	

	@Test
	public void deveSubtrairDoisValores() {
		
		//Cenário
		
		int a = 5;
		int b = 4;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//Ação
		
		int resultado = calculadora.subtrair(a,b);
		
		
		
		//Verificação
		Assert.assertEquals(1, resultado); 
		// Usou a variável resultado, pois ela trará o valor real.
		
		
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		
		//Cenário
		
		int a = 4;
		int b = 2;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//Ação
		
		int resultado = calculadora.dividir(a,b);
		
		
		
		//Verificação
		Assert.assertEquals(2, resultado); 
		// Usou a variável resultado, pois ela é que trará o valor real.
	}
	
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		
		//Cenário
		
		int a = 10;
		int b = 0;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//Ação
		
		calculadora.dividir(a,b);
		
		//Verificação
		
	}
	
	

}
