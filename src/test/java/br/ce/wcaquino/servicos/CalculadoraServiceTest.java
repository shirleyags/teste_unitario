package br.ce.wcaquino.servicos;

import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

import org.junit.Assert;
import org.junit.Before;

public class CalculadoraServiceTest {
	
	//TDD
	
	private CalculadoraService calculadora;
	
	@Before
	public void setup() {
		calculadora = new CalculadoraService();
	}
	

	
	@Test
	public void deveSomarDoisValores() {
		
		//Cen�rio
		
		int a = 5;
		int b = 4;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//Acao
		
		int resultado = calculadora.somar(a,b);
		
		
		
		//Verifica��o
		Assert.assertEquals(9, resultado); 
		// Usou a vari�vel resultado, pois ela � que trar� o valor real.
		
		
	}
	

	@Test
	public void deveSubtrairDoisValores() {
		
		//Cen�rio
		
		int a = 5;
		int b = 4;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//Acao
		
		int resultado = calculadora.subtrair(a,b);
		
		
		
		//Verifica��o
		Assert.assertEquals(1, resultado); 
		// Usou a vari�vel resultado, pois ela � que trar� o valor real.
		
		
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		
		//Cen�rio
		
		int a = 4;
		int b = 2;
		
		CalculadoraService calculadora = new CalculadoraService();
		
		//Acao
		
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
