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
	public void criarEscalonador() {
		escalonador = new Escalonador();
	}

	/*
	 * Teste 1 Escalonador vazio Status -> Nenhum processo Tick: 0 Quantim :
	 * default
	 */
	@Test
	public void testeEscalonadorVazio() {
		String status = escalonador.getStatus();
		assertEquals("Nenhum processo", status);
	}

	/*
	 * Teste 2 Verificar se o tick está incrementando
	 */
	@Test
	public void testeIncrementaTick() {
		escalonador.tick();
		assertEquals(1, escalonador.getTick());
	}

	/*
	 * Teste 3 Adicionar Processo P1 no Tick 0, Chamar o Tick e ver se P1
	 * continua executando:
	 */
	@Test
	public void testeUmProcessoTickZero() {
		Processo p = new Processo("P1", escalonador.getTick());
		escalonador.adicionarProcesso(p);
		escalonador.tick();
		System.out.println(escalonador.getStatus());
		assertEquals("P1: Executando, Tick: 0, Quantum: 2\n", escalonador.getStatus());
		assertEquals(p.getStatus(), Status.Executando);

	}
	
	@Test
	public void testeFinalizarProcesso(){
		testeUmProcessoTickZero();
		Processo p = escalonador.getProcessoByName("P1");
		escalonador.finalizarProcesso(p);
		escalonador.tick();
		testeEscalonadorVazio();
		
	}
	
	
	
	// T4 A Partir do T3, Finalizar P1:

	@Test
	public void criarDoisProcessosNoTick() {

		// T5: Cria dois processos no mesmo Tick e roda;
		
		Processo p1 = new Processo("P1",0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		
		escalonador.estourarQuantum(escalonador.getQuantum());
		
		escalonador.estourarQuantum(escalonador.getQuantum());
		
		escalonador.estourarQuantum(escalonador.getQuantum());
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + 
				"P2: Esperando, Tick: 0, Quantum: 2\n" + 
				"P1: Executando, Tick: 1, Quantum: 2\n" + 
				"P2: Esperando, Tick: 1, Quantum: 2\n" + 
				"P1: Esperando, Tick: 2, Quantum: 2\n" + 
				"P2: Executando, Tick: 2, Quantum: 2\n" + 
				"P1: Esperando, Tick: 3, Quantum: 2\n" + 
				"P2: Executando, Tick: 3, Quantum: 2\n" + 
				"P1: Executando, Tick: 4, Quantum: 2\n" + 
				"P2: Esperando, Tick: 4, Quantum: 2\n" + 
				"P1: Executando, Tick: 5, Quantum: 2\n" + 
				"P2: Esperando, Tick: 5, Quantum: 2\n";
		
		assertEquals(escalonador.getStatus(), resultado);
		
	}
	
	@Test
	public void criarTresProcessosNoTick() {
		
		// T6: Cria tres processos no mesmo Tick e roda;
		
		Processo p1 = new Processo("P1",0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		Processo p3 = new Processo("P3", 0);
		escalonador.adicionarProcesso(p3);
		
		escalonador.estourarQuantum(escalonador.getQuantum());
		
		escalonador.estourarQuantum(escalonador.getQuantum());
		
		escalonador.estourarQuantum(escalonador.getQuantum());
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + 
				"P2: Esperando, Tick: 0, Quantum: 2\n" + 
				"P3: Esperando, Tick: 0, Quantum: 2\n" + 
				"P1: Executando, Tick: 1, Quantum: 2\n" + 
				"P2: Esperando, Tick: 1, Quantum: 2\n" + 
				"P3: Esperando, Tick: 1, Quantum: 2\n" + 
				"P1: Esperando, Tick: 2, Quantum: 2\n" + 
				"P2: Esperando, Tick: 2, Quantum: 2\n" + 
				"P3: Executando, Tick: 2, Quantum: 2\n" + 
				"P1: Esperando, Tick: 3, Quantum: 2\n" + 
				"P2: Esperando, Tick: 3, Quantum: 2\n" + 
				"P3: Executando, Tick: 3, Quantum: 2\n" + 
				"P1: Esperando, Tick: 4, Quantum: 2\n" + 
				"P2: Executando, Tick: 4, Quantum: 2\n" + 
				"P3: Esperando, Tick: 4, Quantum: 2\n" + 
				"P1: Esperando, Tick: 5, Quantum: 2\n" + 
				"P2: Executando, Tick: 5, Quantum: 2\n" + 
				"P3: Esperando, Tick: 5, Quantum: 2\n";
		
		assertEquals(escalonador.getStatus(), resultado);
	}
	
	@Test
	public void comConcorrenciaProcessoFinalizaExecutando() {
		
		// T8: Com concorr�ncia o processo finaliza quando estava executando. E no pr�ximo Tick o segundo processo passa para CPU; 
		
		Processo p1 = new Processo("P1", Status.Executando, 0, 0);
		p1.setStatus(Status.Executando);
		Processo p2 = new Processo("P2", Status.Esperando, 0, 0);
		p1.setStatus(Status.Esperando);
		
		escalonador.adicionarProcesso(p1);
		escalonador.adicionarProcesso(p2);
		escalonador.tick();
		escalonador.finalizarProcesso(p1);
		escalonador.tick();
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + 
				"P2: Esperando, Tick: 0, Quantum: 2\n" + 
				"P2: Executando, Tick: 1, Quantum: 2\n";
		
		assertEquals(escalonador.getStatus(), resultado);
	}
	@Test
	public void comConcorrenciaProcessoFinalizaEsperando() {
		
		// T9: Com concorr�ncia o processo finaliza quando estava esperando. E o primeiro processo n�o perde a CPU; 
		
		Processo p1 = new Processo("P1", Status.Executando, 0, 0);
		p1.setStatus(Status.Executando);
		Processo p2 = new Processo("P2", Status.Esperando, 0, 0);
		p1.setStatus(Status.Esperando);
		
		escalonador.adicionarProcesso(p1);
		escalonador.adicionarProcesso(p2);
		escalonador.estourarQuantum(escalonador.getQuantum());
		escalonador.finalizarProcesso(p2);
		escalonador.estourarQuantum(escalonador.getQuantum());
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + 
				"P2: Esperando, Tick: 0, Quantum: 2\n" + 
				"P2: Executando, Tick: 1, Quantum: 2\n";
		
		//assertEquals(escalonador.getStatus(), resultado);
	}

	// T9 Com concorr�ncia, Processo finaliza quando estava esperando. Quando o
	// Quantum estourar o primeiro Processo n�o perde a CPU:

	// T10 T5 com Quantum n�o Default

	// T11 Dois Processos com intervalo no meio:

	// T12 A partir de T6, o processo Executando Bloqueia:

	// T13 A partir de T12, P1 � retomado quando P2 est� executando:

	// T14 Os Tr�s Processos Bloqueiam e retomam na ordem P2, P1, P3 e o quantum
	// funciona nesta nova ordem:

}
