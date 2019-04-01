
package escalonador.aps.model;

import java.util.List;
import java.util.ArrayList;

public class Escalonador {
	private int tick;
	private int quantum;
	private List<Processo> processos;
	
	public Escalonador() {
		this.tick = 0;
		this.quantum = 5;
		this.processos = new ArrayList<>();
	}

	public void adicionarProcesso(Processo processo) {
		this.processos.add(processo);
	}

	public void finalizarProcesso(Processo processo) {
		processo.setStatus(Status.Finalizado);
	}

	public void removerProcesso(Processo processo) {
		this.processos.remove(processo);
	}

	public void tick() {
		this.tick += 1;
	}

	public int getTick() {
		return this.tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	public String status() {
		String status = ("Tick: " + this.tick) + ", Quantum: " + this.quantum;
		return status;

	}
	public List<Processo> listaEscalonador;
	
	public void rodar(){
		int duracao = tick;
		for (int i=0;i<=duracao;i++){
			for (Processo processo : this.processos){
				
			}
		}
	}

}
