package br.ce.wcaquino.servicos;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import br.ce.wcaquino.entidades.Locacao;
import junit.framework.Assert;

public class CalculadoraMockTest {
	
	@Test
	public void teste() {
		CalculadoraService calc = Mockito.mock(CalculadoraService.class);

		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 2));
		System.out.println(argCapt.getAllValues());

	}
	

}
