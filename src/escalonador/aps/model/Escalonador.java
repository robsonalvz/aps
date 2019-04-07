
package escalonador.aps.model;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class Escalonador {
	private int tick;
	private int quantum;
	private List<Processo> processos;
	private String status;

	public Escalonador() {
		this.tick = 0;
		this.quantum = 2;
		this.status = "";
		this.processos = new ArrayList<>();
	}

	public void adicionarProcesso(Processo processo) {
		this.processos.add(processo);
	}

	public void finalizarProcesso(Processo processo) {
		processo.setStatus(Status.Finalizado);
		removerProcesso(processo);
	}

	public void removerProcesso(Processo processo) {
		this.processos.remove(processo);
	}


	public int getTick() {
		return this.tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	public String getStatus() {
		if (this.processos.size() == 0) {
			this.status = "Nenhum processo";
		}
		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}
	public Processo getProcessoByName(String nome){
		for (Processo p : this.processos){
			if (p.getNome().equals(nome))return p;
		}
		return null;
	}
	public int getQuantum() {
		return this.quantum;
	}


	public boolean quantumEstourado() {
		if (this.getTick() >= this.getQuantum() - 1) {
			return true;
		} else {
			return false;
		}
	}

	public List<Processo> listaEscalonador;



	public void addStatus(Processo processo) {
		this.status += processo.getNome() + ": " + processo.getStatus().toString() + ", " + ("Tick: " + this.tick)
				+ (", Quantum: " + this.getQuantum()) + "\n";
	}

	public String getSaida(Processo processo) {
		return processo.getNome() + ": " + processo.getStatus(); 
	}
	
	public boolean escalonadorLivre(Processo processo) {
		for (Processo p : this.processos) {
			if (p.getNome() != processo.getNome() && p.getStatus().equals(Status.Executando)) {
					return false;
			}
		}
		return true;
		
	}
	
	public void rodar() {
		for (Processo processo : this.processos) {
				if (escalonadorLivre(processo)) {
					processo.setStatus(Status.Executando);
					addStatus(processo);
				} else {
					processo.setStatus(Status.Esperando);
					addStatus(processo);	
				}
			}
	}
	
	public void tick() {
		rodar();
		this.tick += 1;
	}
	
	public void estourarQuantum(int quantum) {
	
		for(int i = 0; i < quantum; i++) {
			tick();
		}
		mudarStatus();

	}
	
	public void mudarStatus() {
		if(quantumEstourado()) {
			for(Processo processo : this.processos) {
				
					if(processo.getStatus().equals(Status.Executando)) {
							processo.setStatus(Status.Esperando);	
					}else {
						if(this.escalonadorLivre(processo)) {
							processo.setStatus(Status.Executando);
						}				
						
					}
				
				}
			}	
	}
	
	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}
	public void bloqueiaProcesso(Processo p) {
		p.setStatus(Status.Bloqueado);
		this.addStatus(p);
		this.removerProcesso(p);
	}

}
