package escalonador.aps.testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import escalonador.aps.exceptions.SemPrioridadeException;
import escalonador.aps.model.Escalonador;
import escalonador.aps.model.Processo;

public class EscalonadorComPrioridadeTest {


	private Escalonador escalonador;

	@Before
	public void criarEscalonador() {
		escalonador = new Escalonador();
	}
	
	@Test(expected = SemPrioridadeException.class)
	public void testeExcecao() throws SemPrioridadeException {
		escalonador.setPrioridade(true);
		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcessoComPrioridade(p1);

	}
}
