package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//Inicializa os testes em ordem alfab�tica, caso a sequ�ncia/nomes dos
//m�todos n�o respeitem a ordem alfab�tica
//coloque alguma letra no in�cio do nome do m�todo.
public class OrdemTest {
	
	private static int contador = 0;
	
	@Test
	public void inicia() {
		contador = 1;
	}
	
	@Test
	public void verifica() {
		Assert.assertEquals(1, contador);
	}
	
	//Colocando um contador e o resto que se refere a ele, � poss�vel
	//ver se qual o m�todo de teste que inicializou primeiro.
	
}
