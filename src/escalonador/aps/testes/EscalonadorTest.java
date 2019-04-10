package escalonador.aps.testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import escalonador.aps.entities.Escalonador;
import escalonador.aps.entities.Processo;
import escalonador.aps.entities.Status;
import escalonador.aps.exceptions.SemPrioridadeException;

public class EscalonadorTest {

	private Escalonador escalonador;

	/**
	 * Inicialização de uma instancia do escalonador para todos os testes
	 */
	@Before
	public void init() {
		escalonador = new Escalonador();
	}
	/**
	 * Método para estourar o tick até o quantum
	 * @param quantum
	 */
	public void estourarQuantum(int quantum) {
		
		for(int i = 0; i < quantum; i++) {
			escalonador.tick();
		}
		escalonador.mudarStatus();

	}
	/**
	 * Teste 1 Escalonador vazio Status -> Nenhum processo Tick: 0 Quantim :
	 * default
	 */
	@Test
	public void testeEscalonadorVazio() {
		String status = escalonador.getStatus();
		assertEquals("Nenhum processo", status);
	}

	/**
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
		assertEquals("P1: Executando, Tick: 0, Quantum: 2\n", escalonador.getStatus());
		assertEquals(p.getStatus(), Status.Executando);

	}

	/**
	 * Teste finalizar processo e verificar se o escalonador esta vazio
	 */
	@Test
	public void testeFinalizarProcesso() {
		testeUmProcessoTickZero();
		Processo p = escalonador.getProcessoByName("P1");
		escalonador.finalizarProcesso(p);
		escalonador.tick();
		testeEscalonadorVazio();

	}

	/**
	 * Teste 4 A Partir do T3, Finalizar P1:
	 */
	@Test
	public void criarDoisProcessosNoTick() {

		// T5: Cria dois processos no mesmo Tick e roda;

		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P1: Executando, Tick: 1, Quantum: 2\n" + "P2: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Esperando, Tick: 2, Quantum: 2\n" + "P2: Executando, Tick: 2, Quantum: 2\n"
				+ "P1: Esperando, Tick: 3, Quantum: 2\n" + "P2: Executando, Tick: 3, Quantum: 2\n"
				+ "P1: Executando, Tick: 4, Quantum: 2\n" + "P2: Esperando, Tick: 4, Quantum: 2\n"
				+ "P1: Executando, Tick: 5, Quantum: 2\n" + "P2: Esperando, Tick: 5, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);

	}

	/**
	 * Teste 6 Cria tres processos no mesmo Tick e roda;
	 */
	@Test
	public void criarTresProcessosNoTick() {

		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		Processo p3 = new Processo("P3", 0);
		escalonador.adicionarProcesso(p3);

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P3: Esperando, Tick: 0, Quantum: 2\n" + "P1: Executando, Tick: 1, Quantum: 2\n"
				+ "P2: Esperando, Tick: 1, Quantum: 2\n" + "P3: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Esperando, Tick: 2, Quantum: 2\n" + "P2: Executando, Tick: 2, Quantum: 2\n"
				+ "P3: Esperando, Tick: 2, Quantum: 2\n" + "P1: Esperando, Tick: 3, Quantum: 2\n"
				+ "P2: Executando, Tick: 3, Quantum: 2\n" + "P3: Esperando, Tick: 3, Quantum: 2\n"
				+ "P1: Esperando, Tick: 4, Quantum: 2\n" + "P2: Esperando, Tick: 4, Quantum: 2\n"
				+ "P3: Executando, Tick: 4, Quantum: 2\n" + "P1: Esperando, Tick: 5, Quantum: 2\n"
				+ "P2: Esperando, Tick: 5, Quantum: 2\n" + "P3: Executando, Tick: 5, Quantum: 2\n" + "";

		assertEquals(escalonador.getStatus(), resultado);
	}

	/**
	 * Teste 8 com concorr�ncia o processo finaliza quando estava executando. E
	 * no proximo Tick o segundo processo passa para CPU;
	 */
	@Test
	public void comConcorrenciaProcessoFinalizaExecutando() {
		Processo p1 = new Processo("P1", Status.Executando, 0, 0);
		p1.setStatus(Status.Executando);
		Processo p2 = new Processo("P2", Status.Esperando, 0, 0);
		p1.setStatus(Status.Esperando);

		escalonador.adicionarProcesso(p1);
		escalonador.adicionarProcesso(p2);
		escalonador.tick();
		escalonador.finalizarProcesso(p1);
		escalonador.tick();

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P2: Executando, Tick: 1, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}

	/**
	 * Teste 9 Com concorrencia o processo finaliza quando estava esperando. E o
	 * primeiro processo nao perde a CPU;
	 */
	@Test
	public void comConcorrenciaProcessoFinalizaEsperando() {

		Processo p1 = new Processo("P1", Status.Executando, 0, 0);
		p1.setStatus(Status.Executando);
		Processo p2 = new Processo("P2", Status.Esperando, 0, 0);
		p1.setStatus(Status.Esperando);

		escalonador.adicionarProcesso(p1);
		escalonador.adicionarProcesso(p2);
		estourarQuantum(escalonador.getQuantum());
		escalonador.finalizarProcesso(p2);
		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P1: Executando, Tick: 1, Quantum: 2\n" + "P2: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Executando, Tick: 2, Quantum: 2\n" + "P1: Executando, Tick: 3, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}

	/**
	 * Teste 10 Cria dois processos no mesmo Tick e roda escolhendo o quantum;
	 */
	@Test
	public void criarDoisProcessosNoTickSemQuantumDefault() {

		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);

		escalonador.setQuantum(4);

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 4\n" + "P2: Esperando, Tick: 0, Quantum: 4\n"
				+ "P1: Executando, Tick: 1, Quantum: 4\n" + "P2: Esperando, Tick: 1, Quantum: 4\n"
				+ "P1: Executando, Tick: 2, Quantum: 4\n" + "P2: Esperando, Tick: 2, Quantum: 4\n"
				+ "P1: Executando, Tick: 3, Quantum: 4\n" + "P2: Esperando, Tick: 3, Quantum: 4\n"
				+ "P1: Esperando, Tick: 4, Quantum: 4\n" + "P2: Executando, Tick: 4, Quantum: 4\n"
				+ "P1: Esperando, Tick: 5, Quantum: 4\n" + "P2: Executando, Tick: 5, Quantum: 4\n"
				+ "P1: Esperando, Tick: 6, Quantum: 4\n" + "P2: Executando, Tick: 6, Quantum: 4\n"
				+ "P1: Esperando, Tick: 7, Quantum: 4\n" + "P2: Executando, Tick: 7, Quantum: 4\n"
				+ "P1: Executando, Tick: 8, Quantum: 4\n" + "P2: Esperando, Tick: 8, Quantum: 4\n"
				+ "P1: Executando, Tick: 9, Quantum: 4\n" + "P2: Esperando, Tick: 9, Quantum: 4\n"
				+ "P1: Executando, Tick: 10, Quantum: 4\n" + "P2: Esperando, Tick: 10, Quantum: 4\n"
				+ "P1: Executando, Tick: 11, Quantum: 4\n" + "P2: Esperando, Tick: 11, Quantum: 4\n";

		assertEquals(escalonador.getStatus(), resultado);

	}

	// T10 T5 com Quantum n�o Default

	// T11 Dois Processos com intervalo no meio:
	/**
	 * Teste 12 A partir de T6, o processo Executando Bloqueia:
	 */
	@Test
	public void processoExecutandoBloqueado() {

		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		Processo p3 = new Processo("P3", 0);
		escalonador.adicionarProcesso(p3);

		estourarQuantum(escalonador.getQuantum());

		escalonador.bloqueiaProcesso(p1);

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P3: Esperando, Tick: 0, Quantum: 2\n" + "P1: Executando, Tick: 1, Quantum: 2\n"
				+ "P2: Esperando, Tick: 1, Quantum: 2\n" + "P3: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Bloqueado, Tick: 2, Quantum: 2\n" + "P2: Executando, Tick: 2, Quantum: 2\n"
				+ "P3: Esperando, Tick: 2, Quantum: 2\n" + "P1: Bloqueado, Tick: 3, Quantum: 2\n"
				+ "P2: Executando, Tick: 3, Quantum: 2\n" + "P3: Esperando, Tick: 3, Quantum: 2\n"
				+ "P1: Bloqueado, Tick: 4, Quantum: 2\n" + "P2: Esperando, Tick: 4, Quantum: 2\n"
				+ "P3: Executando, Tick: 4, Quantum: 2\n" + "P1: Bloqueado, Tick: 5, Quantum: 2\n"
				+ "P2: Esperando, Tick: 5, Quantum: 2\n" + "P3: Executando, Tick: 5, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);

	}

	/**
	 * Teste 13 A partir de T12, P1 é retomado quando P2 esta executando:
	 */
	@Test
	public void desbloqueandoProcesso() {

		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		Processo p3 = new Processo("P3", 0);
		escalonador.adicionarProcesso(p3);

		escalonador.tick();

		escalonador.bloqueiaProcesso(p1);

		escalonador.tick();

		escalonador.desbloquearProcesso(p1);

		escalonador.tick();

		escalonador.mudarStatus();

		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P3: Esperando, Tick: 0, Quantum: 2\n" + "P1: Bloqueado, Tick: 1, Quantum: 2\n"
				+ "P2: Executando, Tick: 1, Quantum: 2\n" + "P3: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Esperando, Tick: 2, Quantum: 2\n" + "P2: Executando, Tick: 2, Quantum: 2\n"
				+ "P3: Esperando, Tick: 2, Quantum: 2\n" + "P1: Esperando, Tick: 3, Quantum: 2\n"
				+ "P2: Esperando, Tick: 3, Quantum: 2\n" + "P3: Executando, Tick: 3, Quantum: 2\n"
				+ "P1: Esperando, Tick: 4, Quantum: 2\n" + "P2: Esperando, Tick: 4, Quantum: 2\n"
				+ "P3: Executando, Tick: 4, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}

	/**
	 * Teste 14 Os Tres Processos Bloqueiam e retomam na ordem P2, P1, P3 e o
	 * quantum
	 */
	@Test
	public void bloqueandoProcessoeMudandoOrdem() {
		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		Processo p3 = new Processo("P3", 0);
		escalonador.adicionarProcesso(p3);

		escalonador.tick();
		escalonador.bloqueiaProcesso(p1);
		escalonador.bloqueiaProcesso(p2);
		escalonador.bloqueiaProcesso(p3);

		escalonador.trocarOrdemExecucao(p2, p1);
		escalonador.tick();
		escalonador.desbloquearProcesso(p1);
		escalonador.desbloquearProcesso(p2);
		escalonador.desbloquearProcesso(p3);
		estourarQuantum(escalonador.getQuantum());
		escalonador.tick();

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P3: Esperando, Tick: 0, Quantum: 2\n" + "P2: Bloqueado, Tick: 1, Quantum: 2\n"
				+ "P1: Bloqueado, Tick: 1, Quantum: 2\n" + "P3: Bloqueado, Tick: 1, Quantum: 2\n"
				+ "P2: Executando, Tick: 2, Quantum: 2\n" + "P1: Esperando, Tick: 2, Quantum: 2\n"
				+ "P3: Esperando, Tick: 2, Quantum: 2\n" + "P2: Executando, Tick: 3, Quantum: 2\n"
				+ "P1: Esperando, Tick: 3, Quantum: 2\n" + "P3: Esperando, Tick: 3, Quantum: 2\n"
				+ "P2: Esperando, Tick: 4, Quantum: 2\n" + "P1: Executando, Tick: 4, Quantum: 2\n"
				+ "P3: Esperando, Tick: 4, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}

	/**
	 * Teste escalonador com prioridade e processo sem prioridade
	 * 
	 * @throws SemPrioridadeException
	 */
	@Test(expected = SemPrioridadeException.class)
	public void testeExcecao() throws SemPrioridadeException {
		escalonador.setPrioridade(true);
		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcessoComPrioridade(p1);
	}

	@Test
	public void testeSemProcessoPrioridade() {
		try {
			testeExcecao();
		} catch (SemPrioridadeException e) {
			testeEscalonadorVazio();
		}
	}

	/**
	 * Teste 16 com prioridade com processo normal
	 */
	@Test
	public void testeComProcessoPrioridade() {
		escalonador.setPrioridade(true);
		Processo p1 = new Processo("P1", 0);
		p1.setPrioridade(3);
		escalonador.adicionarProcessoComPrioridade(p1);
		escalonador.tick();
		assertEquals("P1: Executando, Tick: 0, Quantum: 2\n", escalonador.getStatus());
		assertEquals(p1.getStatus(), Status.Executando);
	}

	/**
	 * 
	 * Teste 17 finalizando processe a partir do teste 16
	 */
	@Test
	public void testeFinalizarProcessoPrioridade() {
		testeComProcessoPrioridade();
		Processo p1 = escalonador.getProcessoByName("P1");
		escalonador.finalizarProcesso(p1);
		escalonador.tick();
		testeEscalonadorVazio();
	}

	/**
	 * Teste 18 com dois processos com prioridade 1
	 */
	@Test
	public void testeDoisProcessosPrioridade() {

		escalonador.setPrioridade(true);
		Processo p1 = new Processo("P1", 0, 1);
		escalonador.adicionarProcessoComPrioridade(p1);
		Processo p2 = new Processo("P2", 0, 1);
		escalonador.adicionarProcessoComPrioridade(p2);

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P1: Executando, Tick: 1, Quantum: 2\n" + "P2: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Esperando, Tick: 2, Quantum: 2\n" + "P2: Executando, Tick: 2, Quantum: 2\n"
				+ "P1: Esperando, Tick: 3, Quantum: 2\n" + "P2: Executando, Tick: 3, Quantum: 2\n"
				+ "P1: Executando, Tick: 4, Quantum: 2\n" + "P2: Esperando, Tick: 4, Quantum: 2\n"
				+ "P1: Executando, Tick: 5, Quantum: 2\n" + "P2: Esperando, Tick: 5, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}
	
	@Test
	public void testeComTresProcessosPrioridade(){

		Processo p1 = new Processo("P1", 0,1);
		escalonador.adicionarProcessoComPrioridade(p1);
		Processo p2 = new Processo("P2", 0,1);
		escalonador.adicionarProcessoComPrioridade(p2);
		Processo p3 = new Processo("P3", 0,1);
		escalonador.adicionarProcessoComPrioridade(p3);

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P3: Esperando, Tick: 0, Quantum: 2\n" + "P1: Executando, Tick: 1, Quantum: 2\n"
				+ "P2: Esperando, Tick: 1, Quantum: 2\n" + "P3: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Esperando, Tick: 2, Quantum: 2\n" + "P2: Executando, Tick: 2, Quantum: 2\n"
				+ "P3: Esperando, Tick: 2, Quantum: 2\n" + "P1: Esperando, Tick: 3, Quantum: 2\n"
				+ "P2: Executando, Tick: 3, Quantum: 2\n" + "P3: Esperando, Tick: 3, Quantum: 2\n"
				+ "P1: Esperando, Tick: 4, Quantum: 2\n" + "P2: Esperando, Tick: 4, Quantum: 2\n"
				+ "P3: Executando, Tick: 4, Quantum: 2\n" + "P1: Esperando, Tick: 5, Quantum: 2\n"
				+ "P2: Esperando, Tick: 5, Quantum: 2\n" + "P3: Executando, Tick: 5, Quantum: 2\n" + "";

		assertEquals(escalonador.getStatus(), resultado);
	}
}
