package aps;

import static org.junit.Assert.*;

import org.junit.Test;

public class EscalonadorTest {

	@Test
	
	// T1 Escalonador Vazio:
	public void T1() {
		Escalonador escal = new Escalonador();
		String status = escal.Status();
		System.out.println(status);
	}
	
	// T2 Chamar o Tick e ver se ele incrementa:
	public void T2() {
		Escalonador escal = new Escalonador();
		int valTick = escal.Tick();
		System.out.println(valTick);
	}
	
	// T3 Adicionar Processo P1 no Tick 0, Chamar o Tick e ver se P1 continua executando:
	
	// T4 A Partir do T3, Finalizar P1:
	
	// T5 Criar dois Processos em Tick 0:
	
	// T6 Repetir T5 com 3 Processsos:
	
	// T7 Seguir o modelo de T5, mas P2 s� � criado em Tick 3:
	
	// T8 Com concorr�ncia, Processo finaliza quando estava executando. No pr�ximo Tick o segundo Processo passa a CPU:
	
	// T9 Com concorr�ncia, Processo finaliza quando estava esperando. Quando o Quantum estourar o primeiro Processo n�o perde a CPU:
	
	// T10 T5 com Quantum n�o Default
	
	// T11 Dois Processos com intervalo no meio:
	
	// T12 A partir de T6, o processo Executando Bloqueia:
	
	// T13 A partir de T12, P1 � retomado quando P2 est� executando:
	
	// T14 Os Tr�s Processos Bloqueiam e retomam na ordem P2, P1, P3 e o quantum funciona nesta nova ordem:

}
