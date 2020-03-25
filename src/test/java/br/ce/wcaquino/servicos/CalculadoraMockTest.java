package br.ce.wcaquino.servicos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.ce.wcaquino.entidades.Locacao;
import junit.framework.Assert;

public class CalculadoraMockTest {
	
	@Mock
	private CalculadoraService calcMock;
	
	@Spy
	private CalculadoraService calcSpy;
	
	//@Spy
	private EmailService email;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void devoMostrarDiferencaEntreMockSpy() {
		
		Mockito.when(calcMock.somar(1, 2)).thenCallRealMethod();
		Mockito.when(calcSpy.somar(1, 2)).thenReturn(8);
		Mockito.doNothing().when(calcSpy).imprime();


		
		System.out.println(calcMock.somar(1, 5));
		System.out.println(calcSpy.somar(1, 5));
		
		System.out.println("Mock");
		calcMock.imprime();
		System.out.println("Spy");
		calcSpy.imprime();

		

		
	}
	@Test
	public void teste() {
		CalculadoraService calc = Mockito.mock(CalculadoraService.class);

		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 2));
		System.out.println(argCapt.getAllValues());//

	}
	

}
