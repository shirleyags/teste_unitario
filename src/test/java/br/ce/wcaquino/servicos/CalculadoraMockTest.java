package br.ce.wcaquino.servicos;

import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoraMockTest {
	
	@Test
	public void teste() {
		
		CalculadoraService calc = Mockito.mock(CalculadoraService.class);
		
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		System.out.println(calc.somar(1, 2));
	}
	

}
