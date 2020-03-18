package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//Inicializa os testes em ordem alfabética, caso a sequência/nomes dos
//métodos não respeitem a ordem alfabética
//coloque alguma letra no início do nome do método.
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
	
	//Colocando um contador e o resto que se refere a ele, é possível
	//ver se qual o método de teste que inicializou primeiro.
	
}
