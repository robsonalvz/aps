package escalonador.aps.testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import escalonador.aps.entities.Escalonador;
import escalonador.aps.entities.EscalonadorComPrioridade;
import escalonador.aps.entities.Processo;
import escalonador.aps.entities.Status;
import escalonador.aps.exceptions.ComPrioridadeExcepetion;
import escalonador.aps.exceptions.SemPrioridadeException;

public class EscalonadorTest {

	private Escalonador escalonador;
	private EscalonadorComPrioridade escalonadorP;

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
	public void teste1() {
		String status = escalonador.getStatus();
		assertEquals("Nenhum processo\n", status);
	}

	/**
	 * Teste 2 Verificar se o tick está incrementando
	 */
	@Test
	public void teste2() {
		escalonador.tick();
		assertEquals(1, escalonador.getTick());
	}

	/*
	 * Teste 3 Adicionar Processo P1 no Tick 0, Chamar o Tick e ver se P1
	 * continua executando:
	 */
	@Test
	public void teste3() {
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
		teste3();
		Processo p = escalonador.getProcessoByName("P1");
		escalonador.finalizarProcesso(p);
		escalonador.tick();
		teste1();

	}

	/**
	 * Teste 4 A Partir do T3, Finalizar P1:
	 */
	@Test
	public void teste4() {

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
	
	@Test
	public void teste5() {
		/**
		 * Teste 5 criar dois processos no tick 0; 
		 */
		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		
		escalonador.setQuantum(1);
		
		estourarQuantum(escalonador.getQuantum());
		estourarQuantum(escalonador.getQuantum());
		estourarQuantum(escalonador.getQuantum());
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 1\n" + 
							"P2: Esperando, Tick: 0, Quantum: 1\n" + 
							"P1: Esperando, Tick: 1, Quantum: 1\n" + 
							"P2: Executando, Tick: 1, Quantum: 1\n" +
							"P1: Executando, Tick: 2, Quantum: 1\n" + 
							"P2: Esperando, Tick: 2, Quantum: 1\n";
		
		assertEquals(escalonador.getStatus(), resultado);
	}
	

	@Test
	public void teste6() {
		/**
		 * Teste 6 Cria tres processos no mesmo Tick e roda;
		 */
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
	 * Teste 7, parecido com o T5 mas p2 s� � criado no tick 3
	 */
	
	@Test
	public void teste7() {
		Processo p1 = new Processo("P1", 0);
		Processo p2 = new Processo("P2", 3);
		
		
		escalonador.adicionarProcesso(p1);
		escalonador.setQuantum(1);
		estourarQuantum(escalonador.getQuantum());
		estourarQuantum(escalonador.getQuantum());
		estourarQuantum(escalonador.getQuantum());
		escalonador.adicionarProcesso(p2);
		estourarQuantum(escalonador.getQuantum());
		
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 1\n" + 
							"P1: Executando, Tick: 1, Quantum: 1\n" + 
							"P1: Executando, Tick: 2, Quantum: 1\n" + 
							"P1: Executando, Tick: 3, Quantum: 1\n" +
							"P2: Esperando, Tick: 3, Quantum: 1\n";
							
		
		assertEquals(escalonador.getStatus(), resultado);
		
	}
	

	/**
	 * Teste 8 com concorr�ncia o processo finaliza quando estava executando. E
	 * no proximo Tick o segundo processo passa para CPU;
	 */
	@Test
	public void teste8() {
		Processo p1 = new Processo("P1", 0);
		Processo p2 = new Processo("P2",0);

		escalonador.adicionarProcesso(p1);
		escalonador.adicionarProcesso(p2);
		escalonador.tick();
		escalonador.finalizarProcesso(p1);
		escalonador.tick();
		

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P2: Executando, Tick: 1, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}
	
	@Test
	public void teste9() {
		Processo p1 = new Processo("P1", 0);
		Processo p2 = new Processo("P2", 0);
		
		escalonador.adicionarProcesso(p1);
		escalonador.adicionarProcesso(p2);
		escalonador.tick();
		escalonador.finalizarProcesso(p2);
		escalonador.tick();
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" +
				"P2: Esperando, Tick: 0, Quantum: 2\n" + 
				"P1: Executando, Tick: 1, Quantum: 2\n";
		
		assertEquals(escalonador.getStatus(), resultado);
	}
	

	/**
	 * Teste 10 Cria dois processos no mesmo Tick e roda escolhendo o quantum;
	 */
	@Test
	public void teste10() {

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



	// T11 Dois Processos com intervalo no meio:
	
	@Test
	public void teste11() {
		Processo p1 = new Processo("P1", 0);
		escalonador.adicionarProcesso(p1);
		escalonador.tick();
		Processo p2 = new Processo("P2", 0);
		escalonador.adicionarProcesso(p2);
		escalonador.tick();
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + 
				"P1: Executando, Tick: 1, Quantum: 2\n" + 
				"P2: Esperando, Tick: 1, Quantum: 2\n";
		
		
		assertEquals(escalonador.getStatus(), resultado);
		
		
	}
	
	
	
	/**
	 * Teste 12 A partir de T6, o processo Executando Bloqueia:
	 */
	@Test
	public void teste12() {

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
	public void teste13() {

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
	public void teste14() {
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
			teste1();
		}
	}

	/**
	 * Teste 16 com prioridade com processo normal
	 */
	@Test
	public void teste16() {
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
	public void teste17() {
		teste16();
		Processo p1 = escalonador.getProcessoByName("P1");
		escalonador.finalizarProcesso(p1);
		escalonador.tick();
		teste1();
	}

	/**
	 * Teste 18 com dois processos com prioridade 1
	 */
	@Test
	public void teste18() {

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
	

	/**
	 * Teste 20, criar dois processos em quantum separado utilizando prioridade
	 */
	@Test
	public void teste20() {
		Processo p1 = new Processo("P1", 0, 1);
		Processo p2 = new Processo("P2", 3, 1);
		
		escalonador.adicionarProcessoComPrioridade(p1);
		escalonador.tickS();
		escalonador.tickS();
		escalonador.tickS();
		escalonador.tickS();
		escalonador.adicionarProcessoComPrioridade(p2);
		escalonador.ordenaPorPrioridade();
		escalonador.tickS();
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" 
				+ "P1: Executando, Tick: 1, Quantum: 2\n" 
				+ "P1: Executando, Tick: 2, Quantum: 2\n"
				+ "P1: Executando, Tick: 3, Quantum: 2\n"
				+ "P2: Esperando, Tick: 3, Quantum: 2\n";
		
		assertEquals(escalonador.getStatus(), resultado);
	}

	
	
	/**
	 * Teste 21 com concorr�ncia o processo finaliza quando estava executando. E
	 * no proximo Tick o segundo processo passa para CPU, com prioridade;
	 */
	@Test
	public void teste21() {
		Processo p1 = new Processo("P1", 0, 1);
		p1.setStatus(Status.Executando);
		Processo p2 = new Processo("P2", 0, 1);
		p2.setStatus(Status.Esperando);

		escalonador.adicionarProcessoComPrioridade(p1);
		escalonador.adicionarProcessoComPrioridade(p2);
		escalonador.tick();
		escalonador.finalizarProcesso(p1);
		escalonador.tick();
		

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P2: Executando, Tick: 1, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}
	
	/**
	 * Teste 22 Com concorrencia o processo finaliza quando estava esperando. E o
	 * primeiro processo nao perde a CPU, com prioridade;
	 */
	
	@Test
	public void teste22() {
		Processo p1 = new Processo("P1",0,1);
		p1.setStatus(Status.Executando);
		Processo p2 = new Processo("P2",0,1);
		p2.setStatus(Status.Esperando);

		escalonador.adicionarProcessoComPrioridade(p1);
		escalonador.adicionarProcessoComPrioridade(p2);
		estourarQuantum(escalonador.getQuantum());
		escalonador.finalizarProcesso(p2);
		estourarQuantum(escalonador.getQuantum());

		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P2: Esperando, Tick: 0, Quantum: 2\n"
				+ "P1: Executando, Tick: 1, Quantum: 2\n" + "P2: Esperando, Tick: 1, Quantum: 2\n"
				+ "P1: Executando, Tick: 2, Quantum: 2\n" + "P1: Executando, Tick: 3, Quantum: 2\n";

		assertEquals(escalonador.getStatus(), resultado);
	}
	
	/**
	 * Teste 23 esse teste cria dois processos no mesmo tick e sem quantum default.
	 */
	
	@Test
	public void teste23() {

		Processo p1 = new Processo("P1", 0,1);
		escalonador.adicionarProcessoComPrioridade(p1);
		Processo p2 = new Processo("P2", 0,1);
		escalonador.adicionarProcessoComPrioridade(p2);
		
		escalonador.ordenaPorPrioridade();
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
	
	/**
	 * Teste 24 criar processo com prioridade e com intervalo
	 */
	@Test
	public void teste24() {
		Processo p1 = new Processo("P1", 0,1);
		escalonador.adicionarProcessoComPrioridade(p1);
		escalonador.tick();
		escalonador.finalizarProcesso(p1);
		
		assertEquals(escalonador.getStatus(), "Nenhum processo\n");
		
		Processo p2 = new Processo("P2", 0,2);
		escalonador.adicionarProcessoComPrioridade(p2);
		escalonador.tick();
		String resultado = "Nenhum processo\n" + 
				"P2: Executando, Tick: 1, Quantum: 2\n";
		
		assertEquals(escalonador.getStatus(), resultado);
	}
	/**
	 * Teste 25 A partir de T6, o processo Executando Bloqueia, com prioridade.
	 */
	@Test
	public void teste25() {
		Processo p1 = new Processo("P1", 0, 1);
		escalonador.adicionarProcessoComPrioridade(p1);
		Processo p2 = new Processo("P2", 0, 2);
		escalonador.adicionarProcessoComPrioridade(p2);
		Processo p3 = new Processo("P3", 0, 3);
		escalonador.adicionarProcessoComPrioridade(p3);

		escalonador.ordenaPorPrioridade();
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
	
	@Test
	
	/**
	 * Teste 26 consiste em decloquear o processo, com prioridade.
	 */
	public void teste26() {

		Processo p1 = new Processo("P1", 0,1);
		escalonador.adicionarProcessoComPrioridade(p1);
		Processo p2 = new Processo("P2", 0,2);
		escalonador.adicionarProcessoComPrioridade(p2);
		Processo p3 = new Processo("P3", 0,3);
		escalonador.adicionarProcessoComPrioridade(p3);

		escalonador.ordenaPorPrioridade();
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
	 * Teste 27 bloqueia os processos e retorna na ordem P2,P1,P3
	 */
	
	@Test
	public void teste27() {

		Processo p1 = new Processo("P1", 0, 1);
		escalonador.adicionarProcessoComPrioridade(p1);;
		Processo p2 = new Processo("P2", 0, 2);
		escalonador.adicionarProcessoComPrioridade(p2);;
		Processo p3 = new Processo("P3", 0, 3);
		escalonador.adicionarProcessoComPrioridade(p3);;

		escalonador.ordenaPorPrioridade();
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

	// Teste 28 A partir de T 16 3 Ticks
	
	@Test
	public void teste28() {
		escalonador.setPrioridade(true);
		Processo p1 = new Processo("P1", 0);
		p1.setPrioridade(3);
		escalonador.adicionarProcessoComPrioridade(p1);
		escalonador.tick();
		escalonador.tick();
		escalonador.tick();
		Processo p2 = new Processo("P2", 0);
		p2.setPrioridade(1);
		escalonador.adicionarProcessoComPrioridade(p2);
		estourarQuantum(10);
		
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n" + "P1: Executando, Tick: 1, Quantum: 2\n"
				+ "P1: Esperando, Tick: 2, Quantum: 2\n" +"P2: Executando, Tick: 2, Quantum: 2\n";
		//assertEquals(escalonador.getStatus(), resultado);
		System.out.println(escalonador.getStatus());
	}
	
	@Test
	public void testComBloqueioProcesso(){
		Processo p1 = new Processo("P1",0,1);
		escalonador.adicionarProcessoComPrioridade(p1);
		Processo p2 = new Processo("P2",0,2);
		escalonador.adicionarProcessoComPrioridade(p2);
		escalonador.ordenaPorPrioridade();
		escalonador.tick();
		String resultado = ("P1: Executando, Tick: 0, Quantum: 2\n"+
					"P2: Esperando, Tick: 0, Quantum: 2\n"+
					"P1: Bloqueado, Tick: 1, Quantum: 2\n"+
					"P2: Executando, Tick: 1, Quantum: 2\n");
		escalonador.bloqueiaProcesso(p1);
		escalonador.tick();
		
		assertEquals(resultado, escalonador.getStatus());
		
		
	}
	/**
	 * Teste 30, a partir de 29 desbloquear p1
	 */
	@Test
	public void teste30(){
		testComBloqueioProcesso();
		Processo p1 = escalonador.getProcessoByName("P1");
		escalonador.tick();
		escalonador.desbloquearProcesso(p1);
		escalonador.tick();
		String resultado = "P1: Executando, Tick: 0, Quantum: 2\n"+
							"P2: Esperando, Tick: 0, Quantum: 2\n"+
							"P1: Bloqueado, Tick: 1, Quantum: 2\n"+
							"P2: Executando, Tick: 1, Quantum: 2\n"+
							"P1: Bloqueado, Tick: 2, Quantum: 2\n"+
							"P2: Executando, Tick: 2, Quantum: 2\n"+
							"P1: Esperando, Tick: 3, Quantum: 2\n"+
							"P2: Executando, Tick: 3, Quantum: 2\n";
		assertEquals(resultado, escalonador.getStatus());
	}
	
	
	/**
	 * Testar exception ao colocar um processo com prioridade no escalonador sem prioridade
	 */
	@Test(expected=ComPrioridadeExcepetion.class)
	public void testaExcecaoSemPrioridade() throws ComPrioridadeExcepetion{
		Processo p1 = new Processo("P1",0,1);
		
		escalonador.setPrioridade(false);
		escalonador.adicionarProcesso(p1);
	}

}
