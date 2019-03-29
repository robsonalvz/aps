package aps;

import java.util.List;
import java.util.ArrayList;

public class Escalonador {
	private int tick;
	private int quantum;
	private List<Processo> processos;
	private Status status;
	
	public Escalonador() {
		this.tick = 0;
		this.quantum = 5;
		this.processos = new ArrayList<>();
		this.status = Status.Nenhum_Processo;
	}
	
	public void adicionarProcesso(Processo processo) {
		this.processos.add(processo);
	}
	
	public void finalizarProcesso() {
		
	}
	
	public void tick(){
		this.tick+=1;
	}
	
	public int getTick() {
		return this.tick;
	}
	
	public String statusAtual() {
		Status p;
		if (this.processos.size() == 0) {
			p = this.status;
		}
		String status = ("Tick: "+this.tick) + ", Quantum: " + this.quantum;
		return status;
		
	}

	
}

