
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
	
	public void adicionarProcesso(Processo p) {
		this.processos.add(p);
	}
	
	public void finalizarProcesso(Processo p) {
		p.setStatus(Status.Finalizado);
	}
	
	public void removerProcesso(Processo p) {
		this.processos.remove(p);
	}
	
	public void tick(){
		this.tick += 1;
	}
	
	public int getTick(){
		return this.tick;
	}
	
	public void setTick(int tick) {
		this.tick = tick;
	}
	
	public String status() {
		String status = ("Tick: "+this.tick) + ", Quantum: " + this.quantum; 
		return status;
		
	}
	
	public int getQuantum() {
		return this.quantum;
	}
	
	public void incrementaQuantum() {
		this.quantum += 1;
	}
	
	public boolean quantumEstourado() {
		if(this.getTick() >= this.getQuantum()-1) {
			return true;
		}else {
			return false;
		}
	}
	
	public String toString(int tick) {
		String saida = "";
		if(this.tick == tick) {
			for(int i = 0; i < this.processos.size(); i++) {
				if(this.processos.get(i).getTickAtual() == tick) {
					saida = this.processos.get(i).getNome() + ": " + this.processos.get(i).getStatus()+"\n";
				}
			}
			
		}
		return saida;
	}	
}

