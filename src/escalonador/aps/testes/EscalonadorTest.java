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
		System.out.println(escalonador.getStatus());
		assertEquals("Nenhum processo", status);
	}

	/*
	 * Teste 2 Verificar se o tick est√° incrementando
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
		assertEquals("P1: Executando, Tick: 0, Quantum: 5\n", escalonador.getStatus());
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
		
		Processo p1 = new Processo("P1", Status.Executando, 0, 0);
		p1.setStatus(Status.Executando);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", Status.Esperando, 0, 0);
		escalonador.adicionarProcesso(p2);
		
		for(int i = 0; i < escalonador.getQuantum() - 1; i ++ ) {
			escalonador.tick();
		}
		
		if (escalonador.quantumEstourado()) {
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
		
		String resultado1 = p1.getNome() + ": " + p1.getStatus();
		String resultado2 = p2.getNome() + ": " + p2.getStatus();
		
		assertEquals(escalonador.getSaida(p1) + "\n" + escalonador.getSaida(p2), resultado1 + "\n" + resultado2);
	}
	
	@Test
	public void criarTresProcessosNoTick() {
		
		// T6: Cria tres processos no mesmo Tick e roda;
		
		Processo p1 = new Processo("P1", Status.Executando, 0, 0);
		p1.setStatus(Status.Executando);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", Status.Esperando, 0, 0);
		escalonador.adicionarProcesso(p2);
		Processo p3 = new Processo("P2", Status.Esperando, 0, 0);
		escalonador.adicionarProcesso(p3);
		
		for(int i = 0; i < escalonador.getQuantum() - 1; i ++ ) {
			escalonador.tick();
		}
		
		if (escalonador.quantumEstourado()) {
			p1.setStatus(Status.Esperando);
			p2.setStatus(Status.Executando);
			p3.setStatus(Status.Esperando);
			int tick = escalonador.getQuantum();
			escalonador.setTick(tick);
		}
		
		for(int i = 0; i < escalonador.getQuantum() - 1; i ++ ) {
			escalonador.tick();
		}
		
		if (escalonador.quantumEstourado()) {
			p1.setStatus(Status.Esperando);
			p2.setStatus(Status.Esperando);
			p3.setStatus(Status.Executando);
			int tick = escalonador.getQuantum() * 2;
			escalonador.setTick(tick);
		}
		
		for(int i = 0; i < (escalonador.getQuantum() - 1) * 2; i ++ ) {
			escalonador.tick();
		}
		
		if(escalonador.quantumEstourado()) {
			p1.setStatus(Status.Executando);
			p2.setStatus(Status.Esperando);
			p3.setStatus(Status.Esperando);
		}
		
		String resultado1 = p1.getNome() + ": " + p1.getStatus();
		String resultado2 = p2.getNome() + ": " + p2.getStatus();
		String resultado3 = p3.getNome() + ": " + p3.getStatus();
		
		assertEquals(escalonador.getSaida(p1) + "\n" + escalonador.getSaida(p2) + "\n" 
		+ escalonador.getSaida(p3), resultado1 + "\n" + resultado2 + "\n" + resultado3);
	}
	
	@Test
	public void comConcorrenciaProcessoFinaliza() {
		
		// T8: Com concorrÍncia o processo finaliza quando estava executando. E no prÛximo Tick o segundo processo passa para CPU; 
		
		Processo p1 = new Processo("P1", Status.Executando, 0, 0);
		p1.setStatus(Status.Executando);
		Processo p2 = new Processo("P2", Status.Esperando, 0, 0);
		p1.setStatus(Status.Esperando);
		
		escalonador.adicionarProcesso(p1);
		escalonador.adicionarProcesso(p2);
		escalonador.finalizarProcesso(p1);
		
		escalonador.tick();
		p2.setTickAtual(escalonador.getTick());
		
		p2.setStatus(Status.Executando);
		
		String resultado1 = p1.getNome() + ": " + p1.getStatus();
		String resultado2 = p2.getNome() + ": " + p2.getStatus();
	
		assertEquals(escalonador.getSaida(p1) + "\n" + escalonador.getSaida(p2), resultado1 + "\n" + resultado2);
		
	}


	// T7 Seguir o modelo de T5, mas P2 sÔøΩ ÔøΩ criado em Tick 3:

	// T8 Com concorrÔøΩncia, Processo finaliza quando estava executando. No
	// prÔøΩximo Tick o segundo Processo passa a CPU:

	// T9 Com concorrÔøΩncia, Processo finaliza quando estava esperando. Quando o
	// Quantum estourar o primeiro Processo nÔøΩo perde a CPU:

	// T10 T5 com Quantum nÔøΩo Default

	// T11 Dois Processos com intervalo no meio:

	// T12 A partir de T6, o processo Executando Bloqueia:

	// T13 A partir de T12, P1 ÔøΩ retomado quando P2 estÔøΩ executando:

	// T14 Os TrÔøΩs Processos Bloqueiam e retomam na ordem P2, P1, P3 e o quantum
	// funciona nesta nova ordem:

}
