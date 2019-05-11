
package escalonador.aps.entities;

import java.util.ArrayList;
import java.util.List;

import escalonador.aps.exceptions.ComPrioridadeExcepetion;
import escalonador.aps.exceptions.SemPrioridadeException;

public class Escalonador {
	private int tick;
	private int quantum;
	private List<Processo> processos;
	private String status;
	private boolean prioridade;
	private int cont = 0;
	
	public Escalonador() {
		this.tick = 0;
		this.quantum = 2;
		this.status = "";
		this.processos = new ArrayList<>();
	}
	public boolean isPrioridade() {
		return this.prioridade;
	}
	public void setPrioridade(boolean prioridade) {
		this.prioridade = prioridade;
	}
	
	
	public void adicionarProcesso(Processo processo) throws ComPrioridadeExcepetion {
		if (processo.getPrioridade()==-1 && this.prioridade==false){
			this.processos.add(processo);
		}else{
			throw new ComPrioridadeExcepetion("Processo com prioridade adicionado ao escalonador sem prioridade");
		}
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
			this.status = "Nenhum processo\n";
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
				
			if(processo.getStatus().equals(Status.Bloqueado)) {
				this.addStatus(processo);
			}else {
				if (escalonadorLivre(processo)) {
					processo.setStatus(Status.Executando);
					addStatus(processo);
				} else {
					processo.setStatus(Status.Esperando);
					addStatus(processo);	
				}
			}
				
			}
	}
	
	public void tick() {
		rodar();
		this.tick += 1;
	}
	
	public void tickS() {
		ordenaPorPrioridade();
		if(this.cont == this.quantum) {
			this.cont = this.cont % this.quantum;
			this.mudarStatus();
			
		}else {
			rodar();
			this.tick += 1;
			this.cont += 1;
		}
	}
	
	public void tickComPrioridade() {
		
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
	}
	
	public void adicionarProcessoComPrioridade(Processo processo) throws SemPrioridadeException {
		
		if (processo.getPrioridade()==-1) {
				throw new SemPrioridadeException("Processo adicionado está sem prioridade.");
			}
		
		this.processos.add(processo);
	}
	
	
	public void ordenaPorPrioridade() {
		int cont = 0;
		List<Processo> lista = new ArrayList<Processo>();	
		while(cont < this.processos.size()){
			Processo po = this.processos.get(0);
			for(Processo p : this.processos) {
				if(p.getPrioridade() < po.getPrioridade()) {
					po = p;
				}
			}
			lista.add(po);
			this.processos.remove(po);
			cont += 1;		
		}
		lista.add(processos.get(0));
		this.processos = lista;
	}
	
	public void desbloquearProcesso(Processo p) {
		for(Processo processo : this.processos) {
			if(processo.getNome().equals(p.getNome())) {
				this.removerProcesso(processo);
				this.adicionarProcesso(p);
				break;
			}
		}
		
		p.setStatus(Status.Esperando);
	}
	
	public void trocarOrdemExecucao(Processo in, Processo out) {
		
		int indexIn = this.processos.indexOf(in);
		int indexOut = this.processos.indexOf(out);
		
		this.processos.set(indexOut, in);
		this.processos.set(indexIn, out);
	}
	
	
	public void exibirPrioridade() {
		for(Processo p : this.processos) {
			System.out.println(p.getNome());
		}
	}

}
