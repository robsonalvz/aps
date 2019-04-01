package escalonador.aps.testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import escalonador.aps.model.Escalonador;
import escalonador.aps.model.Processo;
import escalonador.aps.model.Status;

public class EscalonadorTest {
	
	private Escalonador escalonador;
	
	@Before
	public void criarEscalonador(){
		escalonador = new Escalonador();
	}

	/* Teste 1
	 * Escalonador vazio
	 * Status -> Nenhum processo
	 * Tick: 0
	 * Quantim : default
	 * */
	@Test
	public void testeEscalonadorVazio() {
		String status = escalonador.status();
		assertEquals("Tick: 0, Quantum: 5",status);
	}
	
	/* Teste 2
	 * Verificar se o tick estÃ¡ incrementando
	 */
	@Test
	public void testeIncrementaTick() {
		escalonador.tick();
		assertEquals(1,escalonador.getTick());
	}
	
	// T5 Criar dois Processos em Tick 0:
	
	@Test
	public void criarDoisProcessosNoTick() {
	
		/* criar dois processos no tick 0
		status -> p1 executando
		p2 - esperando
		tick-0
		quantum...
		Chamar o tick até estourar o quantum
		status - > p2 - executando
		p1-esperando
		tick:quantumDefault
		quantim....
		chamar o tick até estourar o quantum
		status-> p1 - executando
		p2 - esperando
		tick - quantum*2 */

		Processo p1 = new Processo();
		p1.setStatus(Status.Executando);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo();
		escalonador.adicionarProcesso(p2);
		for(int i = 0; i < escalonador.getQuantum() - 1; i ++ ) {
			escalonador.tick();
		}
		
		if(escalonador.quantumEstourado()) {
			p1.setStatus(Status.Esperando);
			p2.setStatus(Status.Executando);
			int tick = escalonador.getQuantum();
			escalonador.setTick(tick);
		}
		
		for(int i = 0; i < escalonador.getQuantum() - 1; i ++ ) {
			escalonador.tick();
		}
		
		if(escalonador.quantumEstourado()) {
			p1.setStatus(Status.Executando);
			p2.setStatus(Status.Esperando);
		}
		
	}
	
	// T3 Adicionar Processo P1 no Tick 0, Chamar o Tick e ver se P1 continua executando:
	
	// T4 A Partir do T3, Finalizar P1:
	
	
	
	// T6 Repetir T5 com 3 Processsos:
	
	// T7 Seguir o modelo de T5, mas P2 sï¿½ ï¿½ criado em Tick 3:
	
	// T8 Com concorrï¿½ncia, Processo finaliza quando estava executando. No prï¿½ximo Tick o segundo Processo passa a CPU:
	
	// T9 Com concorrï¿½ncia, Processo finaliza quando estava esperando. Quando o Quantum estourar o primeiro Processo nï¿½o perde a CPU:
	
	// T10 T5 com Quantum nï¿½o Default
	
	// T11 Dois Processos com intervalo no meio:
	
	// T12 A partir de T6, o processo Executando Bloqueia:
	
	// T13 A partir de T12, P1 ï¿½ retomado quando P2 estï¿½ executando:
	
	// T14 Os Trï¿½s Processos Bloqueiam e retomam na ordem P2, P1, P3 e o quantum funciona nesta nova ordem:

}
