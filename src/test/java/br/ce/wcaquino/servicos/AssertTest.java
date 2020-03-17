package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void test() {

		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals("Erro de comparação", 1, 1);

		
		Assert.assertEquals(0.51234, 0.512, 0.001); //Em caso de números boleanos
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);//Transformou o número em um Integer
		Assert.assertEquals(i, i2.intValue());//Transformou o objeto em um número
		Assert.assertNotEquals("bola", "casa");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("Bola"));

		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = u2;
		Usuario u4 = null;

		//** Colocar hashCode Equals na classe usuário.
		//A partir daí como o usuário do u2 e u1 é "usuário 1", enxerga como igual, se mudar o nome não enxerga mais.
		Assert.assertEquals(u1, u2);
		// Forma negativa
		Assert.assertNotEquals(u1, u2);

		Assert.assertSame(u2, u3); //Quando eu quero verificar se os objetos são da mesma instancia
		Assert.assertSame(u2, u2); //Quando eu quero verificar se os objetos são da mesma instancia
		//Forma negativa
		Assert.assertNotSame(u2, u2); //Para verificar se são iguais, mas de instancias diferentes.
		
		
		Assert.assertNull(u3);
		//Forma negativa 
		Assert.assertNotNull(u3);

		//Ou Assert.assertTrue(u4 == null);
		
		
		//Forma genérica
		Assert.assertNull(u3);

		
		
		
		


		
		
		
		

		
		
		
		
	}

}
