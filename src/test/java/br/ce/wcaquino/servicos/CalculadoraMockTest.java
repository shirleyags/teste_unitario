package br.ce.wcaquino.servicos;

import org.junit.Test;
import org.mockito.Mockito;

import junit.framework.Assert;

public class CalculadoraMockTest {
	
	@Test
	public void teste() {
		
		CalculadoraService calc = Mockito.mock(CalculadoraService.class);
		
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 2));
	}
	

}
