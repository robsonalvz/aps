package aps;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EscalonadorTest {

	/*Teste com 3 processos*/
	@Test
	public void testeTresProcessos() {
		Escalonador escalonador = new Escalonador(5);
		escalonador.addProcesso("P1",0,10);
		escalonador.addProcesso("P2",0,3);
		escalonador.addProcesso("P3",3,3);
		String tabela = escalonador.getTabelaRR();
		System.out.println(tabela);
		String tabelaTest = "RRRRRWWWWWWRRRRF\n"+
		                    "WWWWWRRRF\n"+
				            "IIIWWWWWRRRF\n";
		assertEquals(tabela,tabelaTest);
	}

	/*Teste com 2 processos*/
	@Test
	public void testeDoisProcessosComTempoInexistente() {
		Escalonador escalonador = new Escalonador(3);
		escalonador.addProcesso("P4",0,3);
		escalonador.addProcesso("P5",5,2);
		String tabela = escalonador.getTabelaRR();
		String tabelaTest = "RRRF\n"
				+ "IIIIIRRF\n";
		
		assertEquals(tabela,tabelaTest);
		
	}
	/*Teste com processos desordenados com quantum 2*/
	@Test
	public void testeQuatroProcessosDesordenados(){
		Escalonador escalonador = new Escalonador(2);
		escalonador.addProcesso("P1",1,2);
		escalonador.addProcesso("P2",0,1);
		escalonador.addProcesso("P3",5,5);	
		escalonador.addProcesso("P4",4,1);
		String tabela = escalonador.getTabelaRR();
		String tabelaTest = "IRRF\n"
				+ "RF\n"
				+ "IIIIIRRRRRF\n"
				+ "IIIIRF\n";
		System.out.println(tabela);
		assertEquals(tabela,tabelaTest);
	}
	@Test
	public void testeQuantumZero(){
		Escalonador escalonador = new Escalonador(0);
		escalonador.addProcesso("P1",0,2);
		assertEquals(0,escalonador.getQuantum());		
	}
	@Test
	public void testeQuantumNegativo(){
		Escalonador escalonador = new Escalonador(-1);
		escalonador.addProcesso("P1",0,2);
		assertEquals(-1,escalonador.getQuantum());		
	}
	@Test
	public void testeProcessosSeguidos(){
		Escalonador escalonador = new Escalonador(2);
		escalonador.addProcesso("P1",0,2);
		escalonador.addProcesso("P2",2, 2);
		
		String tabela = escalonador.getTabelaRR();
		String tabelaTest = "RRF\n"
				+ "IIRRF\n";
		assertEquals(tabela,tabelaTest);
		
	}
	@Test
	public void testeQuatroProcessosQuatumTres() {
		
		Escalonador escalonador = new Escalonador(3);
		escalonador.addProcesso("P1", 0, 3);
		escalonador.addProcesso("P2", 0, 4);
		escalonador.addProcesso("P3", 1, 2);
		escalonador.addProcesso("P4", 2, 7);
		
		String tabela = escalonador.getTabelaRR();
		String tabelaTest = "RRRF\n"
				+ "WWWRRRWWWWWRF\n"
				+ "IWWWWWRRF\n"
				+"IIWWWWWWRRRWRRRRF\n";
		assertEquals(tabela,tabelaTest);
	}
	@Test
	public void testeComUmProcesso() {
		Escalonador escalonador = new Escalonador(3);
		escalonador.addProcesso("P1", 0, 5);
		
		String tabela = escalonador.getTabelaRR();
		System.out.println(tabela);
		String tabelaTest = "RRRRRF";
		
		assertEquals(tabela,tabelaTest);
		
}
	

}